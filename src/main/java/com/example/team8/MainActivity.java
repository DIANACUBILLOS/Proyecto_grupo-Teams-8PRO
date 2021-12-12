package com.example.team8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_jugar;
    private Button btn_perfil;
    private Button btn_ajustes;
    private Button btn_acerca_de;

    private Activity miActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miActividad = this;

        btn_jugar = findViewById(R.id.btn_jugar);
        btn_perfil= findViewById(R.id.btn_perfil);
        btn_ajustes= findViewById(R.id.btn_ajustes);
        btn_acerca_de= findViewById(R.id.btn_acerca_de);

        btn_jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            //contexto enviar parametro en donde voy a visualizar
            public void onClick(View v) {
                Log.e("CLICK_MAIN2","le he dado al boton JUGAR2");
                Toast.makeText(miActividad,"le he dado al boton jugar",Toast.LENGTH_SHORT).show();


            }
        });

        btn_jugar.setOnClickListener(this);
        btn_perfil.setOnClickListener(this);
        btn_ajustes.setOnClickListener(this);
        btn_acerca_de.setOnClickListener(this);
    }
    //this global
    public void clickJugar(View v){
        Log.e("CLICK_MAIN","le he dado al boton JUGAR");
        Toast.makeText(miActividad,"le he dado al boton jugar",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_jugar:
                Toast.makeText(this,getString(R.string.txt_jugar),Toast.LENGTH_SHORT).show();
            break;
            case R.id.btn_perfil:
                Toast.makeText(this,getString(R.string.txt_perfil),Toast.LENGTH_SHORT).show();
                 break;
            case R.id.btn_ajustes:
                Toast.makeText(this,getString(R.string.txt_ajustes), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_acerca_de:
                Toast.makeText(this,getString(R.string.txt_acerca_de), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}