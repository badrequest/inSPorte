package br.com.insporte.rest.model.auth;

import java.io.Serializable;

/**
 * Armazena autenticacao basica com bilhete unico e id (cpf)
 */
public class Auth implements Serializable {
	
	private String bu;
	private String id;

	public Auth() {
	}
	
	public String getBu() {
		return bu;
	}

	public void setBu(String bu) {
		this.bu = bu;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
