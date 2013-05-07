package hr.fiskalizacija;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Document;

import hr.model.BusinessAreaRequest;
import hr.model.bill.BillRequest;


public class CreateXmls{

	
	// Metoda koja kreira Echo poruku koja se moze poslati serveru kako bi se provjerilo da li server funkcionira
    public SOAPMessage createEchoMessage(){
       
        	SOAPMessage soapEchoMessage = null;
			try {
				soapEchoMessage = MessageFactory.newInstance().createMessage();
				SOAPHeader header = soapEchoMessage.getSOAPHeader();
				header.detachNode();
				SOAPPart soapPart = soapEchoMessage.getSOAPPart();
				SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
				SOAPBody soapBody = soapEnvelope.getBody();
     
				// Kreiranje elementa u envelope sa odgovarajucim vrijednostima
				QName elementInfo = new QName(null, "EchoRequest", "tns");
				QName schemaInfo = new QName(null, "schemaLocation", "xsi");
				SOAPBodyElement bodyElement = soapBody.addBodyElement(elementInfo);
				bodyElement.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
				bodyElement.addNamespaceDeclaration("tns", "http://www.apis-it.hr/fin/2012/types/f73");
				bodyElement.addAttribute(schemaInfo, "http://www.apis-it.hr/fin/2012/types/f73 FiskalizacijaSchema.xsd");
				bodyElement.addTextNode("Komunikacija s servisom Porezne Uprave je uspjesno ostvarena");
    
				soapEchoMessage.saveChanges();
				//System.out.println(writeSoap(soapEchoMessage));
			} catch (SOAPException e) {
				e.printStackTrace();
			}	
        return soapEchoMessage;
    }
	
	
	
	
	public String createXmlForRequest(Object requestObject){
		
		// Potrebno implementirati provjere za podatke koji moraju biti u xml-u, te bacanje grešaka ako neki nije unesen
				 
				StringWriter writer = new StringWriter();
				  try{
					  JAXBContext jaxbContext = null;
					  if(requestObject instanceof BusinessAreaRequest){
							 jaxbContext = JAXBContext.newInstance(new Class[] {BusinessAreaRequest.class});
						}else if(requestObject instanceof BillRequest){
							jaxbContext = JAXBContext.newInstance(new Class[] {BillRequest.class});
						}else{
							throw new RuntimeException("Objekt koji je proslijeðen metodi nije tipa BusinessAreaRequest ili BillRequest");
						}
					  Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					  jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					  jaxbMarshaller.marshal(requestObject, writer);
				}catch(Exception e){
					e.printStackTrace();
				}
			        return writer.toString();			
	}
	
	
	
	
	public SOAPMessage createSoapMessage(String input_msg){
    	SOAPMessage message = null;
    	InputStream in_stream = null;
    	try{
	        in_stream = new ByteArrayInputStream(input_msg.getBytes());
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(true);
	        Document doc = dbf.newDocumentBuilder().parse(in_stream);
	       
	        message = MessageFactory.newInstance().createMessage();
	        SOAPHeader header = message.getSOAPHeader();
	        header.detachNode();
	        SOAPPart soapPart = message.getSOAPPart();
	        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
	        SOAPBody soapBody = soapEnvelope.getBody();
	        soapBody.addDocument(doc);
	       
	        message.saveChanges();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	finally{
    		try{
    			in_stream.close();
    		}
    		catch (Exception e){
    			e.printStackTrace();
    		}
    	}
        return message;
    }

}
