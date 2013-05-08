package hr.fiskalizacija;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import hr.model.Address;
import hr.model.AddressData;
import hr.model.BusinessArea;
import hr.model.BusinessAreaRequest;
import hr.model.RequestHeader;
import hr.model.bill.Bill;
import hr.model.bill.BillNumber;
import hr.model.bill.BillRequest;
import hr.model.bill.Refund;
import hr.model.bill.TaxRate;

public class Test {

	public static void main(String[] args){
		
	/**
		 * Jedan objekt klase Fiscalization predstavlja jedan .pfx keystore. Ako unutar ima više aliasa (u cerifikatu za fiskalizaciju je samo jedan) aliasi koji æe biti u .jks su:
		 * prvi: alias, drugi: alias_2, treæi: alis_3....
		 * Moguæe je iz više .pfx keystorea aliase smjestiti u jedan .jks, potencijalna opasnost je da se ukoliko se koristi isti alias i isti .jks novi par kljuèeva prepiše preko starog,
		 * U tom sluèaju nakon kreiranja objekta fiskal potrebno je postaviti ime .jks filea u koji se spremaju svi certifikati fiskal.setNameOfJKSCert(nameOfJKSCert)
		 */
		
		Fiscalization fiskal = new Fiscalization("", "FiskalCert", "Mar+ininUr3d");
			
			
			/*
			Ova funkcija se pozva samo jednom, te je potrebno da oba certifikata budu istog naziva i u istom direktoriju - putanji (npr. FiskalCert.cer i FiskalCert.pfx)
			Ako nemaju istu putanju ili naziv, moguæe je postaviti setterima sve vrijednosti. Defaultni alias za ssl certifikat je "SSL" + oib
			*/	
		//	Fiscalization.convertFromPKCSAndSSLToJKS(fiskal);
				
			// Slanje i ispis echo odgovora od servisa porezne uprave
				System.out.println(fiskal.sendEchoMessage(fiskal));
			
				
				Address adress = new Address();
				adress.setCity("Zagreb");
				adress.setStreet("Zagrebacka");
				adress.setHouseNumber("100");
				adress.setExtrahouseNumber("A");
				adress.setSettlement("Rudes");
				adress.setZipCode("10000");
				
				AddressData adressData = new AddressData();
				adressData.setAdress(adress);
				
				BusinessArea businessArea = new BusinessArea();
				businessArea.setAdressData(adressData);
				
				
				// kod GregoranCalendara može biti problem u mjesecu, jer sijeèanj je int 0, ako korisnik to ne zna, može doæi do problema
				businessArea.setDateOfusage(new GregorianCalendar(2013, GregorianCalendar.APRIL, 1));
				
				// Ovo je šifra poslovnog prostora, moguæe je slati izmjene, ali ne bi trebalo previše mijenjati, jer mislim da svaka promjena otvara novi poslovni prostor
				businessArea.setNoteOfBusinessArea("ODV1");
				// businessArea.setNoteOfClosing("z");
				businessArea.setOib(fiskal.getOIBFromCert(fiskal));
				//businessArea.setSpecificPurpose("spec namjena");
				// Slobodan tekst, ukoliko ima više radnih vremena u bazi moguæe je impementirati funkciju za kreiranje stringa
				businessArea.setWorkingTime("Pon:08-11h Uto:15-17");
				BusinessAreaRequest businessAreaRequest = new BusinessAreaRequest(new RequestHeader(), businessArea);
				

			/*	
		 	// Ispis xml-a nakon kreiranja xml-a
			
			System.out.println(xml.businessAreaXml(businessAreaRequest));
		
			// Ispis nakon kreiranja soap poruke, neposredno prije slanja
			// System.out.println(fiskal.writeSoap(new CreateXmls().createSoapMessage(new CreateXmls().businessAreaXml(businessAreaRequest))));
				CreateXmls xml = new CreateXmls();
			SignVerify signVerify = new SignVerify();
			System.out.println(fiskal.writeSoap(signVerify.signSoap(xml.createSoapMessage(xml.businessAreaXml(businessAreaRequest)), fiskal)));
		*/	
			
				// potrebno je postaviti alias, ako ima više parova privatni kljuè - certifikat, ili koristi konstruktor s 4 parametara
			//	fiskal.setAliasForPairJKSCert("60251384564");
			
			//	System.out.println(fiskal.sendSoapBusinessArea(fiskal, businessAreaRequest));

				
				
				
			//	Kreiranje raèuna
//----------------------------------------------------------------------------------------------------------------------------------------
				
				Refund refund = new Refund("Naziv naknade", 5.4);
				
				BillNumber billNumber = new BillNumber(1, "ODV1", "NAPL1");
				
				List<TaxRate> listPdv = new ArrayList<TaxRate>();
				listPdv.add(new TaxRate(25, 400, 20, ""));
				listPdv.add(new TaxRate(10, 500, 15.4, ""));
				
				List<TaxRate> listPnp = new ArrayList<TaxRate>();
				listPnp.add(new TaxRate(30, 100, 10, ""));
				listPnp.add(new TaxRate(20, 200.1, 20, ""));
				
				List<TaxRate> listOtherTaxRate = new ArrayList<TaxRate>();
				listOtherTaxRate.add(new TaxRate(40, 453.3, 12, "Naziv1"));
				listOtherTaxRate.add(new TaxRate(27, 445, 50, "Naziv2"));
				
				
				Bill bill = new Bill();
				
				bill.setOib("12345678901");
				bill.setHavePDV(true);
				bill.setDateTime(new GregorianCalendar(2013, GregorianCalendar.MAY, 5));
				bill.setNoteOfOrder('P');
				bill.setBillNumber(billNumber);
				bill.setListPDV(listPdv);
				bill.setListPNP(listPnp);
				bill.setListOtherTaxRate(listOtherTaxRate);
				bill.setTaxFreeValue(23.53);
				bill.setMarginForTaxRate(32.544);
				bill.setTaxFree(5);
				//bill.setRefund(refund);
				bill.setTotalValue(456);
				bill.setTypeOfPlacanje("G");
				bill.setOibOperative("3456212343");
				bill.setSecurityCode("ZKI");
				bill.setNoteOfRedelivary(false);
				
				
				BillRequest billRequest = new BillRequest(new RequestHeader(), bill);
				
				CreateXmls createXmls = new CreateXmls();
				
				System.out.println(createXmls.createXmlForRequest(billRequest));
				
				
				
	}

}
