package t2_v2;

import java.util.ArrayList;

public class AltoMando {
    //Variables caracteristicas de la clase.
    private int numero; 
    private String nombre; 
    private ArrayList<String> equipoNombres; 

    // crea el alto mando
    public AltoMando(int n, String nom, ArrayList<String> eq) {
        this.numero = n; 
        this.nombre = nom; 
        this.equipoNombres = eq; 
    }

    public String getNombre() { return nombre; } //Obtener el nombre del alto mando.
    public ArrayList<String> getEquipoNombres() { return equipoNombres; } //Equipo de pokemones del alto mando.
}
