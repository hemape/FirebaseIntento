package com.example.hector.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hector.firebase.model.Usuario;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import static android.R.attr.password;

public class RegistroDeUsuario extends AppCompatActivity {

    EditText usuariazo;
    EditText nombre;
    EditText apellido;
    EditText direccion;
    EditText correo;
    EditText contra;
    ListView lista;
    Button mostrar;
    Button anyadir;
    Button modificar;
    ArrayList<String> listadito;
    ArrayList<String> listaditoUsuarios;


    private FirebaseAuth mAuth;
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_de_usuario);




        usuariazo = (EditText) findViewById(R.id.usuarioUsu);
        nombre = (EditText) findViewById(R.id.nombreUsu);
        apellido = (EditText) findViewById(R.id.apellidosUsu);
        direccion = (EditText) findViewById(R.id.direccionUsu);
        correo = (EditText) findViewById(R.id.correoUsu);
        contra = (EditText) findViewById(R.id.contraUsu);
        mostrar = (Button) findViewById(R.id.botonMostrar);
        anyadir = (Button) findViewById(R.id.botonAnyadirUsu);
        modificar = (Button) findViewById(R.id.botonModificar );
        lista = (ListView) findViewById(R.id.listViewItems);




        bbdd = FirebaseDatabase.getInstance().getReference(getString(R.string.nodo_usuarios));

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                listadito = new ArrayList<String>();
                listaditoUsuarios = new ArrayList<String>();

                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                    Usuario usu = datasnapshot.getValue(Usuario.class);

                    String nombre = usu.getNombre();
                    String usuario = usu.getUsuario();
                    listadito.add(nombre);
                    listaditoUsuarios.add(usuario);

                }
                adaptador = new ArrayAdapter<String>(RegistroDeUsuario.this,android.R.layout.simple_list_item_1,listadito);
                lista.setAdapter(adaptador);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nombre.getText().toString();
                String cognom = apellido.getText().toString();
                String direccio = direccion.getText().toString();
                String correu = correo.getText().toString();
                String usuario = usuariazo.getText().toString();
                String contrasenya = contra.getText().toString();



                if (nom.isEmpty() || cognom.isEmpty() || direccio.isEmpty() || correu.isEmpty() || usuario.isEmpty() || contrasenya.isEmpty()){

                    Toast.makeText(RegistroDeUsuario.this, " Se deben rellenar todos los datos para crear un usuario", Toast.LENGTH_LONG).show();
                }
                else{


                    boolean existe = true;
                    for (int i = 0; i<listaditoUsuarios.size(); i++){
                        if (usuariazo.getText().toString().equals(listaditoUsuarios.get(i))){
                            Toast.makeText(RegistroDeUsuario.this, "Ya existe", Toast.LENGTH_LONG).show();
                            existe = false;
                        }
                    }
                    if (existe){
                        String clave = bbdd.push().getKey();
                        Usuario u = new Usuario(nom, cognom, direccio, correu, usuario);


                        bbdd.child(clave).setValue(u);

                        registrarCorreoContra(correu,contrasenya);

                        Toast.makeText(RegistroDeUsuario.this, "Se ha creado un usuario con los datos introducidos", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        modificar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String asd = usuariazo.getText().toString();

                if(!asd.isEmpty()){
                    Query q=bbdd.orderByChild(getString(R.string.campo_usuario)).equalTo(asd);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();
                                if (!apellido.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.nodo_apellidos)).setValue(apellido.getText().toString());
                                }
                                if (!nombre.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.nodo_nombre)).setValue(nombre.getText().toString());
                                }
                                if (!direccion.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.nodo_direccion)).setValue(direccion.getText().toString());
                                }
                                if (!correo.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.nodo_correo)).setValue(correo.getText().toString());
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(RegistroDeUsuario.this, "Los datos se han modificado con exito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(RegistroDeUsuario.this, "Debes de introducir datos diferentes", Toast.LENGTH_LONG).show();
                }

            }
        });

        modificar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String asd = usuariazo.getText().toString();

                if(!asd.isEmpty()){
                    Query q=bbdd.orderByChild(getString(R.string.campo_usuario)).equalTo(asd);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();
                                if (!apellido.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.nodo_apellidos)).setValue(apellido.getText().toString());
                                }
                                if (!nombre.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.nodo_nombre)).setValue(nombre.getText().toString());
                                }
                                if (!direccion.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.nodo_direccion)).setValue(direccion.getText().toString());
                                }
                                if (!correo.getText().toString().isEmpty()){
                                    bbdd.child(clave).child(getString(R.string.nodo_correo)).setValue(correo.getText().toString());
                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(RegistroDeUsuario.this, "Los datos se han modificado con exito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(RegistroDeUsuario.this, "Debes de introducir datos diferentes", Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    private void registrarCorreoContra(String correu, String contrasenya) {
        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(this, "soy registrar correo contra", Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(correu, contrasenya)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(RegistroDeUsuario.this, InicioDeSesion.class));
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegistroDeUsuario.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();

                        }


                    }
                });
    }


}


