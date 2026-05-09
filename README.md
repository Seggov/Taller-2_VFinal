# Taller 2 — Simulador Pokémon en Java

**Valentina Castillo** -151666922 - Ingeniería en Tecnologías de la Información

**Pedro Segovia** - 21672694-4 - Ingeniería en Tecnologías de la Información

---

## Descripción

Juego de rol Pokémon por consola desarrollado en Java. El jugador puede capturar Pokémon, desafiar gimnasios en orden, enfrentar al Alto Mando y guardar su progreso en archivos de texto.

---

## Estructura del Proyecto

```
t2_vFinal/
├── src/t2_v2/
│   ├── Main.java           # Punto de entrada, menús y lógica principal
│   ├── Pokemon.java        # Clase Pokémon con stats y estado
│   ├── Jugador.java        # Clase jugador con equipo y PC
│   ├── Gimnasio.java       # Datos de cada gimnasio y su líder
│   ├── AltoMando.java      # Miembros del Alto Mando
│   ├── MotorCombate.java   # Lógica de combate turno a turno
│   ├── TablaTipos.java     # Matriz de efectividad de tipos (18x18)
│   └── Tipo.java           # Enum con los 18 tipos
├── Pokedex.txt             # Base de datos de Pokémon
├── Habitats.txt            # Zonas de captura disponibles
├── Gimnasios.txt           # Datos de gimnasios y líderes
├── Alto Mando.txt          # Datos del Alto Mando
└── Registros.txt           # Archivo de guardado del jugador
DiagramaDeClases/
├── Pokemon.drawio          # Diagrama UML editable (draw.io)
└── Pokemon.drawio.png      # Imagen del diagrama de clases
```

---

## Funcionalidades

- **Nueva partida / Continuar** — crea jugador o carga desde `Registros.txt`
- **Captura** — aparición aleatoria por zona con probabilidad por Pokémon
- **Combate** — comparación de stats totales con modificador de tipo (x2 / x0.5)
- **Gimnasios** — se deben derrotar en orden secuencial
- **Alto Mando** — accesible solo tras ganar todas las medallas
- **PC** — intercambio de posiciones en el equipo
- **Curar** — restaura el estado de todos los Pokémon
- **Guardar** — persiste nombre, último líder y equipo en `Registros.txt`

---

## Cómo Ejecutar

**Requisitos:** Java 8 o superior.

```bash
# Desde la carpeta t2_vFinal/
javac -d bin src/t2_v2/*.java
cd bin
java t2_v2.Main
```

> Los archivos `.txt` de datos deben estar en el directorio de trabajo al momento de ejecutar.

---

## Diagrama de Clases

El diagrama UML se encuentra en `DiagramaDeClases/`. Puede abrirse y editarse en [draw.io](https://app.diagrams.net/) usando el archivo `Pokemon.drawio`.
