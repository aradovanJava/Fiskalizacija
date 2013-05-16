package hr.javaweb.zavrsni.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hr.javaweb.zavrsni.model.Runner;
import hr.javaweb.zavrsni.service.ServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManualHorseRacing{
	
	@Autowired
	private ServiceInterface service;

	
	@RequestMapping(value = "/manualHorseRacing.html", method = RequestMethod.GET)
	public String getEvent(Model model){
		model.addAttribute("horseRacing", service.findAllHorseRacing());
		return "manualHorseRacing";	
	}
	
	
	@RequestMapping(value = "/deleteRace.html", method = RequestMethod.POST)
	public String obrisiZaposlenika(@RequestParam("id") int id, Model model) {
		service.obrisiEvent(id);
		model.addAttribute("horseRacing", service.findAllHorseRacing());
		return "manualHorseRacing";
	}
	
	
	@RequestMapping(value = "/specRace.html", method = RequestMethod.POST)
	public String getRunners(@RequestParam("id") int id, Model model){
		
		List<ArrayList<Runner>> listForJSP = new ArrayList<ArrayList<Runner>>(); // u svakoj unutarnjoj listi se nalaze objekti istog runnera
		List<Runner> listRunners = service.findAllRunners(id);
		
		/*
		Collections.sort(listRunners, new Comparator<Runner>() {
			  public int compare(Runner r1, Runner r2) {
				  return r1.getRunnerName().compareTo(r1.getRunnerName());
			  }});
		*/
		boolean uListiJe;
		for(Runner runner : listRunners){
			uListiJe = false;
			for(int i = 0 ; i < listForJSP.size() ; ++i){
					if(runner.getRunnerName().equals(listForJSP.get(i).get(0).getRunnerName())){
						uListiJe = true;
						listForJSP.get(i).add(runner);
						break;
					}
			}
			if(!uListiJe){
				ArrayList<Runner> tempList = new ArrayList<Runner>();
				tempList.add(runner);
				listForJSP.add(tempList);
			}
		}
		model.addAttribute("listForJSP", listForJSP);			
		return "specRace";	
	}
	

	
	@RequestMapping(value = "/refreshHorse.html", method = RequestMethod.GET)
	public String searchRunners(Model model) {

	downloadFile("http://sports.betfair.com/horse-racing/market?id=1.107754117", "C:/Users/Programing/Proba_file/horse_racing.html");
		
		model.addAttribute("horseRacing", service.findAllHorseRacing());
		return "specRace";
	}
	
	// metoda za download podataka s interneta na url-u pod nazivon tennis_url u lakaciju na disku pod nazivom _locatin_of_file 
		public void downloadFile(String tennis_url, String location_of_file) {

			Runner runner = new Runner();
			
			try {
				URL url = new URL(tennis_url);

				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				//BufferedWriter writer = new BufferedWriter(new FileWriter(location_of_file));

				String line;									// ako je linija prazna nemoj je ispisati, izbacuje sve prazne linije radi bržeg pretraživanja
				while ((line = reader.readLine()) != null) {		// mjenja (izbacuje) sve whitespaces od poèetka linije do nekog znaka 
					if (line.contains("<span class=\"sel-name\">")) {
						line = reader.readLine();
						runner.setRunnerName(line.replaceAll("^\\s+", ""));
						while ((line = reader.readLine()) != null){
							if(line.contains("<div class=\"runner-people\">")){
								line = reader.readLine();
								line = reader.readLine();
								runner.setJokeyName(line.replaceAll("^\\s+", ""));
							}
							
						}
						
						
						/*
						writer.write(line);
						writer.newLine();
						
						*/
					}

				}

				reader.close();
				//writer.close();
			} catch (MalformedURLException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	
	
}
