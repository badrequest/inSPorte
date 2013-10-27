package br.com.insporte.rest.model.questionario;

import java.io.Serializable;
import java.util.List;

public class Resposta implements Serializable {

	private Long idQuestionario;

	private List<Pergunta> perguntas;

	public Resposta() {

	}

	public Long getIdQuestionario() {
		return idQuestionario;
	}

	public void setIdQuestionario(Long idQuestionario) {
		this.idQuestionario = idQuestionario;
	}

	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}

}
