package t2_v2;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class Main {
    private static ArrayList<Pokemon> pokedex = new ArrayList<>(); 
    private static ArrayList<String> habitats = new ArrayList<>(); 
    private static ArrayList<Gimnasio> gimnasios = new ArrayList<>(); 
    private static ArrayList<AltoMando> altoMandos = new ArrayList<>(); 
    private static Jugador jugador; 
    private static Scanner sc = new Scanner(System.in); 
    private static Random rand = new Random(); 

    // inicia todo el programa
    public static void main(String[] args) {
        cargarArchivos(); 
        menuInicial(); 
    }

    
    // lee los txt guardados
    private static void cargarArchivos() {
        try (Scanner lector = new Scanner(new File("Pokedex.txt"))) {
            // lee pokedex linea linea
            while (lector.hasNextLine()) {
                String[] pts = lector.nextLine().split(";"); 
                pokedex.add(new Pokemon(pts[0], pts[1], Double.parseDouble(pts[2]), 
                Integer.parseInt(pts[3]), Integer.parseInt(pts[4]), Integer.parseInt(pts[5]), 
                Integer.parseInt(pts[6]), Integer.parseInt(pts[7]), Integer.parseInt(pts[8]), pts[9])); 
            }
        } catch (Exception e) {} 

        try (Scanner lector = new Scanner(new File("Habitats.txt"))) {
            // lee zonas linea linea
            while (lector.hasNextLine()) habitats.add(lector.nextLine()); 
        } catch (Exception e) {} 

        try (Scanner lector = new Scanner(new File("Gimnasios.txt"))) {
            // lee gym linea linea
            while (lector.hasNextLine()) {
                String[] pts = lector.nextLine().split(";"); 
                ArrayList<String> eq = new ArrayList<>(); 
                for(int i=4; i < pts.length; i++) eq.add(pts[i]); 
                gimnasios.add(new Gimnasio(Integer.parseInt(pts[0]), pts[1], pts[2], eq)); 
            }
        } catch (Exception e) {} 

        try (Scanner lector = new Scanner(new File("Alto Mando.txt"))) {
            // lee jefes linea linea
            while (lector.hasNextLine()) {
                String[] pts = lector.nextLine().split(";"); 
                ArrayList<String> eq = new ArrayList<>(); 
                for(int i=2; i < pts.length; i++) eq.add(pts[i]); 
                altoMandos.add(new AltoMando(Integer.parseInt(pts[0]), pts[1], eq)); 
            }
        } catch (Exception e) {} 
    }

    // carga menu de inicio
    private static void menuInicial() {
        // repite menu hasta entrar
        while(true) { // aqui
            System.out.println("1) Continuar."); 
            System.out.println("2) Nueva Partida."); 
            System.out.println("3) Salir."); 
            System.out.print("Ingrese Opcion: "); 
            String op = sc.nextLine(); 
            
            if(op.equals("1")) {
                cargarPartida(); 
                if(jugador != null) menuPrincipal(); 
            } else if(op.equals("2")) {
                System.out.print("Ingrese Apodo: "); 
                String nom = sc.nextLine(); 
                jugador = new Jugador(nom, "none"); 
                System.out.println("Bienvenido " + nom + "!!"); 
                menuPrincipal(); 
            } else if(op.equals("3")) {
                break; 
            }
        }
    }

    // lee el save game
    private static void cargarPartida() {
        try (Scanner lector = new Scanner(new File("Registros.txt"))) {
            if(!lector.hasNextLine()) return; 
            String[] info = lector.nextLine().split(";"); 
            jugador = new Jugador(info[0], info[1]); 
            
            // arma equipo del txt
            while (lector.hasNextLine()) {
                String[] pts = lector.nextLine().split(";"); 
                Pokemon base = buscarEnPokedex(pts[0]); 
                if(base != null) {
                    Pokemon capturado = new Pokemon(base, pts[1]); 
                    jugador.addPokemon(capturado); 
                }
            }
            System.out.println("Bienvenido " + jugador.getNombre() + "!!"); 
        } catch (Exception e) {
            System.out.println("No hay partida guardada"); 
        }
    }

    // guarda progreso en txt
    private static void guardarPartida() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Registros.txt"))) {
            bw.write(jugador.getNombre() + ";" + jugador.getUltimoLider() + "\n"); 
            // escribe cada bicho
            for(Pokemon p : jugador.getPokemons()) {
                bw.write(p.getNombre() + ";" + p.getEstado() + "\n"); 
            }
        } catch (Exception e) {} 
    }

    // menu principal del juego
    private static void menuPrincipal() {
        // repite opciones del jugador
        while(true) {
            System.out.println(jugador.getNombre() + ", que deseas hacer?"); 
            System.out.println("1) Revisar equipo."); 
            System.out.println("2) Salir a capturar."); 
            System.out.println("3) Acceso al PC (cambiar Pokémon del equipo)."); 
            System.out.println("4) Retar un gimnasio."); 
            System.out.println("5) Desafío al Alto Mando."); 
            System.out.println("6) Curar Pokémon."); 
            System.out.println("7) Guardar."); 
            System.out.println("8) Guardar y Salir."); 
            System.out.print("Ingrese Opcion: "); 
            String op = sc.nextLine(); 

            switch(op) {
                case "1" -> revisarEquipo(); 
                case "2" -> capturar(); 
                case "3" -> accesoPC(); 
                case "4" -> retarGimnasio(); 
                case "5" -> retarAltoMando(); 
                case "6" -> {
                    jugador.curarTodos(); 
                    System.out.println("Tu equipo se ha recuperado!"); 
                }
                case "7" -> guardarPartida(); 
                case "8" -> {
                    guardarPartida(); 
                    System.out.println("Nos vemos entrenador..."); 
                    System.exit(0); 
                }
            }
        }
    }

    // muestra seis pokemons principales
    private static void revisarEquipo() {
        System.out.println("Equipo Actual:"); 
        ArrayList<Pokemon> eq = jugador.getEquipo(); 
        for(int i=0; i<eq.size(); i++) {
            Pokemon p = eq.get(i); 
            System.out.println((i+1) + ") " + p.getNombre() + "|" + p.getTipo() + "|Stats totales: " + p.getStatsTotales()); 
        }
    }

    // logica atrapar bicho salvage
    private static void capturar() {
        System.out.println("Donde deseas ir a explorar?\nZonas disponibles:"); 
         System.out.println();
         
        
        System.out.print("Ingrese Zona: "); 
        int z = leerNumero() - 1; 
        if(z < 0 || z >= habitats.size()) return; 
        
        String hab = habitats.get(z); 
        ArrayList<Pokemon> posibles = new ArrayList<>(); 
        // filtra bichos por zona
        for(Pokemon p : pokedex) {
            if(p.getHabitat().equalsIgnoreCase(hab)) posibles.add(p); 
        }
        if(posibles.isEmpty()) return; 
        
        double r = rand.nextDouble(); 
        double sum = 0; 
        Pokemon salvaje = posibles.get(0); 
        // ruleta para sacar bicho
        for(Pokemon p : posibles) {
            sum += p.getProb(); 
            if(r <= sum) {
                salvaje = p; 
                break; 
            }
        }
        
        System.out.println("Oh!! Ha aparecido un increible " + salvaje.getNombre() + "!!"); 
        System.out.println("Que deseas hacer?\n1) Capturar\n2) Huir"); 
        System.out.print("Ingrese Opcion: "); 
        int c = leerNumero(); 
        if(c == 1) {
            jugador.addPokemon(new Pokemon(salvaje, "Vivo")); 
            System.out.println(salvaje.getNombre() + " capturado con exito!!"); 
            if(jugador.getEquipo().size() <= 6) System.out.println(salvaje.getNombre() + " ha sido agregado a tu equipo!"); 
        }
    }

    // logica del pc
    private static void accesoPC() {
        ArrayList<Pokemon> lista = jugador.getPokemons(); 
        for(int i=0; i<lista.size(); i++) {
            System.out.println((i+1) + ") " + lista.get(i).getNombre()); 
        }
        System.out.println("1) Cambiar Pokémon. 2) Salir."); 
        int op = leerNumero(); 
        if(op == 1) {
            System.out.println("Elija numero 1:"); 
            int n1 = leerNumero() - 1; 
            System.out.println("Elija numero 2:"); 
            int n2 = leerNumero() - 1; 
            if(n1 >= 0 && n2 >= 0 && n1 < lista.size() && n2 < lista.size()) {
                jugador.intercambiar(n1, n2); 
            }
        }
    }

    // logica retar un gym
    private static void retarGimnasio() {
        System.out.println("A cual Lider deseas retar??"); 
        for(int i=0; i<gimnasios.size(); i++) {
            Gimnasio g = gimnasios.get(i); 
            System.out.println((i+1) + ") " + g.getLider() + " - Estado: " + g.getEstado()); 
        }
        System.out.println((gimnasios.size()+1) + ") Volver al menu."); 
        int op = leerNumero() - 1; 
        if(op < 0 || op >= gimnasios.size()) return; 
        
        if(op > 0) {
            if(!gimnasios.get(op-1).getEstado().equals("Derrotado")) {
                System.out.println("Calmado Entrenador!!! No puedes retar a " + gimnasios.get(op).getLider() + " sin haber derrotado a los lideres anteriores!!"); 
                return; 
            }
        }
        
        System.out.println("Desafiando a " + gimnasios.get(op).getLider() + "!!"); 
        ArrayList<Pokemon> equipoRival = instanciarEquipo(gimnasios.get(op).getEquipoNombres()); 
        
        boolean ganaJugador = MotorCombate.pelear(jugador, equipoRival, sc); 
        if(ganaJugador) {
            gimnasios.get(op).setEstado("Derrotado"); 
            jugador.setUltimoLider(gimnasios.get(op).getLider()); 
            System.out.println("Derrotaste a " + gimnasios.get(op).getLider() + "!"); 
        }
    }

    // pelea con jefes finales
    private static void retarAltoMando() {
        boolean ganoTodos = true; 
        for(Gimnasio g : gimnasios) {
            if(!g.getEstado().equals("Derrotado")) ganoTodos = false; 
        }
        if(!ganoTodos) {
            System.out.println("Aun te faltan medallas!"); 
            return; 
        }
        
        // pelea todos los jefes
        for(AltoMando am : altoMandos) {
            System.out.println("Desafiando a " + am.getNombre() + " del Alto Mando!"); 
            ArrayList<Pokemon> rivales = instanciarEquipo(am.getEquipoNombres()); 
            boolean gano = MotorCombate.pelear(jugador, rivales, sc); 
            if(!gano) {
                System.out.println("Has perdido el desafio al Alto Mando..."); 
                return; 
            }
            System.out.println("Derrotaste a " + am.getNombre() + "!"); 
        }
        System.out.println("ERES EL CAMPEON DE LA REGION!!"); 
    }

    // lee numero sin caer
    private static int leerNumero() {
        try {
            return Integer.parseInt(sc.nextLine()); 
        } catch (Exception e) {
            return -1; 
        }
    }

    // saca pokemon de bd
    private static Pokemon buscarEnPokedex(String nom) {
        for(Pokemon p : pokedex) {
            if(p.getNombre().equalsIgnoreCase(nom)) return p; 
        }
        return null; 
    }
    
    // arma equipo del jefe
    private static ArrayList<Pokemon> instanciarEquipo(ArrayList<String> nombres) {
        ArrayList<Pokemon> res = new ArrayList<>(); 
        for(String n : nombres) {
            Pokemon base = buscarEnPokedex(n); 
            if(base != null) res.add(new Pokemon(base, "Vivo")); 
        }
        return res; 
    }
}
