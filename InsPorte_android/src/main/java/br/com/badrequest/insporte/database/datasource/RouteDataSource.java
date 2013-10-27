package br.com.badrequest.insporte.database.datasource;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import br.com.badrequest.insporte.beans.Route;
import br.com.badrequest.insporte.util.Constants;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class RouteDataSource extends SQLiteAssetHelper {

    private final String QRY_ROUTES = "SELECT * FROM linha";

    public RouteDataSource(Context context) {
        super(context, Constants.DATABASE_NAME,
                null,
                null, Constants.DATABASE_VERSION);
        setForcedUpgradeVersion(Constants.DATABASE_VERSION);
    }

    public List<Route> listRoutes() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        List<Route> routes = new ArrayList<Route>();
        try {
            cursor = db.rawQuery(QRY_ROUTES, null);
            while (cursor.moveToNext()) {
                Route route = new Route();
                route.setId(cursor.getInt(0));
                route.setCod(cursor.getString(1));
                route.setName(cursor.getString(2));
                routes.add(route);
            }

            return routes;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

    }


}
