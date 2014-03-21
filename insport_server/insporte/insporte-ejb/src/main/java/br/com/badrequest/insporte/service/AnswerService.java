package br.com.badrequest.insporte.service;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.badrequest.insporte.exception.InsporteException;
import br.com.badrequest.insporte.exception.InvalidBusLine;
import br.com.badrequest.insporte.exception.InvalidOption;
import br.com.badrequest.insporte.exception.InvalidQuestion;
import br.com.badrequest.insporte.exception.InvalidUser;
import br.com.badrequest.insporte.model.Answer;
import br.com.badrequest.insporte.model.AnswerOption;
import br.com.badrequest.insporte.model.BusLine;
import br.com.badrequest.insporte.model.Image;
import br.com.badrequest.insporte.model.Option;
import br.com.badrequest.insporte.model.Question;
import br.com.badrequest.insporte.model.Questionary;
import br.com.badrequest.insporte.model.QuestionaryAnswer;
import br.com.badrequest.insporte.model.User;

@Stateful
public class AnswerService {

	@Inject
	EntityManager em;
	
	@Inject
	UserService userService;

	@Inject
	BusService busService;
	
	@Inject
	QuestionaryService questionaryService;
	
	public QuestionaryAnswer insert(QuestionaryAnswer qAnswer) throws InsporteException {
		
		User user = userService.getUser(qAnswer.getUser().getEmail());
		qAnswer.setUser(user);
		if (user == null) {
			throw new InvalidUser();
		}
		
		BusLine busLine = busService.getBusLine(qAnswer.getBusLine().getCode());
		qAnswer.setBusLine(busLine);
		if (busLine == null) {
			throw new InvalidBusLine();
		}
		
		Questionary questionary = questionaryService.getQuestionary(qAnswer.getQuestionary().getId());
		qAnswer.setQuestionary(questionary);
		
		if (qAnswer.getCommentary().getInsertImage() != null) {
			Image image = new Image();
			image.setPath(qAnswer.getCommentary().getInsertImage().toString());
			qAnswer.getCommentary().setImage(image);
		} 
		
		for (Answer answer : qAnswer.getAnswers()) {
			
			Question question = null;
			for (Question q : questionary.getQuestions()) {
				if (q.getId().equals(answer.getQuestion().getId())) {
					question = q;
					break;
				}
			}
			
			answer.setQuestion(question);
			answer.setQuestionaryAnswer(qAnswer);
			
			if (answer.getInsertImage() != null) {
				Image image = new Image();
				image.setPath(answer.getInsertImage().toString());
				answer.setImage(image);
			}
			
			if (question == null) {
				throw new InvalidQuestion();
			}
			
			for (AnswerOption answerOption : answer.getOptions()) {
				
				Option option = null;
				for (Option op : question.getOptions()) {
					if (op.getId().equals(answerOption.getOption().getId())) {
						option = op;
						break;
					}
				}
				
				if (option == null) {
					throw new InvalidOption();
				}
				
				answerOption.setAnswer(answer);
				answerOption.setOption(option);
			}

		}
		
		qAnswer = em.merge(qAnswer);
		
		return qAnswer;
	}
	
	
}
