package t2_v2;

import java.util.ArrayList;
import java.util.Scanner;

public class MotorCombate {
    
    // logica principal del combate
    public static boolean pelear(Jugador j, ArrayList<Pokemon> rivales, Scanner sc) {
        int indiceRival = 0; 
        Pokemon actualRival = clonePokemon(rivales.get(indiceRival)); 
        
        System.out.println("El rival saca a " + actualRival.getNombre() + "!"); 
        System.out.println();
        Pokemon actualJugador = elegirPokemon(j, sc); 
        if(actualJugador == null) return false; 
        
        System.out.println(j.getNombre() + " saca a " + actualJugador.getNombre() + "!"); 
        
        // repite hasta que muera
        while(indiceRival < rivales.size() && j.tieneVivos()) { 
            System.out.println("Que deseas hacer?"); 
            System.out.println("1) Atacar"); 
            System.out.println("2) Cambiar de pokemon"); 
            System.out.println("3) Rendirse"); 
            System.out.println();
            System.out.print("Ingrese Opcion: ");
            int op = leerOpcion(sc); 
            if (op == 1) {
                boolean ganaJugador = simularChoque(actualJugador, actualRival); 
                if (ganaJugador) {
                    actualRival.setEstado("Debilitado"); 
                    indiceRival++; 
                    if(indiceRival < rivales.size()) {
                        actualRival = clonePokemon(rivales.get(indiceRival)); 
                        System.out.println("El rival saca a " + actualRival.getNombre() + "!"); 
                        System.out.println();
                    }
                } else {
                    actualJugador.setEstado("Debilitado"); 
                    if(j.tieneVivos()) {
                        actualJugador = elegirPokemon(j, sc); 
                    }
                }
            } else if (op == 2) {
                actualJugador = elegirPokemon(j, sc); 
            } else if (op == 3) {
                return false; 
            }
        }
        
        if (!j.tieneVivos()) {
            System.out.println("Te has quedado sin pokemons en tu equipo!"); 
            System.out.println();
            System.out.println("Volviendo al menu..."); 
            return false; 
        }
        return true; 
    }

    // calcula quien gana choque
    private static boolean simularChoque(Pokemon j, Pokemon r) {
        double statJ = j.getStatsTotales(); 
        double statR = r.getStatsTotales(); 
        
        System.out.println(j.getNombre() + " -> " + statJ + " puntos"); 
        System.out.println(r.getNombre() + " -> " + statR + " puntos"); 
        
        double mult = TablaTipos.obtener(j.getTipo(), r.getTipo()); 
        if (mult > 1.0) {
            statJ *= 2; 
            System.out.println(j.getNombre() + " es efectivo contra " + r.getNombre() + "!"); 
        } else if (mult < 1.0) {
            statJ *= 0.5; 
            System.out.println(j.getNombre() + " no es efectivo contra " + r.getNombre() + "!"); 
        }
        
        if(mult != 1.0) {
            System.out.println("Nuevo puntaje:"); 
            System.out.println(j.getNombre() + " -> " + statJ + " puntos"); 
            System.out.println(r.getNombre() + " -> " + statR + " puntos"); 
        }

        if (statJ >= statR) {
            System.out.println("Ha ganado " + j.getNombre() + "! " + r.getNombre() + " ha sido derrotado..."); 
            return true; 
        } else {
            System.out.println("Ha ganado " + r.getNombre() + "! " + j.getNombre() + " ha sido derrotado..."); 
            return false; 
        }
    }
    
    // menu cambiar de pokemon
    private static Pokemon elegirPokemon(Jugador j, Scanner sc) {
        // repite hasta elegir vivo
        while(true) {
            System.out.println("Elige un pokemon de tu equipo:"); 
            for (int i=0; i < j.getEquipo().size(); i++) {
                Pokemon p = j.getEquipo().get(i); 
                System.out.println((i+1) + ") " + p.getNombre() + " - " + p.getEstado()); 
            }
            int op = leerOpcion(sc) - 1; 
            if (op >= 0 && op < j.getEquipo().size()) {
                Pokemon p = j.getEquipo().get(op); 
                if (p.getEstado().equals("Vivo")) return p; 
                System.out.println("Este pokemon esta debilitado!"); 
            }
        }
    }

    // lee numero sin caerse
    private static int leerOpcion(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine()); 
        } catch (Exception e) {
            return -1; 
        }
    }

    // clona pokemon base
    private static Pokemon clonePokemon(Pokemon base) {
        return new Pokemon(base, "Vivo"); 
    }
}