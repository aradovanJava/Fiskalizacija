package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Naknada")
@XmlAccessorType(XmlAccessType.FIELD)
public class Naknada{
	
	@XmlElement(name = "NazivN")
	private String nameNaknada;

	@XmlElement(name = "IznosN")
	private double valueNaknada;
	
	
	public Naknada(String nameNaknada, double valueNaknada) {
		super();
		this.nameNaknada = nameNaknada;
		this.valueNaknada = valueNaknada;
	}

	
	public String getNameNaknada() {
		return nameNaknada;
	}

	public void setNameNaknada(String nameNaknada) {
		this.nameNaknada = nameNaknada;
	}

	public double getValueNaknada() {
		return valueNaknada;
	}

	public void setValueNaknada(double valueNaknada) {
		this.valueNaknada = valueNaknada;
	}

}
