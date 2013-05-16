package hr.javaweb.zavrsni.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Index {

	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String GetIndex(Model model){
	
		
		return "index";
		
		
		
	}
	
	
}
