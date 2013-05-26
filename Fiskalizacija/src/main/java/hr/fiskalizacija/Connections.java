package hr.fiskalizacija;

import hr.model.CertParameters;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;


public class Connections{

	 private static final String TRUSTED_MANAGER_TYPE_PKIX = "PKIX";
	 private static final String URL_DEMO = "https://cistest.apis-it.hr:8449/FiskalizacijaServiceTest";
		
	
    /** override metode openConnection, kako bi se postavio timeout */
    URLStreamHandler urlStrHandler = new URLStreamHandler(){
		@Override
		protected URLConnection openConnection(URL url) throws IOException {
			  URL target = new URL(url.toString());
              URLConnection urlConn = target.openConnection();
              
              /** Timeout konekcija */
              urlConn.setConnectTimeout(1000); 
              urlConn.setReadTimeout(15000); 
              return urlConn;
		}   	
    };
 
    
    /**
     *  Slanje SOAP poruku prema serveru Porezne uprave
     * @param message potpisana SOAP poruka koja se šalje servisu
     * @param certParameters objekt u kojem su definirane postavke keystorea za dohvat SSL certifikata 
     * @return SOAP poruku odgovora web servisa
     */
    public SOAPMessage sendSoapMessage(SOAPMessage message, CertParameters certParameters){
       
        SOAPMessage soapResponse = null;
		try {
			
			/** Dohvat SSL certifikata iz keystorea */
			KeyStore keyStore = KeyStore.getInstance(CertParameters.KEYSTORE_TYPE_JKS);
			keyStore.load(new FileInputStream(certParameters.getPathOfJKSCert() + certParameters.getNameOfJKSCert() + 
					CertParameters.EXTENSION_OF_JKS), certParameters.getPasswdOfJKSCert().toCharArray());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TRUSTED_MANAGER_TYPE_PKIX);
			tmf.init(keyStore);
			SSLContext sslctx = SSLContext.getInstance(CertParameters.CERTIFICATE_TYPE_SSL);
			sslctx.init(null, tmf.getTrustManagers(), null);

			/** Postavljanje SSL certifikata u factory te slanje SOAP poruke */
			HttpsURLConnection.setDefaultSSLSocketFactory(sslctx.getSocketFactory());
			SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = sfc.createConnection();
			URL endpoint = new URL(new URL(URL_DEMO), "", urlStrHandler);
			soapResponse = soapConnection.call(message, endpoint);
			soapConnection.close();
		}catch(Exception e){
			e.printStackTrace();	
		}		
        return soapResponse;
    }
    
	
}
