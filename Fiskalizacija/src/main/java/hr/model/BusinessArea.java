package hr.model;


import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name = "PoslovniProstor", namespace = "http://www.apis-it.hr/fin/2012/types/f73")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessArea {
	
	@XmlElement(name = "Oib")
	private String oib;
	
	@XmlElement(name = "OznPoslProstora")
	private String noteOfBusinessArea;
	
	@XmlElement(name = "AdresniPodatak")
	private AdressData adressData;
	
	@XmlElement(name = "RadnoVrijeme")
	private String workingTime;
	
	@XmlElement(name = "DatumPocetkaPrimjene")
	private String dateOfusage;
	
	@XmlElement(name = "OznakaZatvaranja")
	private String noteOfClosing;

	@XmlElement(name = "SpecNamj")
	private String specificPurpose;
	
	@XmlTransient
	SimpleDateFormat dateformatForUsageDate = new SimpleDateFormat("dd.MM.yyyy");

	public BusinessArea(){
	}

	public String getOib() {
		return oib;
	}
	public void setOib(String oib) {
		this.oib = oib;
	}
	public String getNoteOfBusinessArea() {
		return noteOfBusinessArea;
	}
	public void setNoteOfBusinessArea(String noteOfBusinessArea) {
		this.noteOfBusinessArea = noteOfBusinessArea;
	}
	public AdressData getAdressData() {
		return adressData;
	}
	public void setAdressData(AdressData adressData) {
		this.adressData = adressData;
	}
	public String getWorkingTime() {
		return workingTime;
	}
	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}
	public String getDateOfusage() {
		return dateOfusage;
	}
	public void setDateOfusage(GregorianCalendar dateOfusage) {
		this.dateOfusage = dateformatForUsageDate.format(dateOfusage.getTime());
	}
	public String getNoteOfClosing() {
		return noteOfClosing;
	}
	public void setNoteOfClosing(String noteOfClosing) {
		this.noteOfClosing = noteOfClosing;
	}
	public String getSpecificPurpose() {
		return specificPurpose;
	}
	public void setSpecificPurpose(String specificPurpose) {
		this.specificPurpose = specificPurpose;
	}
	
	
}
