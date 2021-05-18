package com.example.login1703;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDataBase;
    private DatabaseReference users;

    private TextView firstNameField;
    private TextView lastNameField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mDataBase = FirebaseDatabase.getInstance();
        users = mDataBase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        MaterialTextView userEmailInfo = view.findViewById(R.id.userEmailInfo);
        userEmailInfo.setText(mAuth.getCurrentUser().getEmail());

        MaterialButton logOutButton = view.findViewById(R.id.log_out);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                ((NavigationHost) getActivity()).navigateTo(new LogInFragment(), false);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        return view;
    }
}