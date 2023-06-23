package com.zrisan.my_finance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 2000; // Duración del splash screen en milisegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Temporizador para mostrar el splash screen durante un tiempo determinado
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Verificar si existe un usuario guardado en las preferencias compartidas
                if (isUserLoggedIn()) {
                    // Si existe un usuario, redirigir al MainActivity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Si no existe un usuario, redirigir al LoginActivity
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish(); // Finalizar la actividad actual para que no se pueda volver atrás
            }
        }, SPLASH_DURATION);
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("Auth", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        return token != null && !token.isEmpty();
    }
}


