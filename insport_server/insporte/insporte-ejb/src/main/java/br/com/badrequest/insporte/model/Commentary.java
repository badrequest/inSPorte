package br.com.badrequest.insporte.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class Commentary {

	@Id
	@SequenceGenerator(name="sq_comment",sequenceName="sq_comment", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_comment")
	private Long id;
	
	private String texto;
	
	private String path = "";
	
	@OneToOne(mappedBy="commentary")
	private QuestionaryAnswer envio;
	
	@OneToOne(cascade={CascadeType.ALL})
	private Image image;
	
	@Transient
	private Boolean insertImage; 
	
}
