package br.com.insporte.rest.model.questionario;

import java.io.Serializable;

public class Opcao implements Serializable{

	public Long idOpcao;

	public Opcao() {
		
	}

	public Long getIdOpcao() {
		return idOpcao;
	}

	public void setIdOpcao(Long idOpcao) {
		this.idOpcao = idOpcao;
	}

}