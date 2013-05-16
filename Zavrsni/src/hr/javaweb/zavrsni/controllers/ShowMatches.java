package hr.javaweb.zavrsni.controllers;

import hr.javaweb.zavrsni.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

@Controller
public class ShowMatches {
	
	@Autowired
	private ServiceInterface service;

	@RequestMapping(value = "/viewMatches.html", method = RequestMethod.GET)
	public String GetMatches(Model model, HttpServletRequest request){
		boolean isRole = false;
		
		if(request.isUserInRole("ROLE_ADMIN")){
			isRole = true;
		}
		
		model.addAttribute("matches", service.findAllMatches());
		model.addAttribute("isRole", isRole);
		return "viewMatches";
		
		
		
	}
}
