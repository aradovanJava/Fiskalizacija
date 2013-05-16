package hr.javaweb.zavrsni.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZAVRSNI.EVENT")
public class Event implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;

	@Column(name ="NAZIV")
	private String naziv;
	
	@Column(name = "RUN_TIME")
	private Date runTime;
	
	@Column(name = "LINK")
	private String link;

	
	public Event() {
	}
	
	public Event(String naziv, Date runTime, String link) {
		this.naziv = naziv;
		this.runTime = runTime;
		this.link = link;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
