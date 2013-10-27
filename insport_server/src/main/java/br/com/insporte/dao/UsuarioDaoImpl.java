package br.com.insporte.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.insporte.db.model.Usuario;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Usuario obterUsuario(String bu, String id) {

		Usuario usuario = (Usuario) sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Usuario u where "
								+ " u.bu = :bu and u.cpf = :cpf ")
				.setParameter("bu", bu).setParameter("cpf", id).uniqueResult();

		return usuario;
	}

	@Override
	@Transactional
	public void inserirUsuario(String bu, String id) {
		Usuario usuario = new Usuario();
		usuario.setBu(bu);
		usuario.setCpf(id);
		sessionFactory.getCurrentSession().persist(usuario);
	}

}
