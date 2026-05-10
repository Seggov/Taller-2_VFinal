package t2_v2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class Main {
    private static ArrayList<Pokemon> pokedex = new ArrayList<>(); 
    private static ArrayList<String> habitats = new ArrayList<>(); //Lista de habitats donde se puede ir a capturar pokemones.
    
    private static ArrayList<Gimnasio> gimnasios = new ArrayList<>(); //Lista que albergará los gimaniso a los que se tiene que derrotar.
    private static ArrayList<AltoMando> altoMandos = new ArrayList<>(); 
    private static Jugador jugador; //Se crea la instancia de jugador sin asignarle los valores de la partida todavía.
    private static Scanner sc = new Scanner(System.in); 
    private static Random rand = new Random(); //Permite generar la aparición aleatoria de pokemones.

    // inicia todo el programa
    public static void main(String[] args) throws FileNotFoundException {
    	
        cargarArchivos("Pokedex.txt","Gimnasios.txt", "Alto Mando.txt","Habitats.txt"); 
        menuInicial(); 
    }

    
    // lee los txt guardados
    private static void cargarArchivos(String string, String stringdos, String stringtres, String stringcuatro) throws FileNotFoundException {
        //Abrir el archivo pokedex y leerlo.
        File file = new File(string);
        Scanner Scaner=new Scanner(file);
        		
        		while(Scaner.hasNextLine()) {
        		String Linea=Scaner.nextLine();
                String[] pts = Linea.split(";"); 
                
                pokedex.add(new Pokemon(pts[0], pts[1], Double.parseDouble(pts[2]),//Crear instancia de pokemon y agregarlo a un ArrayList. 
                Integer.parseInt(pts[3]), Integer.parseInt(pts[4]), Integer.parseInt(pts[5]), 
                Integer.parseInt(pts[6]), Integer.parseInt(pts[7]), Integer.parseInt(pts[8]), pts[9])); 
            }
        		 Scaner.close();
         

        

        File fila = new File(stringdos);// Abrir el archivo de Gimnasio.txt. 
        Scanner  scann=new Scanner(fila);
            // lee gym linea linea
        while (scann.hasNextLine()) {
                String[] pts = scann.nextLine().split(";"); 
                ArrayList<String> eq = new ArrayList<>(); 
                for(int i=4; i < pts.length; i++) eq.add(pts[i]); 
                gimnasios.add(new Gimnasio(Integer.parseInt(pts[0]), pts[1], pts[2], eq)); //Crear instancia de clase Gimnasio y agregarlo a un ArrayList.
            }scann.close();
         
            
            File fil = new File(stringtres);//Abrir Archivo de alto Mando.txt.
        	Scanner  scanner=new Scanner(fil);
            // lee jefes linea linea
            while (scanner.hasNextLine()) {
                String[] pts = scanner.nextLine().split(";"); 
                ArrayList<String> eq = new ArrayList<>(); 
                for(int i=2; i < pts.length; i++) eq.add(pts[i]); 
                altoMandos.add(new AltoMando(Integer.parseInt(pts[0]), pts[1], eq)); //Crear instancia de alto mando y agregar a un ArrayList.
            }scanner.close();
            
            File files = new File(stringcuatro);//Abrir y leer Habitat.txt
            Scanner Scatter=new Scanner(files);
                // lee zonas linea linea
                while (Scatter.hasNextLine()) 
                	habitats.add(Scatter.nextLine());//Agregar habitat a un ArrayList.
               
                
                
                
            }  
       
       


    // carga menu de inicio
    private static void menuInicial() {
        //  menu hasta entrar
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
            	System.exit(0); //Salir del juego.
                break; 
            }
        }
    }

    // lee el Jurgo guardado.
    private static void cargarPartida() {
        try (Scanner lector = new Scanner(new File("Registros.txt"))) {
            if(!lector.hasNextLine()) return; 
            String[] info = lector.nextLine().split(";"); 
            jugador = new Jugador(info[0], info[1]); //Nomber del jugador y cuantos gimnacios ha derrotado.
            
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
                    System.exit(0); //Opción de guardar y salir.
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

    // logica atrapar Pokemon salvaje
    private static void capturar() {
        System.out.println("Donde deseas ir a explorar?\nZonas disponibles:"); 
        
        //Escoger zona para ir a capturar Pokemon.
        System.out.print("Ingrese Zona: ");
        int z = leerNumero(); 
        if(z == 7) {
        	return;
        			}
        if(z < 1 || z > 6) {//Cerciorarse que la opción existe.
            System.out.println("Zona invalida");
            return;
        }
        
        String hab = habitats.get(z - 1);//Que la opción escogida tenga un indice  existente. 
        ArrayList<Pokemon> posibles = new ArrayList<>(); 
        
        // filtra Pokemones por zona
        for(Pokemon p : pokedex) {
            if(p.getHabitat().equalsIgnoreCase(hab)) posibles.add(p); 
        }
        if(posibles.isEmpty()) return; // Si la opción no es válida, volver a menú principal.
        
        double r = rand.nextDouble(); //Generar aleatoriedad dependiendo del porcentaje de aparición.
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
        if(c == 1) {//escoger capturar  pokemon.
            jugador.addPokemon(new Pokemon(salvaje, "Vivo")); 
            System.out.println(salvaje.getNombre() + " capturado con exito!!"); 
            if(jugador.getEquipo().size() <= 6) System.out.println(salvaje.getNombre() + " ha sido agregado a tu equipo!"); 
        }
    }
    

    // logica del pc
    private static void accesoPC() {// Cambiar orden de los  pokemones que se poseen. 
        ArrayList<Pokemon> lista = jugador.getPokemons(); 
        for(int i=0; i<lista.size(); i++) {
            System.out.println((i+1) + ") " + lista.get(i).getNombre()); 
        }
        System.out.println("1) Cambiar Pokémon. 2) Salir."); 
        int op = leerNumero(); 
        if(op == 1) {
            System.out.print("Elija Primer numero:"); 
            int n1 = leerNumero() - 1; 
            System.out.println("Elija Segundo numero:"); 
            int n2 = leerNumero() - 1; 
            if(n1 >= 0 && n2 >= 0 && n1 < lista.size() && n2 < lista.size()) {
                jugador.intercambiar(n1, n2); 
                System.out.println();
                System.out.println("El cambio ha sido exitoso");
                System.out.println();
            }
        }
    }

    // logica retar un gym
    private static void retarGimnasio() {
        System.out.println("A cual Lider deseas retar??"); 
        for(int i=0; i<gimnasios.size(); i++) {//Mostrar cuales son los lideres de los gym, si se ha derrotado o no. 
            Gimnasio g = gimnasios.get(i); 
            System.out.println((i+1) + ") " + g.getLider() + " - Estado: " + g.getEstado()); 
        }
        System.out.println((gimnasios.size()+1) + ") Volver al menu."); 
        int op = leerNumero() - 1; 
        if(op < 0 || op >= gimnasios.size()) return; 
        
        if(op > 0) {//En caso de que se trate de  desafiar a un lider de gimnasio cuando el anterior lider no ha sido derrotado, invalidandolo 
            if(!gimnasios.get(op-1).getEstado().equals("Derrotado")) {
                System.out.println("Calmado Entrenador!!! No puedes retar a " + gimnasios.get(op).getLider() + " sin haber derrotado a los lideres anteriores!!"); 
                System.out.println();
                return; 
            }
        }
        
        System.out.println("Desafiando a " + gimnasios.get(op).getLider() + "!!"); //En caso de Desafia a un lider de gimnasio que sea el primero o que se haya derrotado al anterior.
        ArrayList<Pokemon> equipoRival = instanciarEquipo(gimnasios.get(op).getEquipoNombres());//Se instancia equipo rival del gimnasio. 
        
        boolean ganaJugador = MotorCombate.pelear(jugador, equipoRival, sc); 
        if(ganaJugador) {//Si gana jugador  cambiar el estado del gimnasio a derrotado.
            gimnasios.get(op).setEstado("Derrotado"); 
            jugador.setUltimoLider(gimnasios.get(op).getLider()); 
            System.out.println("Derrotaste a " + gimnasios.get(op).getLider() + "!"); 
        }
    }

    // pelea con jefes
    private static void retarAltoMando() {
        boolean ganoTodos = true; 
        for(Gimnasio g : gimnasios) {//Recorrer los gimnasios diponibles en el juego.
            if(!g.getEstado().equals("Derrotado")) ganoTodos = false; 
        }
        if(!ganoTodos) {
            System.out.println("Aun te faltan medallas!");//En caso de intentar desafiar al alto mando antes de derrotar a todos los gimnasios(No es válido). 
            return; 
        }
        
        // pelea todos los jefes
        for(AltoMando am : altoMandos) {
            System.out.println("Desafiando a " + am.getNombre() + " del Alto Mando!"); 
            ArrayList<Pokemon> rivales = instanciarEquipo(am.getEquipoNombres()); //Crear lista de pokemones a enfrentar
            boolean gano = MotorCombate.pelear(jugador, rivales, sc); //Comparar los tipos de pokemones del jugador con los del ltomando para determinar quien gana. 
            if(!gano) {
                System.out.println("Has perdido el desafio al Alto Mando..."); //Los pokemones  tienen estado Debilitado, no se opuede seguir peleando.
                return; 
            }
            System.out.println("Derrotaste a " + am.getNombre() + "!"); //Se derroto al alto mando.
        }
        System.out.println("ERES EL CAMPEON DE LA REGION!!"); //Se derroto a todo el alto mando.
    }

    // lee numero sin caer
    private static int leerNumero() {//Leer la opcion ingresada por el teclado.
        try {
            return Integer.parseInt(sc.nextLine()); //Devolver el valor como entero.
        } catch (Exception e) {//Control de error.
            return -1; 
        }
    }

    // saca pokemon de bd
    private static Pokemon buscarEnPokedex(String nom) {//Buscar pokemon en el pokedex y retorna nombre del pokemon.
        for(Pokemon p : pokedex) {//Bucle donde se recorre todos los pokemones del juego.
            if(p.getNombre().equalsIgnoreCase(nom)) return p; //Cuando se encuentre el que coincida se devuelve el nombre del pokemon.
        }
        return null; //Si no se encuentra coincidencia se devuelve un valor nulo.
    }
    
    // arma equipo del jefe
    private static ArrayList<Pokemon> instanciarEquipo(ArrayList<String> nombres) {
        ArrayList<Pokemon> res = new ArrayList<>(); //Se crea lista vacia  de pokemones a los que se enfrentará el jugador. 
        for(String n : nombres) {
            Pokemon base = buscarEnPokedex(n); //Se instancia pokemones del equipo rival.
            if(base != null) res.add(new Pokemon(base, "Vivo")); //Se ingresa pokemon a la lista del equipo rival.
        }
        return res; //Devolver la lista.
    }
}
