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

public class Tela2Fragment extends Fragment {

    private static String STATE_USER_TEXT = "user_text";

    private EditText editText1;
    private Button button1;
    private TextView textView1;

    public Tela2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tela2, container, false);

        getActivity().setTitle("Tela 2");

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

        if (savedInstanceState != null) {
            textView1.setText(savedInstanceState.getString(STATE_USER_TEXT));
        } else {
            textView1.setText("Olá de novo!!!");
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_USER_TEXT, textView1.getText().toString());
        super.onSaveInstanceState(outState);
    }


}