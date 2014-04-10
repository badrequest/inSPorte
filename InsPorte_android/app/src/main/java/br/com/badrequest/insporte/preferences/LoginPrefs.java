package br.com.badrequest.insporte.preferences;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by gmarques on 3/27/14.
 */

@SharedPref
public interface LoginPrefs {

    String userId();

    String pass();

}
