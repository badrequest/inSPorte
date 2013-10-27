package br.com.insporte.rest.model.like;

import java.io.Serializable;

public class Like implements Serializable {

	public Boolean status;
	
	public Long id;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
