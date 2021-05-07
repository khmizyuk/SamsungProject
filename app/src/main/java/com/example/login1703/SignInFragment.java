package com.example.login1703;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;

    String email, password, firstName, lastName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_in_design, container, false);

        final TextInputEditText emailEditText = view.findViewById(R.id.sign_in_email_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.sign_in_password_input);
        final TextInputEditText firstnameEditText = view.findViewById(R.id.sign_in_name_input);
        final TextInputEditText lastnameEditText = view.findViewById(R.id.sign_in_last_name_input);

        MaterialButton signInButton = view.findViewById(R.id.complete_sign_in_button);
        MaterialButton backButton = view.findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new LogInFragment(), true);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                firstName = firstnameEditText.getText().toString();
                lastName = lastnameEditText.getText().toString();

                if (!validateForm()) {
                    Toast.makeText(getContext(), "Not validate form.",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    createAccount(email, password);
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        return view;
    }


    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        showProgressBar();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            //sendEmailVerification();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Your account is created.",
                                    Toast.LENGTH_SHORT).show();
                            ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), true);
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }

                        hideProgressBar();
                    }
                });
    }

    private void sendEmailVerification() {

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(),
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(getContext(),
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @VisibleForTesting
    public ProgressBar mProgressBar;

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    public void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            valid = false;
        }
        if (TextUtils.isEmpty(password)) {
            valid = false;
        }
        /*if (TextUtils.isEmpty(firstName)) {
            valid = false;
        }
        if (TextUtils.isEmpty(lastName)) {
            valid = false;
        }*/

        return valid;
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressBar();
    }

}