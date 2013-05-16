package hr.javaweb.zavrsni.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Basic;
import java.io.Serializable;

/**
 * Persistent class which variable mapped on column in BOOKIE table. It contains detail of some bookies, this data are uniform and only 
 * developer can add information in that table, executing SQL queries   
 * @author Juraj
 */
@Entity
@Table(name="ZAVRSNI.BOOKIE")
public class Bookie implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param id mapped on ID column in table BOOKIE and it's primary key in that table. Also it's auto generated value
	 */
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	
	/**
	 * Other Column in BOOKIE table
	 * @param name mapped on column NAME in BOOKIE table. It contains name of bookie
	 * @param banner mapped on column BANNER in BOOKIE table. It contains refer link to bookie where need to place odds  
	 * @param tenis_link maped on column TENNIS_LINK in BOOKIE table. It contains link for mainly current offering of tennis (usually daily)
	 * 
	 */
	
	@Basic
	@NotNull
	@Column(name ="NAME")
	private String name;
	
	@Basic
	@NotNull
	@Column(name="BANNER")
	private String banner;
	
	@Basic
	@NotNull
	@Column(name="TENNIS_LINK")
	private String tennis_link;
	
	@Basic
	@NotNull
	@Column(name="LOCATION_OF_FILE")
	private String location_of_file;

	/**
	 * Constructor with all parameters
	 */
	
	public Bookie(int id, String name, String banner, String tennis_link){
		this.id = id;
		this.name = name;
		this.banner = banner;
		this.tennis_link = tennis_link;	
	}
	
	/**
	 * Constructor without parameters
	 */
	
	public Bookie(){
	}
	
	/**
	 * Getter and setter methods for variables
	 */
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getBanner(){
		return banner;
	}
	
	public void setbanner(String banner){
		this.banner = banner;
	}
	
	public String getTennis_link(){
		return tennis_link;
	}
	
	public void setTennis_link(String tennis_link){
		this.tennis_link = tennis_link;
	}
	
	public String getLocation_of_file() {
		return location_of_file;
	}

	public void setLocation_of_file(String location_of_file) {
		this.location_of_file = location_of_file;
	}

	/**
	 * Override method equals() for compare two objects tip Bookie
	 */
	
	@Override
	public boolean equals(Object o){
		
		if(this == o)return true;
		if(o == null || getClass() != o.getClass())return false;
		
		Bookie bookie = (Bookie) o;
		
		if(id != bookie.id)return false;
		if(name != null ? name.equals(bookie.name) : bookie.name != null)return false;
		if(banner != null ? banner.equals(bookie.banner) : bookie.banner != null)return false;
		if(tennis_link != null ? tennis_link.equals(bookie.banner) : bookie.banner != null)return false;
		if(location_of_file != null ? location_of_file.equals(bookie.location_of_file) : bookie.location_of_file != null)return false;
		return true;
			
	}
	
	/**
	 * Override method hashCode() for compare two object tip Bookie
	 */
	
	@Override
	public int hashCode(){
		
		int result = id;
		
		result = result * 7 + (name != null ? name.hashCode() : 0);
		result = result * 7 + (banner != null ? banner.hashCode() : 0);
		result = result * 7 + (tennis_link != null ? tennis_link.hashCode():0);
		result = result * 7 + (location_of_file != null ? location_of_file.hashCode():0);
		return result;
		
	}
	
	
	
}
