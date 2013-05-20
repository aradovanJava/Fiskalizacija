package hr.model.bill;

import java.text.DecimalFormat;
import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter extends XmlAdapter<String, Double>{

	/**
	 * Metoda za postavljanje Doble s dvije decimale i defaultnim localeom prilikom kreiranja XML-a 
	 */
@Override
public String marshal(Double arg0) throws Exception {
		Locale.setDefault(new Locale("en", "US"));
        DecimalFormat twoDForm = new DecimalFormat("##.00");
    return twoDForm.format(arg0);
}

/**
 * Ova metoda se trenutno ne koristi
 */
@Override
public Double unmarshal(String arg0) throws Exception {
	return null;
}

}
