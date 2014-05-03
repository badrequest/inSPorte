package br.com.badrequest.insporte.integration.bean;

import lombok.*;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */

@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
@EqualsAndHashCode(of = "idOpcao")
public @Getter @Setter class QuestionOption implements Serializable {

    private int idOpcao;

}
