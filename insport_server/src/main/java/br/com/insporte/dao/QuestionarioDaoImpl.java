package br.com.insporte.dao;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.insporte.db.model.Opcao;
import br.com.insporte.db.model.Pergunta;
import br.com.insporte.db.model.Questionario;

@Repository
public class QuestionarioDaoImpl implements QuestionarioDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Questionario obterQuestionario(Long id) {

		Questionario questionario = (Questionario) sessionFactory
				.getCurrentSession()
				.createQuery("from Questionario q where q.id = :id")
				.setParameter("id", id).uniqueResult();

		Hibernate.initialize(questionario.getPerguntas());

		for (Pergunta pergunta : questionario.getPerguntas()) {
			Hibernate.initialize(pergunta.getOpcoes());
		}

		return questionario;
	}

	@Override
	@Transactional
	public Pergunta obterPergunta(Long id) {

		Pergunta pergunta = (Pergunta) sessionFactory.getCurrentSession()
				.createQuery("from Pergunta p where p.id = :id")
				.setParameter("id", id).uniqueResult();

		return pergunta;
	}

	@Override
	@Transactional
	public Opcao obterOpcao(Long id) {
		Opcao opcao = (Opcao) sessionFactory.getCurrentSession()
				.createQuery("from Opcao p where p.id = :id")
				.setParameter("id", id).uniqueResult();

		return opcao;
	}

}
