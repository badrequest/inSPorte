package br.com.badrequest.insporte.model;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.Transient;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name = "answer")
@Data
public class Answer implements Serializable {

	@Id
	@SequenceGenerator(name="sq_anwer",sequenceName="sq_anwer", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_anwer")
	private Long id;
	
	@ManyToOne
	private QuestionaryAnswer questionaryAnswer;
	
	@ManyToOne
	private Question question;
	
	@OneToMany(mappedBy = "answer", cascade={CascadeType.ALL})
	private List<AnswerOption> options;
	
	@OneToOne(cascade={CascadeType.ALL})
	private Image image;
		
	@Transient
	private Boolean insertImage;

}
