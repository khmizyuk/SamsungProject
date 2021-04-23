package com.example.login1703;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.login1703.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class SignInFragment extends Fragment {

    //FirebaseAuth auth; // Авторизация
    //FirebaseDatabase db; // Подключение к бд
    // DatabaseReference users; // Работа с табличками внутри бд

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        final TextInputEditText emailEditText = view.findViewById(R.id.log_in_email_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.log_in_password_input);
        final TextInputEditText firstname = view.findViewById(R.id.sign_in_name_input);
        final TextInputEditText lastname = view.findViewById(R.id.sign_in_last_name_input);

        /*auth = FirebaseAuth.getInstance(); // Запускаем авторизацию в бд
        db = FirebaseDatabase.getInstance(); // Подключаемся непосредственно к бд
        users = db.getReference("Users"); // Указываем с какой табличкой работаем

        auth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        user.setEmail((emailEditText.getText().toString()));
                        user.setPassword((passwordEditText.getText().toString()));
                        user.setFirstname((firstname.getText().toString()));
                        user.setLastname((lastname.getText().toString()));

                        users.child(user.getEmail())
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Snackbar.make(container, "Пользователь добавлен", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                    }
                });

        Button complete_sign_in_button = view.findViewById(R.id.complete_sign_in_button);
        complete_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(emailEditText.getText().toString())) {
                    return;
                }
                if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
                    return;
                }
                if (TextUtils.isEmpty(firstname.getText().toString())) {
                    return;
                }
                if (TextUtils.isEmpty(lastname.getText().toString())) {
                    return;
                }
                //((NavigationHost) getActivity()).navigateTo(new LogInFragment(), false);
            }
        });*/


        return view;
    }
}