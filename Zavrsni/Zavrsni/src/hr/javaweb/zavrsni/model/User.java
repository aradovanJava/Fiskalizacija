package hr.javaweb.zavrsni.model;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Basic;
import java.io.Serializable;

import javax.validation.Valid;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;



@Entity
@Table(name = "ZAVRSNI.USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	

	@ManyToOne
	@JoinColumn(name ="USERNAME")
	@Valid
	private Role role;
	
	@NotEmpty( message ="Polje mora biti popunjeno!")
	@Basic
	@Column(name = "PASSWORD")
	private String password;
	
	/*
	@Min(value = 6, message = "Lozinka mora biti duže od 6 znakova!")
	@Max(value = 20, message = "Lozinka ime mora biti kraæa od 20 znakova!")
	@Expression(value = "confirmNewPassword = newPassword", applyIf = "newPassword is not blank") 
	private String confirm_password;
	PORADITI NA OVIM VALIDATORIMA JOŠ
	@NotNull( message ="Polje mora biti popunjeno!")
	@Min(value = 6, message = "Korisnièko ime mora biti duže od 6 znakova!")
	@Max(value = 20, message = "Korisnièko ime mora biti kraæe od 20 znakova!")
	*/
	
	
	@Email(message = "E-mail nije dobro napisan!")
	@NotEmpty( message ="Polje mora biti popunjeno!")
	@Basic
	@Column(name = "MAIL")
	private String mail;
	
	@Basic
	@Column(name = "ADRESS")
	private String adress;
	
	@NotEmpty( message ="Polje mora biti popunjeno!")
	@Basic
	@Column(name = "FIRST_NAME")
	private String first_name;
	
	@NotEmpty( message ="Polje mora biti popunjeno!")
	@Basic
	@Column(name = "LAST_NAME")
	private String last_name;
	
	@Basic
	@Column(name = "CITY")
	private String city;
	
	@NotEmpty( message ="Polje mora biti popunjeno!")
	@Basic
	@Column(name = "MOBILE")
	private String mobile;
	
	@Basic
	@Column(name = "DATE_OF_BIRTH")
	private Date date_of_birth;

	/**
	 * Constructor with no parameters
	 */

	public User(){
	}

	/**
	 * Constructor with all parameters
	 */
	
	public User(String password, String mail, String adress, String first_name, 
			    String last_name, String city, String mobile, Date date_of_birth){
		this.password = password;
		this.mail = mail;
		this.adress = adress;
		this.first_name = first_name;
		this.last_name = last_name;
		this.city = city;
		this.mobile = mobile;
		this.date_of_birth = date_of_birth;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setMail(String mail){
		this.mail = mail;
	}
	
	public void setAdress(String adress){
		this.adress = adress;
	}
	
	public void setFirst_name(String first_name){
		this.first_name = first_name;
	}
	
	public void setLast_name(String last_name){
		this.last_name = last_name;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	
	public void setDate_of_birth(Date date_of_birth){
		this.date_of_birth = date_of_birth;
	}
	
	public int getId(){
	return id;
}

public Role getRole() {
		return role;
	}

public String getMail(){
	return mail;
}

public String getAdress(){
	return adress;
}

public String getFirst_name(){
	return first_name;
}

public String getLast_name(){
	return last_name;
}

public String getCity(){
	return city;
}

public String getMobile(){
	return mobile;
}

public Date getDate_of_birth(){
	return date_of_birth;
}
public String getPassword() {
	return password;
}


	
	
}