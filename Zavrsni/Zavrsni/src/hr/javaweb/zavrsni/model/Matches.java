package hr.javaweb.zavrsni.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Basic;
	
	/**
	 * Persistent class for storing processed data with assigned the largest odds for both events
	 * With this class user take data from database, it mapped on MATCHES Table in database  
	 * 
	 * @author Juraj
	 */
@Entity
@Table(name="ZAVRSNI.MATCHES")
public class Matches implements Serializable {
	
		private static final long serialVersionUID = 1L;

	/**
	 * @param id mapped to ID column in MATCH table, it's primary key in that table and it's auto generated value
	 */
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	
	/**
	 * Other column in MATCHES table
	 * @param couple mapped on column COUPLE in table MATCHES. In that variable we stored couple name which constitute from strings
	 * 				 of first player, separator and second player
	 * @param first_odd mapped on column FIRST_ODD in table MATCHES. In that variable we stored value of odd for first player in couple name
	 * @param second_odd mapped on column SECOND_ODDS in table MATCHES. In that variable we stored value of odd for second player in couple name
	 * @param times mapped on column TIMES in table MATCHES. In that variable we stored time when couple play 
	 * @param dates mapped on column DATES in table MATCHES. In that variable we stored date when couple play
	 * @param rate mapped to column RATES it table MATCHES. In that variable we stored calculate value of percent of deposit , which we will get after
	 * 			   couple finished match   
	 */
	
	@Basic
	@Column(name="COUPLE")
	private String couple;
	
	@Basic
	@Column(name="FIRST_ODD")
	private float first_odd;
	
	@Basic
	@Column(name="SECOND_ODD")
	private float second_odd;
	
	@Basic
	@Column(name="TIMES")
	private Time times;
	
	@Basic
	@Column(name="DATES")
	private Date dates;
	
	@Basic
	@Column(name="RATE")
	private float rate;
	
	/**
	 * Object from Bookie class, which represent foreign keys which used to connect two different relationship on same column named ID in table BOOKIE 
	 */
	
	@ManyToOne
	@JoinColumn(name="BOOKIE_ID_FIRST")
	private Bookie bookie_for_first;
	
	@ManyToOne
	@JoinColumn(name="BOOKIE_ID_SECOND")
	private Bookie bookie_for_second;
	
	/**
	 * Constructor with all parameters
	 */
	public Matches(int id, String couple, float first_odd, float second_odd, Time times, Date dates, float rate){
		this.id = id;
		this.couple = couple;
		this.first_odd = first_odd;
		this.second_odd = second_odd;
		this.times = times;
		this.dates = dates;
		this.rate = rate;
	}
	/**
	 * Constructor with no parameters
	 */
	
	public Matches(){
	}
	
	/**
	 * Getter and setter methods for all variable
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getCouple(){
		return couple;
	}
	
	public void setCouple(String couple){
		this.couple = couple;
	}
	
	public float getFirst_odd(){
		return first_odd;
		
	}
	
	public void setFirst_odd(float first_odd){
		this.first_odd = first_odd;
	}
	
	public float getSecond_odd(){
		return second_odd;
	}
	
	public void setSecond_odd(float secound_odd){
		this.second_odd = secound_odd;
	}
	
	public Time getTimes(){
		return times;
	}
	
	public void setTimes(Time times){
		this.times = times;
	}
	
	public Date getDates(){
		return dates;
	}
	
	public void setDates(Date dates){
		this.dates = dates;
	}
	
	public float getRate(){
		return rate;
	}
	
	public void setRate(float rate){
		this.rate = rate;
	}

	public Bookie getBookie_for_first() {
		return bookie_for_first;
	}

	public void setBookie_for_first(Bookie bookie_for_first) {
		this.bookie_for_first = bookie_for_first;
	}

	public Bookie getBookie_for_second() {
		return bookie_for_second;
	}

	public void setBookie_for_second(Bookie bookie_for_second) {
		this.bookie_for_second = bookie_for_second;
	}

	/**
	 * Override method equals() for compare two objects tip Matches
	 */
	
	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		
		Matches matches = (Matches) o;
		
		if((id != matches.id)||(first_odd != matches.first_odd)||(second_odd != matches.second_odd)||(rate != matches.rate)) return false;
		if(bookie_for_first != null ? bookie_for_first.equals(matches.bookie_for_first) : matches.bookie_for_first != null) return false;
		if(bookie_for_second != null ? bookie_for_second.equals(matches.bookie_for_second) : matches.bookie_for_second != null)return false;
		if(couple != null ? !couple.equals(matches.couple) : matches.couple != null) return false;
		if(times != null ? !times.equals(matches.times) : matches.times != null) return false;
		if(dates != null ? !dates.equals(matches.dates) : matches.dates != null) return false;
		return true;
	}
	
	/**
	 * Override method hashCode() for compare two object tip Matches
	 */
	
	@Override
	public int hashCode(){
		int result = id;
		
		result = 7 * result + (bookie_for_first != null ? bookie_for_first.hashCode() : 0);
		result = 7 * result + (bookie_for_second != null ? bookie_for_second.hashCode() : 0);
		result = 7 * result + (couple != null ? couple.hashCode() : 0);
		result = 7 * result + (int)first_odd;
		result = 7 * result + (int)second_odd;
		result = 7 * result + (times != null ? times.hashCode() : 0);
		result = 7 * result + (dates != null ? dates.hashCode() : 0);
		result = 7 * result + (int)rate;
		
		return result;
	}
	
}
