package t2_v2;

public class Pokemon {
    //Atributos de la clase pokemon.
    private String nombre; 
    private String habitat; 
    private double prob; 
    private int vida, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad; 
    private String tipo; 
    private String estado; 

    // crear pokemon.
    public Pokemon(String n, String h, double p, int v, int a, int d, int ae, int de, int vel, String t) {
        this.nombre = n; 
        this.habitat = h; 
        this.prob = p; 
        this.vida = v; 
        this.ataque = a; 
        this.defensa = d; 
        this.ataqueEspecial = ae; 
        this.defensaEspecial = de; 
        this.velocidad = vel; 
        this.tipo = t; 
        this.estado = "Vivo"; 
    }
    
    // clona un Pokemon capturado.
    public Pokemon(Pokemon otro, String estado) {
        this.nombre = otro.nombre; 
        this.habitat = otro.habitat; 
        this.prob = otro.prob; 
        this.vida = otro.vida; 
        this.ataque = otro.ataque; 
        this.defensa = otro.defensa; 
        this.ataqueEspecial = otro.ataqueEspecial; 
        this.defensaEspecial = otro.defensaEspecial; 
        this.velocidad = otro.velocidad; 
        this.tipo = otro.tipo; 
        this.estado = estado; 
    }

    // suma de los stats (Es la que se muestra en la opciónn 1 del menu principal ). 
    public int getStatsTotales() {
        return vida + ataque + defensa + ataqueEspecial + defensaEspecial + velocidad; 
    }

    public String getNombre() { return nombre; } //obtener el nombre del pokemon.
    public String getTipo() { return tipo; } //obtener tipo del pokemon.
    public String getEstado() { return estado; } //obtener si esta vivo o debilitado.
    public void setEstado(String e) { this.estado = e; } 
    public double getProb() { return prob; } // obtener la probabilidad de aparición.
    public String getHabitat() { return habitat; } //Obtener el habitat del pokemon.
}
