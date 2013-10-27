package br.com.insporte.rest.model.questionario;

import java.io.Serializable;

public class Comentario implements Serializable {
	
	private String texto;
	
	private Boolean imagem;
	
	public Comentario() {
		
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Boolean getImagem() {
		return imagem;
	}

	public void setImagem(Boolean imagem) {
		this.imagem = imagem;
	}

}
