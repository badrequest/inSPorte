package br.com.insporte.rest.model.questionario;

import java.io.Serializable;
import java.util.List;

public class Pergunta implements Serializable {

	private Long idPergunta;

	private List<Opcao> opcoes;

	private Boolean imagem;

	public Pergunta() {

	}

	public Long getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(Long idPergunta) {
		this.idPergunta = idPergunta;
	}

	public List<Opcao> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(List<Opcao> opcoes) {
		this.opcoes = opcoes;
	}

	public Boolean getImagem() {
		return imagem;
	}

	public void setImagem(Boolean imagem) {
		this.imagem = imagem;
	}

}
