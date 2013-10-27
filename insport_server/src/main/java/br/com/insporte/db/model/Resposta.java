package br.com.insporte.db.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "resposta")
@SequenceGenerator(name = "sq_resposta", sequenceName = "sq_resposta", initialValue = 1, allocationSize = 1)
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_resposta")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="id_envio")
	private Envio envio;
	
	@ManyToOne
	@JoinColumn(name="id_pergunta")
	private Pergunta pergunta;
	
	@OneToMany(mappedBy = "resposta")
	private List<RespostaOpcao> opcoes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Envio getEnvio() {
		return envio;
	}

	public void setEnvio(Envio envio) {
		this.envio = envio;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public List<RespostaOpcao> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(List<RespostaOpcao> opcoes) {
		this.opcoes = opcoes;
	}
	
}
