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
		
	
    // override metode openConnection, kako bi se postavio timeout
    URLStreamHandler urlStrHandler = new URLStreamHandler(){
		@Override
		protected URLConnection openConnection(URL url) throws IOException {
			  URL target = new URL(url.toString());
              URLConnection urlConn = target.openConnection();
              // Postavke timeout-a konekcije
              urlConn.setConnectTimeout(1000); 
              urlConn.setReadTimeout(20000); 
              return urlConn;
		}   	
    };
 
    // Metoda koja salje SOAP poruku prema serveru koji vraca JIR
    public SOAPMessage sendSoapMessage(SOAPMessage message, CertParameters certParameters){
       
        SOAPMessage soapResponse = null;
		try {
			KeyStore keyStore = KeyStore.getInstance(CertParameters.KEYSTORE_TYPE_JKS);
			keyStore.load(new FileInputStream(certParameters.getPathOfJKSCert() + certParameters.getNameOfJKSCert() + CertParameters.EXTENSION_OF_JKS), certParameters.getPasswdOfJKSCert().toCharArray());
      
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TRUSTED_MANAGER_TYPE_PKIX);
			tmf.init(keyStore);
      
			SSLContext sslctx = SSLContext.getInstance(CertParameters.CERTIFICATE_TYPE_SSL);
			sslctx.init(null, tmf.getTrustManagers(), null);

			HttpsURLConnection.setDefaultSSLSocketFactory(sslctx.getSocketFactory());
      
			SOAPConnectionFactory sfc = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = sfc.createConnection();
   
			URL endpoint = new URL(new URL(URL_DEMO), "", urlStrHandler);
			soapResponse = soapConnection.call(message, endpoint);
      
			soapConnection.close();
		} catch (Exception e) {
			e.printStackTrace();	
		}
				
        return soapResponse;
    }
    
	
}
