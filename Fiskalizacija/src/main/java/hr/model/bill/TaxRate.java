package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
	@XmlJavaTypeAdapter(Adapter.class)
	@XmlElement(type = String.class, name = "Stopa")
	private Double rate;
	
	
	/**
	 * Osnovica koja se oporezuje navedenom stopom
	 * 
	 * Obvezan
	 */
	@XmlJavaTypeAdapter(Adapter.class)
	@XmlElement(type = String.class, name = "Osnovica")
	private Double baseValue;
	
	
	/**
	 * Iznos poreza za odreðenu stopu
	 * 
	 * Obvezan
	 */
	@XmlJavaTypeAdapter(Adapter.class)
	@XmlElement(type = String.class, name = "Iznos")
	private Double value;
	
	
	public TaxRate(){
	}
	
	
	public TaxRate(Double rate, Double baseValue, Double value, String name) {
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getMain() {
		return baseValue;
	}

	public void setMain(Double mainValue) {
		this.baseValue = mainValue;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
