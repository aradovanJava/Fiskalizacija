package hr.fiskalizacija;

import hr.model.CertParameters;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;


public class PrepareCertificate{

    private static final String KEYSTORE_TYPE_PKCS12 = "PKCS12";
    private static final String EXTENSION_OF_PFX = ".pfx";
    private static final String EXTENSION_OF_CER = ".cer";
    private static final String CERTIFICATE_TYPE_X509 = "X.509";
   
    
	public static int convertFromPKCSAndSSLToJKS(CertParameters certParameters){
		int count = 0;
		String aliasNameJKS = "";
		String wrongPassword  = "Pogrešna lozinka .pfx datoteke";
		try {
			/** Instanca keystore certifikata .p12/pfx formatu (PKCS12) */
			KeyStore keystorePFX = KeyStore.getInstance(KEYSTORE_TYPE_PKCS12);
			
			/** Uèitavanje .pfx certifikata pomoæu šifre */
			keystorePFX.load(new FileInputStream(certParameters.getPathOfPFXCert() + certParameters.getNameOfPFXCert() + 
					EXTENSION_OF_PFX), certParameters.getPasswdOfPFXCert().toCharArray());
			
			Enumeration<String> en = keystorePFX.aliases();
			
			/** Prolaz kroz sve aliase */
			while (en.hasMoreElements()){
				String alias = (String) en.nextElement();
				System.out.println("Alias je: " + alias);

				/** Dohvat certifikata s javnim kljuèem koji ce biti zapisan u jks datoteku */
				Certificate[] chain = keystorePFX.getCertificateChain(alias);

				/** Dohvati privatni kljuè certifikata koji æe biti zapisan u .jks datoteku */
				Key privateKey = keystorePFX.getKey(alias, certParameters.getPasswdOfPFXCert().toCharArray());
				
					if(certParameters.getAliasForPairJKSCert() == null){
						certParameters.setAliasForPairJKSCert(certParameters.getOIBFromCert(keystorePFX, alias));
					}
					if(certParameters.getAliasCERCert() == null){
						
						/** ako nije postavljen Alias za SSL certifikat, bit æe SSL32344121 ( broj je oib) */
						certParameters.setAliasCERCert(CertParameters.CERTIFICATE_TYPE_SSL + certParameters.getOIBFromCert(keystorePFX, alias));	
				}
				
				File fileJKS = new File(certParameters.getPathOfJKSCert() + certParameters.getNameOfJKSCert() + CertParameters.EXTENSION_OF_JKS);
				
				/** Instanca keystore certifikata .jks formatu */
				KeyStore keyStoreJKS = KeyStore.getInstance(CertParameters.KEYSTORE_TYPE_JKS);
				
				/** Ako ne postoji .jks file, potrebno ga je kreirati zajedno s putanjom direktorija */
				if (!fileJKS.exists()){
				//	fileJKS.getParentFile().mkdirs();
					fileJKS.createNewFile();
					keyStoreJKS.load(null, certParameters.getPasswdOfJKSCert().toCharArray());
				}else{
					wrongPassword = "Datoteka .jks s tim imenom postoji, potrebno je upisati ispravnu lozinku.";
					keyStoreJKS.load(new FileInputStream(certParameters.getPathOfJKSCert() + certParameters.getNameOfJKSCert() + CertParameters.EXTENSION_OF_JKS), certParameters.getPasswdOfJKSCert().toCharArray());
				}
				aliasNameJKS = certParameters.getAliasForPairJKSCert();
				/** Ako je u ovom trenutku brojaè veæi od 1 znaèi da ima više aliasa, pa ih je potrebno spremiti pod drugim nazivima: alias_brojac
				    To se ne bi trebala dogoditi prilikom fiskalizacije, jer svaki korisnik ima jedan certifikat u svoj keystoreu */
				if(count > 0){
					aliasNameJKS = certParameters.getAliasForPairJKSCert() + "_" + (count+1);
				}
				keyStoreJKS.setKeyEntry(aliasNameJKS, privateKey, certParameters.getPasswdOfJKSCert().toCharArray(), chain);
				
				/** Dio koji uèitava SSL certifikat i postavlja ga u JKS */
				CertificateFactory certFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE_X509);
				Certificate certForSSL = certFactory.generateCertificate(new BufferedInputStream(new FileInputStream(certParameters.getPathOfCERCert() + certParameters.getNameOfCERCert() + EXTENSION_OF_CER)));
				keyStoreJKS.setCertificateEntry(certParameters.getAliasCERCert(), certForSSL);
				
				/** Zapiši uèitani certifikat sa željenim aliasom, šifrom, privatnim kljuèem i certifikatom u .jks datoteku */
				keyStoreJKS.store(new FileOutputStream(certParameters.getPathOfJKSCert() + certParameters.getNameOfJKSCert() + CertParameters.EXTENSION_OF_JKS), certParameters.getPasswdOfJKSCert().toCharArray());
				count++;
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
				throw new RuntimeException(wrongPassword);
		}catch (Exception e){
			e.printStackTrace();
		}
		return count;
	}
	
}
