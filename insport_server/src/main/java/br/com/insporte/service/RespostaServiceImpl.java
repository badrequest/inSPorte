package br.com.insporte.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.insporte.dao.RespostaDao;
import br.com.insporte.dao.UsuarioDao;
import br.com.insporte.db.model.Comentario;
import br.com.insporte.db.model.Envio;
import br.com.insporte.db.model.Linha;
import br.com.insporte.db.model.Resposta;
import br.com.insporte.db.model.RespostaOpcao;
import br.com.insporte.db.model.Usuario;
import br.com.insporte.rest.model.questionario.Info;
import br.com.insporte.rest.model.questionario.Opcao;
import br.com.insporte.rest.model.questionario.Pergunta;
import br.com.insporte.rest.model.questionario.Questionario;

@Service
public class RespostaServiceImpl implements RespostaService {

	@Autowired
	private RespostaDao respostaDao;

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private LinhaService linhaService;

	@Autowired
	private QuestionarioService questionarioService;

	@Override
	public void salvarResposta(Questionario questionario) {

		if (questionario.getAuth() != null) {

			Usuario usuario = usuarioDao.obterUsuario(questionario.getAuth()
					.getBu(), questionario.getAuth().getId());

			if (usuario != null) {

				Info info = questionario.getInfo();

				Linha linha = linhaService.obterLinha(info.getId());

				Comentario comentariodb = null;
				if (questionario.getComentario() != null
						&& !questionario.getComentario().getTexto().isEmpty()) {
					comentariodb = new Comentario();
					comentariodb.setTexto(questionario.getComentario()
							.getTexto());
					respostaDao.salvarComentario(comentariodb);
				}

				Envio envio = new Envio();
				envio.setData(new Date(info.getData()));
				envio.setLat(info.getLat());
				envio.setLon(info.getLon());
				envio.setLinha(linha);
				envio.setUsuario(usuario);
				envio.setComentario(comentariodb);

				respostaDao.salvarEnvio(envio);

				if (questionario.getResposta() != null) {
					for (Pergunta p : questionario.getResposta().getPerguntas()) {

						br.com.insporte.db.model.Pergunta perguntaDB = questionarioService
								.obterPergunta(p.getIdPergunta());

						Resposta resposta = new Resposta();
						resposta.setEnvio(envio);
						resposta.setPergunta(perguntaDB);

						respostaDao.salvarResposta(resposta);

						for (Opcao ro : p.getOpcoes()) {

							br.com.insporte.db.model.Opcao opcaoDB = questionarioService
									.obterOpcao(ro.getIdOpcao());

							RespostaOpcao respostaOpcaoDB = new RespostaOpcao();
							respostaOpcaoDB.setOpcao(opcaoDB);
							respostaOpcaoDB.setResposta(resposta);

							respostaDao.salvarRespostaOpcao(respostaOpcaoDB);
						}

					}
				}

			}
		}
	}

}
