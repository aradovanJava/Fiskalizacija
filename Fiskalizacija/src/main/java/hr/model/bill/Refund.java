package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Naknada")
@XmlAccessorType(XmlAccessType.FIELD)
public class Refund{
	
	@XmlElement(name = "NazivN")
	private String nameRefund;

	@XmlElement(name = "IznosN")
	private double valueRefund;
	
	
	public Refund(String nameRefund, double valueRefund) {
		super();
		this.nameRefund = nameRefund;
		this.valueRefund = valueRefund;
	}

	
	public String getNameRefund() {
		return nameRefund;
	}

	public void setNameRefund(String nameRefund) {
		this.nameRefund = nameRefund;
	}

	public double getValueRefund() {
		return valueRefund;
	}

	public void setValueRefund(double valueRefund) {
		this.valueRefund = valueRefund;
	}

}
