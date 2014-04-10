package br.com.badrequest.insporte.integration.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gmarques on 3/18/14.
 */

@NoArgsConstructor
public @Getter @Setter class ExtraSurveyInfo implements Serializable {

    private String id;

    private Double lat;

    private Double lon;

    private Date data;

    public ExtraSurveyInfo(String id, Double lat, Double lon, Date data) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.data = data;
    }
}
