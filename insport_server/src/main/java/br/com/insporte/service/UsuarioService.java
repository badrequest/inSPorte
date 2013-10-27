package br.com.insporte.service;

import br.com.insporte.db.model.Usuario;
import br.com.insporte.rest.model.auth.Auth;

public interface UsuarioService {

	public Usuario obterUsuario(String bu, String id);

	public void inserirUsuario(String bu, String id);
	
	public void inserirUsuario(Auth auth);
	
	public boolean validarUsuario(Auth auth);

	public Usuario obterUsuario(Auth auth);
}
