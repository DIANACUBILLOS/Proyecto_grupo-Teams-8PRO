package com.example.team8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TerminosActivity extends AppCompatActivity {
    private Button btn_regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminos);

        btn_regresar = findViewById(R.id.btn_regresar);

        btn_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent= new Intent();
                resultIntent.putExtra("estado","aceptado");
                //cuando le envio 1 o 2 parametros se aceptan terminos y condiciones:
                setResult(Activity.RESULT_OK,resultIntent);
                finish();

            }
        });

    }
}