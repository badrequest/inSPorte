package br.com.badrequest.insporte.bean.database;

import com.orm.SugarRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
public @Getter @Setter class SurveyJson extends SugarRecord<SurveyJson> implements Serializable {

    private String json;

    private boolean sent = false;

    public SurveyJson setJson(String json) {
        this.json = json;
        return this;
    }

    public SurveyJson setSent(boolean sent) {
        this.sent = sent;
        return this;
    }

    public static List<SurveyJson> findAllToSend() {
        return SugarRecord.find(SurveyJson.class, "sent = ?", "0");
    }

}
