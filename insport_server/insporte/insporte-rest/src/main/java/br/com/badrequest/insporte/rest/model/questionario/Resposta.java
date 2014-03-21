package br.com.badrequest.insporte.rest.model.questionario;

import java.io.Serializable;
import java.util.List;

public class Resposta implements Serializable {

	public Long idQuestionario;

	public List<Pergunta> perguntas;

}
