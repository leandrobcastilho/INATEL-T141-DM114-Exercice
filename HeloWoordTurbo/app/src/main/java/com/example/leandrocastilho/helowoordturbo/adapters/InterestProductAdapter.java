package com.example.leandrocastilho.helowoordturbo.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.models.InterestProduct;

import java.util.List;

public class InterestProductAdapter extends BaseAdapter {

    private final Activity activity;
    private List<InterestProduct> interestProducts;

    public InterestProductAdapter(Activity activity, List<InterestProduct> interestProducts) {
        this.activity = activity;
        this.interestProducts = interestProducts;
    }

    @Override
    public int getCount() {
        return this.interestProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.interestProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.interestProducts.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = this.activity.getLayoutInflater().inflate(R.layout.interest_product_list_item, null);
        InterestProduct interestProduct = this.interestProducts.get(position);
        TextView interestProductListItemNumber = (TextView) view.findViewById(R.id.interestProductListItemNumber);
        interestProductListItemNumber.setText(Integer.toString(position + 1));
        TextView interestProductListItemCode = (TextView) view.findViewById(R.id.interestProductListItemCode);
        interestProductListItemCode.setText(interestProduct.getCode());
        TextView interestProductListItemPrice = (TextView) view.findViewById(R.id.interestProductListItemPrice);
        interestProductListItemPrice.setText(Double.toString(interestProduct.getPrice()));
        return view;
    }
}
