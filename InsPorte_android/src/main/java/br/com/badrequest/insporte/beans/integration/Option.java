package br.com.badrequest.insporte.beans.integration;

import java.io.Serializable;

/**
 * Created by gmarques on 3/18/14.
 */
public class Option implements Serializable {

    Integer idOpcao;

    public Option(Integer idOpcao) {
        this.idOpcao = idOpcao;
    }

    public Integer getIdOpcao() {
        return idOpcao;
    }

    public void setIdOpcao(Integer idOpcao) {
        this.idOpcao = idOpcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (!idOpcao.equals(option.idOpcao)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idOpcao.hashCode();
    }
}
