package br.com.insporte.rest.model;

import java.io.Serializable;

/*
 * Response padrao para retorno dos metodos REST
 */
public class Response implements Serializable {
	
	public static final String OK = "ok";
	public static final String FAIL = "fail";
	
	private String ans;

	public Response() {
	}

	public Response(String authOk) {
		this.ans = authOk;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}
	
}
