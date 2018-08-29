package com.example.leandrocastilho.helowoordturbo.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.adapters.InterestProductAdapter;
import com.example.leandrocastilho.helowoordturbo.models.InterestProduct;
import com.example.leandrocastilho.helowoordturbo.tasks.InterestProductEvents;
import com.example.leandrocastilho.helowoordturbo.tasks.InterestProductTasks;
import com.example.leandrocastilho.helowoordturbo.util.CheckNetworkConnection;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class InterestProductListFragment extends Fragment implements InterestProductEvents {

    private static String STATE_LIST_INTEREST_PRODUCT = "List_Interest_Product";

    private ListView listViewInterestProducts;
    private List<InterestProduct> interestProducts;

    public InterestProductListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_interest_products_list, container, false);

        getActivity().setTitle("Interest Product List");

        setHasOptionsMenu(true);

        this.listViewInterestProducts = (ListView) rootView.findViewById(R.id.interestProducts_list);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_LIST_INTEREST_PRODUCT)) {
            String interestProductListJson = savedInstanceState.getString(STATE_LIST_INTEREST_PRODUCT);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<InterestProduct>>() {
            }.getType();
            this.interestProducts = gson.fromJson(interestProductListJson, listType);
            loadInterestProductList(this.interestProducts);
        } else {
            loadInterestProducts();
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.interest_product_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_interest_product_list:
                Toast.makeText(getActivity(), R.string.refresh_interest_product, Toast.LENGTH_SHORT).show();
                this.listViewInterestProducts.setAdapter(null);
                loadInterestProducts();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadInterestProducts() {
        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            InterestProductTasks interestProductTasks = new InterestProductTasks(getActivity(),
                    InterestProductListFragment.this);
            SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String wsUsername = sharedSettings.getString(
                    getActivity().getString(R.string.pref_user_login),
                    getActivity().getString(R.string.pref_ws_default_username));
            interestProductTasks.getInterestProductsByEmail(wsUsername);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (this.interestProducts != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<InterestProduct>>() {
            }.getType();
            String interestProductListJson = gson.toJson(this.interestProducts, listType);
            outState.putString(STATE_LIST_INTEREST_PRODUCT, interestProductListJson);
            super.onSaveInstanceState(outState);
        }
    }

    private void loadInterestProductList(List<InterestProduct> interestProducts) {
        InterestProductAdapter interestProductAdapter = new InterestProductAdapter(
                getActivity(), interestProducts);
        this.listViewInterestProducts.setAdapter(interestProductAdapter);
    }

    @Override
    public void getInterestProductsFinished(List<InterestProduct> interestProducts) {
        this.interestProducts = interestProducts;
        loadInterestProductList(interestProducts);
    }

    @Override
    public void getInterestProductsFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Error - " + webServiceResponse.getResponseMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void getInterestProductsByEmailFinished(List<InterestProduct> interestProducts) {
        this.interestProducts = interestProducts;
        loadInterestProductList(interestProducts);
    }

    @Override
    public void getInterestProductsByEmailFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Error - " + webServiceResponse.getResponseMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void postInterestProductFinished(InterestProduct interestProduct) {
    }

    @Override
    public void postInterestProductFailed(WebServiceResponse webServiceResponse) {
    }

    @Override
    public void deleteInterestProductFinished(InterestProduct interestProduct) {
    }

    @Override
    public void deleteInterestProductFailed(WebServiceResponse webServiceResponse) {
    }

}


