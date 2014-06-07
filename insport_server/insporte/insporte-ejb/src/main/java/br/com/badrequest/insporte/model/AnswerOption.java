package br.com.badrequest.insporte.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Table(name = "answer_option")
@Data
@EqualsAndHashCode(of={"option"})
public class AnswerOption implements Serializable {

	@Id
	@SequenceGenerator(name="sq_answer_option",sequenceName="sq_answer_option", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="sq_answer_option")
	private Long id;

	@ManyToOne
	private Answer answer;
	
	@ManyToOne
	private Option option;

}
