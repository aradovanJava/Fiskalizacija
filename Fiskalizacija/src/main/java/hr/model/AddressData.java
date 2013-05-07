package hr.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "AdresniPodatak")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressData{
	
	@XmlElement(name = "Adresa")
	private Address adress;
	
	@XmlElement(name = "OstaliTipoviPP")
	private String otherBusinessArea; 
	
	 public Address getAdress(){
		return adress;
	}
	public void setAdress(Address adress){
		this.adress = adress;
	}
	public String getOtherBusinessArea(){
		return otherBusinessArea;
	}
	public void setOtherBusinessArea(String otherBusinessArea){
		this.otherBusinessArea = otherBusinessArea;
	}
	
}
