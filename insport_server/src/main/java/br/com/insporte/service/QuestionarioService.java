package br.com.insporte.service;

import br.com.insporte.db.model.Opcao;
import br.com.insporte.db.model.Pergunta;
import br.com.insporte.db.model.Questionario;

public interface QuestionarioService {

	public Questionario obterQuestionario(Long id);
	
	public Pergunta obterPergunta(Long id);
	
	public Opcao obterOpcao(Long id);

}
