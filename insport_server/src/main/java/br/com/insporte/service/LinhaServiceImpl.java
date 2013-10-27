package br.com.insporte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.insporte.dao.LinhaDao;
import br.com.insporte.db.model.Linha;
import br.com.insporte.db.model.Usuario;
import br.com.insporte.rest.model.auth.Auth;

@Service
public class LinhaServiceImpl implements LinhaService {

	@Autowired
	private LinhaDao linhaDao;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public Linha obterLinha(Long id) {
		return linhaDao.obterLinha(id);
	}

	@Override
	public List<Linha> listarLinhas() {
		return linhaDao.listarLinhas();
	}

	@Override
	public Boolean obterLike(Long idLinha, Auth auth) {
		Linha linha = linhaDao.obterLinha(idLinha);
		Usuario usuario = usuarioService.obterUsuario(auth);
		return linhaDao.obterLike(linha, usuario);
	}

	@Override
	public void salvarLike(Long idLinha, Auth auth, Boolean like) {
		Linha linha = linhaDao.obterLinha(idLinha);
		Usuario usuario = usuarioService.obterUsuario(auth);
		linhaDao.salvarLike(linha, usuario, like);
	}

}
