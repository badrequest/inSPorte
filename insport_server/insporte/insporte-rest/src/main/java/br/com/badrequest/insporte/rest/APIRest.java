package br.com.badrequest.insporte.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.badrequest.insporte.model.Answer;
import br.com.badrequest.insporte.model.AnswerOption;
import br.com.badrequest.insporte.model.QuestionaryAnswer;
import br.com.badrequest.insporte.rest.model.Response;
import br.com.badrequest.insporte.service.AnswerService;
import br.com.badrequest.insporte.service.UserService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * JAX-RS 
 * 
 * This class produces a RESTful service to read the contents of the Questionaries
 * table.
 */
@Path("/api")
@RequestScoped
@SuppressWarnings("unchecked")
public class APIRest {

	@Inject
	private AnswerService answerService;
	
	@Inject
	private UserService userService;
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String answer(@QueryParam("start") String start, @QueryParam("end") String end) {

		try {
			Date startDate, endDate;
			SimpleDateFormat completeFormat, smallFormat;
			completeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			smallFormat = new SimpleDateFormat("dd/MM/yyyy");
		
			startDate = start.length() <= 10 ? smallFormat.parse(start) : completeFormat.parse(start);
			endDate = end.length() <= 10 ? smallFormat.parse(end) : completeFormat.parse(end);
			
			List<QuestionaryAnswer> results = answerService.getAnswers(startDate, endDate);

			JsonArray jsonArray = new JsonArray();
			
			for (QuestionaryAnswer questionaryAnswer : results) {
				
				JsonObject commentaryJsonObject = null;
				
				if (questionaryAnswer.getCommentary() != null) {
					
					commentaryJsonObject = new JsonObject();
					
					if (questionaryAnswer.getCommentary() != null) {
						commentaryJsonObject.addProperty("text", questionaryAnswer.getCommentary().getTexto());
					}
					
					if (questionaryAnswer.getCommentary().getImage() != null) {
						commentaryJsonObject.addProperty("image_id", questionaryAnswer.getCommentary().getImage().getId());
					}
				}
				
				JsonArray answersJsonArray = new JsonArray();
				for (Answer answer : questionaryAnswer.getAnswers()) {
					JsonObject answerJsonObject = new JsonObject();
					answerJsonObject.addProperty("id", answer.getId());
					answerJsonObject.addProperty("question_id", answer.getQuestion().getId());
					
					if (answer.getImage() != null) {
						answerJsonObject.addProperty("image_id", answer.getImage().getId());
					}
					
					JsonArray optionsJsonArray = new JsonArray();
					for (AnswerOption answerOption : answer.getOptions()) {
						optionsJsonArray.add(new JsonPrimitive(answerOption.getOption().getId()));
					}
					
					answerJsonObject.add("options", optionsJsonArray);
					answersJsonArray.add(answerJsonObject);
				}
				
				JsonObject questionaryAnswerJsonObject = new JsonObject();
				questionaryAnswerJsonObject.addProperty("id", questionaryAnswer.getId());
				questionaryAnswerJsonObject.addProperty("user_id", questionaryAnswer.getUser().getId());
				questionaryAnswerJsonObject.addProperty("date", completeFormat.format(questionaryAnswer.getData()));
				questionaryAnswerJsonObject.addProperty("lat", questionaryAnswer.getLat());
				questionaryAnswerJsonObject.addProperty("lon", questionaryAnswer.getLon());
				questionaryAnswerJsonObject.addProperty("busline_code", questionaryAnswer.getBusLine().getCode());
				
				if (commentaryJsonObject != null) {
					questionaryAnswerJsonObject.add("commentary", commentaryJsonObject);
				}
				
				questionaryAnswerJsonObject.add("answers", answersJsonArray);

				jsonArray.add(questionaryAnswerJsonObject);
			}
			
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return new Gson().toJson(new Response(Response.FAIL));
		}
	}

}
