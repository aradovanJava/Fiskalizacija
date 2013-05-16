package hr.javaweb.zavrsni.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "ZAVRSNI.ROLE")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Basic
	@Column(name = "ROLE")
	private String role;
	
	@NotEmpty( message ="Polje mora biti popunjeno!")
	@Id
	@Column(name ="USERNAME")
	public String username;
	
	
	public String getRole(){
		return role;
	}

	public void setRole(String role){
		this.role = role;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	
	public Role(String role, String username){
		this.role = role;
		this.username = username;
	}
	
	public Role(){
	}
	
	@Override
	public boolean equals(Object o){
		if(this == o)return true;
		if(o == null || (getClass() != o.getClass()) )return false;
		
		Role role = (Role) o;
		
		if(username != null ? !username.equals(role.username) : role.username != null)return false;
		if(role != null ? !role.equals(role.role) : role.role != null)return false;
		return true;
	}
	
	
	@Override
	 public int hashCode(){
		 
		int result = 5;
		 
		 result = result*9 + (username != null ? username.hashCode() : 0);
		 result = result*9 + (role != null ? role.hashCode() : 0);
		 
		 return result;
	 }
}
