package com.example.ovman.appbotox;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MenuActivity extends Activity {

    private Button allergen;
    private Button botox;
    private Button zones;
    /*private ImageButton allergen;
    private ImageButton botox;
    private ImageButton zones;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Obtener el modelo para hacer un list view de la carpeta de assets, de DemoActivity.java
        /*AssetManager assets = getApplicationContext().getAssets();
        String[] models = null;
        try {
            models = assets.list("models");
        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }*/

        allergen = (Button)findViewById(R.id.infoAllerButton);
        botox = (Button)findViewById(R.id.infoBotoxButton);
        zones = (Button)findViewById(R.id.zonasButton);

        //Mandar a sus respectivas pantallas
        allergen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AllergenActivity.class);
                startActivity(intent);
            }
        });
        botox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, BotoxActivity.class);
                startActivity(intent);
            }
        });
        zones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this.getApplicationContext(), ZonesActivity.class);
                Bundle b = new Bundle();
                b.putString("assetDir", "models");
                //b.putString("assetFilename", "anatomico_v2.obj");
                b.putString("assetFilename", "ToyPlane.obj");
                b.putString("immersiveMode", "true");
                intent.putExtras(b);
                Log.i("MenuBundle", "Menu bundle es: "+b);
                startActivity(intent);
            }
        });
    }
}
