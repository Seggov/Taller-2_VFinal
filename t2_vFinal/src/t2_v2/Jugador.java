package t2_v2;

import java.util.ArrayList;

public class Jugador {
    private String nombre; 
    private String ultimoLider; 
    private ArrayList<Pokemon> pokemons; 

    // constructor del prota
    public Jugador(String n, String ul) {
        this.nombre = n; 
        this.ultimoLider = ul; 
        this.pokemons = new ArrayList<>(); 
    }

    // mete bicho al pc
    public void addPokemon(Pokemon p) {
        pokemons.add(p); 
    }

    public ArrayList<Pokemon> getPokemons() { return pokemons; }
    public String getNombre() { return nombre; }
    public String getUltimoLider() { return ultimoLider; }
    public void setUltimoLider(String l) { this.ultimoLider = l; }

    // saca los seis principales
    public ArrayList<Pokemon> getEquipo() {
        ArrayList<Pokemon> eq = new ArrayList<>(); 
        // limite seis bichos
        for (int i = 0; i < Math.min(6, pokemons.size()); i++) { 
            eq.add(pokemons.get(i)); 
        }
        return eq; 
    }

    // ve si puede pelear
    public boolean tieneVivos() {
        for (Pokemon p : getEquipo()) { 
            if (p.getEstado().equals("Vivo")) return true; 
        }
        return false; 
    }

    // sana todos los bichos
    public void curarTodos() {
        for (Pokemon p : pokemons) { 
            p.setEstado("Vivo"); 
        }
    }
    
    // cambia orden en pc
    public void intercambiar(int i, int j) {
        Pokemon temp = pokemons.get(i); 
        pokemons.set(i, pokemons.get(j)); 
        pokemons.set(j, temp); 
    }
}
