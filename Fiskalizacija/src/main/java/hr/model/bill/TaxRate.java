package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Porez")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaxRate{
	
	@XmlElement(name = "Naziv")
	private String name;
	
	@XmlElement(name = "Stopa")
	private double rate;
	
	@XmlElement(name = "Osnovica")
	private double baseValue;
	
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
