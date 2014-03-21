package br.com.badrequest.insporte.rest.model.like.graph;

public class LikeQtd {
	
	private String linha;
	
	private Long qtd;
	
	public LikeQtd() {
		
	}
	
	public LikeQtd(String linha, Long qtd) {
		this.linha = linha;
		this.qtd = qtd;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public Long getQtd() {
		return qtd;
	}

	public void setQtd(Long qtd) {
		this.qtd = qtd;
	}

}