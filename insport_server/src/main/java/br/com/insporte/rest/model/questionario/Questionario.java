package br.com.insporte.rest.model.questionario;

import java.io.Serializable;
import java.util.List;

import br.com.insporte.rest.model.auth.Auth;

public class Questionario implements Serializable {

	private Auth auth;
	
	private String request;
	
	private Info info;
	
	private Resposta resposta;
	
	private Comentario comentario;
	
	public Questionario() {
		
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}
	
}
