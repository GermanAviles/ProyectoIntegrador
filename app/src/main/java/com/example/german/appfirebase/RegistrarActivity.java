package com.example.german.appfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrarActivity extends AppCompatActivity {
    private Button btnregistrar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtConfirmarEmail;
    private EditText txtPassword;
    private EditText txtTelefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        btnregistrar = (Button) findViewById(R.id.btnRegistrar);

        txtConfirmarEmail= (EditText) findViewById(R.id.txtConfirmarEmail);
        txtEmail=(EditText) findViewById(R.id.txtEmail);
        txtNombre=(EditText) findViewById(R.id.txtNombre);
        txtPassword=(EditText) findViewById(R.id.txtPassword);
        txtTelefono= (EditText) findViewById(R.id.txtTelefono);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("LoginActivity", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("LoginActivity", "onAuthStateChanged:signed_out");
                }
            }
        };

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtEmail.getText().toString().trim().equals(txtConfirmarEmail.getText().toString().trim())){
                    mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString().trim(), txtPassword.getText().toString().trim())
                            .addOnCompleteListener(RegistrarActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegistrarActivity.this, "Registro Fallido :"+task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }else{

                                        Toast.makeText(RegistrarActivity.this, "Registro Exitoso :",
                                                Toast.LENGTH_SHORT).show();
                                        mAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(RegistrarActivity.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(!task.isSuccessful()){
                                                            Toast.makeText(RegistrarActivity.this, "Error al enviar email. "+task.getException().getMessage(),
                                                                    Toast.LENGTH_SHORT).show();
                                                            System.out.println(task.getException().getMessage());
                                                        }else{

                                                            Toast.makeText(RegistrarActivity.this, "Recuerde validar su email validar su email",
                                                                    Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });

                                        UserProfileChangeRequest usuario = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(txtNombre.getText().toString().trim()).build();

                                        mAuth.getCurrentUser().updateProfile(usuario);

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference usuariosRef = database.getReference("Registro usuarios").child("UID de usuarios").child(mAuth.getCurrentUser().getUid());


                                        usuariosRef.child("nombre").setValue(txtNombre.getText().toString().trim());
                                        usuariosRef.child("email").setValue(txtEmail.getText().toString().trim());
                                        usuariosRef.child("telefono").setValue(txtTelefono.getText().toString().trim());
                                        usuariosRef.child("uid").setValue(mAuth.getCurrentUser().getUid());


                                        txtEmail.setText("");
                                        txtPassword.setText("");
                                        txtTelefono.setText("");
                                        txtNombre.setText("");
                                        txtConfirmarEmail.setText("");
                                        Intent intent = new Intent(RegistrarActivity.this,MainActivity.class);
                                        startActivity(intent);

                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }
}
