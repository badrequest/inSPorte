package br.com.insporte.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.insporte.db.model.Likes;
import br.com.insporte.db.model.Linha;
import br.com.insporte.db.model.Usuario;

@Repository
public class LinhaDaoImpl implements LinhaDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Linha obterLinha(Long id) {
		Linha linha = (Linha) sessionFactory.getCurrentSession()
				.createQuery("from Linha l where l.id = :id")
				.setParameter("id", id).uniqueResult();
		return linha;
	}

	@Override
	@Transactional
	public List<Linha> listarLinhas() {
		List<Linha> linhas = (List<Linha>) sessionFactory.getCurrentSession()
				.createQuery("from Linha").list();
		return linhas;
	}

	//TODO: Fazer consulta
	@Override
	@Transactional
	public Boolean obterLike(Linha linha, Usuario usuario) {

		Likes l = this.obter(linha, usuario);
		
		if (l!= null) return l.getStatus();
		
		return null;

	}

	@Override
	@Transactional
	public void salvarLike(Linha linha, Usuario usuario, Boolean like) {

		Likes l = this.obter(linha, usuario);
		
		if (l == null) {
			l = new Likes();
			l.setLinha(linha);
			l.setUsuario(usuario);
		}
		
		l.setStatus(like);
		
		sessionFactory.getCurrentSession().saveOrUpdate(l);
	}

	@Override
	public Likes obter(Linha linha, Usuario usuario) {
		try {
			List<Likes> likes = (List<Likes>) sessionFactory.getCurrentSession().createQuery("from Likes").list();

			if (likes == null) return null;
			for (Likes l : likes) {
				Hibernate.initialize(l.getLinha());
				Hibernate.initialize(l.getUsuario());
				if (l.getLinha().getId().equals(linha.getId()) && 
						l.getUsuario().getId().equals(usuario.getId())) {
					return l;
				}
			}
			
			return null;

		} catch (Exception e) {
			return null;
		}
	}

}
