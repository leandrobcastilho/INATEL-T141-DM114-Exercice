package com.example.leandrocastilho.helowoordturbo.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
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
import com.example.leandrocastilho.helowoordturbo.adapters.OrderAdapter;
import com.example.leandrocastilho.helowoordturbo.models.Order;
import com.example.leandrocastilho.helowoordturbo.tasks.OrderEvents;
import com.example.leandrocastilho.helowoordturbo.tasks.OrderTasks;
import com.example.leandrocastilho.helowoordturbo.util.CheckNetworkConnection;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class OrdersFragment extends Fragment implements OrderEvents {

    private static String STATE_LIST_ORDER = "List_Order";
    private ListView listViewOrders;
    private List<Order> orders;

    public OrdersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders_list, container, false);
        getActivity().setTitle("Orders");

        setHasOptionsMenu(true);

        listViewOrders = (ListView) rootView.findViewById(R.id.orders_list);

        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startOrderDetail(id);
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_LIST_ORDER)) {
            String orderListJson = savedInstanceState.getString(STATE_LIST_ORDER);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            this.orders = gson.fromJson(orderListJson, listType);
            loadOrderList(this.orders);
        } else {
            LoadOrders();
        }

        return rootView;
    }

    private void LoadOrders() {
        if (CheckNetworkConnection.isNetworkConnected(getActivity())) {
            OrderTasks orderTasks = new OrderTasks(getActivity(), this);
            orderTasks.getOrders();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.order_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_order_list:
                Toast.makeText(getActivity(), R.string.refresh_order, Toast.LENGTH_SHORT).show();
                listViewOrders.setAdapter(null);
                LoadOrders();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (this.orders != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Order>>() {
            }.getType();
            String orderListJson = gson.toJson(this.orders, listType);
            outState.putString(STATE_LIST_ORDER, orderListJson);
            super.onSaveInstanceState(outState);
        }
    }

    private void startOrderDetail(long orderId) {
        Class fragmentClass;
        Fragment fragment = null;
        fragmentClass = OrderDetailFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if (orderId >= 0) {
                Bundle args = new Bundle();
                args.putLong("order_id", orderId);
                fragment.setArguments(args);
            }
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container, fragment, OrderDetailFragment.class.getCanonicalName());
            transaction.addToBackStack(OrdersFragment.class.getCanonicalName());
            transaction.commit();
        } catch (Exception e) {
            try {
                Toast.makeText(getActivity(), "Erro ao tentar abrir detalhes do pedido", Toast.LENGTH_SHORT).show();
            } catch (Exception e1) {
            }
        }
    }

    private void loadOrderList(List<Order> orders) {
        OrderAdapter orderAdapter = new OrderAdapter(
                getActivity(), orders);
        listViewOrders.setAdapter(orderAdapter);
    }

    @Override
    public void getOrdersFinished(List<Order> orders) {
        this.orders = orders;
        loadOrderList(this.orders);
    }

    @Override
    public void getOrdersFailed(WebServiceResponse webServiceResponse) {
        Toast.makeText(getActivity(),
                "Erro: " + webServiceResponse.getResponseMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void getOrderByIdFinished(Order order) {
    }

    @Override
    public void getOrderByIdFailed(WebServiceResponse webServiceResponse) {
    }

}