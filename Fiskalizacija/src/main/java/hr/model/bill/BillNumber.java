package hr.model.bill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Klasa za kreiranje taga koji definira broj raèuna/poslovni prostor/naplatni ureðaj
 * 
 * 
 */
@XmlRootElement(name = "BrRac")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillNumber {

	
	/**
	 * Brojèana oznaka raèuna
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "BrOznRac")
	private int numberNoteBill;
	
	
	/**
	 * Oznaka poslovnog prostora koji je ranije prijavljen XML-om
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "OznPosPr")
	private String noteOfBusinessArea;
	
	
	/**
	 * Oznaka naplatnog ureðaja
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "OznNapUr")
	private String noteOfExcangeDevice;
	
	public BillNumber(){
	}
	
	public BillNumber(int numberNoteBill, String noteOfBusinessArea, String noteOfExcangeDevice) {
		super();
		this.numberNoteBill = numberNoteBill;
		this.noteOfBusinessArea = noteOfBusinessArea;
		this.noteOfExcangeDevice = noteOfExcangeDevice;
	}
	
	public int getNumnerNoteBill() {
		return numberNoteBill;
	}

	public void setNumberNoteBill(int numberNoteBill) {
		this.numberNoteBill = numberNoteBill;
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
