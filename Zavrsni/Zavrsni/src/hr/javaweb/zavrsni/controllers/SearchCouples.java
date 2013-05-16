package hr.javaweb.zavrsni.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.ws.Response;

import hr.javaweb.zavrsni.model.Bet;
import hr.javaweb.zavrsni.model.Bookie;
import hr.javaweb.zavrsni.model.Matches;
import hr.javaweb.zavrsni.service.ServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zenkey.net.prowser.Prowser;
import com.zenkey.net.prowser.Request;
import com.zenkey.net.prowser.Tab;

@Controller
public class SearchCouples {

	@Autowired
	private ServiceImplementation service;

	// metoda za download podataka s interneta na url-u pod nazivon tennis_url u lakaciju na disku pod nazivom _locatin_of_file 
	public void downloadFile(String tennis_url, String location_of_file) {

		try {
			URL url = new URL(tennis_url);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					location_of_file));

			String line;
			while ((line = reader.readLine()) != null) {
				line = line.replaceAll("^\\s+", "");		// mjenja (izbacuje) sve whitespaces od poèetka linije do nekog znaka 
				if (line.length() > 0) {					// ako je linija prazna nemoj je ispisati, izbacuje sve prazne linije radi bržeg pretraživanja
					writer.write(line);
					writer.newLine();
				}

			}

			reader.close();
			writer.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
			// Èitanje svih podataka u listu Bet-ova iz HTML datoteke koja je spremljena na disk na lokaciji loaction_of_file, samo za Betfair kladionicu
	public List<Bet> betfairTennis(String location_of_file) {

		List<Bet> list = new ArrayList<Bet>();
		String vrijeme;
		Bet bet = null;

		try {

			BufferedReader bufRead = new BufferedReader(new FileReader(
					location_of_file));

			String line = bufRead.readLine();

			while (line != null) {

				if (line.matches("<h[1-6] class=\"competition-header open\">")) {
					line = bufRead.readLine();
					if (!line.contains("Coming")) {
						vrijeme = line;
						while (line != null) {

							if (("<span class=\"home-team\">").equals(line)) {
								bet = new Bet();
								bet.setPlaying_date(vrijeme);
								line = bufRead.readLine();
								bet.setFirst_player(line);
							}

							if (("<span class=\"away-team\">").equals(line)) {
								line = bufRead.readLine();
								bet.setSecond_player(line);
							}

							if (line.contains("<span class=\"start-time \">")) {
								Pattern koef = Pattern
										.compile("<span class=\"start-time \">(.*)</span>");
								Matcher matcher = koef.matcher(line);
								if (matcher.matches()) {
									bet.setPlaying_time(matcher.group(1));
								}
							}

							if (line.equals("<td class=\"odds back selection-1\">")) {  // možda bi bilo pametnije da sam išao na flag-ove umjesto tri puta pisati line = bufRead.readLine();
								line = bufRead.readLine();
								line = bufRead.readLine();
								line = bufRead.readLine();
								if(line.equals("</span>")){
									line = "1.0";
								}
								bet.setFirst_player_odd(Float.valueOf(
										line.trim()).floatValue());
							}

							if (line.equals("<td class=\"odds back selection-2\">")) {
								line = bufRead.readLine();
								line = bufRead.readLine();
								line = bufRead.readLine();
								if(line.equals("</span>")){
									line = "1.0";
								}
								bet.setSecond_player_odd(Float.valueOf(
										line.trim()).floatValue());
								list.add(bet);
							}

							if (line.contains("class=\"competition-date-heading")) { // ako doðe do još jednog podnaslova (headera) s datumom igranja, onda izaði iz unutarnje petlje
								break;
							}

							line = bufRead.readLine();
						}
					}
				}

				line = bufRead.readLine();
			}
			bufRead.close();

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	// Èitanje svih podataka u listu Bet-ova iz HTML datoteke koja je spremljena na disk na lokaciji loaction_of_file, samo za Bluesq kladionicu
	public List<Bet> bluesqTennis(String location_of_file) {

		List<Bet> list = new ArrayList<Bet>();
		String vrijeme = null;
		Bet bet = null;
		float prvi = 0, drugi = 1, rez, prvi_2 = 0, drugi_2 = 1, rez_2;

		try {

			BufferedReader bufRead = new BufferedReader(new FileReader(
					location_of_file));

			String line = bufRead.readLine();

			while (line != null) {

				if (line.contains("<td class=\"TdOutlineBeginRow\" style=\"padding-left:12px; font-weight:bold\"><img src=\"/bluesquare/sportsbook/interface/images/clock_small.gif\" width=\"17\" height=\"17\" align=\"absbottom\">")) {

					Pattern datum = Pattern
							.compile("<td class=\"TdOutlineBeginRow\" style=\"padding-left:12px; font-weight:bold\"><img src=\"/bluesquare/sportsbook/interface/images/clock_small.gif\" width=\"17\" height=\"17\" align=\"absbottom\">&nbsp;&nbsp;(.*)</td>");
					Matcher matcher = datum.matcher(line);
					if (matcher.matches()) {
						vrijeme = matcher.group(1);
					}
					while (line != null) {

						if (line.contains("<td width=\"40\" class=\"MediumTextGrey\" style=\"padding-left:8px\">")) {
							bet = new Bet();
							bet.setPlaying_date(vrijeme);
							Pattern vr = Pattern
									.compile("<td width=\"40\" class=\"MediumTextGrey\" style=\"padding-left:8px\">(.*)</td>");
							Matcher matcher1 = vr.matcher(line);
							if (matcher1.matches()) {
								bet.setPlaying_time(matcher1.group(1));
							}
						}

						if (line.contains("<td width=\"239\" align=\"right\" style=\"padding-left:8px\">")) {
							Pattern player1_name = Pattern
									.compile("<td width=\"239\" align=\"right\" style=\"padding-left:8px\">(.*)</td>");
							Matcher matcher2 = player1_name.matcher(line);
							if (matcher2.matches()) {
								bet.setFirst_player(matcher2.group(1));
							}
							line = bufRead.readLine();
							if(line.contains("evens")){   //evens je izraz se koristi kada je koeficijent u decimalnom obliku 2.0, a u fractions 1/1
								line= line.replaceAll("evens", "1/1");
							}
							Pattern player1_koef_part1 = Pattern
									.compile("<td width=\"50\" align=\"center\"><b>(.*)/[0-9]{1,}</b></td>"); // zbog prikaza podataka u fractions-ima ( npr. 3/4 ili 9/2) potrebno je èitati svaku znamenku posebno
							Matcher matcher3_part1 = player1_koef_part1                                       // te meðusobno podjelit ta dva broja i dodati im jedan i došli smo u decimalni oblik
									.matcher(line);
							if (matcher3_part1.matches()) {
								
								prvi = Float.valueOf(
										matcher3_part1.group(1).trim())
										.floatValue();
							}
							Pattern player1_koef_part2 = Pattern
									.compile("<td width=\"50\" align=\"center\"><b>[0-9]{1,}/(.*)</b></td>");
							Matcher matcher3_part2 = player1_koef_part2
									.matcher(line);
							if (matcher3_part2.matches()) {
								drugi = Float.valueOf(
										matcher3_part2.group(1).trim())
										.floatValue();
							}
							rez = prvi / drugi + 1;
							bet.setFirst_player_odd(rez);

							line = bufRead.readLine();
							line = bufRead.readLine();
							
							
							Pattern player2_name = Pattern
									.compile("<td width=\"239\" align=\"right\" style=\"padding-left:8px\">(.*)</td>");
							Matcher matcher4 = player2_name.matcher(line);
							if (matcher4.matches()) {
								bet.setSecond_player(matcher4.group(1));
							}
							line = bufRead.readLine();
							
							if(line.contains("evens")){ 
								line= line.replaceAll("evens", "1/1");
							}
							Pattern player2_koef_part1 = Pattern
									.compile("<td width=\"50\" align=\"center\"><b>(.*)/[0-9]{1,}</b></td>");
							Matcher matcher5_part1 = player2_koef_part1
									.matcher(line);
							if (matcher5_part1.matches()) {
								prvi_2 = Float.valueOf(
										matcher5_part1.group(1).trim())
										.floatValue();
							}
							Pattern player2_koef_part2 = Pattern
									.compile("<td width=\"50\" align=\"center\"><b>[0-9]{1,}/(.*)</b></td>");
							Matcher matcher5_part2 = player2_koef_part2
									.matcher(line);
							if (matcher5_part2.matches()) {
								drugi_2 = Float.valueOf(
										matcher5_part2.group(1).trim())
										.floatValue();
							}
							rez_2 = prvi_2 / drugi_2 + 1;
							bet.setSecond_player_odd(rez_2);
							list.add(bet);
						}

						if (line.contains("<!------Date / time table------->")) {
							break;
						}

						line = bufRead.readLine();
					}

				}

				line = bufRead.readLine();
			}
			bufRead.close();

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	// Èitanje svih podataka u listu Bet-ova iz HTML datoteke koja je spremljena na disk na lokaciji loaction_of_file, samo za Ladbrokes kladionicu
	public List<Bet> ladbrokesTennis(String location_of_file) {

		List<Bet> list = new ArrayList<Bet>();
		Bet bet = null;
		int i = 0;

		try {

			BufferedReader bufRead = new BufferedReader(new FileReader(
					location_of_file));

			String line = bufRead.readLine();

			while (line != null) {
				if(line.equals("<!-- selection loop -->")){
					break;
				}

				if (line.contains("class=\"eventTime\"")) {
					bet = new Bet();
					
				while(line != null){
					
					if (line.contains("<div class=\"eventTime\">")) {

						if (line.equals("<div class=\"eventTime\"></div>")) {  // Ako se unutar div-a ne nalazi nikakva oznaka vremena, znaèi da je danas, pa se koristi current day
							Calendar cal =Calendar.getInstance();
							String year =  Integer.toString(cal.get(Calendar.YEAR));           
							String month = Integer.toString(cal.get(Calendar.MONTH));
							String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH)); 
							bet.setPlaying_date(year+" "+month+" "+day);
							//bet.setPlaying_date("Today");
							line = bufRead.readLine();
							Pattern vrijeme1 = Pattern
									.compile("<a class=\"live_time\" href=\"/hr-hr/[\\p{Punct}a-z0-9A-Z]*\" title=\"Dostupni koeficijenti uÅ¾ivo\">(.*)</a>"); // ž u regexp implementiran na zanimljiv naèin
							Matcher matcher1 = vrijeme1.matcher(line);
							if (matcher1.matches()) {
								
								bet.setPlaying_time(matcher1.group(1));
							}

						} else {
										
							Pattern datum = Pattern
									.compile("<div class=\"eventTime\">(.*).");
							Matcher matcher1 = datum.matcher(line);
							if (matcher1.matches()) {
								bet.setPlaying_date(matcher1.group(1));
							}
							line = bufRead.readLine();
							line = bufRead.readLine();
							Pattern vrijeme2 = Pattern
									.compile("<a class=\"live_time\" href=\"/hr-hr/[\\p{Punct}a-z0-9A-Z]*\" title=\"Dostupni koeficijenti uÅ¾ivo\">(.*)</a>");
							Matcher matcher2 = vrijeme2.matcher(line);
							if (matcher2.matches()) {
								
								bet.setPlaying_time(matcher2.group(1));
							}

						}
					}

					if (line.contains("<div id=\"eventTime")) {

						Pattern datum1 = Pattern
								.compile("<div id=\"eventTime_[0-9]{9}_[0-9]{10}\" class=\"eventTime\" title=\"(.*) @ (.*)\">[\\p{Punct}A-Za-z0-9]*");
						Matcher matcher4 = datum1.matcher(line);
						if (matcher4.matches()) {
							bet.setPlaying_date(matcher4.group(1));
							bet.setPlaying_time(matcher4.group(2));
						}
					}

				if (line.contains("class=\"eventLink\"")) {
					Pattern couple = Pattern
							.compile("<a id=\"eventLink_[0-9]{9}_[0-9]{10}\" class=\"eventLink\" href=\"/hr-hr/tenis/[\\p{Punct}a-z0-9A-Z]*\">(.*) - (.*)</a>");
					Matcher matcher3 = couple.matcher(line);
					if (matcher3.matches()) {
						bet.setFirst_player((matcher3.group(1)));
						bet.setSecond_player(matcher3.group(2));
					}
				}

				if (line.equals(">")) {										
					line = bufRead.readLine();
					if (i == 0) {							// Korištenje flaga i da bi se znalo koji je koeficijent od prvog igraèa, a koji je od drugog
						bet.setFirst_player_odd(Float.valueOf(line.trim())
								.floatValue());
						i++;
					} else if (i == 1) {
						bet.setSecond_player_odd(Float.valueOf(line.trim()).floatValue());
						i = 0;
						list.add(bet);
						break;
				}
					}
				line = bufRead.readLine();
				}
				}

				line = bufRead.readLine();
			}

			bufRead.close();

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	List <Matches> adapt_for_compare_betfair(List<Bet> bet, List<Bookie> bookie_list){
		
		List<Matches> matches_list = new ArrayList<Matches>();
		
		Matches matches = null;
		
		for(Bet b: bet){
			matches = new Matches();
			matches.setBookie_for_first(bookie_list.get(0));
			matches.setBookie_for_second(bookie_list.get(0));
			matches.setFirst_odd(b.getFirst_player_odd());
			matches.setSecond_odd(b.getSecond_player_odd());
			
			b.setFirst_player_odd(((b.getFirst_player_odd()-1)*0.95f)+1);
			b.setSecond_player_odd(((b.getSecond_player_odd()-1)*0.95f)+1);
			float num = 100*(((b.getFirst_player_odd()*b.getSecond_player_odd())/(b.getFirst_player_odd()+b.getSecond_player_odd()))-1);
			matches.setRate((Math.round(num*1000.0f)/1000.0f));

			if(!((b.getPlaying_time())==null)){
			String str = b.getPlaying_time()+":00";
			matches.setTimes(Time.valueOf(str));
			}else{
				Calendar calendar = new GregorianCalendar();
				  int hour = calendar.get(Calendar.HOUR);
				  int minute = calendar.get(Calendar.MINUTE);
				  int second = calendar.get(Calendar.SECOND);
				  String hour1 = Integer.toString(hour);
				  String minute1 = Integer.toString(minute);
				  String second1 = Integer.toString(second);
				  matches.setTimes(Time.valueOf(hour1+":"+minute1+":"+second1));
			}
			if((b.getFirst_player().contains("/"))&&(b.getFirst_player().contains(" "))){
				Pattern couple1 = Pattern.compile("[a-zA-Z]* (.*)/(.*)");
				Matcher matcher1 = couple1.matcher(b.getFirst_player());
				if (matcher1.matches()) {
					b.setFirst_player(matcher1.group(1)+"/"+matcher1.group(2));
				}
				Pattern couple2 = Pattern.compile("[a-zA-Z]* [a-zA-Z]* (.*)/(.*)");
				Matcher matcher2 = couple2.matcher(b.getFirst_player());
				if (matcher2.matches()) {
					b.setFirst_player(matcher2.group(1)+"/"+matcher1.group(2));
				}
			
				Pattern couple3 = Pattern.compile("(.*)/[a-zA-Z]* (.*)");
				Matcher matcher3 = couple3.matcher(b.getFirst_player());
				if (matcher3.matches()) {
					b.setFirst_player(matcher3.group(1)+"/"+matcher3.group(2));
				}
				Pattern couple4 = Pattern.compile("(.*)/[a-zA-Z]* [a-zA-Z]* (.*)");
				Matcher matcher4 = couple4.matcher(b.getFirst_player());
				if (matcher4.matches()) {
					b.setFirst_player(matcher4.group(1)+"/"+matcher4.group(2));	
				}
				Pattern couple5 = Pattern.compile("[a-zA-Z]* (.*)/[a-zA-Z]* (.*)");
				Matcher matcher5 = couple5.matcher(b.getFirst_player());
				if (matcher5.matches()) {
					b.setFirst_player(matcher5.group(1)+"/"+matcher5.group(2));
				}
			}else if(b.getSecond_player().contains("/")&&(b.getSecond_player().contains(" "))){
				
					Pattern couple1 = Pattern.compile("[a-zA-Z]* (.*)/(.*)");
					Matcher matcher1 = couple1.matcher(b.getSecond_player());
					if (matcher1.matches()) {
						b.setSecond_player(matcher1.group(1)+"/"+matcher1.group(2));
					}
					Pattern couple2 = Pattern.compile("[a-zA-Z]* [a-zA-Z]* (.*)/(.*)");
					Matcher matcher2 = couple2.matcher(b.getSecond_player());
					if (matcher2.matches()) {
						b.setSecond_player(matcher2.group(1)+"/"+matcher1.group(2));
					}
				
					Pattern couple3 = Pattern.compile("(.*)/[a-zA-Z]* (.*)");
					Matcher matcher3 = couple3.matcher(b.getSecond_player());
					if (matcher3.matches()) {
						b.setSecond_player(matcher3.group(1)+"/"+matcher3.group(2));
					}
					Pattern couple4 = Pattern.compile("(.*)/[a-zA-Z]* [a-zA-Z]* (.*)");
					Matcher matcher4 = couple4.matcher(b.getSecond_player());
					if (matcher4.matches()) {
						b.setSecond_player(matcher4.group(1)+"/"+matcher4.group(2));	
					}
					Pattern couple5 = Pattern.compile("[a-zA-Z]* (.*)/[a-zA-Z]* (.*)");
					Matcher matcher5 = couple5.matcher(b.getSecond_player());
					if (matcher5.matches()) {
						b.setSecond_player(matcher5.group(1)+"/"+matcher5.group(2));
					}
			}
			
				matches.setCouple(b.getFirst_player()+" - "+b.getSecond_player());	
			
			Calendar rightnow = Calendar.getInstance();
			if(b.getPlaying_date().equals("Today")){
				java.util.Date date = new java.util.Date();
				date = rightnow.getTime();
				long t = date.getTime();
				java.sql.Date dt = new java.sql.Date(t);
				matches.setDates(dt);	
			}
			else if(b.getPlaying_date().equals("Tomorrow")){
				rightnow.add(Calendar.DATE, 1);
				java.util.Date date = new java.util.Date();
				date = rightnow.getTime();
				long t = date.getTime();
				java.sql.Date dt = new java.sql.Date(t);
				matches.setDates(dt);
				}
				else{
				Pattern vri5 = Pattern.compile("[a-zA-Z]*, (.*) (.*) (.*)");
				Matcher matcher6 = vri5.matcher(b.getPlaying_date());
				if (matcher6.matches()) {
					int monthString = 0;
				
					if(matcher6.group(2).equals("January")) {monthString = 0;}
					if(matcher6.group(2).equals("February")) {monthString = 1;}         
					if(matcher6.group(2).equals("March")) {monthString = 2;} 
					if(matcher6.group(2).equals("April")) {monthString = 3;}
					if(matcher6.group(2).equals("May")) {monthString = 4;}
					if(matcher6.group(2).equals("June")) {monthString = 5;}
					if(matcher6.group(2).equals("July")) {monthString = 6;}
					if(matcher6.group(2).equals("August")) {monthString = 7;}
					if(matcher6.group(2).equals("September")) {monthString = 8;}
					if(matcher6.group(2).equals("October")) {monthString = 9;}
					if(matcher6.group(2).equals("November")) {monthString = 10;}
					if(matcher6.group(2).equals("December")) {monthString = 11;}
					
					
					rightnow.set(Integer.parseInt(matcher6.group(3)), monthString, Integer.parseInt(matcher6.group(1)));
					java.util.Date date = new java.util.Date();
					date = rightnow.getTime();
					long t = date.getTime();
					java.sql.Date dt = new java.sql.Date(t);
					matches.setDates(dt);
				}
				
			}
			if(matches.getCouple() != null){
				matches_list.add(matches);
			}
			
		}
	return matches_list;
	}
	
	
	
List <Matches> adapt_for_compare_bluesq(List<Bet> bet, List<Bookie> bookie_list){
		
		List<Matches> matches_list = new ArrayList<Matches>();
		
		Matches matches = null;
		
		for(Bet b: bet){
			matches = new Matches();
			matches.setBookie_for_first(bookie_list.get(2));
			matches.setBookie_for_second(bookie_list.get(2));
			matches.setFirst_odd(Math.round(b.getFirst_player_odd()*100.0f)/100.0f);
			matches.setSecond_odd(Math.round(b.getSecond_player_odd()*100.0f)/100.0f);
			
			float num = 100*(((b.getFirst_player_odd()*b.getSecond_player_odd())/(b.getFirst_player_odd()+b.getSecond_player_odd()))-1);
			matches.setRate((Math.round(num*1000.0f)/1000.0f));

			String str = b.getPlaying_time()+":00";
			matches.setTimes(Time.valueOf(str));
			
			if((b.getFirst_player().contains("/"))&&(b.getFirst_player().contains(" "))){
				Pattern couple1 = Pattern.compile("(.*) [a-zA-Z]* / (.*) [a-zA-Z]*");
				Matcher matcher1 = couple1.matcher(b.getFirst_player());
				if (matcher1.matches()) {
					b.setFirst_player(matcher1.group(1)+"/"+matcher1.group(2));
				}
			}else if(b.getSecond_player().contains("/")&&(b.getSecond_player().contains(" "))){
				
					Pattern couple2 = Pattern.compile("(.*) [a-zA-Z]* / (.*) [a-zA-Z]*");
					Matcher matcher2 = couple2.matcher(b.getSecond_player());
					if (matcher2.matches()) {
						b.setSecond_player(matcher2.group(1)+"/"+matcher2.group(2));
					}	
			}
			b.setFirst_player(b.getFirst_player().replace("-"," "));
			b.setSecond_player(b.getSecond_player().replace("-"," "));
			b.setFirst_player(b.getFirst_player().replace(".",""));
			b.setSecond_player(b.getSecond_player().replace(".",""));
			
				matches.setCouple(b.getFirst_player()+" - "+b.getSecond_player());	
			
				
				
			Calendar rightnow = Calendar.getInstance();
				
				Pattern vri5 = Pattern.compile("[a-zA-Z]*, (.*).. [a-zA-Z]* (.*), (.*)");
				Matcher matcher6 = vri5.matcher(b.getPlaying_date());
				if (matcher6.matches()) {
					int monthString = 0;
				
					if(matcher6.group(2).equals("Jan")) {monthString = 0;}
					if(matcher6.group(2).equals("Feb")) {monthString = 1;}         
					if(matcher6.group(2).equals("Mar")) {monthString = 2;} 
					if(matcher6.group(2).equals("Apr")) {monthString = 3;}
					if(matcher6.group(2).equals("May")) {monthString = 4;}
					if(matcher6.group(2).equals("Jun")) {monthString = 5;}
					if(matcher6.group(2).equals("Jul")) {monthString = 6;}
					if(matcher6.group(2).equals("Aug")) {monthString = 7;}
					if(matcher6.group(2).equals("Sep")) {monthString = 8;}
					if(matcher6.group(2).equals("Oct")) {monthString = 9;}
					if(matcher6.group(2).equals("Nov")) {monthString = 10;}
					if(matcher6.group(2).equals("Dec")) {monthString = 11;}
					
					
					rightnow.set(Integer.parseInt(matcher6.group(3)), monthString, Integer.parseInt(matcher6.group(1)));
					java.util.Date date = new java.util.Date();
					date = rightnow.getTime();
					long t = date.getTime();
					java.sql.Date dt = new java.sql.Date(t);
					matches.setDates(dt);
				
				
			}
			if(matches.getCouple() != null){
				matches_list.add(matches);
			}
			
		}
		
		
		
		
		
	return matches_list;
	}


List <Matches> adapt_for_compare_ladbrokes(List<Bet> bet, List<Bookie> bookie_list){
	
	List<Matches> matches_list = new ArrayList<Matches>();
	
	Matches matches = null;
	
	for(Bet b: bet){
		matches = new Matches();
		matches.setBookie_for_first(bookie_list.get(1));
		matches.setBookie_for_second(bookie_list.get(1));
		matches.setFirst_odd(b.getFirst_player_odd());
		matches.setSecond_odd(b.getSecond_player_odd());
		
		float num = 100*(((b.getFirst_player_odd()*b.getSecond_player_odd())/(b.getFirst_player_odd()+b.getSecond_player_odd()))-1);
		matches.setRate((Math.round(num*1000.0f)/1000.0f));

		String str = b.getPlaying_time()+":00";
		matches.setTimes(Time.valueOf(str));
		
		
		
		
		b.setFirst_player(b.getFirst_player().replace("-"," "));
		b.setSecond_player(b.getSecond_player().replace("-"," "));
		b.setFirst_player(b.getFirst_player().replace("Ã¡","a"));
		b.setSecond_player(b.getSecond_player().replace("Ã¡","a"));
		b.setFirst_player(b.getFirst_player().replace("Å¡","s"));
		b.setSecond_player(b.getSecond_player().replace("Å¡","s"));
		
		if((b.getFirst_player().contains("/"))&&(b.getFirst_player().contains(" "))){
			Pattern couple1 = Pattern.compile("(.*), [a-zA-Z]*/(.*), [a-zA-Z]*");
			Matcher matcher1 = couple1.matcher(b.getFirst_player());
			if (matcher1.matches()) {
				b.setFirst_player(matcher1.group(1)+"/"+matcher1.group(2));
			}
			
		}
		if(b.getSecond_player().contains("/")&&(b.getSecond_player().contains(" "))){
			
			Pattern couple6 = Pattern.compile("(.*), [a-zA-Z]*/(.*), [a-zA-Z]*");
			Matcher matcher2 = couple6.matcher(b.getSecond_player());
			if (matcher2.matches()) {
				b.setSecond_player(matcher2.group(1)+"/"+matcher2.group(2));
			}
		
		}
			matches.setCouple(b.getFirst_player()+" - "+b.getSecond_player());	
		
		Calendar rightnow = Calendar.getInstance();
		
			Pattern couple2 = Pattern.compile("([0-9]*) ([0-9]*) ([0-9]*)");
			Matcher matcher3 = couple2.matcher(b.getPlaying_date());
			if (matcher3.matches()) {
				rightnow.set(Integer.parseInt(matcher3.group(1)), Integer.parseInt(matcher3.group(2)), Integer.parseInt(matcher3.group(3)));
				java.util.Date date = new java.util.Date();
				date = rightnow.getTime();
				long t = date.getTime();
				java.sql.Date dt = new java.sql.Date(t);
				matches.setDates(dt);	
			}
			
			Pattern couple3 = Pattern.compile("(.*)&nbsp;(.*)");
			Matcher matcher4 = couple3.matcher(b.getPlaying_date());
			if (matcher4.matches()) {
				int month1 = 0;
				
				if(matcher4.group(2).equals("Sij")) {month1 = 0;}
				if(matcher4.group(2).equals("Vel")) {month1 = 1;}         
				if(matcher4.group(2).equals("Ozu")) {month1 = 2;} 
				if(matcher4.group(2).equals("Tra")) {month1 = 3;}
				if(matcher4.group(2).equals("Svi")) {month1 = 4;}
				if(matcher4.group(2).equals("Lip")) {month1 = 5;}
				if(matcher4.group(2).equals("Srp")) {month1 = 6;}
				if(matcher4.group(2).equals("Kol")) {month1 = 7;}
				if(matcher4.group(2).equals("Ruj")) {month1 = 8;}
				if(matcher4.group(2).equals("Lis")) {month1 = 9;}
				if(matcher4.group(2).equals("Stu")) {month1 = 10;}
				if(matcher4.group(2).equals("Pro")) {month1 = 11;}
				
				rightnow.set(2012, month1, Integer.parseInt(matcher4.group(1)));
				java.util.Date date = new java.util.Date();
				date = rightnow.getTime();
				long t = date.getTime();
				java.sql.Date dt = new java.sql.Date(t);
				matches.setDates(dt);	
			}
			
			Pattern couple5 = Pattern.compile("(.*) ([a-zA-Z]*) (.*)");
			Matcher matcher5 = couple5.matcher(b.getPlaying_date());
			if (matcher5.matches()) {
				int month2 = 0;
				
				if(matcher5.group(2).equals("Sijecanj")) {month2 = 0;}
				if(matcher5.group(2).equals("Veljaca")) {month2 = 1;}         
				if(matcher5.group(2).equals("Ozujak")) {month2 = 2;} 
				if(matcher5.group(2).equals("Travanj")) {month2 = 3;}
				if(matcher5.group(2).equals("Svibanj")) {month2 = 4;}
				if(matcher5.group(2).equals("Lipanj")) {month2 = 5;}
				if(matcher5.group(2).equals("Srpanj")) {month2 = 6;}
				if(matcher5.group(2).equals("Kolovoz")) {month2 = 7;}
				if(matcher5.group(2).equals("Rujan")) {month2 = 8;}
				if(matcher5.group(2).equals("Listopad")) {month2 = 9;}
				if(matcher5.group(2).equals("Studeni")) {month2 = 10;}
				if(matcher5.group(2).equals("Prosinac")) {month2 = 11;}
				
				rightnow.set(Integer.parseInt(matcher5.group(3)), month2, Integer.parseInt(matcher5.group(1)));
				java.util.Date date = new java.util.Date();
				date = rightnow.getTime();
				long t = date.getTime();
				java.sql.Date dt = new java.sql.Date(t);
				matches.setDates(dt);	
			}
			
		if(matches.getCouple() != null){
			matches_list.add(matches);
		}
			
	}
return matches_list;
}


public List<String> takeLinksLadbrokes(String locationOfFile){
	
	List<String> listStrings = new ArrayList<String>();
	
	try {

		BufferedReader bufRead = new BufferedReader(new FileReader(
				locationOfFile));
		
			String line = bufRead.readLine();

			while (line != null) {
				if(line.equals("<div class=\"\">")){
					while(!line.equals("</div>")){
						Pattern str = Pattern.compile("<a href=\"(.*)\">[a-zA-Z ]*</a>");
						Matcher matcher = str.matcher(line);
						if (matcher.matches()) {
							listStrings.add(matcher.group(1));
						}
						line = bufRead.readLine();
					}
					
				}
				line = bufRead.readLine();
			}
			bufRead.close();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	return listStrings;
	
}

	public List<String> downloadLinksLadbrokes(List<String> listStrings){
		
		String temp = null, name ="C:/Users/Programing/Proba_file/ladbrokes_tennis";
		int i = 1;
		List<String> listNames = new ArrayList<String>();
		
		for (String l : listStrings){
			
			try {
				temp = name+Integer.toString(i)+".html";
				i++;
				URL url = new URL(l);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

				String line;
				while ((line = reader.readLine()) != null) {
					line = line.replaceAll("^\\s+", "");		// mjenja (izbacuje) sve whitespaces od poèetka linije do nekog znaka 
					if (line.length() > 0) {					// ako je linija prazna nemoj je ispisati, izbacuje sve prazne linije radi bržeg pretraživanja
						writer.write(line);
						writer.newLine();
					}

				}

				reader.close();
				writer.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			listNames.add(temp);
			
			
		}
		
		return listNames;
	}

	
	public List<String> takeLinksBetfair(String locationOfFile){
		
		List<String> listStrings = new ArrayList<String>();
		
		try {

			BufferedReader bufRead = new BufferedReader(new FileReader(
					locationOfFile));
			
				String line = bufRead.readLine();

				while (line != null) {
					if(line.equals("<ul class=\"children\">")){
						while(!line.equals("</ul>")){
							Pattern str = Pattern.compile("<a id=\"mnav-MENU[0-9]{8}\" href=\"(.*)\"class=\"link[0-9]{1,2} has-children has-content i13n-ltxt-LHN i13n-pos-2 i13n-tab-AllSprts child\"  >");
							Matcher matcher = str.matcher(line);
							if (matcher.matches()) {
								listStrings.add(matcher.group(1));
							}
							line = bufRead.readLine();
						}
						
					}
					line = bufRead.readLine();
				}
				bufRead.close();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}
		return listStrings;
		
		
	}
	
	
public List<String> downloadLinksBetfair(List<String> listStrings){
		
		String temp = null, name ="C:/Users/Programing/Proba_file/betfair_tennis";
		int i = 1;
		List<String> listNames = new ArrayList<String>();
		
		for (String l : listStrings){
			
			try {
				temp = name+Integer.toString(i)+".html";
				i++;
				URL url = new URL("http://sports.betfair.com"+l);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

				String line;
				while ((line = reader.readLine()) != null) {
					line = line.replaceAll("^\\s+", "");		// mjenja (izbacuje) sve whitespaces od poèetka linije do nekog znaka 
					if (line.length() > 0) {					// ako je linija prazna nemoj je ispisati, izbacuje sve prazne linije radi bržeg pretraživanja
						writer.write(line);
						writer.newLine();
					}

				}

				reader.close();
				writer.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			listNames.add(temp);
		}
		return listNames;
			
		}
	

	public List<Matches> compareBookie(List<List<Matches>> ListOfList, List<Bookie> listBookie){
		
		List<Matches> listMatches = new ArrayList<Matches>();
		List <Matches> temp, temp1;
		float max1, max2, koef1, koef2, num, koef11, koef22, num1 ; 
		int flag1 , flag2 , i, isti;
		
		for(Matches ladbrokes : ListOfList.get(1)){
			temp = new ArrayList<Matches>();
			temp.add(ladbrokes);
			
			for(Matches betfair : ListOfList.get(0)){
				if(ladbrokes.getCouple().equals(betfair.getCouple())){
					temp.add(betfair);
					
					break;
				}
			}
			for(Matches bluesq : ListOfList.get(2)){
				if(bluesq.getCouple().equals(ladbrokes.getCouple())){
					temp.add(bluesq);
					
					break;
				}
			}
			i= 0;
			flag1 = 0;
			flag2 = 0;
			for(Matches m: temp){
				System.out.println(m.getBookie_for_first().getName());
				System.out.println(m.getCouple());
			}
			max1 = 1f;
			max2 = 1f;
			for(Matches tp : temp){
				
				koef1 = temp.get(flag1).getFirst_odd();
				koef2 = temp.get(flag1).getSecond_odd();
				if(tp.getBookie_for_first().getName().equals("Betfair")){
					koef1 = ((tp.getFirst_odd()-1)*0.95f)+1;
				}
				if(tp.getBookie_for_second().getName().equals("Betfair")){
					koef2 = ((tp.getSecond_odd()-1)*0.95f)+1;
				}
				
				if(koef1>max1){
					max1 = tp.getFirst_odd();
					flag1 = i;
					//System.out.println(tp.getCouple());
				}
				if(koef2>max2){
					max2 = tp.getSecond_odd();
					flag2 = i;
					//System.out.println(tp.getCouple());
					
				}
				
				i++;
			}
			temp.get(flag1).setSecond_odd(temp.get(flag2).getSecond_odd());
			temp.get(flag1).setBookie_for_second(temp.get(flag2).getBookie_for_second());
			koef1 = temp.get(flag1).getFirst_odd();
			koef2 = temp.get(flag1).getSecond_odd();
			if(temp.get(flag1).getBookie_for_first().getName().equals("Betfair")){
				koef1 = ((temp.get(flag1).getFirst_odd()-1)*0.95f)+1;
			}
			if(temp.get(flag1).getBookie_for_second().getName().equals("Betfair")){
				koef2 = ((temp.get(flag1).getSecond_odd()-1)*0.95f)+1;
			}
			num = 100*(((koef1*koef2)/(koef1+koef2))-1);
			temp.get(flag1).setRate((Math.round(num*1000.0f)/1000.0f));
			listMatches.add(temp.get(flag1));
		}
		
		for(Matches betfair1 : ListOfList.get(0)){
			isti = 0;
			for(Matches main : listMatches){
				if(betfair1.getCouple().equals(main.getCouple())){
					isti = 1;
					break;	
				}	
			}
			if(isti == 0){
				temp1 = new ArrayList<Matches>();
				temp1.add(betfair1);
				for(Matches bluesq1 : ListOfList.get(2)){
					if(betfair1.getCouple().equals(bluesq1.getCouple())){
						temp1.add(bluesq1);
						break;
					}
				}
				if(temp1.size()==1){
					listMatches.add(betfair1);
				}else{
					koef11 = ((temp1.get(0).getFirst_odd()-1)*0.95f)+1;
					koef22 = ((temp1.get(0).getSecond_odd()-1)*0.95f)+1;
			
					if(temp1.get(1).getFirst_odd()>koef11){
						temp1.get(0).setFirst_odd(temp1.get(1).getFirst_odd());
						temp1.get(0).setBookie_for_first(temp1.get(1).getBookie_for_first());
						koef11 = temp1.get(0).getFirst_odd();
					}
					if(temp1.get(1).getSecond_odd()>koef22){
						temp1.get(0).setSecond_odd(temp1.get(1).getSecond_odd());
						temp1.get(0).setBookie_for_second(temp1.get(1).getBookie_for_second());
						koef22 = temp1.get(0).getSecond_odd();
					}
					
					
					num1 = 100*(((koef11*koef22)/(koef11+koef22))-1);
					temp1.get(0).setRate((Math.round(num1*1000.0f)/1000.0f));
					
					
				}
				for(Matches m1: temp1){
					System.out.println(m1.getBookie_for_first().getName());
					System.out.println(m1.getCouple());
				}
				
				listMatches.add(temp1.get(0));
			}	
		}
		return listMatches;
	}
	
		

	@RequestMapping(value = "/admin/refresh.html")
	public String searchMatches(Model model) {
		
		service.deleteMatches();  // pozivanje metode iz servisnog sloja koja briše sve zapise u bazi, nije baš najsretnije implementirana, jer mora opet uèitati sve podatke da bi znala što treba brisati
		
		 
		List<Bookie> bookie_list = service.findAllBookie(); // uèitavanje popisa kladionica s svim potrebnim podatcima o njima
		
		
		
		  for(Bookie d:bookie_list) { downloadFile(d.getTennis_link(),
		  d.getLocation_of_file()); }
		 
		
		List<String> listNamesLadbrokes = downloadLinksLadbrokes(takeLinksLadbrokes(bookie_list.get(1).getLocation_of_file()));
		List<String> listNamesBetfair = downloadLinksBetfair(takeLinksBetfair(bookie_list.get(0).getLocation_of_file()));
		
		
		List<Bet> fullListLadbrokes = new ArrayList<Bet>();
		for (String l : listNamesLadbrokes){
			List<Bet> ladbrokes_list = ladbrokesTennis(l);
				for( Bet b: ladbrokes_list){
					fullListLadbrokes.add(b);
				}
		}
		

		List<Bet> fullListBetfair = new ArrayList<Bet>();
		for (String l : listNamesBetfair){
			List<Bet> betfair_list = betfairTennis(l);
				for( Bet b: betfair_list){
					fullListBetfair.add(b);
				}
		}
		
		List<Bet> bluesq_list = bluesqTennis(bookie_list.get(2).getLocation_of_file());
		
		
		List<Matches> matchesListBetfair = adapt_for_compare_betfair(fullListBetfair, bookie_list);
		List<Matches> matchesListBluesq = adapt_for_compare_bluesq(bluesq_list, bookie_list);
		List<Matches> matchesListLadbrokes = adapt_for_compare_ladbrokes(fullListLadbrokes, bookie_list);

		List<List<Matches>> listOfList = new ArrayList<List<Matches>>();
		
		listOfList.add(matchesListBetfair);  // Kao što su u bazi posložene poredu tim su redosljedom dodane u listu
		listOfList.add(matchesListLadbrokes);
		listOfList.add(matchesListBluesq);

		
		/*	
		List<Bet> betfair_list = betfairTennis(bookie_list.get(0)
				.getLocation_of_file());
					
		List<Bet> ladbrokes_list = ladbrokesTennis(bookie_list.get(1)
				.getLocation_of_file());
		*/
		
		/*	
			
		System.out.println("Ladbrokes");
		for (Bet bl : fullListLadbrokes) {
			System.out.println(bl.getPlaying_date());
			System.out.println(bl.getFirst_player());
			System.out.println(bl.getSecond_player());
			System.out.println(bl.getPlaying_time());
			System.out.println(bl.getFirst_player_odd());
			System.out.println(bl.getSecond_player_odd());
		}
		
		System.out.println("Betfair");
		  for (Bet bt : betfair_list) { System.out.println(bt.getPlaying_date());
		  System.out.println(bt.getFirst_player());
		  System.out.println(bt.getSecond_player());
		  System.out.println(bt.getPlaying_time());
		  System.out.println(bt.getFirst_player_odd());
		  System.out.println(bt.getSecond_player_odd()); }
		  
	 
		  System.out.println("Bluesq");
		  for (Bet bq : bluesq_list) {
		  System.out.println(bq.getPlaying_date());
		  System.out.println(bq.getFirst_player());
		  System.out.println(bq.getSecond_player());
		  System.out.println(bq.getPlaying_time());
		  System.out.println(bq.getFirst_player_odd());
		  System.out.println(bq.getSecond_player_odd()); }
	*/		

			
		  for( Matches mat: compareBookie(listOfList, bookie_list)){
			  /*
			  System.out.println(mat.getCouple());
			  System.out.println(mat.getFirst_odd());
			  System.out.println(mat.getSecond_odd());
			  System.out.println(mat.getTimes());
			  System.out.println(mat.getDates());
			  System.out.println(mat.getRate());
		 */
		  service.insertMatches(mat);
		  
		  }
	 
	
		model.addAttribute("matches", service.findAllMatches());  // ponovni poziv ispisa iz baze nakon pretrage podataka ( prikažu se novo pretraženi podatci)
		model.addAttribute("isRole", true);
		return "viewMatches";

	}

}
