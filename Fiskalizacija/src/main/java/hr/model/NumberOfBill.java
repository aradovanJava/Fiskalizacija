package hr.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BrRac")
@XmlAccessorType(XmlAccessType.FIELD)
public class NumberOfBill{

	
	@XmlElement(name = "BrOznRac")
	double noteNumberOfBill;
	
	@XmlElement(name = "OznPosPr")
	String noteOfBusinessArea;
	
	@XmlElement(name = "OznNapUr")
	String noteOfChargeDevice;
	
	
	public double getNoteNumberOfBill() {
		return noteNumberOfBill;
	}

	public void setNoteNumberOfBill(double noteNumberOfBill) {
		this.noteNumberOfBill = noteNumberOfBill;
	}

	public String getNoteOfBusinessArea() {
		return noteOfBusinessArea;
	}

	public void setNoteOfBusinessArea(String noteOfBusinessArea) {
		this.noteOfBusinessArea = noteOfBusinessArea;
	}

	public String getNoteOfChargeDevice() {
		return noteOfChargeDevice;
	}

	public void setNoteOfChargeDevice(String noteOfChargeDevice) {
		this.noteOfChargeDevice = noteOfChargeDevice;
	}
	
	
}
