package hr.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Addresa")
@XmlAccessorType(XmlAccessType.FIELD)
public class Adress {

	@XmlElement(name = "Ulica")
	private String street;
	
	@XmlElement(name = "KucniBroj")
	private String houseNumber;
	
	@XmlElement(name = "KucniBrojDodatak")
	private String extrahouseNumber;
	
	@XmlElement(name = "BrojPoste")
	private String zipCode;
	
	@XmlElement(name = "Naselje")
	private String settlement;
	
	@XmlElement(name = "Opcina")
	private String city;
	
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	
	public String getHouseNumber() {
		return houseNumber;
	}
	
	
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	
	public String getExtrahouseNumber() {
		return extrahouseNumber;
	}
	public void setExtrahouseNumber(String extrahouseNumber) {
		this.extrahouseNumber = extrahouseNumber;
	}
	
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
