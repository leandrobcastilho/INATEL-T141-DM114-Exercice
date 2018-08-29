package com.example.leandrocastilho.helowoordturbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.models.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ListaPedidosFragment extends Fragment {

    private ListView listViewPedidos;
    private List<Pedido> pedidos;

    public ListaPedidosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_lista_pedidos, container, false);

        getActivity().setTitle("Lista de Pedidos");

        pedidos = new ArrayList<Pedido>();
        for (int j = 0; j < 5; j++) {
            Pedido pedidoAux = new Pedido();
            pedidoAux.setOrderId(j);
            pedidoAux.setDataPedido("10/0" + j + "/2016 11:50:00");
            pedidos.add(pedidoAux);
        }

        listViewPedidos = (ListView) rootView.findViewById(R.id.listView1);

        listViewPedidos.setAdapter(new ArrayAdapter<Pedido>(
                getActivity(), android.R.layout.simple_list_item_1,
                pedidos));


        return rootView;
    }
}