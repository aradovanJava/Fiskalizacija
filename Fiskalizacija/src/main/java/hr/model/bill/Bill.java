package hr.model.bill;

import hr.fiskalizacija.Fiscalization;
import hr.model.CertParameters;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Klasa za kreiranje XML tagova ra�una, potrebno ju je popuniti s neophodnim
 * i opcionalnim elementima koji se nalaze na ra�unu te se �alju prema serveru porezne uprave 
 * 
 *
 */
@XmlRootElement(name = "Racun")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bill{

	
	/**
	 * OIB firme koja je kreirala te �alje ra�un prema servisu porezne uprave 
	 * Obavezan podatak
	 */
	@XmlElement(name = "Oib")
	private String oib;
	
	
	/**
	 * Oznaka da li je firma koja kreira ra�un u sustavu PDV-a
	 * Obavezan podatak
	 */
	@XmlElement(name = "USustPdv")
	private boolean havePDV;
	
	
	/**
	 *  Datum i vrijeme koji se ispisuju na ra�unu
	 *  Obavezan podatak 
	 */
	@XmlElement(name = "DatVrijeme")
	private String dateTime;
	
	
	/**
	 * Oznaka slijednosti brojeva ra�una, da li redoslijed brojeva ra�una ide prema:
	 *  - naplanom ure�aju - "N" (defaultno)
	 *  - poslovnom prostoru - "D" 
	 *  
	 * Obavezan podatak 
	 */
	@XmlElement(name = "OznSlijed")
	private String noteOfOrder = "N";
	
	
	/**
	 * Klasa koja sadr�i: 
	 * - broj ra�una
	 * - oznaku poslovnog prostora
	 * - oznaku naplatnog ure�aja
	 * 
	 * Prilikom ispisa potrebno ga je ispisati u obliku:
	 * broj�ana oznaka ra�una/oznaka poslovnog prostora/oznaka naplatnog ure�aja
	 * Obavezan podatak 
	 */
	@XmlElement(name = "BrRac")
	private BillNumber billNumber;
	
	
	/**
	 * Lista klase TaxRate koja sadr�i:
	 * - poreznu stopu
	 * - osnovicu
	 * - iznos poreza
	 * U slu�aju da nema PDV-a, nije potrebno ni�ta unesti u listu, pa se ne�e ni izgenerirati u XML-u
	 * Ako ima vi�e razli�itih stopa PDV-a, potrebno je za svaku stopu unijeti objekt klase TaxRate u listu 
	 * 
	 * Obavezno ako ima PDV-a 
	 */
	@XmlElementWrapper(name = "Pdv")
	@XmlElement(name = "Porez")
	private List<TaxRate> listPDV;
	
	
	/**
	 * Lista klase TaxRate koja sadr�i:
	 * - poreznu stopu
	 * - osnovicu
	 * - iznos poreza
	 * U slu�aju da nema PNP-a (poreza na potro�nju), nije potrebno ni�ta unesti u listu, pa se ne�e ni izgenerirati u XML-u
	 * Ako ima vi�e razli�itih stopa PNP-a, potrebno je za svaku stopu unesti objekt klase TaxRate u listu 
	 * 
	 * Obavezno ako ima PNP-a 
	 */
	@XmlElementWrapper(name = "Pnp")
	@XmlElement(name = "Porez")
	private List<TaxRate> listPNP;
	
	
	
	/**
	 * Lista klase TaxRate koja sadr�i:
	 * - naziv poreza
	 * - poreznu stopu
	 * - osnovicu
	 * - iznos poreza
	 * U slu�aju da nema ostalih poreza, nije potrebno ni�ta unesti u listu, pa se ne�e ni izgenerirati u XML-u
	 * Ako ima vi�e razli�itih stopa Ostalih poreza, potrebno je za svaku stopu unesti objekt klase TaxRate u listu 
	 * 
	 * Obavezno ako ima Ostalih poreza 
	 */
	@XmlElementWrapper(name = "OstaliPor")
	@XmlElement(name = "Porez")
	private List<TaxRate> listOtherTaxRate;
	

	/**
	 * U slu�aju da postoji oslobo�enje od PDV-a, potrebno je unesti ukupni iznos oslobo�enja
	 * 
	 * Obvezno u slu�aju da ima oslobo�enja od PDV-a
	 */

    @XmlJavaTypeAdapter(Adapter.class)
	@XmlElement(type = String.class, name = "IznosOslobPdv")
	private Double taxFreeValuePdv;

	
	/**
	 * Unosi se ukupni iznos koji se oporezuje ako je mar�a definirana na ra�unu
	 * 
	 * Obvezan ako postoji posebno oporezivanje prema �lanku 22. Zakona o PDV-u
	 */
    @XmlJavaTypeAdapter(Adapter.class)
	@XmlElement(type = String.class, name = "IznosMarza")
	private Double marginForTaxRate;
	
	
	/**
	 * Iznos koji ne podlije�e oporezivanju
	 * 
	 * Obvezan ako postoji iznos koji ne podlije�e oporezivanju
	 */
    @XmlJavaTypeAdapter(Adapter.class)
	@XmlElement(type = String.class, name = "IznosNePodlOpor")
	private Double taxFreeValue;
	
	
	/**
	 * Lista naknada koje se nalaze na ra�unu
	 * Za svaki novi naziv naknade potrebno je postaviti novi objekt klase Refund u listu
	 * 
	 * Obvezan ako postoji naknada na ra�unu
	 */
	@XmlElementWrapper(name = "Naknade")
	@XmlElement(name = "Naknada")
	private List<Refund> refund;
	
	
	/**
	 * Ukupan iznos na ra�unu
	 * 
	 * Obvezan
	 */
	@XmlJavaTypeAdapter(Adapter.class)
	@XmlElement(type = String.class, name = "IznosUkupno")
	private Double totalValue;
	
	
	/**
	 * Na�ini pla�anja:
	 *  G � gotovina
		K � kartice
		C � �ek
		T � transakcijski ra�un
		O � ostalo (ako ima vi�e na�ina pla�anja i ako ima neki na�in koji nije naveden)
		
		Obvezan
	 */
	@XmlElement(name = "NacinPlac")
	private String typeOfPaying;
	
	
	/**
	 * OIB operatera koji radi na naplatnom ure�aju
	 * Ako se radi o naplatno automatu, dostavlja se OIB firme koja izdaje ra�un
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "OibOper")
	private String oibOperative;
	
	
	/**
	 * Za�titni kod izdavatelja, generira se pomo�u metode getZKI
	 * ima 32 alfanimeri�ka znaka (0-9 i mala slova a-z)
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "ZastKod")
	private String securityCode;
	
	
	/**
	 * Mora biti postavljen na true prilikom naknadne dostave ra�una
	 * defaultno je false
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "NakDost")
	private boolean noteOfRedelivary = false;
	
	
	/**
	 * Kod naknadne fiskalizacije, ako je korisnik morao koristiti 
	 * paragon blokove zbog npr. potpunog prestanka rada naplatnog ure�aja
	 * 
	 * Obvezan ako se prepisuju paragon blokovi te naknadno fiskalizira
	 */
	@XmlElement(name = "ParagonBrRac")
	private String noteOfParagonBill;
	
	
	/**
	 *  U slu�aju naknadne dostave podataka koji su potrebni za analizu
	 *  
	 *  Opcionalan
	 */
	@XmlElement(name = "SpecNamj")
	private String specificPurpose;
	
	
	@XmlTransient
	private SimpleDateFormat dateTimeformatForBill = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss");

	public Bill(){
	}
	
	
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

	public Double getTaxFreeValue() {
		return taxFreeValuePdv;
	}

	public void setTaxFreeValue(Double taxFreeValuePdv) {
		this.taxFreeValuePdv = taxFreeValuePdv;
	}

	public Double getMarginForTaxRate() {
		return marginForTaxRate;
	}

	public void setMarginForTaxRate(Double marginForTaxRate) {
		this.marginForTaxRate = marginForTaxRate;
	}

	public Double getTaxFree() {
		return taxFreeValue;
	}

	public void setTaxFree(Double taxFreeValue) {
		this.taxFreeValue = taxFreeValue;
	}

	public List<Refund> getRefund() {
		return refund;
	}

	public void setRefund(List<Refund> refund) {
		this.refund = refund;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public String getTypeOfPaying() {
		return typeOfPaying;
	}

	public void setTypeOfPlacanje(String typeOfPaying) {
		this.typeOfPaying = typeOfPaying;
	}

	public String getOibOperative() {
		return oibOperative;
	}

	public void setOibOperative(String oibOperative) {
		this.oibOperative = oibOperative;
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
	
	
	
	/**
	 * metoda vra�a ZKI (za�titni kod ra�una) za odre�ene ulazne parametre
	 *
	 * @param oib
	 * @param date
	 * @param brBill
	 * @param noteBussArea
	 * @param noteExcangeDevice
	 * @param totalValue
	 * @param fiskal
	 * @return
	 */
	public String securityCode(String oib, String date, String brBill, String noteBussArea, String noteExcangeDevice, String totalValue, Fiscalization fiskal) {
		
		String tempResult = oib + date.replace("T", " ") + brBill + noteBussArea + noteExcangeDevice + totalValue;
		
		byte[] potpisano = null;
		
		try{
			FileInputStream file_inputstream = new FileInputStream(fiskal.getPathOfJKSCert() + 
					fiskal.getNameOfJKSCert() + CertParameters.EXTENSION_OF_JKS);
			KeyStore keyStore = KeyStore.getInstance(CertParameters.KEYSTORE_TYPE_JKS);
			keyStore.load( file_inputstream, fiskal.getPasswdOfJKSCert().toCharArray() );
			Key privatni = keyStore.getKey( fiskal.getAliasForPairJKSCert(), fiskal.getPasswdOfJKSCert().toCharArray());
			
			Signature biljeznik = Signature.getInstance("SHA1withRSA");
			biljeznik.initSign((PrivateKey)privatni);
			biljeznik.update(tempResult.getBytes());
			potpisano = biljeznik.sign();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String zki = DigestUtils.md5Hex(potpisano);
		
		return zki;
	}
}
