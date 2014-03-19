package br.com.badrequest.insporte.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.insporte.model.Answer;
import br.com.badrequest.insporte.model.AnswerOption;
import br.com.badrequest.insporte.model.BusLine;
import br.com.badrequest.insporte.model.Commentary;
import br.com.badrequest.insporte.model.Option;
import br.com.badrequest.insporte.model.Question;
import br.com.badrequest.insporte.model.Questionary;
import br.com.badrequest.insporte.model.QuestionaryAnswer;
import br.com.badrequest.insporte.model.User;
import br.com.badrequest.insporte.rest.model.Response;
import br.com.badrequest.insporte.rest.model.questionario.Imagem;
import br.com.badrequest.insporte.rest.model.questionario.Opcao;
import br.com.badrequest.insporte.rest.model.questionario.Pergunta;
import br.com.badrequest.insporte.rest.model.questionario.Questionario;
import br.com.badrequest.insporte.rest.model.questionario.QuestionarioResponse;
import br.com.badrequest.insporte.service.AnswerService;
import br.com.badrequest.insporte.service.UserService;

import com.google.gson.Gson;

/**
 * JAX-RS 
 * 
 * This class produces a RESTful service to read the contents of the Questionaries
 * table.
 */
@Path("/answer")
@RequestScoped
@SuppressWarnings("unchecked")
public class AnswerRest {

	@Inject
	private AnswerService answerService;
	
	@Inject
	private UserService userService;
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String answer(String json) {
		
		try {
			Questionario questionario = new Gson().fromJson(json, Questionario.class);
			QuestionaryAnswer qAnswer = convertJsonToModel(questionario);
			
			if (!userService.validateUser(qAnswer.getUser().getEmail(), qAnswer.getUser().getPassword())) {
				return new Gson().toJson(new Response(Response.FAIL));
			}
			
			qAnswer = answerService.insert(qAnswer);
			
			QuestionarioResponse qResponse = new QuestionarioResponse();
			qResponse.ans = Response.OK;

			List<Imagem> imagens = new ArrayList<Imagem>();
			for (Answer answer : qAnswer.getAnswers()) {
				if (answer.getImage().getPath().equals(Boolean.TRUE.toString())) {
					Imagem img = new Imagem();
					img.idImagem = answer.getId();
					img.idPergunta = answer.getQuestion().getId();
					imagens.add(img);
				}
			}
			
			qResponse.idImagens = imagens;
			
			if (qAnswer.getCommentary().getPath().equals(Boolean.TRUE.toString())) {
				qResponse.idImagemComentario = qAnswer.getCommentary().getId();
			}
			
			return new Gson().toJson(qResponse);
		} catch (Exception e) {
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}

	private QuestionaryAnswer convertJsonToModel(Questionario questionario) throws Exception {

		QuestionaryAnswer qAnswer = new QuestionaryAnswer();
		qAnswer.setLat(questionario.info.lat);
		qAnswer.setLon(questionario.info.lon);
		
		qAnswer.setData(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(questionario.info.data));
		
		User user = new User();
		user.setBu(questionario.auth.bu);
		user.setDocument(questionario.auth.id);
		user.setEmail(questionario.auth.email);
		user.setPassword(questionario.auth.senha);
		
		BusLine busLine = new BusLine();
		busLine.setCode(questionario.info.id);
		
		Commentary commentary = new Commentary();
		commentary.setTexto(questionario.comentario.texto);
		commentary.setInsertImage(questionario.comentario.imagem);
		commentary.setEnvio(qAnswer);
		
		Questionary questionary = new Questionary();
		questionary.setId(questionario.resposta.idQuestionario);
		
		List<Answer> answers = new ArrayList<Answer>();
		for (Pergunta p : questionario.resposta.perguntas) {
			
			Question question = new Question();
			question.setId(p.idPergunta);
			
			Answer answer = new Answer();
			answer.setQuestion(question);
			answer.setInsertImage(p.imagem);
			answer.setQuestionaryAnswer(qAnswer);
			
			List<AnswerOption> options = new ArrayList<AnswerOption>();
			for (Opcao opcao : p.opcoes) {
				
				Option option = new Option();
				option.setId(opcao.idOpcao);

				AnswerOption answerOption = new AnswerOption();
				answerOption.setOption(option);
				answerOption.setAnswer(answer);
				
				options.add(answerOption);
			}
			
			answer.setOptions(options);
			answers.add(answer);
		}
		
		qAnswer.setAnswers(answers);
		qAnswer.setBusLine(busLine);
		qAnswer.setCommentary(commentary);
		qAnswer.setQuestionary(questionary);
		qAnswer.setUser(user);
		
		return qAnswer;
	}
	
}
