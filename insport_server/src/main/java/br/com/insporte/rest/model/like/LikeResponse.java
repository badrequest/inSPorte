package br.com.insporte.rest.model.like;

import java.io.Serializable;

public class LikeResponse implements Serializable {

	private String ans;
	
	private Boolean like;
	
	public LikeResponse() {
		
	}
	
	public LikeResponse(String ans, Boolean like) {
		this.ans = ans;
		this.like = like;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public Boolean isLike() {
		return like;
	}

	public void setLike(Boolean like) {
		this.like = like;
	}
	
}