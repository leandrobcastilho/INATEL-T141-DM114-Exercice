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
import com.example.leandrocastilho.helowoordturbo.models.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ListaPedidosFragmentMenu extends Fragment {

    private ListView listViewPedidos;
    private List<Pedido> pedidos;

    public ListaPedidosFragmentMenu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista_pedidos_sofisticado, container, false);

        getActivity().setTitle("Lista de Pedidos Sofisticado");

        pedidos = new ArrayList<Pedido>();
        for (int j = 0; j < 3; j++) {
            Pedido pedidoAux = new Pedido();
            pedidoAux.setOrderId(j);
            pedidoAux.setDataPedido("10/0" + j + "/2016 11:50:00");
            pedidos.add(pedidoAux);
        }

        listViewPedidos = (ListView) rootView.findViewById(R.id.listView1);

        PedidoAdapter pedidoAdapter = new PedidoAdapter(
                getActivity(), pedidos);
        listViewPedidos.setAdapter(pedidoAdapter);


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
                Toast.makeText(getActivity(),
                        R.string.menu_novo_pedido, Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}