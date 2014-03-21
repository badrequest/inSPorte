package br.com.badrequest.insporte.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Entity
@Table(name = "questionary")
@Data
@EqualsAndHashCode(of={"id"})
public class Questionary implements Serializable {

	@Id
	private Long id;
	
	private String description;
	
	@OneToMany(mappedBy="questionary")
	private Set<Question> questions;
	
	@OneToOne(mappedBy="questionary")
	private QuestionaryAnswer questionaryAnswer;

}
