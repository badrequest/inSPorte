package br.com.badrequest.insporte.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@SuppressWarnings("serial")
@Entity
@Table(name = "answer_option")
@Data
public class AnswerOption implements Serializable {

	@Id
	@SequenceGenerator(name="sq_anwer_option",sequenceName="sq_anwer_option", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_anwer_option")
	private Long id;

	@ManyToOne
	private Answer answer;
	
	@OneToOne
	private Option option;

}
