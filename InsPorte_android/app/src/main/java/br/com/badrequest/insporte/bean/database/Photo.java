package br.com.badrequest.insporte.bean.database;

import com.orm.SugarRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gmarques on 4/27/14.
 */
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
public @Getter @Setter class Photo extends SugarRecord<Photo> implements Serializable {

    private int idPergunta;
    private int idImagem;
    private String imgPath;
    private boolean sent = false;

    public static List<Photo> findAllToSend() {
        return SugarRecord.find(Photo.class, "sent = ?", "0");
    }

}
