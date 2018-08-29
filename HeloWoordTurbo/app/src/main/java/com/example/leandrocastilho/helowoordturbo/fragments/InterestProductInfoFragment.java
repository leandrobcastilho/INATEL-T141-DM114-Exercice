package com.example.leandrocastilho.helowoordturbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.models.InterestProduct;
import com.example.leandrocastilho.helowoordturbo.tasks.InterestProductEvents;
import com.example.leandrocastilho.helowoordturbo.tasks.InterestProductTasks;
import com.example.leandrocastilho.helowoordturbo.util.CheckNetworkConnection;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceResponse;
import com.google.gson.Gson;

import java.util.List;

public class InterestProductInfoFragment extends Fragment {

    private static String STATE_INTEREST_PRODUCT = "InterestProduct";
    private InterestProduct interestProduct;

    private TextView textViewInterestProductId;
    private TextView textViewInterestProductCode;
    private TextView textViewInterestProductEmail;
    private TextView textViewInterestProductPrice;


    public InterestProductInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_interest_product_info, container, false);

        getActivity().setTitle("Interest Product Info");
        textViewInterestProductId = (TextView) rootView.findViewById(R.id.textViewInterestProductId);
        textViewInterestProductCode = (TextView) rootView.findViewById(R.id.textViewInterestProductCode);
        textViewInterestProductEmail = (TextView) rootView.findViewById(R.id.textViewInterestProductEmail);
        textViewInterestProductPrice = (TextView) rootView.findViewById(R.id.textViewInterestProductPrice);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_INTEREST_PRODUCT)) {
            String interestProductJson = savedInstanceState.getString(STATE_INTEREST_PRODUCT);
            Gson gson = new Gson();
            this.interestProduct = gson.fromJson(interestProductJson, InterestProduct.class);
            loadInterestProduct(this.interestProduct);
        } else {
            Bundle arguments = getArguments();
            if (arguments != null && arguments.containsKey("productOfInterest")) {
                InterestProduct interestProduct = (InterestProduct) arguments.getSerializable("productOfInterest");
                loadInterestProduct(interestProduct);
            }
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (this.interestProduct != null) {
            Gson gson = new Gson();
            String interestProductListJson = gson.toJson(this.interestProduct, InterestProduct.class);
            outState.putString(STATE_INTEREST_PRODUCT, interestProductListJson);
            super.onSaveInstanceState(outState);
        }
    }

    private void loadInterestProduct(InterestProduct interestProduct) {
        textViewInterestProductId.setText(Long.toString(interestProduct.getId()));
        textViewInterestProductCode.setText(interestProduct.getCode());
        textViewInterestProductEmail.setText(interestProduct.getEmail());
        textViewInterestProductPrice.setText(Double.toString(interestProduct.getPrice()));
    }

}