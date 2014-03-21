package br.com.badrequest.insporte.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Data;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
@Entity
//@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"bu", "document"}))
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@Data 
public class User implements Serializable {

	@Id
	@SequenceGenerator(name="sq_users",sequenceName="sq_users", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_users")
	private Long id;
	
	private String bu;
	
	@SerializedName("id")
	private String document;

	private String email;
	
	private String password;
	
}
