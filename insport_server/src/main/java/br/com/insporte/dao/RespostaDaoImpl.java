package br.com.insporte.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.insporte.db.model.Comentario;
import br.com.insporte.db.model.Envio;
import br.com.insporte.db.model.Resposta;
import br.com.insporte.db.model.RespostaOpcao;

@Repository
public class RespostaDaoImpl implements RespostaDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void salvarEnvio(Envio envio) {
		sessionFactory.getCurrentSession().persist(envio);
	}

	@Override
	@Transactional
	public void salvarResposta(Resposta resposta) {
		sessionFactory.getCurrentSession().persist(resposta);		
	}

	@Override
	@Transactional
	public void salvarRespostaOpcao(RespostaOpcao respostaOpcaoDB) {
		sessionFactory.getCurrentSession().persist(respostaOpcaoDB);
	}

	@Override
	@Transactional
	public void salvarComentario(Comentario comentario) {
		sessionFactory.getCurrentSession().persist(comentario);
	}

}
