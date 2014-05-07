package br.com.badrequest.insporte.activity.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by gmarques on 3/28/14.
 */
public class TranslucentActivity extends InsporteBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getResources().getIdentifier("config_enableTranslucentDecor", "bool", "android");
        if (id != 0) { //KitKat or higher
            boolean translucentBarSupported = getResources().getBoolean(id);
            if (translucentBarSupported) {
                Window w = getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        systemBarTint();
    }

    void systemBarTint() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#CF2B2F"));
    }
}
