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
import com.example.leandrocastilho.helowoordturbo.adapters.OrderDetailsAdapter;
import com.example.leandrocastilho.helowoordturbo.models.Order;
import com.example.leandrocastilho.helowoordturbo.tasks.OrderEvents;
import com.example.leandrocastilho.helowoordturbo.tasks.OrderTasks;
import com.example.leandrocastilho.helowoordturbo.util.CheckNetworkConnection;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceResponse;
import com.google.gson.Gson;

import java.util.List;

public class OrderDetailFragment extends Fragment implements OrderEvents {

    private static String STATE_ORDER = "Order";
    private Order order;

    private TextView textViewOrderId;
    private TextView textViewOrderEmail;
    private TextView textViewOrderFreightPrice;
    private ListView listViewOrderItens;

    public OrderDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_details, container, false);

        getActivity().setTitle("Order");
        textViewOrderId = (TextView) rootView.findViewById(R.id.textViewOrderId);
        textViewOrderEmail = (TextView) rootView.findViewById(R.id.textViewOrderEmail);
        textViewOrderFreightPrice = (TextView) rootView.findViewById(R.id.textViewOrderFreightPrice);
        listViewOrderItens = (ListView) rootView.findViewById(R.id.listViewOrderItemList);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ORDER)) {
            String orderJson = savedInstanceState.getString(STATE_ORDER);
            Gson gson = new Gson();
            this.order = gson.fromJson(orderJson, Order.class);
            loadOrder(this.order);
        } else {
            Bundle arguments = getArguments();
            if (arguments != null && arguments.containsKey("order_id")) {
                long orderId = arguments.getLong("order_id");
                if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
                    OrderTasks orderTasks = new OrderTasks(getActivity(), this);
                    orderTasks.getOrderById(orderId);
                }
            }
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (this.order != null) {
            Gson gson = new Gson();
            String orderListJson = gson.toJson(this.order, Order.class);
            outState.putString(STATE_ORDER, orderListJson);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void getOrdersFinished(List<Order> orders) {
    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {
    }

    @Override
    public void getOrderByIdFinished(Order order) {
        this.order = order;
        loadOrder(this.order);
    }

    private void loadOrder(Order order) {
        textViewOrderId.setText(Long.toString(order.getId()));
        textViewOrderEmail.setText(order.getEmail());
        textViewOrderFreightPrice.setText(Double.toString(order.getFreightPrice()));

        OrderDetailsAdapter orderDetailsAdapter = new OrderDetailsAdapter(
                getActivity(), order.getOrderItems());
        listViewOrderItens.setAdapter(orderDetailsAdapter);
    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {

        Toast.makeText(getActivity(),
                "Erro: " + webServiceResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
    }

}