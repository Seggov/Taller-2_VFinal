package t2_v2;

import java.util.ArrayList;

public class Gimnasio {
    // atributos
    private int numero; 
    private String lider; 
    private String estado; 
    private ArrayList<String> equipoNombres; 

    // crea un gym
    public Gimnasio(int num, String lid, String est, ArrayList<String> eq) {
        this.numero = num; 
        this.lider = lid; 
        this.estado = est; 
        this.equipoNombres = eq; 
    }

    public int getNumero() { return numero; } //Obtener número del lider del gimnasio.
    public String getLider() { return lider; } //Obtener el nombre del lider del gimnasio.
    public String getEstado() { return estado; } //Sber si es que se ha derrotado o no.
    public void setEstado(String e) { this.estado = e; } 
    public ArrayList<String> getEquipoNombres() { return equipoNombres; } //Pokemones del lider del gimnasio.
}
