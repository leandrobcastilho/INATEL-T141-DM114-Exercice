package com.example.leandrocastilho.helowoordturbo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leandrocastilho.helowoordturbo.R;

public class Tela1Fragment extends Fragment {

    private EditText editText1;
    private Button button1;
    private TextView textView1;

    public Tela1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tela1, container, false);

        getActivity().setTitle("Tela 1");

        editText1 = (EditText) rootView.findViewById(R.id.editText1);
        button1 = (Button) rootView.findViewById(R.id.button1);
        textView1 = (TextView) rootView.findViewById(R.id.textView1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText1.getText().toString().isEmpty())
                    textView1.setText(editText1.getText().toString());
            }
        });

        return rootView;
    }


}