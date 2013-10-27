package br.com.badrequest.insporte.util;

import com.google.gson.GsonBuilder;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class ServiceRequest {

	public static String makeJSONRequest(String path, String json) throws ParseException, ClientProtocolException, IOException {
		String resposta = "";

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, Constants.TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(httpParameters, Constants.TIMEOUT_DATA);
		
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpPost httpPost = new HttpPost(path);
		StringEntity se = new StringEntity(json, "UTF-8");

		httpPost.setEntity(se);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		resposta = EntityUtils.toString(httpClient.execute(httpPost)
				.getEntity());

		return resposta;
	}

	public static String makeRequest(String path, Map<String, ?> params) throws ParseException, ClientProtocolException, IOException {
		String resposta = "";

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, Constants.TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(httpParameters, Constants.TIMEOUT_DATA);

		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpPost httpPost = new HttpPost(path);

		String json = new GsonBuilder().create().toJson(params, Map.class);
		StringEntity se = new StringEntity(json, "UTF-8");
		httpPost.setEntity(se);

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		resposta = EntityUtils.toString(httpClient.execute(httpPost).getEntity());

		return resposta;
	}

}
