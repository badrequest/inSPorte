package br.com.badrequest.insporte.util;

import android.os.Environment;

public class Constants {


    public static final String TAG = "inSPorte";

    public static final String PICTURES_PATH = "pictures_path";


    /**
     * Database
     */
    public static final String DATABASE_FOLDER_PATH = "databases" ;


    public static final String DATABASE_FILE_PATH =
            Environment.getExternalStoragePublicDirectory(DATABASE_FOLDER_PATH).getAbsolutePath();

    public static final String DATABASE_NAME = "local";

    public static final int UNCOMPRESSED_SIZE = 6144;

    public static final int DATABASE_VERSION = 3;

    /**
     * URL AND WS
     */

    public static final String URL_WEBSERVICE = "http://000.000.000";

    public static final String URI_AUTH = "/auth";

    public static final String URI_UPLOAD_IMG = "/img";

    public static final int TIMEOUT_CONNECTION = 10000;

    public static final int TIMEOUT_DATA = 10000;

    /**
     *  PROPERTIES FIELDS
     */

    public static final String PROP_URL_WS = "URL_WEBSERVICE";

    public static final String PROP_BUS_CARD = "buscard";

    public static final String PROP_USER_ID = "userid";

}
