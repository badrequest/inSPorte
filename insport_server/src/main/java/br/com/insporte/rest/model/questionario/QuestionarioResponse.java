package br.com.insporte.rest.model.questionario;

import java.io.Serializable;

public class QuestionarioResponse implements Serializable {
	
	private String ans;

	public QuestionarioResponse() {
	}

	public QuestionarioResponse(String authOk) {
		this.ans = authOk;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}
	
}
