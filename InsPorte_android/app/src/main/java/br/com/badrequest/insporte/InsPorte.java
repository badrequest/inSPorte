package br.com.badrequest.insporte;

import android.app.Application;
import br.com.badrequest.insporte.bean.dao.JsonSubmit;
import org.orman.dbms.Database;
import org.orman.dbms.sqliteandroid.SQLiteAndroid;
import org.orman.mapper.MappingSession;
import org.orman.mapper.SchemaCreationPolicy;

public class InsPorte extends Application {

    @Override
    public void onCreate() {
        Database db = new SQLiteAndroid(getApplicationContext(), "insport.db", 1);
        MappingSession.getConfiguration().setCreationPolicy(SchemaCreationPolicy.CREATE_IF_NOT_EXISTS);
        MappingSession.registerEntity(JsonSubmit.class);
        MappingSession.registerDatabase(db);
        MappingSession.start();
    }

}
