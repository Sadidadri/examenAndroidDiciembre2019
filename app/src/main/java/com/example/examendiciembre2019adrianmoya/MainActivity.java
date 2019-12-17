package com.example.examendiciembre2019adrianmoya;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private final int ids[]= {
            R.id.ficha1,R.id.ficha2,R.id.ficha3,R.id.ficha4,R.id.ficha5,R.id.ficha6,R.id.ficha7,R.id.ficha8,
            R.id.ficha9,R.id.ficha10,R.id.ficha11,R.id.ficha12,R.id.ficha13,R.id.ficha14,R.id.ficha15,R.id.ficha16,
            R.id.ficha17,R.id.ficha18,R.id.ficha19,R.id.ficha20,R.id.ficha21,R.id.ficha22,R.id.ficha23,R.id.ficha24,
            R.id.ficha25,R.id.ficha26,R.id.ficha27,R.id.ficha28,R.id.ficha29,R.id.ficha30,R.id.ficha31,R.id.ficha32
    };
    private int recursos[] = {
    R.drawable.ficha1,R.drawable.ficha2,R.drawable.ficha3,R.drawable.ficha4,R.drawable.ficha5,R.drawable.ficha6,R.drawable.ficha7,R.drawable.ficha8,
    R.drawable.ficha9,R.drawable.ficha10,R.drawable.ficha11,R.drawable.ficha12,R.drawable.ficha13,R.drawable.ficha14,R.drawable.ficha15,R.drawable.ficha16
    };
    private final int N_FICHAS = 32;
    private int posicion1 =-1;
    private int id1 =-1;
    //Variables sonido
    SoundPool soundPool;
    int idAcierto;
    int idFallo;
    //Variables tiempos
    private long tiempoInicial;
    private long tiempoFinal;
    private long diferenciaEnSegundos;

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC , 0);
        idAcierto = soundPool.load(this, R.raw.acierto, 0);
        idFallo = soundPool.load(this, R.raw.error, 0);
        tiempoInicial = System.currentTimeMillis();

        game = new Game(recursos);
    }

    public void levantaFicha(View v){




        final int fichaTocada = v.getId();
        final int posFicha = posicionFicha(fichaTocada);

        if(game.seHanAcertadoTodos()){ //Se han conseguido 16 parejas y si toca el usuario se muestra que ha ganado
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "¡HAS GANADO! Duración: "+diferenciaEnSegundos+" segundos.", Toast.LENGTH_SHORT);

            toast1.show();
        }
        else{ //Aun no se han conseguido las 16 parejas
            if(game.getEstado().equals("pulsacion1")){ //Estamos en la primera pulsada
                dibujaFicha(posFicha,fichaTocada);
                posicion1 = posFicha;
                id1 = fichaTocada;
                game.setEstado("pulsacion2");
                dibujaFicha(posFicha,fichaTocada);
            }
            else if(game.getEstado().equals("pulsacion2")){ //Estamos en la segunda pulsada
                dibujaFicha(posFicha,fichaTocada);
                if(posicion1 == posFicha){ //Comprobamos si vuelve a pulsar la misma ficha
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Has pulsado la misma ficha.", Toast.LENGTH_SHORT);

                    toast1.show();
                }
                else{ //Si pulsa otra ficha distinta, se ejecuta el delay

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(game.compruebaFichas(posicion1,posFicha)) { //Son parejas
                                soundPool.play(idAcierto, 1, 1, 1, 0, 1);
                                game.hasAcertado();
                                limpiaFicha(id1);
                                limpiaFicha(fichaTocada);
                                String cadena = "¡Has acertado!";

                                if(game.seHanAcertadoTodos()){ //Se han conseguido 16 parejas y gana
                                    tiempoFinal = System.currentTimeMillis();
                                    diferenciaEnSegundos = (tiempoFinal - tiempoInicial)/1000;
                                    cadena = "¡HAS GANADO! Duración: "+diferenciaEnSegundos+" segundos.";
                                }
                                Toast toast1 =
                                        Toast.makeText(getApplicationContext(),
                                                cadena, Toast.LENGTH_SHORT);
                                toast1.show();


                            }else{ //No son iguales
                                soundPool.play(idFallo, 1, 1, 1, 0, 1);
                                voltearFicha(id1);
                                voltearFicha(fichaTocada);
                                Toast toast1 =
                                        Toast.makeText(getApplicationContext(),
                                                "Has fallado :(", Toast.LENGTH_SHORT);

                                toast1.show();
                            }
                            game.setEstado("pulsacion1");
                        }

                    }, 400);
                }

            }
        }






        /*    Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            ""+posFicha, Toast.LENGTH_SHORT);

            toast1.show();
        */
    }

    private int posicionFicha(int id){
        int pos = 0;
        for (int i=0;i<N_FICHAS;i++) {
            if (ids[i] == id){
                pos = i;
            }
        }
        return pos;
    }

    private void dibujaFicha(int pos,int id){

        ImageView iView = (ImageView)findViewById(id);

        iView.setImageResource(game.getTablero()[pos]);

    }
    private void limpiaFicha(int id){

        ImageView iView = (ImageView)findViewById(id);

        iView.setImageResource(0);

    }
    private void voltearFicha(int id){

        ImageView iView = (ImageView)findViewById(id);

        iView.setImageResource(R.drawable.fondo);

    }
}
