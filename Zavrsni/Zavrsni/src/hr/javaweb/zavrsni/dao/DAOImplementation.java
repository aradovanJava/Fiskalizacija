package hr.javaweb.zavrsni.dao;

import java.util.List;

import hr.javaweb.zavrsni.model.Bookie;
import hr.javaweb.zavrsni.model.Event;
import hr.javaweb.zavrsni.model.Matches;
import hr.javaweb.zavrsni.model.Role;
import hr.javaweb.zavrsni.model.Runner;
import hr.javaweb.zavrsni.model.User;

import org.springframework.stereotype.Repository;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@SuppressWarnings("unchecked")
@Repository
public class DAOImplementation extends HibernateDaoSupport implements DAOInterface {
	
	public void insertUser(User users) { getHibernateTemplate().saveOrUpdate(users); }
	
	public void insertRole(Role role) { getHibernateTemplate().saveOrUpdate(role); }
	
	public void insertMatches(Matches matches) { getHibernateTemplate().saveOrUpdate(matches); }

	public List<User> findAllUsers() { return getHibernateTemplate().find("from User"); }
	
	public List<Bookie> findAllBookie() { return getHibernateTemplate().find("from Bookie"); }
	
	public List<Matches> findAllMatches() { return getHibernateTemplate().find("from Matches ORDER BY dates, times" ); }
	
	public void deleteMatches() {getHibernateTemplate().deleteAll(findAllMatches()); }
	
	public List<Event> findAllHorseRacing(){return getHibernateTemplate().find("from Event");}
	
	public List<Runner> findAllRunners(int id){return getHibernateTemplate().find("FROM Runner AS r LEFT JOIN r.event AS e WHERE e.id = " + id);}
	
	public void obrisiEvent(int id){getHibernateTemplate().delete(getHibernateTemplate().load(Event.class, id));}
	

}
