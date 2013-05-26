package hr.fiskalizacija;

import hr.model.CertParameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Klasa koja vr�i potpisivanje privatnim klju�em, te verifikaciju potpisa javnim klju�em
 */
public class SignVerify {

	 private static final String PATTERN_FOR_GET_ID = "//*[@Id='%s']";
	 private static final String BUSINESS_AREA_ID = "poslovniProstorId";
	 private static final String BILL_ID = "racunId";
	 private static final String NO_ID_FOR_SIGN = "Program ne pronalazi id za potpis";
	 private static final String JSR_105_PROVIDER = "jsr105Provider";
	 private static final String XML_D_SIG_RI = "org.jcp.xml.dsig.internal.dom.XMLDSigRI";
	 private static final String DOM = "DOM";
	 private static final String TAG_SIGNATURE = "Signature";
	 private static final String NO_TAG_SIGNATURE = "Ne postoji Signature element u poruci koju je server vratio!";
	 private static final String NULL_KEYINFO = "Null KeyInfo objekt!";
	 private static final String ALGORITHM_NOT_MATCH = "Metoda algoritma kriptiranja ne odgovara metodi algoritma iz XML-a.";
	 private static final String NO_X509DATA = "Nije prona�en elemet X509Data.";
	 private static final String DSA = "DSA";
	 private static final String RSA = "RSA";
	 
	 
	 /**
	  * Metoda za potpisivanje SOAP poruke
	  * 
	  * @param soapMessage SAOP poruka koju je potrebno potpisati
	  * @param certParameters objekt koji sadr�i podatke o pristupu certifikatu 
	  * @return vra�a potpisanu SOAP poruku
	  */
	   public SOAPMessage signSoap(SOAPMessage soapMessage, CertParameters certParameters){
		   
	       SOAPPart soapPart = null;
	       soapPart = soapMessage.getSOAPPart();
	       soapPart.getContentId();
	       
	        Node nodepartForSign = null;
	        XPathExpression xPathExp = null;
	        NodeList nodeList;
	        ArrayList<Transform> transforms = null;
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        try{
	        	
	        		/** 
	        		 * Tra�enje id-a po kojem se vr�i potpis u SOAP poruci, defaultno se postavlja "racunId",
	        		 * a ako se radi o SOAP poruci koja predstavlja poslovni prostor onda je "poslovniProstorId"
	        		 **/
	    			ByteArrayOutputStream out = new ByteArrayOutputStream();
					soapMessage.writeTo(out);
	    			String idForSign = BILL_ID;
	    			if(new String(out.toByteArray()).contains(BUSINESS_AREA_ID)){
	    				idForSign = BUSINESS_AREA_ID;
	    			}
	        
	    			/** Pronalazak elemeta s id prema koje se vr�i potpis unutar soap poruke */
	    			xPathExp = xpath.compile(String.format(PATTERN_FOR_GET_ID, idForSign));
				    nodeList = (NodeList) xPathExp.evaluate(soapPart, XPathConstants.NODESET);
				    
				    /** Ako nije prona�en element koji sadr�i id prema kojem se potpisuje, baci exception */
				    if (nodeList.getLength() == 0){
				        throw new RuntimeException(NO_ID_FOR_SIGN);
				    }
				    nodepartForSign = nodeList.item(0);
      			
				final XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance(DOM,
						(Provider) Class.forName(System.getProperty(JSR_105_PROVIDER, XML_D_SIG_RI)).newInstance());
   
				/** Elemeti koji �e biti dodani u XML nakon potpisa */
				transforms = new ArrayList<Transform>(){
					private static final long serialVersionUID = 1L;
					TransformParameterSpec transformSpec = null;
					{add(sigFactory.newTransform(Transform.ENVELOPED, transformSpec ));
				    add(sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, transformSpec));
				    }};
      
					Reference ref = sigFactory.newReference("#" + idForSign, sigFactory.newDigestMethod(DigestMethod.SHA1, null), transforms, null, null);
				    
					C14NMethodParameterSpec c14NMethodParameterSpec = null;
					SignedInfo signedInfo = sigFactory.newSignedInfo(sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE, c14NMethodParameterSpec),
					            sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null),Collections.singletonList(ref));
	   
					/** Kreiranje KeyInfo sa podacima koji su uneseni u objekt klase XMLSignatureFactory  */
					KeyInfoFactory keyinfoFactory = sigFactory.getKeyInfoFactory();
				    
				    
				/** U�itavanje privatnog klju�a za potpisivanje */
				KeyStore keyStore = KeyStore.getInstance(CertParameters.KEYSTORE_TYPE_JKS);
				keyStore.load(new FileInputStream(certParameters.getPathOfJKSCert() + certParameters.getNameOfJKSCert() + 
						CertParameters.EXTENSION_OF_JKS), certParameters.getPasswdOfJKSCert().toCharArray());
				PrivateKey privateKey = (PrivateKey) keyStore.getKey(certParameters.getAliasForPairJKSCert(), certParameters.getPasswdOfJKSCert().toCharArray());
				X509Certificate cert = (X509Certificate) keyStore.getCertificate(certParameters.getAliasForPairJKSCert());
   
				/** Dodavanje dodatnih podataka i tagova vezanih za certifikat */
				X509IssuerSerial issuer = keyinfoFactory.newX509IssuerSerial(cert.getIssuerX500Principal().getName(), cert.getSerialNumber());   
				ArrayList<Object> x509Content = new ArrayList<Object>();
				x509Content.add(cert);
				x509Content.add(cert.getSubjectX500Principal().getName());
				x509Content.add(issuer);
				X509Data x509Data = keyinfoFactory.newX509Data(x509Content);
				KeyInfo keyInfo = keyinfoFactory.newKeyInfo(Collections.singletonList(x509Data));
      
				/** Kreiranje konteksta za potpis, postavlja se privatni klju� i dio koji �e se potpisati */
				DOMSignContext dsc = new DOMSignContext(privateKey, nodepartForSign);
				XMLSignature signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
   
				/** Potpisivanje XML-a */
				signature.sign(dsc);
   
				/** Pohrana promjene na SOAP poruci */
				soapMessage.saveChanges();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return soapMessage;
	    }

	   
	   
	   	/**
	   	 *  Metoda za verifikaciju potpisane SOAP poruke koju vra�a porezni servis
	   	 * 
	   	 * @param soapMessage SOAP poruka za koju se provjerava potpis
	   	 * @return tru ili false ovisno o tome da li je provjera bila uspje�na il ne
	   	 * @throws Exception
	   	 */
	    Boolean verifyMessage(SOAPMessage soapMessage){
	    	
	    	boolean isCorectSign = false; 
	    	
	        try {
				/** Preuzimanje XML-a u objekt klase Document iz SOAP poruke koju je poslao servis porezne uprave */
				ByteArrayOutputStream byArOutStream = new ByteArrayOutputStream();
				soapMessage.writeTo(byArOutStream);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
				Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(byArOutStream.toByteArray()));
				byArOutStream.close();
				
				/** Preuzimanje Signature elementa */
				NodeList nodeList = doc.getElementsByTagNameNS(XMLSignature.XMLNS, TAG_SIGNATURE);
				
				/** Provjera da li postoji Signature elemet, ako ne, baca se exception s gre�kom */
				if (nodeList.getLength() == 0){
				    throw new RuntimeException(NO_TAG_SIGNATURE);
				}
				
				/** Kreiranje validacijskog konteksta za provjeru potpisa */
				DOMValidateContext valContext = new DOMValidateContext(new KeyValueKeySelector(), nodeList.item(0)); 
				XMLSignature signature = XMLSignatureFactory.getInstance(DOM).unmarshalXMLSignature(valContext);
  
				/**
				 *  Provjeri da li je dokument ispravno potpisan, provjera se radi s klju�em koji
				 * je do�ao s porukom unutar XML-a
				 * */
				isCorectSign = signature.validate(valContext);
			}catch (Exception e){
			e.printStackTrace();
			}
	        return isCorectSign;
	    }
	   
	     
	   
	   /**
	    * 
	    * Kreiranjem instance klase preuzima se javni klju� iz XML-a koji je poslao servis porezne
	    * uprave zajedno s porukom.
	    * S obzirom da naslje�uje klasu KeySelector kreiranje instance izvr�ava se overrideana metoda
	    * select, te se pomo�u su�elja KeySelectorResult vra�a javni klju� koji je preuzet iz XML-a
	    *
	    */
	    private class KeyValueKeySelector extends KeySelector{
	        
	    	@SuppressWarnings("unchecked")
			public KeySelectorResult select(KeyInfo keyInfo, KeySelector.Purpose purpose, 
	    			AlgorithmMethod method, XMLCryptoContext context) throws KeySelectorException{
	            
	    		if(keyInfo == null){
	                throw new KeySelectorException(NULL_KEYINFO);
	            }
	            
	            /** Prolazak kroz sve elemete KeyInfo, iako je prvi element X509Data */
	            for(XMLStructure xmlStructure : (List<XMLStructure>) keyInfo.getContent()){
	              
	            	/** Ako je element XmlStructure instanca X509Data */
	                if(xmlStructure instanceof X509Data){
	                	PublicKey pk = null;
	                	
	                	/** Prolazak kroz stukturu X509Data elementa */
	                	for(Object data : ((X509Data) xmlStructure).getContent()){
		                    
	                		/** Ako je objek data tipa X509Certificate prona�en je elemet u kojem se nalazi javni klju� */
	                		if(data instanceof X509Certificate){
		                        	pk = ((X509Certificate) data).getPublicKey();
		                        	
		                        	/** Provjera da li metoda algoritma odgovara metodi algoritma koja se nalazi u XML-u */
		    	                    if (algEquals(method.getAlgorithm(), pk.getAlgorithm())){
		    	                        return new SimpleKeySelectorResult(pk);
		    	                    }else{
		    	                    	throw new KeySelectorException(ALGORITHM_NOT_MATCH);
		    	                    }
		                        }
	                	}
	                }
	            }
	            throw new KeySelectorException(NO_X509DATA);
	        }

	    	
	    	
	    	
	    	/**
	    	 * Provjera da je algoritam s kojim se izvr�io digitalni potpis komatibilan s metodom koja se nalazi u XML-u
	    	 * 
	    	 * @param algURI url algoritma s kojom je izvr�en digitalni potpis 
	    	 * @param algName naziv algoritma koji je kori�ten prilikom digitalnog potpisa
	    	 * @return true ako algoritam i url algoritma odgovaraju DSA ili RSA algoritmima, ina�e false
	    	 */
	        boolean algEquals(String algURI, String algName){
	            boolean checkAlg = false;
	        	if(algName.equalsIgnoreCase(DSA) && algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)){
	        		checkAlg = true;
	            }else if(algName.equalsIgnoreCase(RSA) && algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1)){
	            	checkAlg = true;
	            } 
	                return checkAlg;
	        }
	    }

	    
	    
	    /**
	     * Kreiranjem instance poziva se overrideana metoda select iz klase KeyValueKeySelector,
	     * te se pomo�u getKey metode umjesto instance dohva�a javni klju� koji je preuzet iz XML-a
	     */
	    private class SimpleKeySelectorResult implements KeySelectorResult{
	        
	    	private PublicKey pk;
	        
	        SimpleKeySelectorResult(PublicKey pk){
	            this.pk = pk;
	        }
	        
	        /** 
	         * Overrideana metoda iz su�elja KeySelectorResult koja prilikom kreiranja 
	         * instance klase SimpleKeySelectorResult vra�a javni klju� koji je preuzet 
	         * iz XML-a odgovora
	         *  
	         *  */
	        public Key getKey(){ 
	        	return pk;
	        }
	    }
}