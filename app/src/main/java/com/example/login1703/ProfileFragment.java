package com.example.login1703;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login1703.Models.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;

    private String firstname, lastname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        MaterialTextView userEmailInfo = view.findViewById(R.id.userEmailInfo);
        userEmailInfo.setText(mAuth.getCurrentUser().getEmail());

        mDataBase.child("users").orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                            User user = userSnapshot.getValue(User.class);
                            firstname = user.getFirstName();
                            lastname = user.getLastName();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        MaterialTextView firstnameField = view.findViewById(R.id.firstname);
        firstnameField.setText(firstname);
        MaterialTextView lastnameField = view.findViewById(R.id.lastname);
        lastnameField.setText(lastname);

        ImageView logOutImage = view.findViewById(R.id.log_out_image);

        logOutImage.setOnClickListener(new View.OnClickListener() {
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