package com.grover101.misarticulos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cerrarSesion = (Button) findViewById(R.id.btn_cerrar_sesion);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                });
            }
        });

        Button datosUsuario = (Button) findViewById(R.id.btn_datos_usuario);
        datosUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lanzarDatosUsuario();
            }
        });

        Button btnAgregarDB = (Button) findViewById(R.id.btn_agregarDB);
        btnAgregarDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("mensaje2");
                myRef.setValue("Hola mundo");
            }
        });

        // Leer datos de firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("mensaje2");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d("Ejemplo Firebase", "Value: "+ value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Ejemplo Firebase", "Error al leer: "+ error.toException());
            }
        });
    }

    public void lanzarDatosUsuario(){
        Intent i = new Intent(this,UsuarioActivity.class);
        startActivity(i);
    }
}