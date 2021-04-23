package com.example.login1703;

import android.content.SharedPreferences;
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
//import com.google.firebase.authNewsMap.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

public class LogInFragment extends Fragment {

    private static final String CORRECT_EMAIL = "khmizyuk";
    private static final String CORRECT_PASSWORD = "1234";
    //Button log_in_button;
    //Button sign_in_button;
    //FirebaseAuth auth; // Авторизация
    //FirebaseDatabase db; // Подключение к бд
    //DatabaseReference users; // Работа с табличками внутри бд

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

        //sign_in_button = view.findViewById(R.id.sign_in_button);
        //log_in_button = view.findViewById(R.id.log_in_button);

        /*
        auth = FirebaseAuth.getInstance(); // Запускаем авторизацию в бд
        db = FirebaseDatabase.getInstance(); // Подключаемся непосредственно к бд
        users = db.getReference("Users"); // Указываем с какой табличкой работаем*/


        /////////////////////////////////////////////////////////

        MaterialButton sign_in_button = view.findViewById(R.id.sign_in_button);
        MaterialButton complete_sign_in_button = view.findViewById(R.id.complete_sign_in_button);
        MaterialButton log_in_button = view.findViewById(R.id.log_in_button);
        final TextInputEditText emailEditText = view.findViewById(R.id.log_in_email_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.log_in_password_input);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.log_in_password_text_input);

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