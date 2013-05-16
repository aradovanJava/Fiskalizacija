package hr.javaweb.zavrsni.service;
import java.util.List;

import hr.javaweb.zavrsni.dao.*;
import hr.javaweb.zavrsni.model.Bookie;
import hr.javaweb.zavrsni.model.Event;
import hr.javaweb.zavrsni.model.Role;
import hr.javaweb.zavrsni.model.Runner;
import hr.javaweb.zavrsni.model.User;
import hr.javaweb.zavrsni.model.Matches;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ServiceImplementation implements ServiceInterface {
	
	@Autowired
	private DAOInterface rep;

	public void insertUser(User user){
		rep.insertUser(user);
	}
	
	public void insertRole(Role role){
		rep.insertRole(role);
	}

	public void insertMatches(Matches matches){
		rep.insertMatches(matches);
	}
	
	public List<Matches> findAllMatches(){
		return rep.findAllMatches();
	}
	
	public List<User> findAllUsers(){
		return rep.findAllUsers();
	}

	public List<Bookie> findAllBookie(){
		return rep.findAllBookie();
	}
	
	public void deleteMatches(){
		rep.deleteMatches();
	}
	
	public List<Event> findAllHorseRacing(){
		return rep.findAllHorseRacing();
	}
	
	public List<Runner> findAllRunners(int id){
		return rep.findAllRunners(id);
	}
	
	public void obrisiEvent(int id){
		rep.obrisiEvent(id);
	}
}
