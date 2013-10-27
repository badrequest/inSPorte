package br.com.insporte.dao;

import java.util.List;

import br.com.insporte.db.model.Likes;
import br.com.insporte.db.model.Linha;
import br.com.insporte.db.model.Usuario;

public interface LinhaDao {

	public Linha obterLinha(Long id);
	
	public List<Linha> listarLinhas();
	
	public Boolean obterLike(Linha linha, Usuario usuario);
	
	public Likes obter(Linha linha, Usuario usuario);
	
	public void salvarLike(Linha linha, Usuario usuario, Boolean like);
	
}
