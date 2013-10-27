package br.com.insporte.service;

import java.util.List;

import br.com.insporte.db.model.Linha;
import br.com.insporte.rest.model.auth.Auth;

public interface LinhaService {

	public Linha obterLinha(Long id);
	
	public List<Linha> listarLinhas();
	
	public Boolean obterLike(Long idLinha, Auth auth);
	
	public void salvarLike(Long idLinha, Auth auth, Boolean like);

}
