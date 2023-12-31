package com.example.emergencyalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    Button btnRegis, btnIniciar;
    FirebaseAuth auth;
    ProgressBar progressBar;
    TextView minvitado;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MenuOpciones.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        btnIniciar = findViewById(R.id.btn_Login);
        progressBar = findViewById(R.id.progressBar2);
        btnRegis = findViewById(R.id.btnRegistrarLog);
        minvitado = findViewById(R.id.txtinvitado);
        minvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MenuOpciones.class);
                startActivity(intent);
                finish();
            }
        });
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                finish();
            }
        });
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "Ingrese Usuario", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Ingresar Contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Inicio Satisfactorio", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MenuOpciones.class);
                                    startActivity(intent);
                                    finish();

                                }else{
                                    Toast.makeText(Login.this, "Autenticación Fallida", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}