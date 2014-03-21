package br.com.badrequest.insporte.rest.model.auth;

import java.io.Serializable;

/**
 * Armazena autenticacao basica com bilhete unico e id (cpf)
 */
public class Auth implements Serializable {
	
	public String bu;
	public String id;
	
	public String token;
	
	public String email;
	public String senha;

}
