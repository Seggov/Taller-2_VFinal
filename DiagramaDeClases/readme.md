# Arquitectura y Diseño del Sistema: Simulador Pokémon

Este documento explica los diagramas de diseño utilizados para estructurar y desarrollar el simulador de combates Pokémon. Se divide en dos partes fundamentales: la vista conceptual del negocio (Modelo del Dominio) y la vista técnica de implementación (Diagrama de Clases).

---

## 1. Modelo del Dominio (`ModelodelDominio.pdf`)

### Entidades Conceptuales y sus Relaciones

Este diagrama representa el "qué" del sistema: los conceptos del mundo real del juego y cómo interactúan entre sí a nivel lógico, sin entrar en detalles de código.

* **Jugador**
    * **Descripción:** Representa al usuario que interactúa con el mundo. 
    * **Relaciones:** * *Con Pokémon:* Tiene una relación de **captura** (puede capturar de 0 a muchos Pokémon) y una relación estricta de **equipo** (puede llevar de 0 a un máximo de 6 Pokémon activos).
        * *Con Gimnasio / Alto Mando:* Tiene una relación de acción donde el Jugador **desafía** a estas entidades para progresar en el juego.
* **Pokémon**
    * **Descripción:** Es la entidad central del dominio, representando a las criaturas con sus estadísticas y estado vital.
    * **Relaciones:** * *Con Hábitat:* **Habita en** un hábitat específico (1 a 1), lo que le da contexto de dónde puede ser encontrado.
        * *Con Tipo:* **Tiene** un tipo elemental (ej. Fuego, Agua) que define su naturaleza.
* **Hábitat**
    * **Descripción:** El bioma o lugar físico. Sirve exclusivamente para categorizar y agrupar de dónde provienen los Pokémon.
* **Tipo**
    * **Descripción:** El atributo elemental abstracto que rige las fortalezas y debilidades.
* **Gimnasio**
    * **Descripción:** El primer nivel de desafío del juego, liderado por un entrenador.
    * **Relaciones:** **Usa en combate** de 1 a muchos Pokémon para enfrentarse al Jugador que lo desafía.
* **Alto Mando**
    * **Descripción:** El desafío final y de mayor dificultad.
    * **Relaciones:** A diferencia del Gimnasio, su relación de **usa en combate** es estricta y de mayor calibre, exigiendo exactamente 6 Pokémon listos para pelear.

---

## 2. Diagrama de Clases (`DiagramadeClases.pdf`)

### Componentes Técnicos y Dependencias

Este diagrama representa el "cómo" del sistema: la estructura de programación orientada a objetos (POO), los atributos, métodos y cómo las clases se instancian o dependen unas de otras en el código.

* **Main**
    * **Descripción:** Es la clase controladora principal (`driver class`). Gestiona el ciclo de vida del programa, el menú y la persistencia de datos (archivos).
    * **Relación:** Tiene una dependencia (Usa/Asociación) con casi todas las demás clases (`Jugador`, `Gimnasio`, `AltoMando`, `Pokemon`). Como orquestador, necesita conocer todas estas piezas para cargar las listas (ArrayLists) e instanciar el mundo al ejecutar el `main(args)`.
* **Jugador**
    * **Descripción:** Almacena el estado actual del usuario (nombre, último líder derrotado) y su inventario de criaturas.
    * **Relación:** Contiene colecciones de la clase `Pokemon`. Interacciona directamente con ella a través de métodos como `addPokemon()`, `getEquipo()` o `curarTodos()`, ejerciendo control sobre las instancias de sus criaturas.
* **Pokemon**
    * **Descripción:** El modelo de datos central. Contiene constructores detallados y todos los *getters/setters* necesarios para gestionar la vida, estadísticas y tipos durante la ejecución.
    * **Relación:** Es una clase base instanciada pasivamente. Es "usada" por el Jugador para su equipo, y leída por el Motor de Combate para extraer datos.
* **MotorCombate**
    * **Descripción:** Clase de servicio encargada puramente de la lógica de negocio de las batallas. No guarda estado a largo plazo.
    * **Relación:** **Usa** activamente a `Jugador` y `Pokemon` para extraer las estadísticas en cada turno (`simularChoque`). Además, **usa** fuertemente a `TablaTipos` para calcular matemáticamente los daños.
* **Gimnasio y AltoMando**
    * **Descripción:** Clases contenedoras de los desafíos rivales. En lugar de guardar objetos pesados, almacenan de forma eficiente listas de textos (`equipoNombres: ArrayList<String>`).
    * **Relación:** Son consumidas por la clase `Main` (que revisa a quién retar) y dependen indirectamente del motor para traducir esos "nombres" en objetos `Pokemon` reales al momento de la pelea.
* **TablaTipos**
    * **Descripción:** Clase utilitaria que contiene una matriz bidimensional estática (`EFECTIVIDAD: double[][]`).
    * **Relación:** Procesa la enumeración `Tipo` para cruzar atacantes contra defensores y retornar el multiplicador exacto (ej. x2, x0.5).
* **<<enumeration>> Tipo**
    * **Descripción:** Un tipo de dato estricto (Enum) que bloquea las opciones a los elementos válidos del universo (Fuego, Agua, Hada, etc.).
    * **Relación:** Es la base tipográfica que estandariza la comunicación entre `Pokemon` y `TablaTipos`, previniendo errores de tipeo al asignar debilidades.
