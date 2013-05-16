package hr.javaweb.zavrsni.controllers;

import hr.javaweb.zavrsni.service.ServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class HorseRacing {

	@Autowired
	private ServiceInterface service;

	@RequestMapping(value = "/horseRacing.html", method = RequestMethod.GET)
	public String GetMatches(Model model){
		model.addAttribute("matches", service.findAllMatches());
		return "horseRacing";
		
		
		
	}
	
	
}
