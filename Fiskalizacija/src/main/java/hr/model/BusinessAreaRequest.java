package hr.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "PoslovniProstorZahtjev", namespace = "http://www.apis-it.hr/fin/2012/types/f73")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessAreaRequest{

//	@XmlAttribute(namespace = "http://www.w3.org/2001/XMLSchema-instance") 
	@XmlElement(name = "Zaglavlje")
	RequestHeader requestHeader;
	
	
	@XmlElement(name = "PoslovniProstor")
//	@XmlAttribute(namespace = "http://www.apis-it.hr/fin/2012/types/f73 ../schema/FiskalizacijaSchema.xsd") 
	BusinessArea businessArea;
	
	@XmlAttribute(name = "Id")
	String id = "poslovniProstorId";
	
	
	public BusinessAreaRequest(){
	}
	
	
	public BusinessAreaRequest(RequestHeader requestHeader, BusinessArea businessArea){
		this.requestHeader = requestHeader;
		this.businessArea = businessArea;
	}
	
	public RequestHeader getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}
	
	public BusinessArea getBusinessArea(){
		return businessArea;
	}
	public void setBusinessArea(BusinessArea businessArea) {
		this.businessArea = businessArea;
	}
	
	
}

