package br.com.insporte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.insporte.dao.QuestionarioDao;
import br.com.insporte.db.model.Opcao;
import br.com.insporte.db.model.Pergunta;
import br.com.insporte.db.model.Questionario;

@Repository
public class QuestionarioServiceImpl implements QuestionarioService {

	@Autowired
	private QuestionarioDao questionarioDao;

	@Override
	public Questionario obterQuestionario(Long id) {
		return questionarioDao.obterQuestionario(id);
	}

	@Override
	public Pergunta obterPergunta(Long id) {
		return questionarioDao.obterPergunta(id);
	}

	@Override
	public Opcao obterOpcao(Long id) {
		return questionarioDao.obterOpcao(id);
	}
}
