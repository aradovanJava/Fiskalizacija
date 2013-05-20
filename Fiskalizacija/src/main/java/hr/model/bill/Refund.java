package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Naknada")
@XmlAccessorType(XmlAccessType.FIELD)
public class Refund{
	
	@XmlElement(name = "NazivN")
	private String nameRefund;

	@XmlJavaTypeAdapter(Adapter.class)
	@XmlElement(type = String.class, name = "IznosN")
	private Double valueRefund;
	
	
	public Refund(){
	}
	
	public Refund(String nameRefund, Double valueRefund) {
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

	public Double getValueRefund() {
		return valueRefund;
	}

	public void setValueRefund(Double valueRefund) {
		this.valueRefund = valueRefund;
	}
}
