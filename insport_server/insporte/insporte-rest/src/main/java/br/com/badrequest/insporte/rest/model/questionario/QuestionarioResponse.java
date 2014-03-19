package br.com.badrequest.insporte.rest.model.questionario;

import java.io.Serializable;
import java.util.List;

public class QuestionarioResponse implements Serializable {
	
	public String ans;

	public List<Imagem> idImagens;
	
	public Long idImagemComentario;
	
}
