package br.com.badrequest.insporte.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public @Getter @Setter class Question implements Serializable {

    private int id;
    private String question;
    private List<Option> options;

}
