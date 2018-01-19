package com.example.hector.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioDeSesion extends AppCompatActivity {
    EditText campoUsuario;
    EditText campoContra;
    Button iniciar;
    Button registrar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_de_sesion);

        campoUsuario = (EditText) findViewById(R.id.usuarioLogin);
        campoContra = (EditText) findViewById(R.id.contrasenyaLogin);
        iniciar = (Button) findViewById(R.id.btnInicioSesion);
        registrar = (Button) findViewById(R.id.btnRegistrarUsuarioNuevo);


        iniciar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                login(campoUsuario.getText().toString(), campoContra.getText().toString());
            }
       });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
}

    private void login(String email, String password) {

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(InicioDeSesion.this, "Authentication success", Toast.LENGTH_SHORT).show();

                            inicio();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(InicioDeSesion.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void inicio() {
        Intent inicio = new Intent(this,Perfiles.class);
        startActivity(inicio);
        finish();
    }

    private void registrar() {
       Intent nueva = new Intent(this, RegistroDeUsuario.class);
        startActivity(nueva);
        finish();
    }





}
