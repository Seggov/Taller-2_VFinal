# Taller 2 — Simulador Pokémon en Java

**Valentina Castillo** -151666922 - Ingeniería en Tecnologías de la Información

**Pedro Segovia** - 21672694-4 - Ingeniería en Tecnologías de la Información

---

Simulador de rol por consola inspirado en la saga Pokémon, desarrollado en Java. El jugador puede capturar criaturas, desafiar líderes de gimnasio en orden, enfrentarse al Alto Mando y guardar su progreso entre sesiones.

---

## Características

- Captura de Pokémon por zona/hábitat con probabilidad de aparición aleatoria
- Sistema de combate por turnos con tabla de efectividad de tipos (18×18)
- 8 gimnasios con progresión secuencial obligatoria
- Desafío al Alto Mando (7 miembros) desbloqueado al obtener todas las medallas
- Gestión del PC: reordenar y administrar el inventario completo de Pokémon
- Guardado y carga de partida mediante archivos `.txt`

---

## Requisitos

- Java 14 o superior (se usa la sintaxis de `switch` con `->`)
- IDE con soporte para proyectos Java (Eclipse, IntelliJ IDEA, VS Code)

---

## Estructura del proyecto

```
Taller-2_VFinal/
├── src/
│   └── t2_v2/
│       ├── Main.java           # Controlador principal, menús y persistencia
│       ├── Pokemon.java        # Modelo de datos de cada criatura
│       ├── Jugador.java        # Estado del jugador y su equipo
│       ├── Gimnasio.java       # Datos de cada gimnasio y su líder
│       ├── AltoMando.java      # Datos de cada miembro del Alto Mando
│       ├── Habitat.java        # Bioma donde habita un Pokémon
│       ├── MotorCombate.java   # Lógica de batalla por turnos
│       ├── TablaTipos.java     # Matriz de efectividad entre tipos (18×18)
│       └── Tipo.java           # Enumeración de los 18 tipos elementales
├── Pokedex.txt                 # Base de datos de Pokémon disponibles
├── Gimnasios.txt               # Configuración de los 8 gimnasios
├── Alto Mando.txt              # Configuración del Alto Mando
├── Habitats.txt                # Lista de zonas de captura
├── Registros.txt               # Archivo de guardado de partida
└── DiagramaDeClases/
    ├── DiagramadeClases.pdf    # Diagrama técnico de clases (UML)
    └── ModelodelDominio.pdf    # Modelo conceptual del dominio
```

---

## Cómo ejecutar

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Seggov/Taller-2_VFinal.git
   ```

2. Abre el proyecto en tu IDE y configura `t2_vFinal` como proyecto Java.

3. Asegúrate de que los archivos `.txt` (`Pokedex.txt`, `Gimnasios.txt`, `Alto Mando.txt`, `Habitats.txt`) estén en el **directorio de trabajo** del proyecto (raíz de `t2_vFinal/`).

4. Ejecuta la clase `Main.java`.

---

## Formato de los archivos de datos

**`Pokedex.txt`** — un Pokémon por línea:
```
Nombre;Habitat;Probabilidad;Vida;Ataque;Defensa;AtaqueEsp;DefensaEsp;Velocidad;Tipo
Magikarp;Lago;0.15;20;10;55;15;20;80;Agua
```

**`Gimnasios.txt`** — un gimnasio por línea:
```
Numero;Lider;Estado;CantPokemon;Pokemon1;Pokemon2;...
1;EmmaLaArdillaRabiosa;Sin derrotar;3;Minun;Plusle;Emolga
```

**`Alto Mando.txt`** — un miembro por línea:
```
Numero;Nombre;Pokemon1;Pokemon2;...
1;MartinGalactico;Magikarp;Noivern;Excadrill;Steelix;Lucario;Scizor
```

**`Habitats.txt`** — una zona por línea:
```
Lago
Cueva
Montaña
Bosque
Prado
Mar
```

---

## Arquitectura de clases

| Clase | Rol |
|---|---|
| `Main` | Orquestador: carga archivos, gestiona menús y persistencia |
| `Pokemon` | Modelo de datos (stats, tipo, hábitat, estado) |
| `Jugador` | Estado del jugador, equipo activo (máx. 6) e inventario completo |
| `Gimnasio` | Contiene el equipo del líder y su estado (derrotado / sin derrotar) |
| `AltoMando` | Contiene el equipo de cada miembro del Alto Mando |
| `Habitat` | Representa el bioma de origen de un Pokémon |
| `MotorCombate` | Resuelve los combates turno a turno usando stats y efectividad de tipos |
| `TablaTipos` | Matriz estática 18×18 con multiplicadores de daño entre tipos |
| `Tipo` | Enum con los 18 tipos elementales del juego |

Para más detalle, consulta los diagramas en `DiagramaDeClases/`.

---

## Cómo jugar

Al iniciar el programa se presenta el menú principal:

```
1) Continuar       →  Carga la partida guardada en Registros.txt
2) Nueva Partida   →  Ingresa un apodo y comienza desde cero
3) Salir
```

Una vez en partida, las opciones disponibles son:

```
1) Revisar equipo          Ver los 6 Pokémon activos y sus stats totales
2) Salir a capturar        Elegir una zona y encontrar un Pokémon salvaje
3) Acceso al PC            Reordenar todos los Pokémon capturados
4) Retar un gimnasio       Desafiar líderes en orden (requiere derrotar al anterior)
5) Desafío al Alto Mando   Disponible solo con las 8 medallas obtenidas
6) Curar Pokémon           Restaura el estado de todo el equipo a "Vivo"
7) Guardar                 Guarda el progreso en Registros.txt
8) Guardar y Salir
```

### Sistema de combate

Cada combate enfrenta al jugador contra el equipo del rival, Pokémon a Pokémon. En cada turno el jugador puede:

- **Atacar** — se comparan los stats totales de ambos Pokémon, aplicando el multiplicador de tipo (×2 si es efectivo, ×0.5 si no lo es, ×0 si es inmune).
- **Cambiar de Pokémon** — seleccionar otro del equipo activo.
- **Rendirse** — abandonar el combate.

El jugador gana si derrota todos los Pokémon del rival. Pierde si todos los suyos quedan debilitados.

---

## Autores

Proyecto desarrollado como Taller 2 del curso de Programación Orientada a Objetos.
