package hr.fiskalizacija;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Document;

import hr.model.BusinessAreaRequest;
import hr.model.bill.BillRequest;


public class CreateXmls{

	private static final String UTF_8 = "UTF-8";
	private static final String WRONG_INSTANCE = "Objekt koji je proslijeðen metodi nije tipa BusinessAreaRequest ili BillRequest";
	private static final String ECHO_REQUEST = "EchoRequest";
	private static final String TNS = "tns";
	private static final String SCHEMA_LOCATION = "schemaLocation";
	private static final String XSI = "xsi";
	private static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
	private static final String TNS_NAMESPACE = "http://www.apis-it.hr/fin/2012/types/f73";
	private static final String PART_SHEMA_INFO = " FiskalizacijaSchema.xsd";
	private static final String MESSAGE = "Komunikacija s servisom Porezne Uprave je uspjesno ostvarena";
	
	
	/** 
	 * Kreiranje Echo poruke kojom je moguæe provjeriti da li je moguæe uspostaviti komunikaciju
	 * s servisom Porezne uprave
	 **/
    public SOAPMessage createEchoMessage(){
       
        	SOAPMessage soapEchoMessage = null;
			try {
				soapEchoMessage = MessageFactory.newInstance().createMessage();
				SOAPBody soapBody = soapEchoMessage.getSOAPPart().getEnvelope().getBody();
     
				QName elementInfo = new QName(null, ECHO_REQUEST, TNS);
				QName schemaInfo = new QName(null, SCHEMA_LOCATION, XSI);
				SOAPBodyElement bodyElement = soapBody.addBodyElement(elementInfo);
				bodyElement.addNamespaceDeclaration(XSI, XSI_NAMESPACE);
				bodyElement.addNamespaceDeclaration(TNS, TNS_NAMESPACE);
				bodyElement.addAttribute(schemaInfo, TNS_NAMESPACE + PART_SHEMA_INFO);
				bodyElement.addTextNode(MESSAGE);
    
				soapEchoMessage.saveChanges();
			} catch (SOAPException e) {
				e.printStackTrace();
			}	
        return soapEchoMessage;
    }
	
	
	
	/**
	 * Kreiranje XML-a iz popunjenog objekta koji predstavlja objekt zahtjeva za raèunom (BillRequest) ili poslovnim prostorom (BusinessAreaRequest)
	 * 
	 * @param requestObject objekt zahtjeva za raèunom ili poslovnim prostorom
	 * @return kreiran XML u obliku stringa
	 */
	public Document createXmlForRequest(Object requestObject){
		
		// Potrebno implementirati provjere za podatke koji moraju biti u xml-u, te bacanje grešaka ako neki nije unesen
				 
				StringWriter writer = new StringWriter();
				Document doc = null;
				  try{
					  JAXBContext jaxbContext = null;
					  
					  /** 
					   * Provjera instance ulaznog parametra, te ovisno o ishodu provjere poziva se
					   * metoda za kreiranje BusinessAreaRequest ili BillRequest XML dokumenta, a
					   * ako nije niti jedan niti drugo, baca se iznimka
					   **/
					  if(requestObject instanceof BusinessAreaRequest){
							 jaxbContext = JAXBContext.newInstance(new Class[] {BusinessAreaRequest.class});
						}else if(requestObject instanceof BillRequest){
							jaxbContext = JAXBContext.newInstance(new Class[] {BillRequest.class});
						}else{
							throw new RuntimeException(WRONG_INSTANCE);
						}
					  Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					  jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					  jaxbMarshaller.marshal(requestObject, writer);
				
					  /** Kreiranje objekta klase Document iz objekta klase StringWriter */
					  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					  dbf.setNamespaceAware(true);	
					  doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(writer.toString().getBytes(UTF_8)));
					  
				  }catch(Exception e){
					e.printStackTrace();
				}
			        return doc;
	}
	
	
	
	/**
	 * Kreiranje SOAP poruke
	 * 
	 * @param doc objekt klase Document iz kojeg se kreira SOAP poruka
	 * @return kreiranju SOAP poruku
	 */
	public SOAPMessage createSoapMessage(Document doc){
    	SOAPMessage message = null;
    	try{
	        message = MessageFactory.newInstance().createMessage();
	        message.getSOAPPart().getEnvelope().getBody().addDocument(doc);
	        message.saveChanges();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return message;
    }

}
