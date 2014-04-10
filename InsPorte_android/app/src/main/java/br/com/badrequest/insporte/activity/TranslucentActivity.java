package br.com.badrequest.insporte.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by gmarques on 3/28/14.
 */
public class TranslucentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int id = getResources().getIdentifier("config_enableTranslucentDecor", "bool", "android");
        if (id != 0) { //KitKat or higher
            boolean enabled = getResources().getBoolean(id);
            // enabled = are translucent bars supported on this device
            if (enabled) {
                Window w = getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        super.onCreate(savedInstanceState);
    }
}
