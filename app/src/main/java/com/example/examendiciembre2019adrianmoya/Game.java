package com.example.examendiciembre2019adrianmoya;

import java.util.Random;

public class Game {
    private static final int N_FICHAS = 32;
    private int aciertos = 0;
    private String estado;
    public int idAlmacenado;

    public int tablero[];
    //Constructor
    Game(int[] recursos){
        tablero = new int[N_FICHAS];
        this.aciertos = 0;
        setEstado("pulsacion1");

        tablero = desordenarRecursos(recursos);

    }
    public int[] getTablero(){
        return this.tablero;
    }
    public String getEstado(){
        return this.estado;
    }
    public int getAciertos(){
        return this.aciertos;
    }
    public void setEstado(String estado){
        this.estado=estado;
    }
    public boolean seHanAcertadoTodos(){
        if(getAciertos() == N_FICHAS/2){
            return true;
        }
            return false;
    }
    public void hasAcertado(){
        this.aciertos++;
    }
    public boolean compruebaFichas(int pos1,int pos2){
        if(getTablero()[pos1] == getTablero()[pos2]){
            return true;
        }
        return false;
    }
    private int[] desordenarRecursos(int[] recursos){

        int[] recursosDesordenado = new int[N_FICHAS];

        for(int i=0;i<N_FICHAS;i++){
            recursosDesordenado[i] = recursos[i%16];
        }

        Random r = new Random();
        for (int i=0; i<recursosDesordenado.length; i++) {
            int posAleatoria = r.nextInt(recursosDesordenado.length);
            int temp = recursosDesordenado[i];
            recursosDesordenado[i] = recursosDesordenado[posAleatoria];
            recursosDesordenado[posAleatoria] = temp;
        }

        return recursosDesordenado;
    }

}
