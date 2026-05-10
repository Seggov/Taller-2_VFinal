package t2_v2;

import java.util.ArrayList;

public class Jugador {
    //Atributos de la clase.
    private String nombre; 
    private String ultimoLider; 
    private ArrayList<Pokemon> pokemons; 

    // constructor de clase.
    public Jugador(String n, String ul) {
        this.nombre = n; 
        this.ultimoLider = ul; 
        this.pokemons = new ArrayList<>(); 
    }

   
    public void addPokemon(Pokemon p) { // Agregar Pokemon al equipo del jugador.
        pokemons.add(p); 
    }

    public ArrayList<Pokemon> getPokemons() { return pokemons; }
    public String getNombre() { return nombre; }//obtener nombre del Jugador.
    public String getUltimoLider() { return ultimoLider; } //Obtener nombre del último lider de gimnacio derrotado. 
    public void setUltimoLider(String l) { this.ultimoLider = l; }

    // saca los seis principales
    public ArrayList<Pokemon> getEquipo() {
        ArrayList<Pokemon> eq = new ArrayList<>(); 
        // limite seis pokemones.
        for (int i = 0; i < Math.min(6, pokemons.size()); i++) { 
            eq.add(pokemons.get(i)); 
        }
        return eq; 
    }

    // ve si puede pelear
    public boolean tieneVivos() {//método para ver si es que se aún quedan pokemones wiwos para pelear. 
        for (Pokemon p : getEquipo()) { 
            if (p.getEstado().equals("Vivo")) return true; 
        }
        return false; 
    }

    // sana todos los pokemones.
    public void curarTodos() {
        for (Pokemon p : pokemons) { 
            p.setEstado("Vivo"); 
        }
    }
    
    // cambia orden en pc (Opción 3 del menú principal).
    public void intercambiar(int i, int j) {
        Pokemon temp = pokemons.get(i); 
        pokemons.set(i, pokemons.get(j)); 
        pokemons.set(j, temp); 
    }
}
