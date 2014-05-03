package br.com.badrequest.insporte.preferences;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by gmarques on 3/27/14.
 */

@SharedPref(SharedPref.Scope.UNIQUE)
public interface LoginPrefs {

    String email();
    String uuid();
    String pass();

}
