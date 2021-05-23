package com.example.login1703;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FirstTitleFragment extends Fragment{
    TextView yourTextView;

    public FirstTitleFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                ((NavigationHost) getActivity()).navigateMenu(new ArticlesFragment(), false);
            }
        };*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_title, container, false);

        //yourTextView = view.findViewById(R.id.title_1);
        //yourTextView.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

    /*@Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    ((NavigationHost) getActivity()).navigateMenu(new ArticlesFragment(), false);
                    return true;
                }
                return false;
            }
        });
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
        ((NavigationHost) getActivity()).navigateMenu(new ArticlesFragment(), false);
    }*/





}