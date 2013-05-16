package hr.javaweb.zavrsni.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ZAVRSNI.RUNNER")
public class Runner implements Serializable {

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	@Column(name = "RUNNER_NAME")
	private String runnerName;
	
	@Column(name = "JOKEY_NAME")
	private String jokeyName;
	
	@Column(name = "ODD")
	private String odd;
	
	@Column(name = "REFRESH_TIME")
	private Date refreshTime;
	
	@Column(name = "MAX_STAKE")
	private String maxStake;
	
	@ManyToOne
	@JoinColumn(name = "ID_EVENT")
	private Event event; 
	
	
	public Runner() {
	}

	public Runner(String runnerName, String jokeyName, String odd,
			Date refreshTime, String maxStake) {
		
		this.runnerName = runnerName;
		this.jokeyName = jokeyName;
		this.odd = odd;
		this.refreshTime = refreshTime;
		this.maxStake = maxStake;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRunnerName() {
		return runnerName;
	}

	public void setRunnerName(String runnerName) {
		this.runnerName = runnerName;
	}

	public String getJokeyName() {
		return jokeyName;
	}

	public void setJokeyName(String jokeyName) {
		this.jokeyName = jokeyName;
	}

	public String getOdd() {
		return odd;
	}

	public void setOdd(String odd) {
		this.odd = odd;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public String getMaxStake() {
		return maxStake;
	}

	public void setMaxStake(String maxStake) {
		this.maxStake = maxStake;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
