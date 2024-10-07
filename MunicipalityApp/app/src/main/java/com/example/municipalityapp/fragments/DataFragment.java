package com.example.municipalityapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.municipalityapp.R;

public class DataFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String input = args.getString("input");
            TextView textView = view.findViewById(R.id.receiveInput);
            textView.setText(input);
        }

        return view;
    }


}