package br.com.badrequest.insporte.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Table(name = "option")
@Data
@EqualsAndHashCode(of={"id"})
public class Option implements Serializable {

	@Id
	private Long id;
	
	private String description;
	
	@ManyToOne
	private Question question;

	@OneToMany(mappedBy="option")
	private List<AnswerOption> answerOptions;
	
}
