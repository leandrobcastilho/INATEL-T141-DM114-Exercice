package com.example.leandrocastilho.helowoordturbo.tasks;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.models.InterestProduct;
import com.example.leandrocastilho.helowoordturbo.util.WSMessageUtil;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceMessageClient;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class InterestProductTasks {

    private static final String GET_INTEREST_PRODUCTS = "/api/products";
    private static final String GET_INTEREST_PRODUCT_BY_EMAIL = "/api/products/byemail?email=";
    private static final String POST_INTEREST_PRODUCTS = "/api/products";
    private static final String DELETE_INTEREST_PRODUCTS = "/api/products/";

    private InterestProductEvents interestProductEvents;
    private Context context;
    private String baseAddress;

    public InterestProductTasks(Context context, InterestProductEvents interestProductEvents) {
        this.context = context;
        this.interestProductEvents = interestProductEvents;
        baseAddress = WSMessageUtil.getHostAddress(context);
    }

    @SuppressLint("StaticFieldLeak")
    public void getInterestProducts() {
        new AsyncTask<Void, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Void... params) {
                SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(context);
                String wsUsername = sharedSettings.getString(
                        context.getString(R.string.pref_user_login),
                        context.getString(R.string.pref_ws_default_username));
                String host = baseAddress + GET_INTEREST_PRODUCT_BY_EMAIL + wsUsername;
                return WebServiceMessageClient.get(context, host);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        List<InterestProduct> interestProducts = gson.fromJson(webServiceResponse.getResultMessage(), new TypeToken<List<InterestProduct>>() {
                        }.getType());
                        interestProductEvents.getInterestProductsFinished(interestProducts);
                    } catch (Exception e) {
                        interestProductEvents.getInterestProductsFailed(webServiceResponse);
                    }
                } else {
                    interestProductEvents.getInterestProductsFailed(webServiceResponse);
                }
            }
        }.execute(null, null, null);
    }

    @SuppressLint("StaticFieldLeak")
    public void getInterestProductsByEmail(String email) {
        new AsyncTask<String, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(String... email) {
                String host = baseAddress + GET_INTEREST_PRODUCT_BY_EMAIL + email[0];
                return WebServiceMessageClient.get(context, host);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        List<InterestProduct> interestProducts = gson.fromJson(webServiceResponse.getResultMessage(), new TypeToken<List<InterestProduct>>() {
                        }.getType());
                        interestProductEvents.getInterestProductsByEmailFinished(interestProducts);
                    } catch (Exception e) {
                        interestProductEvents.getInterestProductsByEmailFailed(webServiceResponse);
                    }
                } else {
                    interestProductEvents.getInterestProductsByEmailFailed(webServiceResponse);
                }
            }
        }.execute(email, null, null);
    }

    @SuppressLint("StaticFieldLeak")
    public void postInterestProduct(String interestProductJson) {
        new AsyncTask<String, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(String... interestProductJson) {
                String host = baseAddress + POST_INTEREST_PRODUCTS;
                return WebServiceMessageClient.post(context, host, interestProductJson[0]);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        InterestProduct interestProduct = gson.fromJson(webServiceResponse.getResultMessage(), InterestProduct.class);
                        interestProductEvents.postInterestProductFinished(interestProduct);
                    } catch (Exception e) {
                        interestProductEvents.postInterestProductFailed(webServiceResponse);
                    }
                } else {
                    interestProductEvents.postInterestProductFailed(webServiceResponse);
                }
            }
        }.execute(interestProductJson, null, null);
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteInterestProduct(long id) {
        new AsyncTask<Long, Void, WebServiceResponse>() {
            @Override
            protected WebServiceResponse doInBackground(Long... id) {
                String host = baseAddress + DELETE_INTEREST_PRODUCTS + Long.toString(id[0]);
                return WebServiceMessageClient.delete(context, host);
            }

            @Override
            protected void onPostExecute(WebServiceResponse webServiceResponse) {
                if (webServiceResponse.getResponseCode() == 200) {
                    Gson gson = new Gson();
                    try {
                        InterestProduct interestProduct = gson.fromJson(webServiceResponse.getResultMessage(), InterestProduct.class);
                        interestProductEvents.deleteInterestProductFinished(interestProduct);
                    } catch (Exception e) {
                        interestProductEvents.deleteInterestProductFailed(webServiceResponse);
                    }
                } else {
                    interestProductEvents.deleteInterestProductFailed(webServiceResponse);
                }
            }
        }.execute(id, null, null);
    }
}


