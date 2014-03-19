package br.com.badrequest.insporte.rest.model.questionario;

import java.io.Serializable;
import java.util.List;

public class Pergunta implements Serializable {

	public Long idPergunta;

	public List<Opcao> opcoes;

	public Boolean imagem;

}
