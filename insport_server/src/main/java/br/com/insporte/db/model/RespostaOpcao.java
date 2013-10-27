package br.com.insporte.db.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "resposta_opcao")
@SequenceGenerator(name = "sq_resposta_opcao", sequenceName = "sq_resposta_opcao", initialValue = 1, allocationSize = 1)
public class RespostaOpcao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_resposta_opcao")
	private Long id;

	@ManyToOne
	@JoinColumn(name="id_resposta")
	private Resposta resposta;
	
	@OneToOne
	@JoinColumn(name="id_opcao")
	private Opcao opcao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	public Opcao getOpcao() {
		return opcao;
	}

	public void setOpcao(Opcao opcao) {
		this.opcao = opcao;
	}
	
}
