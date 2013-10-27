package br.com.insporte.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.insporte.dao.UsuarioDao;
import br.com.insporte.db.model.Usuario;
import br.com.insporte.rest.model.auth.Auth;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public Usuario obterUsuario(String bu, String id) {
		return this.usuarioDao.obterUsuario(bu, id);
	}

	@Override
	public Usuario obterUsuario(Auth auth) {
		return this.usuarioDao.obterUsuario(auth.getBu(), auth.getId());
	}
	
	@Override
	public void inserirUsuario(String bu, String id) {
		this.usuarioDao.inserirUsuario(bu, id);
	}

	@Override
	public boolean validarUsuario(Auth auth) {
		if (this.obterUsuario(auth.getBu(), auth.getId()) != null) {
			return true;
		}
		return false;
	}

	@Override
	public void inserirUsuario(Auth auth) {
		if (obterUsuario(auth.getBu(), auth.getId()) != null) return;
		this.inserirUsuario(auth.getBu(), auth.getId());
	}
	
}
