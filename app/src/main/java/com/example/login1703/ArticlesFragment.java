package com.example.login1703;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

public class ArticlesFragment extends Fragment {

    private MaterialCardView card_title_1;
    private MaterialCardView card_title_2;
    private MaterialCardView card_title_3;

    public ArticlesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        card_title_1 = view.findViewById(R.id.title_1);
        card_title_2 = view.findViewById(R.id.title_2);

        card_title_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new FirstTitleFragment(), true);
            }
        });

        card_title_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new SecondTitleFragment(), true);
            }
        });
        return view;
    }
}