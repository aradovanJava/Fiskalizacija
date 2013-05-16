package hr.javaweb.zavrsni.controllers;

import hr.javaweb.zavrsni.service.ServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ShowReg {
	
	@Autowired
	ServiceImplementation service;
	
	@RequestMapping(value = "/admin/showReg.html", method = RequestMethod.GET)
	public String showReg(Model model){
		
		model.addAttribute("users", service.findAllUsers());
		return "showReg";
		
	}

}
