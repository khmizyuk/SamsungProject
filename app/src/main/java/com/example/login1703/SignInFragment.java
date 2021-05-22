package com.example.login1703;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.login1703.Models.Markers;
import com.example.login1703.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class SignInFragment extends Fragment {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    //private FirebaseDatabase mDataBase;
    private DatabaseReference mDataBase;
    private DatabaseReference users;

    String email, password, firstName, lastName;

    RelativeLayout container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_in_design, container, false);

        final TextInputEditText emailEditText = view.findViewById(R.id.sign_in_email_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.sign_in_password_input);
        final TextInputEditText firstnameEditText = view.findViewById(R.id.sign_in_name_input);
        final TextInputEditText lastnameEditText = view.findViewById(R.id.sign_in_last_name_input);

        container = view.findViewById(R.id.container);

        mDataBase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

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
                                    Toast.LENGTH_LONG).show();
                            ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false);

                            DatabaseReference ref = mDataBase.child("users").push();
                            String pathKey = ref.getKey();
                            ref.setValue(new User(
                                    firstName,
                                    lastName,
                                    email,
                                    pathKey,
                                    0
                            )).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "addUserToDatabase:success");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.d(TAG, "addUserToDatabase:failure. " + e);
                                }
                            });

                            /*User newUser = new User();
                            newUser.setEmail(email);
                            newUser.setLastName(lastName);
                            newUser.setFirstName(firstName);
                            newUser.setId(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            users.push().setValue(newUser);

                            mDataBase.child("markers").child(FirebaseAuth.getInstance().getUid()).setValue(
                                    new User(
                                            firstName,
                                            lastName,
                                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                            0
                                    )
                            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "addUserToDatabase:success");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Log.d(TAG, "addUserToDatabase:failure."+e);
                                }
                            });*/

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }
                        hideProgressBar();
                    }
                });

        /*mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setFirstName(firstName);
                        user.setLastName(lastName);
                        user.setCountOfMarkers(0);

                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "createUserWithEmail:success");
                                Snackbar.make(container, "Your account is created.", Snackbar.LENGTH_SHORT).show();

                                ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false);

                                hideProgressBar();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Snackbar.make(container, "Your account was not created.", Snackbar.LENGTH_SHORT).show();
                                Log.w(TAG, "createUserWithEmail:failure");
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Snackbar.make(container, "Creating problem...", Snackbar.LENGTH_SHORT).show();
            }
        });*/

        /*mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User();
                            user.setEmail(email);
                            user.setFirstName(firstName);
                            user.setLastName(lastName);
                            user.setCountOfMarkers(0);

                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Snackbar.make(container, "Your account is created.", Snackbar.LENGTH_SHORT).show();

                                                ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false);

                                                hideProgressBar();
                                            }
                                            else {
                                                Toast.makeText(getContext(), "Authentication failed " + task.getException(),
                                                        Toast.LENGTH_SHORT).show();}
                                        }
                                    });
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }

                        hideProgressBar();
                    }
                });*/
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
        if (TextUtils.isEmpty(firstName)) {
            valid = false;
        }
        if (TextUtils.isEmpty(lastName)) {
            valid = false;
        }

        return valid;
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressBar();
    }

}