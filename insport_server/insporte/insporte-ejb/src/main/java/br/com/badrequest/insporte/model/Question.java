package br.com.badrequest.insporte.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Table(name = "question")
@Data
@EqualsAndHashCode(of={"id"})
public class Question implements Serializable {

	@Id
	private Long id;
	
	private String description;
	
	@OneToMany(mappedBy="question")
	private Set<Option> options;
	
	@ManyToOne
	private Questionary questionary;
	
	@OneToMany(mappedBy="question")
	private Set<Answer> answers;
	
}
