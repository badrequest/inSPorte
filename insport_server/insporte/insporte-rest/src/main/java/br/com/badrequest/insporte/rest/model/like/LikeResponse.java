package br.com.badrequest.insporte.rest.model.like;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LikeResponse implements Serializable {

	private String ans;
	
	private Boolean like;
	
}