package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import hr.model.RequestHeader;
@XmlRootElement(name = "RacunZahtjev", namespace = "http://www.apis-it.hr/fin/2012/types/f73")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillRequest {

	// Dodati namespaceove u prvi tag (RacunZahtjev)
	
	@XmlElement(name = "Zaglavlje")
	private RequestHeader requestHeader;
	
	@XmlElement(name = "Racun")
	private Bill bill;	

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
