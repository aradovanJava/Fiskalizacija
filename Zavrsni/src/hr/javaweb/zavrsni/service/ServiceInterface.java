package hr.javaweb.zavrsni.service;

import java.util.List;

import hr.javaweb.zavrsni.model.Bookie;
import hr.javaweb.zavrsni.model.Event;
import hr.javaweb.zavrsni.model.Role;
import hr.javaweb.zavrsni.model.Runner;
import hr.javaweb.zavrsni.model.User;
import hr.javaweb.zavrsni.model.Matches;


public interface ServiceInterface {

	public void insertUser(User user);
	
	public void insertRole(Role role);
	
	public void insertMatches(Matches matches);
	
	public List<User> findAllUsers();
	
	public List<Matches> findAllMatches();
	
	public List<Bookie> findAllBookie();
	
	public void deleteMatches();
	
	public List<Event> findAllHorseRacing();
	
	public List<Runner> findAllRunners(int id);
	
	public void obrisiEvent(int id);
	
}
