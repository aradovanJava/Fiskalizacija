package hr.javaweb.zavrsni.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import hr.javaweb.zavrsni.model.Role;
import hr.javaweb.zavrsni.model.User;
import hr.javaweb.zavrsni.service.ServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Registration {
	
	@Autowired
	private ServiceImplementation service;
	
	@RequestMapping(value ="/registration.html", method = RequestMethod.GET)
	public String getForm(Model model){
		model.addAttribute("user", new User());
		
		return "registration";
		
	}
		
	@RequestMapping(value = "/registration.html", method = RequestMethod.POST)
	public String regUser(@Valid @ModelAttribute User user, BindingResult result, Model model){
		if(result.hasErrors()){
			return "registration";
		}
		Role role = new Role();
		role.setRole("ROLE_GUEST");
		role.setUsername(user.getRole().getUsername());
		service.insertRole(role);
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        md.update(user.getPassword().getBytes());
        byte byteData[] = md.digest(); 
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        user.setPassword(sb.toString());
		service.insertUser(user); 
		model.addAttribute("user", new User()); 
		model.addAttribute("uneseno", "Podaci su uspješno uneseni."); 	
		return"registration";
	}
	
}
