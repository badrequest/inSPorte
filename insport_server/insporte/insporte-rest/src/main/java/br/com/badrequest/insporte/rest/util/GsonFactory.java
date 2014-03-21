package br.com.badrequest.insporte.rest.util;

import java.util.List;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    public static Gson build(final List<String> fields, final List<Class<?>> clazes) {
        GsonBuilder b = new GsonBuilder();
        b.addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
            	String field = f.getClass().getSimpleName() + "." + f.getName();
                return fields == null ? false : !fields.contains(field);            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return clazes == null ? false : !clazes.contains(clazz.getSimpleName());
            }
        });
        return b.create();
    }
    
    public static Gson build(final List<String> fields) {
        GsonBuilder b = new GsonBuilder();
        b.addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
            	String field = f.getDeclaringClass().getSimpleName() + "." + f.getName();
                return fields == null ? false : !fields.contains(field);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        return b.create();
    }

	public String toJson(List<String> attrs, Object results) {
		return GsonFactory.build(attrs).toJson(results);
	}
	
}