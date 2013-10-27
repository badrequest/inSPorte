package br.com.insporte.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "comentario")
@SequenceGenerator(name = "sq_comentario", sequenceName = "sq_comentario", initialValue = 1, allocationSize = 1)
public class Comentario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_comentario")
	private Long id;
	
	private String texto;
	
	@OneToOne(mappedBy="comentario")
	private Envio envio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
}
