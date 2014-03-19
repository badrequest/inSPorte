package br.com.badrequest.insporte.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name = "image")
@Data
public class Image implements Serializable {

	@Id
	@SequenceGenerator(name="sq_image",sequenceName="sq_image", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_image")
	private Long id;
	
	private String path = "";
	
	@OneToOne(mappedBy="image", fetch=FetchType.LAZY)
	private Answer answer;
	
	@OneToOne(mappedBy="image", fetch=FetchType.LAZY)
	private Commentary commentary;
	
}
