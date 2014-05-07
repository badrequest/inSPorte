package br.com.badrequest.insporte.bean;

public enum SurveyType {
    ONIBUS(1),
    MOTORISTA(2),
    PONTO(3),
    INCIDENTE(4);

    private int id;

    SurveyType(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
