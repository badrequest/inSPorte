//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package br.com.badrequest.insporte.integration.service.handler;

import android.content.Context;

public final class ServiceErrorHandler_
    extends ServiceErrorHandler
{

    private Context context_;

    private ServiceErrorHandler_(Context context) {
        context_ = context;
        init_();
    }

    public static ServiceErrorHandler_ getInstance_(Context context) {
        return new ServiceErrorHandler_(context);
    }

    private void init_() {
    }

    public void rebind(Context context) {
        context_ = context;
        init_();
    }

}