package hr.fiskalizacija;



import hr.model.CertParameters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;

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
 * Klasa koja vrši potpisivanje privatnim kljuèem, te verifikaciju potpisa javnim kljuèem 
 * 
 *
 */
public class SignVerify {

	private static final String POSLOVNI_PROSTOR_ID = "poslovniProstorId";
	 private static final String PATTERN_FOR_GET_ID = "//*[@Id='%s']";
	
	
	 /**
	  * Metoda za potpisivanje SOAP poruke
	  * 
	  * vraæa potpisanu SOAP poruku
	  * 
	  * @param soap
	  * @param certParameters
	  * @return
	  */
	   public SOAPMessage signSoap(SOAPMessage soap, CertParameters certParameters){
		   
	        // Ucitaj SOAP poruku i dohvati sadrzaj
	        SOAPMessage doc = soap;
	        SOAPPart soapPart = doc.getSOAPPart();
	       
	        // Pronadi id koji treba potpisati
	        Node nodeToSign = null;
	        Node sigParent = null;
	        String referenceURI = null;
	        XPathExpression expr = null;
	        NodeList nodes;
	        ArrayList<Transform> transforms = null;
	   
	        XPathFactory factory = XPathFactory.newInstance();
	        XPath xpath = factory.newXPath();
	       
	        try{
				expr = xpath.compile(String.format(PATTERN_FOR_GET_ID, POSLOVNI_PROSTOR_ID));
				    nodes = (NodeList) expr.evaluate(soapPart, XPathConstants.NODESET);
				    if (nodes.getLength() == 0){
				        throw new RuntimeException("Program ne pronalazi node sa id-em: " + POSLOVNI_PROSTOR_ID);
				    }
   
				nodeToSign = nodes.item(0);
				sigParent = nodeToSign;
				referenceURI = "#" + POSLOVNI_PROSTOR_ID;
      
				 
				// Pripremi potpis
				String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
      
				final XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance("DOM",(Provider) Class.forName(providerName).newInstance());
   
				// Transformacije koje se koriste prilikom potpisivanja
				transforms = new ArrayList<Transform>(){
					
					private static final long serialVersionUID = 1L;

				{add(sigFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null ));
				    add(sigFactory.newTransform(CanonicalizationMethod.EXCLUSIVE, (TransformParameterSpec) null));
				    }};
      
				// Ucitaj kljuc za potpisivanje
				KeyStore keyStore = KeyStore.getInstance(CertParameters.KEYSTORE_TYPE_JKS);
				keyStore.load(new FileInputStream(certParameters.getPathOfJKSCert() + certParameters.getNameOfJKSCert() + CertParameters.EXTENSION_OF_JKS), certParameters.getPasswdOfJKSCert().toCharArray());
   
				PrivateKey privateKey = (PrivateKey) keyStore.getKey(certParameters.getAliasForPairJKSCert(), certParameters.getPasswdOfJKSCert().toCharArray());
   
				X509Certificate cert = (X509Certificate) keyStore.getCertificate(certParameters.getAliasForPairJKSCert());
   
				// Stvori referencu na enveloped document
				Reference ref = sigFactory.newReference(referenceURI, sigFactory.newDigestMethod(DigestMethod.SHA1, null), transforms, null, null);
   
				// Stvori SignedInfo
				SignedInfo signedInfo = sigFactory.newSignedInfo(sigFactory.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE,(C14NMethodParameterSpec) null),
				            sigFactory.newSignatureMethod(SignatureMethod.RSA_SHA1,null),Collections.singletonList(ref));
   
				// Stvori KeyInfo sa svim potrebnim X509 podacima
   
				KeyInfoFactory keyinfoFactory = sigFactory.getKeyInfoFactory();
      
				X509IssuerSerial issuer= keyinfoFactory.newX509IssuerSerial(cert.getIssuerX500Principal().getName(), cert.getSerialNumber());
      
				ArrayList<Object> x509Content = new ArrayList<Object>();
				x509Content.add(cert);
				x509Content.add(cert.getSubjectX500Principal().getName());
				x509Content.add(issuer);
				X509Data xd = keyinfoFactory.newX509Data(x509Content);
				KeyInfo keyInfo = keyinfoFactory.newKeyInfo(Collections.singletonList(xd));
      
				// Stvori DOMSignContext i specificiraj RSA PrivateKey i
				// lokaciju parent elementa u kojem ce se nalaziti XMLSignature
				DOMSignContext dsc = new DOMSignContext(privateKey,sigParent);
   
				// Kreiraj XMLSignature (jos nije potpisan)
				XMLSignature signature = sigFactory.newXMLSignature(signedInfo, keyInfo);
   
				// Generiraj i potpisi
				signature.sign(dsc);
   
				// Pohrani promjene na SOAP poruci
				doc.saveChanges();
			} catch (Exception e) {
				e.printStackTrace();
			}
	       
	        return doc;
	    }

	/*   
	   
	// Metoda za provjeru valjanosti potpisa poruke koju vraca porezni servis
	    private Boolean verifyMessage(SOAPMessage message) throws Exception{
	       
	        Boolean verified = false;
	       
	        // Kreiraj XML iz SOAP poruke
	       
	        SOAPMessage msg = message;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        msg.writeTo(out);
	        
	        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(true);
	        Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(out.toByteArray()));
	        
	        // Zatvori ByteArrayOutputStream
	        out.close();
	        // Provjeri postoji li Signature element u XML poruci
	        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS,"Signature");
	        if (nl.getLength() == 0) {
	            throw new Exception("Ne postoji Signature element u poruci koju je server vratio!");
	        }

	        // Ucitaj kljuc po kojem ce se provjeravati
	        KeyStore keyStore = KeyStore.getInstance(FINA_KEY_STORE_TYPE);
	        keyStore.load(
	            new FileInputStream(FINA_KEY_STORE_FILE), 
	            FINA_KEY_STORE_PASS.toCharArray()
	        );

	        X509Certificate cert = (X509Certificate) keyStore.getCertificate(FINA_KEY_ALIAS);
	        PublicKey publicKey = cert.getPublicKey();
	       
	        // Stvori validacijski kontekst i potpis
	        DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));
	        XMLSignature signature = fac.unmarshalXMLSignature(valContext);
	  
	        // Provjeri da li je dokument ispravno potpisan
	        boolean coreValidity = signature.validate(valContext);
	       
	        // Detaljna provjera koja javlja da li je problem u potpisu ili stavkama, nije nuzna
	         */
	   
	        /* Provjera valjanosti potpisa
	        if (coreValidity == false) {
	            System.out.println("Signature failed core validation!");
	            boolean sv = signature.getSignatureValue().validate(valContext);
	            System.out.println("Signature validation status: " + sv);
	            // Provjera stavki
	            Iterator i = signature.getSignedInfo().getReferences().iterator();
	            for (int j = 0; i.hasNext(); j++) {
	                boolean refValid = ((Reference) i.next()).validate(valContext);
	                System.out.println("Reference (" + j + ") validation status: "
	                        + refValid);
	            }
	        } else {
	            verified = true;
	        }
	         
	        //*/
	   /*
	       
	        verified = coreValidity;
	        return verified;
	    }
	   
	   */
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
}
