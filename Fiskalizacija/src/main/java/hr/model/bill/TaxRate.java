package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Tag koji definira vrijednosti poreza
 * 
 *  Potrebno je postaviti vrijednosti:
 *  	Za PDV, PNP i Ostali Porezi:
 *  		- rate
 *  		- baseValue
 *  		- value
 *  te za ostale poreze uz 3 gore navedena još i name
 *
 *  Nakon kreiranja objekata, potrebno ih je insertati, ako postoje na raèunu, u liste poreza u klasi Bill
 *   
 *
 */
@XmlRootElement(name = "Porez")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxRate{
	
	
	/**
	 * Naziv poreza, koristi se samo kod ostalih poreza
	 * 
	 * Obvezan za Ostale poreze, za PDV i PNP se ne postavlja ništa
	 */
	@XmlElement(name = "Naziv")
	private String name;
	
	
	/**
	 * Iznos porezna stopa
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "Stopa")
	private double rate;
	
	
	/**
	 * Osnovica koja se oporezuje navedenom stopom
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "Osnovica")
	private double baseValue;
	
	
	/**
	 * Iznos poreza za odreðenu stopu
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "Iznos")
	private double value;
	
	
	public TaxRate(){
	}
	
	
	public TaxRate(double rate, double baseValue, double value, String name) {
		super();
		this.name = name;
		this.rate = rate;
		this.baseValue = baseValue;
		this.value = value;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getMain() {
		return baseValue;
	}

	public void setMain(double mainValue) {
		this.baseValue = mainValue;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
