package com.example.login1703;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstTitleFragment extends Fragment {
    TextView yourTextView;

    public FirstTitleFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    // ываывавы
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_title, container, false);
        //yourTextView = view.findViewById(R.id.title_1);
        //yourTextView.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }
}