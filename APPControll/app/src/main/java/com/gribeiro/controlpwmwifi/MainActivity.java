package com.gribeiro.controlpwmwifi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private String ip;
    private EditText ip1;
    private EditText ip2;
    private EditText ip3;
    private EditText ip4;
    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip1 = findViewById(R.id.ip1);
        ip2 = findViewById(R.id.ip2);
        ip3 = findViewById(R.id.ip3);
        ip4 = findViewById(R.id.ip4);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(128);




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (ip != null && !ip.isEmpty()) {  // Verifica se o IP foi definido
                    EnviarPWM enviarPWM = new EnviarPWM(i, ip, 12345);  // Porta TCP (12345)
                    enviarPWM.start();  // Inicia a Thread para enviar o valor
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void defenirIP(View view){
        ip = ip1.getText().toString() + "." + ip2.getText().toString() + "." + ip3.getText().toString() + "." + ip4.getText().toString() ;
        Toast.makeText(
                MainActivity.this,
                "IP Definido",
                Toast.LENGTH_SHORT
        ).show();
    }



    class EnviarPWM extends Thread {
        private int valor;
        private String ip;
        private int porta;

        EnviarPWM(int valor, String ip, int porta) {
            this.valor = valor;
            this.ip = ip;
            this.porta = porta;
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket(ip, porta);
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                output.println(String.valueOf(valor));  // Envia o valor da SeekBar como mensagem

                Log.i("LOG", "Mensagem enviada: " + valor);

                output.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Erro ao enviar mensagem", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


}
