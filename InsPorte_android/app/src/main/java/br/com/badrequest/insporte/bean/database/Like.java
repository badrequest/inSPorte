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
public @Getter @Setter class Like extends SugarRecord<Like> implements Serializable {

    private boolean like;
    private String routeId;
    private boolean sent = false;

    public Like(boolean like, String routeId) {
        this.like = like;
        this.routeId = routeId;
        this.sent = false;
    }

    public Like setLike(boolean like) {
        this.like = like;
        this.sent = false;
        return this;
    }

    public Like setSent(boolean sent) {
        this.sent = sent;
        return this;
    }

    public static List<Like> findAllToSend() {
        return SugarRecord.find(Like.class, "sent = ?", "0");
    }

    public static Like getLikeByRoute(String route) {
        List<Like> like = Like.find(Like.class, "route_id = ?", route);

        return !like.isEmpty() ? like.get(0) : null;

    }
}
