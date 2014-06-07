package br.com.badrequest.insporte.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Table(name = "questionary_answer")
@Data
@EqualsAndHashCode(of={"id"})
public class QuestionaryAnswer implements Serializable {

	@Id
	@SequenceGenerator(name="sq_questionary_answer",sequenceName="sq_questionary_answer", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_questionary_answer")
	private Long id;

	@ManyToOne
	private User user;
	
	@ManyToOne
	private BusLine busLine;
	
	private Date data;
	
	private Double lat;
	
	private Double lon;

	@ManyToOne
	private Questionary questionary;
	
	@OneToOne(cascade={CascadeType.ALL})
	private Commentary commentary;
	
	@OneToMany(mappedBy = "questionaryAnswer", cascade={CascadeType.ALL})
	private Set<Answer> answers;
		
}
