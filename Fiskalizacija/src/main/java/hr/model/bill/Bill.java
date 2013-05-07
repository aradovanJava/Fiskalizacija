package hr.model.bill;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name = "Racun")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bill{

	@XmlElement(name = "Oib")
	private String oib;
	
	@XmlElement(name = "USustPdv")
	private boolean havePDV;
	
	@XmlElement(name = "DatVrijeme")
	private String dateTime;
	
	@XmlElement(name = "OznSlijed")
	private String noteOfOrder;
	
	@XmlElement(name = "BrRac")
	private BillNumber billNumber;
	
	@XmlElementWrapper(name = "Pdv")
	@XmlElement(name = "Porez")
	private List<TaxRate> listPDV;
	
	@XmlElementWrapper(name = "Pnp")
	@XmlElement(name = "Porez")
	private List<TaxRate> listPNP;
	
	@XmlElementWrapper(name = "OstaliPor")
	@XmlElement(name = "Porez")
	private List<TaxRate> listOtherTaxRate;
	
	@XmlElement(name = "IznosOslobPdv")
	private double taxFreeValuePdv;
	
	@XmlElement(name = "IznosMarza")
	private double marzaForTaxRate;
	
	@XmlElement(name = "IznosNePodlOpor")
	private double taxFreeValue;
	
	@XmlElement(name = "Naknada")
	private Naknada naknada;
	
	@XmlElement(name = "IznosUkupno")
	private double mainValue;
	
	@XmlElement(name = "NacinPlac")
	private String typeOfPlacanje;
	
	@XmlElement(name = "OibOper")
	private String oibOperater;
	
	@XmlElement(name = "ZastKod")
	private String securityCode;
	
	@XmlElement(name = "NakDost")
	private boolean noteOfRedelivary;
	
	@XmlElement(name = "ParagonBrRac")
	private String noteOfParagonBill;
	
	@XmlElement(name = "SpecNamj")
	private String specificPurpose;
	
	
	@XmlTransient
	SimpleDateFormat dateTimeformatForBill = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss");

	
	public String getOib() {
		return oib;
	}

	public void setOib(String oib) {
		this.oib = oib;
	}

	public boolean isHavePDV() {
		return havePDV;
	}

	public void setHavePDV(boolean havePDV) {
		this.havePDV = havePDV;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(GregorianCalendar dateTime) {
		this.dateTime = dateTimeformatForBill.format(dateTime.getTime());;
	}

	public String getNoteOfOrder() {
		return noteOfOrder;
	}

	public void setNoteOfOrder(String noteOfOrder) {
		this.noteOfOrder = noteOfOrder;
	}

	public BillNumber getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(BillNumber billNumber) {
		this.billNumber = billNumber;
	}

	public List<TaxRate> getListPDV() {
		return listPDV;
	}

	public void setListPDV(List<TaxRate> listPDV) {
		this.listPDV = listPDV;
	}

	public List<TaxRate> getListPNP() {
		return listPNP;
	}

	public void setListPNP(List<TaxRate> listPNP) {
		this.listPNP = listPNP;
	}

	public List<TaxRate> getListOtherTaxRate() {
		return listOtherTaxRate;
	}

	public void setListOtherTaxRate(List<TaxRate> listOtherTaxRate) {
		this.listOtherTaxRate = listOtherTaxRate;
	}

	public double getTaxFreeValue() {
		return taxFreeValuePdv;
	}

	public void setTaxFreeValue(double taxFreeValuePdv) {
		this.taxFreeValuePdv = taxFreeValuePdv;
	}

	public double getMarzaForTaxRate() {
		return marzaForTaxRate;
	}

	public void setMarzaForTaxRate(double marzaForTaxRate) {
		this.marzaForTaxRate = marzaForTaxRate;
	}

	public double getTaxFree() {
		return taxFreeValue;
	}

	public void setTaxFree(double taxFreeValue) {
		this.taxFreeValue = taxFreeValue;
	}

	public Naknada getNaknada() {
		return naknada;
	}

	public void setNaknada(Naknada naknada) {
		this.naknada = naknada;
	}

	public double getMainValue() {
		return mainValue;
	}

	public void setMainValue(double mainValue) {
		this.mainValue = mainValue;
	}

	public String getTypeOfPlacanje() {
		return typeOfPlacanje;
	}

	public void setTypeOfPlacanje(String typeOfPlacanje) {
		this.typeOfPlacanje = typeOfPlacanje;
	}

	public String getOibOperater() {
		return oibOperater;
	}

	public void setOibOperater(String oibOperater) {
		this.oibOperater = oibOperater;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	
	public boolean isNoteOfRedelivary() {
		return noteOfRedelivary;
	}

	public void setNoteOfRedelivary(boolean noteOfRedelivary) {
		this.noteOfRedelivary = noteOfRedelivary;
	}

	public String getNoteOfParagonBill() {
		return noteOfParagonBill;
	}

	public void setNoteOfParagonBill(String noteOfParagonBill) {
		this.noteOfParagonBill = noteOfParagonBill;
	}

	public String getSpecificPurpose() {
		return specificPurpose;
	}

	public void setSpecificPurpose(String specificPurpose) {
		this.specificPurpose = specificPurpose;
	}
}
