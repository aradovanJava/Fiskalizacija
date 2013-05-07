package hr.fiskalizacija;

import java.io.FileInputStream;
import java.security.KeyStore;

import hr.model.BusinessAreaRequest;
import hr.model.CertParameters;

public class Fiscalization extends CertParameters{

	
	public Fiscalization(String filePath, String fileName, String password) {
		super(filePath, fileName, password);
	}

	
	public Fiscalization(String filePath, String fileName, String password, String aliasForCertInJKS) {
		super(filePath, fileName, password, aliasForCertInJKS);
	}
	
	
	public static int convertFromPKCSAndSSLToJKS(Fiscalization fiskal){
		return PrepareCertificate.convertFromPKCSAndSSLToJKS(fiskal);
	}
	
	
	public String sendEchoMessage(Fiscalization fiskal){
		return writeSoap(new Connections().sendSoapMessage(new CreateXmls().createEchoMessage(), fiskal));
	}
	
	
	public String sendSoapBusinessArea(Fiscalization fiskal, BusinessAreaRequest businessAreaRequest){
		return writeSoap(new Connections().sendSoapMessage(new SignVerify().signSoap(new CreateXmls().createSoapMessage(new CreateXmls().businessAreaXml(businessAreaRequest)), fiskal),fiskal));
	}
	
	
	public String getOIBFromCert(Fiscalization fiskal){
		String oib = null;
		try {
			KeyStore keyStoreJKS = KeyStore.getInstance(KEYSTORE_TYPE_JKS);
			keyStoreJKS.load(new FileInputStream(fiskal.getPathOfJKSCert() + fiskal.getNameOfJKSCert() + EXTENSION_OF_JKS), fiskal.getPasswdOfJKSCert().toCharArray());
			oib = getOIBFromCert(keyStoreJKS, fiskal.getAliasForPairJKSCert());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oib;
	}
}
