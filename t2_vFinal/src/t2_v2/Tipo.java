package t2_v2;

public enum Tipo {
    NORMAL(0), FUEGO(1), AGUA(2), PLANTA(3), ELECTRICO(4), HIELO(5), LUCHA(6),
    VENENO(7), TIERRA(8), VOLADOR(9), PSIQUICO(10), BICHO(11), ROCA(12),
    FANTASMA(13), DRAGON(14), ACERO(15), SINIESTRO(16), HADA(17);

    private int id; 

    // constructor del enum
    Tipo(int id) {
        this.id = id; 
    }

    public int getId() {
        return id; 
    }

    // convierte texto a tipo
    public static Tipo deString(String txt) {
        return Tipo.valueOf(txt.toUpperCase()); 
    }
}
