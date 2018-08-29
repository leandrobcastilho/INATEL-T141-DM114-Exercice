package com.example.leandrocastilho.helowoordturbo.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.leandrocastilho.helowoordturbo.R;
import com.example.leandrocastilho.helowoordturbo.models.Pedido;

import java.util.List;

public class PedidoAdapter extends BaseAdapter {

    private final Activity activity;
    List<Pedido> pedidos;

    public PedidoAdapter(Activity activity, List<Pedido> pedidos) {
        this.activity = activity;
        this.pedidos = pedidos;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pedidos.get(position).getOrderId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(
                R.layout.pedido_list_item, null);

        Pedido pedido = pedidos.get(position);
        TextView pedidoListItemNumber = (TextView) view.findViewById(R.id.pedidoListItemNumber);
        pedidoListItemNumber.setText(Integer.toString(position + 1));
        TextView pedidoListItemId = (TextView) view.findViewById(R.id.pedidoListItemId);
        pedidoListItemId.setText(Integer.toString(pedido.getOrderId()));
        TextView pedidoListItemDate = (TextView) view.findViewById(R.id.pedidoListItemDate);
        pedidoListItemDate.setText(pedido.getDataPedido());

        return view;
    }
}
