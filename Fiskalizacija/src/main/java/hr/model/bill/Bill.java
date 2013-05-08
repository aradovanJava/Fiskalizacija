package hr.model.bill;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Klasa za kreiranje XML tagova raèuna, potrebno ju je popuniti s neophodnim
 * i opcionalnim elementima koji se nalaze na raèunu te se šalju prema serveru porezne uprave 
 * 
 *
 */
@XmlRootElement(name = "Racun")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bill{

	
	/**
	 * OIB firme koja je kreirala te šalje raèun prema servisu porezne uprave 
	 * Obavezan podatak
	 */
	@XmlElement(name = "Oib")
	private String oib;
	
	
	/**
	 * Oznaka da li je firma koja kreira raèun u sustavu PDV-a
	 * Obavezan podatak
	 */
	@XmlElement(name = "USustPdv")
	private boolean havePDV;
	
	
	/**
	 *  Datum i vrijeme koji se ispisuju na raèunu
	 *  Obavezan podatak 
	 */
	@XmlElement(name = "DatVrijeme")
	private String dateTime;
	
	
	/**
	 * Oznaka slijednosti brojeva raèuna, da li redoslijed brojeva raèuna ide prema:
	 *  - naplanom ureðaju - "N" (defaultno)
	 *  - poslovnom prostoru - "D" 
	 *  
	 * Obavezan podatak 
	 */
	@XmlElement(name = "OznSlijed")
	private char noteOfOrder = 'N';
	
	
	/**
	 * Klasa koja sadrži: 
	 * - broj raèuna
	 * - oznaku poslovnog prostora
	 * - oznaku naplatnog ureðaja
	 * 
	 * Prilikom ispisa potrebno ga je ispisati u obliku:
	 * brojèana oznaka raèuna/oznaka poslovnog prostora/oznaka naplatnog ureðaja
	 * Obavezan podatak 
	 */
	@XmlElement(name = "BrRac")
	private BillNumber billNumber;
	
	
	/**
	 * Lista klase TaxRate koja sadrži:
	 * - poreznu stopu
	 * - osnovicu
	 * - iznos poreza
	 * U sluèaju da nema PDV-a, nije potrebno ništa unesti u listu, pa se neæe ni izgenerirati u XML-u
	 * Ako ima više razlièitih stopa PDV-a, potrebno je za svaku stopu unijeti objekt klase TaxRate u listu 
	 * 
	 * Obavezno ako ima PDV-a 
	 */
	@XmlElementWrapper(name = "Pdv")
	@XmlElement(name = "Porez")
	private List<TaxRate> listPDV;
	
	
	/**
	 * Lista klase TaxRate koja sadrži:
	 * - poreznu stopu
	 * - osnovicu
	 * - iznos poreza
	 * U sluèaju da nema PNP-a (poreza na potrošnju), nije potrebno ništa unesti u listu, pa se neæe ni izgenerirati u XML-u
	 * Ako ima više razlièitih stopa PNP-a, potrebno je za svaku stopu unesti objekt klase TaxRate u listu 
	 * 
	 * Obavezno ako ima PNP-a 
	 */
	@XmlElementWrapper(name = "Pnp")
	@XmlElement(name = "Porez")
	private List<TaxRate> listPNP;
	
	
	
	/**
	 * Lista klase TaxRate koja sadrži:
	 * - naziv poreza
	 * - poreznu stopu
	 * - osnovicu
	 * - iznos poreza
	 * U sluèaju da nema ostalih poreza, nije potrebno ništa unesti u listu, pa se neæe ni izgenerirati u XML-u
	 * Ako ima više razlièitih stopa Ostalih poreza, potrebno je za svaku stopu unesti objekt klase TaxRate u listu 
	 * 
	 * Obavezno ako ima Ostalih poreza 
	 */
	@XmlElementWrapper(name = "OstaliPor")
	@XmlElement(name = "Porez")
	private List<TaxRate> listOtherTaxRate;
	

	/**
	 * U sluèaju da postoji osloboðenje od PDV-a, potrebno je unesti ukupni iznos osloboðenja
	 * 
	 * Obvezno u sluèaju da ima osloboðenja od PDV-a
	 */
	@XmlElement(name = "IznosOslobPdv")
	private double taxFreeValuePdv;

	
	/**
	 * Unosi se ukupni iznos koji se oporezuje ako je marža definirana na raèunu
	 * 
	 * Obvezan ako postoji posebno oporezivanje prema èlanku 22. Zakona o PDV-u
	 */
	@XmlElement(name = "IznosMarza")
	private double marginForTaxRate;
	
	
	/**
	 * Iznos koji ne podliježe oporezivanju
	 * 
	 * Obvezan ako postoji iznos koji ne podliježe oporezivanju
	 */
	@XmlElement(name = "IznosNePodlOpor")
	private double taxFreeValue;
	
	
	/**
	 * Lista naknada koje se nalaze na raèunu
	 * Za svaki novi naziv naknade potrebno je postaviti novi objekt klase Refund u listu
	 * 
	 * Obvezan ako postoji naknada na raèunu
	 */
	@XmlElementWrapper(name = "Naknade")
	@XmlElement(name = "Naknada")
	private List<Refund> refund;
	
	
	/**
	 * Ukupan iznos na raèunu
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "IznosUkupno")
	private double totalValue;
	
	
	/**
	 * Naèini plaæanja:
	 *  G – gotovina
		K – kartice
		C – èek
		T – transakcijski raèun
		O – ostalo (ako ima više naèina plaæanja i ako ima neki naèin koji nije naveden)
		
		Obvezan
	 */
	@XmlElement(name = "NacinPlac")
	private String typeOfPaying;
	
	
	/**
	 * OIB operatera koji radi na naplatnom ureðaju
	 * Ako se radi o naplatno automatu, dostavlja se OIB firme koja izdaje raèun
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "OibOper")
	private String oibOperative;
	
	
	/**
	 * Zaštitni kod izdavatelja, generira se pomoæu metode getZKI
	 * ima 32 alfanimerièka znaka (0-9 i mala slova a-z)
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "ZastKod")
	private String securityCode;
	
	
	/**
	 * Mora biti postavljen na true prilikom naknadne dostave raèuna
	 * defaultno je false
	 * 
	 * Obvezan
	 */
	@XmlElement(name = "NakDost")
	private boolean noteOfRedelivary = false;
	
	
	/**
	 * Kod naknadne fiskalizacije, ako je korisnik morao koristiti 
	 * paragon blokove zbog npr. potpunog prestanka rada naplatnog ureðaja
	 * 
	 * Obvezan ako se prepisuju paragon blokovi te naknadno fiskalizira
	 */
	@XmlElement(name = "ParagonBrRac")
	private String noteOfParagonBill;
	
	
	/**
	 *  U sluèaju naknadne dostave podataka koji su potrebni za analizu
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

	public char getNoteOfOrder() {
		return noteOfOrder;
	}

	public void setNoteOfOrder(char noteOfOrder) {
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

	public double getMarginForTaxRate() {
		return marginForTaxRate;
	}

	public void setMarginForTaxRate(double marginForTaxRate) {
		this.marginForTaxRate = marginForTaxRate;
	}

	public double getTaxFree() {
		return taxFreeValue;
	}

	public void setTaxFree(double taxFreeValue) {
		this.taxFreeValue = taxFreeValue;
	}

	public List<Refund> getRefund() {
		return refund;
	}

	public void setRefund(List<Refund> refund) {
		this.refund = refund;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
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
	 * metoda vraæa ZKI (zaštitni kod raèuna) za odreðene ulazne parametre
	 * @param sDate
	 * @param sBrojDok
	 * @param sSifObjekta
	 * @param sSifBlag
	 * @param sIznos
	 * @return
	 */
	public String securityCode(String sDate, String sBrojDok, String sSifObjekta, String sSifBlag, String sIznos) {
		String sMedjRez = "";
		String sRezMD5 = "";
		try {		// sPK + sOIB + ubaciti u red ispod
			sMedjRez =  sDate + sBrojDok + sSifObjekta + sSifBlag + sIznos;

			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(sMedjRez.getBytes());

			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);

			for (int i = 0; i < len; i++) {
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}

			sRezMD5 = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return sRezMD5;
	}
	
}
