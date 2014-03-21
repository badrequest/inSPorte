package br.com.badrequest.insporte.rest.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Response implements Serializable {
	
	public static final String OK = "ok";
	public static final String FAIL = "fail";
	
	private String ans;
}
