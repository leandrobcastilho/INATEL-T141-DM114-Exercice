package com.example.leandrocastilho.helowoordturbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.adapters.PedidoAdapter;
import com.example.leandrocastilho.helowoordturbo.models.Order;
import com.example.leandrocastilho.helowoordturbo.models.Pedido;
import com.example.leandrocastilho.helowoordturbo.tasks.OrderEvents;
import com.example.leandrocastilho.helowoordturbo.tasks.OrderTasks;
import com.example.leandrocastilho.helowoordturbo.webservice.WebServiceResponse;

import java.util.ArrayList;
import java.util.List;

public class ListaPedidosSofisticadoFragment extends Fragment implements OrderEvents {

    private ListView listViewPedidos;
    private List<Pedido> pedidos;

    public ListaPedidosSofisticadoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista_pedidos_sofisticado, container, false);

        getActivity().setTitle("Lista de Pedidos Sofisticado");

        setHasOptionsMenu(true);

        pedidos = new ArrayList<Pedido>();
        for (int j = 0; j < 15; j++) {
            Pedido pedidoAux = new Pedido();
            pedidoAux.setOrderId(j);
            pedidoAux.setDataPedido("10/0" + j + "/2016 11:50:00");
            pedidos.add(pedidoAux);
        }

        listViewPedidos = (ListView) rootView.findViewById(R.id.listView1);

        PedidoAdapter pedidoAdapter = new PedidoAdapter(
                getActivity(), pedidos);
        listViewPedidos.setAdapter(pedidoAdapter);

        OrderTasks orderTasks = new OrderTasks(getActivity(), this);
        orderTasks.getOrders();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pedidos_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo_pedido:
                Toast.makeText(getActivity(), "Novo pedido", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getOrdersFinished(List<Order> orders) {
        Toast.makeText(getActivity(),
                "Numero Pedidos " + orders.size(), Toast.LENGTH_SHORT).show();
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