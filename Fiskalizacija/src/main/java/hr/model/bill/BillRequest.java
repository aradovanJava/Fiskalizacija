package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import hr.model.RequestHeader;


/**
 * Klasa koja definira finalni tag XML-a kreiraog raèuna
 * 
 */
@XmlRootElement(name = "RacunZahtjev", namespace = "http://www.apis-it.hr/fin/2012/types/f73")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillRequest{

	// Dodati namespaceove u prvi tag
	/*
	  	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://www.apis-it.hr/fin/2012/types/f73 ../schema/FiskalizacijaSchema.xsd"
	 */
	
	
	/**
	 * U zaglavlju su definirani datum slanja i UUID poruke
	 * Postavljaju se prilikom kreiranja objekta klase RequestHeader defaultno
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "Zaglavlje")
	private RequestHeader requestHeader;
	
	
	/**
	 * Tag za kreiranje raèuna
	 * 
	 * Obvezan
	 * 
	 */
	@XmlElement(name = "Racun")
	private Bill bill;	

	
	/**
	 * Id raèuna koji se postavlja kao atribut poèetnog taga. Pomoæu njega se digitalno potpisuje
	 */
	@XmlAttribute(name = "Id")
	String id = "racunId";
	
	public BillRequest(){
	}
	
	public BillRequest(RequestHeader requestHeader, Bill bill) {
		super();
		this.requestHeader = requestHeader;
		this.bill = bill;
	}

	public RequestHeader getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	} 
}
