package com.example.login1703;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.Nullable;

public class LogInFragment extends Fragment {

    //private MaterialButton signIn, logIn;
    //private TextInputEditText email, password;
    //private TextInputLayout passwordTextInput;

    //private String getEmailText, getPasswordText;
    private static final String correctEmail = "123";
    private static final String correctPassword = "123";

    public LogInFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        MaterialButton signIn, logIn;
        //TextInputEditText email, password;

        TextInputEditText email = view.findViewById(R.id.log_in_email_input);
        TextInputEditText password = view.findViewById(R.id.log_in_password_input);

        TextInputLayout passwordTextInput;

        //email = view.findViewById(R.id.log_in_email_input);
        //password = view.findViewById(R.id.log_in_password_input);

        //getEmailText = email.getText().toString();
        //getPasswordText = password.getText().toString();

        passwordTextInput = view.findViewById(R.id.log_in_password_text_input);

        signIn = view.findViewById(R.id.sign_in_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NavigationHost) getActivity()).navigateTo(new SignInFragment(), true);
            }
        });

        logIn = view.findViewById(R.id.next_button);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! isPasswordValid(email.getText(), password.getText())) {
                    passwordTextInput.setError(getString(R.string.log_in_error_password));
                    //password.setText(null);
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    ((NavigationHost) getActivity()).navigateTo(new MainPageFragment(), false); // Navigate to the next Fragment
                }
            }
        });

        // Clear the error once more than 8 characters are typed.
        /*password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(email.getText(), password.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                }/
                return false;
            }
        });*/
        return view;
    }

    private boolean isPasswordValid(@NonNull Editable checkEmail, @NonNull Editable checkPassword) {
        return checkEmail.equals(correctEmail) && checkPassword.equals(correctPassword);
    }

}