package com.example.leandrocastilho.helowoordturbo.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.models.OrderItem;

import java.util.List;

public class OrderDetailsAdapter extends BaseAdapter {
    private final Activity activity;
    List<OrderItem> orderItems;

    public OrderDetailsAdapter(Activity activity, List<OrderItem> orderItems) {
        this.activity = activity;
        this.orderItems = orderItems;
    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public Object getItem(int position) {
        return orderItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return orderItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.order_details_list_item, null);
        OrderItem orderItem = orderItems.get(position);
        TextView orderDetailListItemNumber = (TextView) view.findViewById(R.id.orderDetailListItemNumber);
        orderDetailListItemNumber.setText(Integer.toString(position + 1));
        TextView orderDetailListItemId = (TextView) view.findViewById(R.id.orderDetailListItemId);
        orderDetailListItemId.setText(Long.toString(orderItem.getId()));
        TextView orderDetailListItemProductId = (TextView) view.findViewById(R.id.orderDetailListItemProductId);
        orderDetailListItemProductId.setText(Long.toString(orderItem.getProductId()));
        return view;
    }
}

