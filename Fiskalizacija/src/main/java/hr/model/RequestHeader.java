package hr.model;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Zaglavlje")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestHeader {

	@XmlElement(name = "IdPoruke")
	private String uuid;
	
	@XmlElement(name = "DatumVrijeme")
	private String dateTimeOfRequest;
	
	@XmlTransient
	SimpleDateFormat dateformatForRequest = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss");
	
	public RequestHeader(){
		uuid = UUID.randomUUID().toString();
		dateformatForRequest = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss");
		dateTimeOfRequest = dateformatForRequest.format( new Date());	
	}
	
	public String getUuid(){
		return uuid;
	}
	
	public String getDateTimeOfRequest(){
		return dateTimeOfRequest;
	}
	
}
