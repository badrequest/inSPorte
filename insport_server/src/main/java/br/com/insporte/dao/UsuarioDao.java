package br.com.insporte.dao;

import br.com.insporte.db.model.Usuario;

public interface UsuarioDao {

	public Usuario obterUsuario(String bu, String cpf);

	public void inserirUsuario(String bu, String cpf);
}
