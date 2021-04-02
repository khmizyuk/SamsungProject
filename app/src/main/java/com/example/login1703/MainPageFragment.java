package com.example.login1703;

import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageFragment extends Fragment {

    public MainPageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);;

        if (savedInstanceState == null) {
            ((NavigationHost) getActivity()).navigateMenuAdd(new MapFragment(), true);
        }

        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.articles:
                        ((NavigationHost) getActivity()).navigateMenu(new ArticlesFragment(), true);
                        break;
                    case R.id.map:
                        ((NavigationHost) getActivity()).navigateMenu(new MapFragment(), true);
                        break;
                    case R.id.profile:
                        ((NavigationHost) getActivity()).navigateMenu(new ProfileFragment(), true);
                        break;
                }
                return false;
            }
        });

        return view;
    }


}