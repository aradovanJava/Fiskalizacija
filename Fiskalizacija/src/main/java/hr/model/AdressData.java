package hr.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "AdresniPodatak")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdressData{
	
	@XmlElement(name = "Adresa")
	private Adress adress;
	
	@XmlElement(name = "OstaliTipoviPP")
	private String otherBusinessArea; 
	
	 public Adress getAdress(){
		return adress;
	}
	public void setAdress(Adress adress){
		this.adress = adress;
	}
	public String getOtherBusinessArea(){
		return otherBusinessArea;
	}
	public void setOtherBusinessArea(String otherBusinessArea){
		this.otherBusinessArea = otherBusinessArea;
	}
	
}
