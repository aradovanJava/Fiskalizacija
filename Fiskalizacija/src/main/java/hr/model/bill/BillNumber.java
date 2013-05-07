package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BrRac")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillNumber {

	@XmlElement(name = "BrOznRac")
	private int numnerNoteBill;
	
	@XmlElement(name = "OznPosPr")
	private String noteOfBusinessArea;
	
	@XmlElement(name = "OznNapUr")
	private String noteOfExcangeDevice;
	
	public BillNumber(){
	}
	
	public BillNumber(int numnerNoteBill, String noteOfBusinessArea, String noteOfExcangeDevice) {
		super();
		this.numnerNoteBill = numnerNoteBill;
		this.noteOfBusinessArea = noteOfBusinessArea;
		this.noteOfExcangeDevice = noteOfExcangeDevice;
	}
	
	public int getNumnerNoteBill() {
		return numnerNoteBill;
	}

	public void setNumnerNoteBill(int numnerNoteBill) {
		this.numnerNoteBill = numnerNoteBill;
	}

	public String getNoteOfBusinessArea() {
		return noteOfBusinessArea;
	}

	public void setNoteOfBusinessArea(String noteOfBusinessArea) {
		this.noteOfBusinessArea = noteOfBusinessArea;
	}

	public String getNoteOfExcangeDevice() {
		return noteOfExcangeDevice;
	}

	public void setNoteOfExcangeDevice(String noteOfExcangeDevice) {
		this.noteOfExcangeDevice = noteOfExcangeDevice;
	}
}
