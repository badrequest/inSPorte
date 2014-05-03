package br.com.badrequest.insporte.integration.service.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * Created by gmarques on 4/23/14.
 */
public class CustomHttpMessageConverter extends GsonHttpMessageConverter {
    protected static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    protected static Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setDateFormat(DATE_FORMAT);

        return gsonBuilder.create();
    }

    public CustomHttpMessageConverter()  {
        super(buildGson());
    }
}
