package com.example.login1703;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LogInFragment extends Fragment {

    //private static final String CORRECT_EMAIL = "khmizyuk";
    //private static final String CORRECT_PASSWORD = "1234";

    public LogInFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in_design, container, false);

        MaterialButton sign_in_button = view.findViewById(R.id.sign_in_button);
        MaterialButton complete_sign_in_button = view.findViewById(R.id.complete_sign_in_button);
        MaterialButton log_in_button = view.findViewById(R.id.log_in_button);
        TextInputEditText emailEditText = view.findViewById(R.id.log_in_email_input);
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
                if (!isPasswordValid(emailEditText.getText().toString(), passwordEditText.getText().toString())) {
                    passwordTextInput.setError(getString(R.string.log_in_error_password));
                    passwordEditText.setText(null);
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false); // Navigate to the next Fragment
                }
            }
        });

        return view;
    }

    private boolean isPasswordValid(@NonNull String checkEmail, @NonNull String checkPassword) {
        //return (checkEmail.equals(CORRECT_EMAIL)) && (checkPassword.equals(CORRECT_PASSWORD));
        return true;
    }
}

