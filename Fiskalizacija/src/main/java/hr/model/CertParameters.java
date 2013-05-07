package hr.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.soap.SOAPMessage;

public class CertParameters{

	private static final String PATTERN_FOR_GET_OIB = "(\\d{11})";
	public static final String EXTENSION_OF_JKS = ".jks";
    public static final String KEYSTORE_TYPE_JKS = "JKS";
    public static final String CERTIFICATE_TYPE_SSL = "SSL";
	
    
	private String pathOfPFXCert; 
	private String nameOfPFXCert;
	private String passwdOfPFXCert;
	private String pathOfJKScert;
	private String nameOfJKSCert;
	private String passwdOfJKSCert;
	private String nameOfCERCert;
	private String aliasCERCert;
	private String aliasForPairJKSCert;
	private String pathOfCERCert;
	
	
	public CertParameters(String filePath, String fileName, String password){
		this.pathOfPFXCert = filePath;
		this.nameOfPFXCert = fileName;
		this.passwdOfPFXCert = password;
		this.pathOfJKScert = filePath;
		this.nameOfJKSCert = fileName;
		this.passwdOfJKSCert = password;
		this.nameOfCERCert = fileName;
		this.pathOfCERCert = filePath;
		
		/**
		 * Ako se koristi konstruktor s tri parametra, a .jks datoteka je veæ kreirana, potrebno je iz postojeæeg JKS keystorea preuzeti alias.
		 * Moguæe ga je preuzeti samo ako se u .jks datoteci nalazi jedan par privatni kljuè - certifikat. Ako ih ima više, ostat æe null, te ga je potrebno prije digitalnog potpisa
		 * postaviti setAliasForPairJKSCert metodom ili æe javiti grešku  
		 * 
		 */
		
		File fileJKS = new File(filePath + fileName + EXTENSION_OF_JKS);
		if(fileJKS.exists()){
			try {
				KeyStore keyStoreJKS = KeyStore.getInstance(KEYSTORE_TYPE_JKS);
				keyStoreJKS.load(new FileInputStream(filePath + fileName + EXTENSION_OF_JKS), password.toCharArray());
				Enumeration<String> en = keyStoreJKS.aliases();
				
				int count = 0;
				String aliasOfLastPair = "";
				while (en.hasMoreElements()){
					String alias = (String) en.nextElement();
					System.out.println("Alias je: " + alias);
					if(!alias.toUpperCase().contains(CERTIFICATE_TYPE_SSL)){
						count ++;
						aliasOfLastPair = alias;
					}
				}
				if(count == 1){
					aliasForPairJKSCert = aliasOfLastPair;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public CertParameters(String filePath, String fileName, String password, String aliasForCertInJKS){
		this(filePath, fileName, password);
		aliasForPairJKSCert = aliasForCertInJKS;
		aliasCERCert = aliasForCertInJKS;
	}
	
	
	
	public CertParameters(File paramFile){
		// DEFINIRATI NA KOJI NAÈIN ÆE BITI ZAPIS U DATOTECI, KAKO BI SE DEFINIRAO KONSTRUKTOR
	}
	
	
	public String getPathOfPFXCert(){
		return pathOfPFXCert;
	}
	public void setPathOfPFXCert(String pathOfPFXCert){
		this.pathOfPFXCert = pathOfPFXCert;
	}
	public String getNameOfPFXCert(){
		return nameOfPFXCert;
	}
	public void setNameOfPFXCert(String nameOfPFXCert){
		this.nameOfPFXCert = nameOfPFXCert;
	}
	public String getPasswdOfPFXCert(){
		return passwdOfPFXCert;
	}
	public void setPasswdOfPFXCert(String passwdOfPFXCert){
		this.passwdOfPFXCert = passwdOfPFXCert;
	}
	public String getPathOfJKSCert(){
		return pathOfJKScert;
	}
	public void setPathOfJKSCert(String pathOfJKScert){
		this.pathOfJKScert = pathOfJKScert;
	}
	public String getNameOfJKSCert(){
		return nameOfJKSCert;
	}
	public void setNameOfJKSCert(String nameOfJKSCert){
		this.nameOfJKSCert = nameOfJKSCert;
	}
	public String getPasswdOfJKSCert(){
		return passwdOfJKSCert;
	}
	public void setPasswdOfJKSCert(String passwdOfJKSCert){
		this.passwdOfJKSCert = passwdOfJKSCert;
	}
	public String getAliasForPairJKSCert(){
		return aliasForPairJKSCert;
	}
	public void setAliasForPairJKSCert(String aliasForPairJKSCert){
		this.aliasForPairJKSCert = aliasForPairJKSCert;
	}
		
	public String getNameOfCERCert(){
		return nameOfCERCert;
	}

	public void setNameOfCERCert(String nameOfCERCert){
		this.nameOfCERCert = nameOfCERCert;
	}

	public String getAliasCERCert(){
		return aliasCERCert;
	}

	public void setAliasCERCert(String aliasCERCert){
		this.aliasCERCert = aliasCERCert;
	}
	
	public String getPathOfCERCert() {
		return pathOfCERCert;
	}

	public void setPathOfCERCert(String pathOfCERCert) {
		this.pathOfCERCert = pathOfCERCert;
	}
	
	
	public String writeSoap(SOAPMessage message){
        ByteArrayOutputStream out = null;
		try {
			SOAPMessage msg = message;
			out = new ByteArrayOutputStream();
				msg.writeTo(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return out.toString();
    }
	
	public String getOIBFromCert(KeyStore keystore, String alias) throws KeyStoreException{
			
			String oib = "";
			// pokušaj dohvata certifikata za taj alias
			Certificate cert = keystore.getCertificate(alias);
			
			// Konverzija certfikata za dohvatpodataka
			X509Certificate x509cert = (X509Certificate) cert;
			// Dohvati podatke koji sadrze i OIB
			
			Principal principal = x509cert.getSubjectDN();
			String subjectDn = principal.getName();
			System.out.println("Podatci iz SubjectDN-a: " + subjectDn);
			
			// Trazi OIB - 11 brojeva
			Pattern p = Pattern.compile(PATTERN_FOR_GET_OIB);
			Matcher m = p.matcher(subjectDn);
			// Dohvat OIB-a i postavljanje u naziv aliasa unutar .jks 
			if (m.find()){
				oib = m.group(1);
				System.out.println("OIB iz certifikata je: " + oib);	
			}
			return oib;
	}
	
}
