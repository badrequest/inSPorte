package br.com.insporte.dao;

import br.com.insporte.db.model.Opcao;
import br.com.insporte.db.model.Pergunta;
import br.com.insporte.db.model.Questionario;

public interface QuestionarioDao {

	public Questionario obterQuestionario(Long id);
	
	public Pergunta obterPergunta(Long id);
	
	public Opcao obterOpcao(Long id);
}
