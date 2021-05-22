package com.example.login1703;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class LogInFragment extends Fragment {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDataBase;
    private DatabaseReference users;

    RelativeLayout container;

    String email;
    String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in_design, container, false);

        mDataBase = FirebaseDatabase.getInstance();
        users = mDataBase.getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        container = view.findViewById(R.id.container);

        MaterialButton sign_in_button = view.findViewById(R.id.sign_in_button);
        MaterialButton log_in_button = view.findViewById(R.id.log_in_button);

        TextInputEditText emailEditText = view.findViewById(R.id.message);
        TextInputEditText passwordEditText = view.findViewById(R.id.log_in_password_input);
        TextInputLayout passwordTextInput = view.findViewById(R.id.log_in_password_text_input);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new SignInFragment(), true);
            }
        });

        log_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                signIn(email, password);

                /*
                if (!isPasswordValid(emailEditText.getText().toString(), passwordEditText.getText().toString())) {
                    passwordTextInput.setError(getString(R.string.log_in_error_password));
                    passwordEditText.setText(null);
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false); // Navigate to the next Fragment
                }*/
            }
        });

        mAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false);
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        if (!validateForm()) {
            Toast.makeText(getContext(), "Authentication failed: not validate form.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressBar();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Snackbar.make(container, "Authentication failed. "+task.getException(), Snackbar.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) { }
                        hideProgressBar();
                    }
                });

        /*mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "signInWithEmail:success");
                        ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Snackbar.make(container, "Authentication failed. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });*/
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

        return valid;
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressBar();
    }

    /*private boolean isPasswordValid(@NonNull String checkEmail, @NonNull String checkPassword) {
        //return (checkEmail.equals(CORRECT_EMAIL)) && (checkPassword.equals(CORRECT_PASSWORD));
        return true;
    }*/
}

