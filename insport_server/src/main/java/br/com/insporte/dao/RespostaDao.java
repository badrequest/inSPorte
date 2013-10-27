package br.com.insporte.dao;

import br.com.insporte.db.model.Comentario;
import br.com.insporte.db.model.Envio;
import br.com.insporte.db.model.Resposta;
import br.com.insporte.db.model.RespostaOpcao;

public interface RespostaDao {

	void salvarEnvio(Envio envio);

	void salvarResposta(Resposta resposta);

	void salvarRespostaOpcao(RespostaOpcao respostaOpcaoDB);
	
	void salvarComentario(Comentario comentario);
	
}
