#  Snake Game - Arquitectura MVC en Java

Este proyecto implementa el clásico juego Snake (Serpiente) utilizando Java Swing para la interfaz gráfica de usuario, siguiendo estrictamente el patrón de diseño arquitectónico Modelo-Vista-Controlador (MVC). El juego consiste en controlar una serpiente que se mueve continuamente por un tablero de juego, la cual debe comer alimentos para crecer y acumular puntos, mientras evita colisionar con las paredes del tablero o con su propio cuerpo, lo que provocaría el fin de la partida.


## Arquitectura del Proyecto

El proyecto está diseñado siguiendo el patrón arquitectónico Modelo-Vista-Controlador (MVC), que es un patrón de diseño de software que separa los datos y la lógica de negocio de una aplicación de su representación visual y de la lógica que controla la interacción del usuario. Esta separación proporciona múltiples beneficios en términos de mantenibilidad, escalabilidad y organización del código.

### Modelo (Model)

El Modelo representa la capa de datos y lógica de negocio de la aplicación. Es completamente independiente de cómo se presentan los datos o de cómo el usuario interactúa con ellos. En este proyecto, el Modelo contiene todas las clases que representan las entidades del juego y la lógica que gobierna su comportamiento. El Modelo no tiene ninguna referencia a la Vista ni al Controlador, lo que lo hace completamente reutilizable en diferentes contextos.

Las clases del Modelo son responsables de mantener el estado del juego, incluyendo la posición y el estado de la serpiente, la ubicación de la comida, la puntuación actual y el estado de finalización del juego. También contienen toda la lógica para determinar cómo se mueve la serpiente, cómo detectar colisiones, cómo generar nuevas posiciones para la comida, y cómo calcular y actualizar las puntuaciones.

El Modelo implementa las reglas del juego de forma independiente de cualquier interfaz de usuario. Esto significa que podríamos crear una interfaz completamente diferente, como una interfaz de consola o una interfaz web, sin necesidad de modificar ninguna de las clases del Modelo. Esta independencia es uno de los principales beneficios del patrón MVC.

### Vista (View)

La Vista es responsable de la presentación visual de los datos contenidos en el Modelo. En este proyecto, la Vista está implementada usando Java Swing, que es la biblioteca estándar de Java para crear interfaces gráficas de usuario de escritorio. La Vista se encarga de renderizar todos los elementos visuales del juego en la pantalla, incluyendo la serpiente, la comida, las puntuaciones y los mensajes de estado.

La implementación de la Vista en este proyecto utiliza componentes de Swing como JFrame para la ventana principal y JPanel para el área de dibujo. El JPanel personalizado sobrescribe el método paintComponent para realizar el dibujado personalizado de todos los elementos del juego. Se utilizan objetos Graphics2D para realizar operaciones de dibujo avanzadas, incluyendo antialiasing para mejorar la calidad visual de los gráficos.

### Controlador (Controller)

El Controlador actúa como intermediario entre el Modelo y la Vista. Su responsabilidad principal es capturar las interacciones del usuario y traducirlas en acciones sobre el Modelo. En este juego, el Controlador captura los eventos del teclado cuando el usuario presiona las teclas de dirección y actualiza el estado del Modelo en consecuencia.

El Controlador también gestiona el ciclo de actualización del juego mediante un Timer que ejecuta la lógica del juego a intervalos regulares. En cada iteración del ciclo de juego, el Controlador instruye al Modelo para que actualice su estado (movimiento de la serpiente, detección de colisiones, etc.) y luego instruye a la Vista para que se actualice y refleje los cambios.

#### Direction.java

Direction es una enumeración (enum) que define las cuatro direcciones posibles en las que la serpiente puede moverse en el juego: UP (arriba), DOWN (abajo), LEFT (izquierda) y RIGHT (derecha). El uso de una enumeración en lugar de constantes enteras o cadenas de texto proporciona seguridad de tipos en tiempo de compilación y hace el código más legible y mantenible.

#### Snake.java

La clase Snake representa la serpiente del juego y encapsula toda la información y comportamiento relacionado con ella. La serpiente se implementa usando una LinkedList de objetos Point, donde cada Point representa la posición de un segmento del cuerpo de la serpiente en el tablero del juego. El primer elemento de la lista representa la cabeza de la serpiente, y cada elemento subsiguiente representa un segmento del cuerpo.

El atributo body de tipo LinkedList<Point> almacena todos los segmentos que componen el cuerpo de la serpiente. Se eligió una LinkedList en lugar de un ArrayList porque las operaciones principales que se realizan sobre el cuerpo de la serpiente son agregar elementos al principio (la nueva posición de la cabeza) y eliminar elementos del final (el último segmento del cuerpo cuando la serpiente se mueve sin comer). Estas operaciones son muy eficientes en una LinkedList, con complejidad O(1), mientras que en un ArrayList, agregar al principio requeriría desplazar todos los elementos existentes, resultando en una complejidad O(n).

El atributo direction de tipo Direction almacena la dirección actual en la que se está moviendo la serpiente. Esta dirección determina hacia dónde se moverá la cabeza de la serpiente en el siguiente paso del juego. La dirección se inicializa como RIGHT (derecha) en el constructor, lo que significa que la serpiente comienza moviéndose hacia la derecha cuando se inicia una nueva partida.

El constructor Snake(int startX, int startY) inicializa una nueva serpiente en la posición especificada por las coordenadas startX y startY. Crea una nueva LinkedList para el cuerpo, agrega un único Point en la posición inicial que representa la cabeza de la serpiente de un solo segmento, y establece la dirección inicial como RIGHT. Esta inicialización crea una serpiente de longitud 1 que comenzará a moverse hacia la derecha.

El método getBody() devuelve una referencia a la LinkedList que contiene todos los segmentos del cuerpo de la serpiente. Este método es utilizado principalmente por la Vista para obtener las posiciones de todos los segmentos que necesitan ser dibujados en la pantalla, y por GameModel para verificar colisiones con el propio cuerpo de la serpiente.

El método getHead() devuelve un objeto Point que representa la posición actual de la cabeza de la serpiente. La cabeza es siempre el primer elemento de la LinkedList body. Este método es utilizado frecuentemente por GameModel para calcular la nueva posición de la cabeza durante el movimiento y para verificar colisiones con las paredes o con la comida.

El método getDirection() simplemente devuelve la dirección actual de movimiento de la serpiente. Este método es utilizado por GameModel cuando necesita calcular la nueva posición de la cabeza de la serpiente en función de su dirección actual de movimiento.

El método setDirection(Direction newDirection) permite cambiar la dirección de movimiento de la serpiente. Sin embargo, no permite cambiar directamente a la dirección opuesta a la actual. Por ejemplo, si la serpiente se está moviendo hacia la derecha (RIGHT), no puede cambiar inmediatamente a izquierda (LEFT). Esta restricción es necesaria porque permitir un cambio inmediato a la dirección opuesta haría que la serpiente colisionara instantáneamente con su propio cuerpo. La validación se realiza mediante el método privado isOppositeDirection().

El método privado isOppositeDirection(Direction newDirection) verifica si la nueva dirección es opuesta a la dirección actual. Devuelve true si se intenta cambiar de UP a DOWN, de DOWN a UP, de LEFT a RIGHT, o de RIGHT a LEFT. Esta verificación es crucial para mantener la jugabilidad correcta del juego y evitar situaciones imposibles donde la serpiente intentaría moverse directamente sobre sí misma.

El método grow() agrega un nuevo segmento al final del cuerpo de la serpiente. Este método se llama cuando la serpiente come un alimento. El nuevo segmento se agrega en la misma posición que el último segmento actual, y en el siguiente movimiento, este segmento se quedará en la posición donde estaba el último segmento antes del movimiento, creando el efecto visual de que la serpiente ha crecido en longitud.

El método getLength() devuelve el número total de segmentos en el cuerpo de la serpiente, que es simplemente el tamaño de la LinkedList body. Este método puede ser útil para estadísticas del juego o para implementar mecánicas adicionales que dependan de la longitud de la serpiente.

#### Food.java

La clase Food representa el alimento que aparece en el tablero del juego y que la serpiente debe comer para crecer y ganar puntos. Cada instancia de Food tiene una posición específica en el tablero y un valor en puntos que se otorga al jugador cuando la serpiente consume ese alimento.

El atributo position de tipo Point almacena las coordenadas x e y de la ubicación del alimento en el tablero del juego. Estas coordenadas son valores enteros que representan la celda del tablero donde se encuentra el alimento. El tablero del juego está dividido en una cuadrícula, y cada celda puede contener un segmento de la serpiente, un alimento, o estar vacía.

El atributo value de tipo int representa cuántos puntos vale este alimento particular. En la implementación actual, todos los alimentos valen 10 puntos, pero tener este atributo como variable en lugar de una constante permite la posibilidad de tener diferentes tipos de alimentos con diferentes valores en el futuro. Por ejemplo, se podrían implementar alimentos especiales que valgan más puntos o alimentos que otorguen bonificaciones temporales.

El constructor Food(int x, int y) crea una nueva instancia de alimento en la posición especificada por las coordenadas x e y. El constructor también inicializa el valor del alimento en 10 puntos. Este constructor es llamado por GameModel cuando necesita generar un nuevo alimento en el tablero, típicamente después de que la serpiente ha consumido el alimento anterior o al iniciar una nueva partida.

El método getPosition() devuelve un objeto Point que contiene las coordenadas x e y de la ubicación actual del alimento. Este método es utilizado por la Vista para determinar dónde dibujar el alimento en la pantalla, y por GameModel para verificar si la cabeza de la serpiente ha alcanzado la posición del alimento, lo que indica que la serpiente ha comido.

El método setPosition(int x, int y) permite cambiar la posición del alimento a nuevas coordenadas x e y. Este método podría ser utilizado para reubicar un alimento existente en lugar de crear uno nuevo, aunque en la implementación actual, GameModel crea nuevas instancias de Food cada vez que se necesita un nuevo alimento.

El método getValue() devuelve el valor en puntos de este alimento. GameModel utiliza este método cuando la serpiente come el alimento para determinar cuántos puntos agregar a la puntuación actual del jugador. Aunque actualmente todos los alimentos valen lo mismo, este método proporciona la flexibilidad para implementar diferentes tipos de alimentos en el futuro.

El método setValue(int value) permite cambiar el valor en puntos del alimento. Este método proporciona flexibilidad para implementaciones futuras donde se podría querer modificar dinámicamente el valor de los alimentos, por ejemplo, para crear alimentos especiales que aparezcan ocasionalmente y valgan más puntos.

#### GameModel.java

GameModel es la clase central del Modelo y contiene toda la lógica del juego. Es responsable de mantener el estado completo de una partida, incluyendo la serpiente, el alimento, las puntuaciones, y el estado de finalización del juego. También implementa todas las reglas del juego, como el movimiento de la serpiente, la detección de colisiones, la generación de alimentos, y el cálculo de puntuaciones.

Las constantes de clase GRID_SIZE, BOARD_WIDTH y BOARD_HEIGHT definen las dimensiones del tablero del juego. GRID_SIZE con valor 20 representa el tamaño en píxeles de cada celda de la cuadrícula del tablero. BOARD_WIDTH con valor 30 representa cuántas celdas de ancho tiene el tablero, y BOARD_HEIGHT con valor 20 representa cuántas celdas de alto tiene el tablero. Estas constantes se definen como static final, lo que significa que son valores constantes compartidos por todas las instancias de GameModel y no pueden ser modificados después de la inicialización.

El atributo snake de tipo Snake almacena la instancia actual de la serpiente en el juego. Esta referencia permite a GameModel acceder y modificar el estado de la serpiente, como su posición, dirección y longitud. La serpiente es uno de los dos elementos principales del juego, siendo el otro el alimento.

El atributo food de tipo Food almacena la instancia actual del alimento que está visible en el tablero. En cualquier momento del juego, hay exactamente un alimento visible en el tablero. Cuando la serpiente come este alimento, se genera uno nuevo en una posición aleatoria diferente.

El atributo score de tipo int mantiene la puntuación actual del jugador en la partida en curso. La puntuación comienza en cero al inicio de cada partida y se incrementa cada vez que la serpiente come un alimento. El incremento es igual al valor del alimento consumido, que actualmente es siempre 10 puntos.

El atributo highScore de tipo int almacena la puntuación más alta que el jugador ha logrado en cualquier partida durante la sesión actual de ejecución de la aplicación. Este valor persiste entre partidas pero no se guarda permanentemente cuando se cierra la aplicación. Se actualiza cada vez que una partida termina si la puntuación de esa partida es mayor que el highScore actual.

El atributo gameOver de tipo boolean indica si el juego ha terminado. Es true cuando la serpiente ha colisionado con una pared o con su propio cuerpo, y false mientras la partida está en progreso. Este indicador es verificado por el Controlador en cada ciclo de actualización para determinar si debe continuar procesando el movimiento del juego o si debe esperar a que el jugador reinicie.

El atributo random de tipo Random es una instancia del generador de números aleatorios de Java. Se utiliza para generar las coordenadas aleatorias donde aparecerán los nuevos alimentos en el tablero. Tener una única instancia de Random y reutilizarla es más eficiente que crear nuevas instancias cada vez que se necesita un número aleatorio.

El constructor GameModel() inicializa una nueva instancia del modelo del juego. Crea la instancia de Random, inicializa highScore en cero, y llama al método initGame() para establecer el estado inicial del juego. El constructor se llama una sola vez cuando la aplicación se inicia, y la misma instancia de GameModel se utiliza para todas las partidas durante la sesión.

El método initGame() configura el estado inicial para una nueva partida. Crea una nueva serpiente en el centro del tablero llamando al constructor de Snake con las coordenadas BOARD_WIDTH / 2 y BOARD_HEIGHT / 2, lo que coloca la serpiente aproximadamente en el centro del tablero. Luego llama a generateFood() para crear el primer alimento. Establece score en cero para comenzar la puntuación desde el principio. Finalmente, establece gameOver en false para indicar que la partida está en progreso. Este método es llamado tanto al inicio de la aplicación como cada vez que el jugador reinicia el juego después de un Game Over.

El método move() ejecuta un paso completo de movimiento de la serpiente. Primero verifica si el juego ha terminado mediante gameOver, y si es así, no hace nada y retorna inmediatamente. Si el juego está activo, obtiene la posición actual de la cabeza de la serpiente y crea un nuevo Point llamado newHead que inicialmente es una copia de la posición de la cabeza. Luego, usando un switch sobre la dirección actual de la serpiente, modifica las coordenadas de newHead para calcular la nueva posición de la cabeza. Si la dirección es UP, decrementa la coordenada y. Si es DOWN, incrementa y. Si es LEFT, decrementa x. Si es RIGHT, incrementa x. Después de calcular la nueva posición, agrega newHead al principio de la lista body de la serpiente, lo que efectivamente mueve la cabeza a la nueva posición. A continuación, verifica si la nueva posición de la cabeza coincide con la posición del alimento usando equals(). Si hay coincidencia, significa que la serpiente ha comido el alimento, por lo que incrementa score en el valor del alimento y llama a generateFood() para crear un nuevo alimento. Si no hay coincidencia, elimina el último segmento de la serpiente llamando a removeLast() en body, lo que hace que la serpiente se mueva sin crecer. Este proceso de agregar a la cabeza y eliminar de la cola crea el efecto visual de movimiento continuo de la serpiente.

El método generateFood() crea un nuevo alimento en una posición aleatoria del tablero. Usa un bucle do-while para generar coordenadas aleatorias hasta encontrar una posición que no esté ocupada por ningún segmento del cuerpo de la serpiente. Dentro del bucle, genera un valor aleatorio x entre 0 y BOARD_WIDTH, y un valor aleatorio y entre 0 y BOARD_HEIGHT usando el generador random. La condición del while verifica si el cuerpo de la serpiente contiene un Point con esas coordenadas. Si es así, el bucle se repite para generar nuevas coordenadas. Una vez que se encuentra una posición válida, crea una nueva instancia de Food con esas coordenadas y la asigna al atributo food. Este método garantiza que el alimento siempre aparezca en una posición vacía del tablero, evitando que se genere sobre la serpiente.

El método checkCollision() verifica si ha ocurrido alguna colisión que termine el juego. Primero obtiene la posición de la cabeza de la serpiente. Luego verifica si la cabeza está fuera de los límites del tablero comparando las coordenadas con los límites definidos por BOARD_WIDTH y BOARD_HEIGHT. Si la coordenada x de la cabeza es menor que cero o mayor o igual que BOARD_WIDTH, o si la coordenada y es menor que cero o mayor o igual que BOARD_HEIGHT, significa que la serpiente ha chocado con una pared. En ese caso, establece gameOver en true, llama a updateHighScore() para actualizar la puntuación más alta si es necesario, y devuelve true. Si no hay colisión con las paredes, el método verifica si la cabeza ha colisionado con algún segmento del cuerpo de la serpiente. Usa un bucle for que comienza en el índice 1 (saltando la cabeza misma en el índice 0) y recorre todos los demás segmentos del cuerpo. Para cada segmento, verifica si su posición es igual a la posición de la cabeza usando equals(). Si encuentra una coincidencia, significa que la serpiente se ha mordido a sí misma, por lo que establece gameOver en true, llama a updateHighScore(), y devuelve true. Si no se detecta ninguna colisión, el método devuelve false.

El método privado updateHighScore() compara la puntuación actual score con la puntuación más alta highScore. Si score es mayor que highScore, actualiza highScore con el valor de score. Este método es llamado por checkCollision() cuando se detecta una colisión y el juego termina, asegurando que la puntuación más alta se actualice antes de que el jugador pueda ver la pantalla de Game Over.

Los métodos getter getSnake(), getFood(), getScore(), getHighScore(), e isGameOver() proporcionan acceso de solo lectura a los atributos privados del modelo. Estos métodos son utilizados por la Vista para obtener la información necesaria para renderizar el estado actual del juego, y por el Controlador para verificar el estado del juego. El encapsulamiento mediante getters protege los datos internos del modelo y previene modificaciones no autorizadas desde otras clases.

Los métodos estáticos getGridSize(), getBoardWidth(), y getBoardHeight() proporcionan acceso a las constantes que definen las dimensiones del tablero. Estos métodos estáticos permiten que otras clases accedan a estas constantes sin necesidad de tener una instancia de GameModel, lo cual es útil para la Vista cuando necesita calcular el tamaño total del área de dibujo.

#### GamePanel.java

GamePanel es un componente personalizado de Swing que extiende JPanel y es responsable de renderizar todos los elementos visuales del juego. Este panel se encarga de dibujar la serpiente, el alimento, las puntuaciones, y los mensajes de estado en la pantalla. Es el componente donde ocurre todo el dibujo gráfico del juego.

El atributo model de tipo GameModel es una referencia al modelo del juego. GamePanel necesita esta referencia para acceder al estado actual del juego y poder dibujarlo en la pantalla. A través de esta referencia, puede obtener la posición de la serpiente, la ubicación del alimento, las puntuaciones, y el estado de finalización del juego.

El constructor GamePanel(GameModel model) inicializa el panel con una referencia al modelo del juego. Almacena la referencia al modelo en el atributo model. Calcula las dimensiones preferidas del panel multiplicando el tamaño de la cuadrícula por el número de celdas en cada dimensión del tablero. Establece estas dimensiones usando setPreferredSize(), lo que indica al layout manager de Swing cuánto espacio necesita este componente. Finalmente, establece el color de fondo del panel en negro usando setBackground(Color.BLACK), lo que crea un fondo oscuro sobre el cual se dibujarán los elementos del juego en colores brillantes.

El método paintComponent(Graphics g) es sobrescrito de la clase JPanel y es llamado automáticamente por Swing cada vez que el componente necesita ser redibujado. Esto ocurre cuando se llama explícitamente a repaint(), cuando la ventana es redimensionada, cuando la ventana es movida, o cuando otra ventana que la estaba ocultando se mueve. El método primero llama a super.paintComponent(g) para permitir que JPanel realice su renderizado básico, como limpiar el área de dibujo con el color de fondo. Luego llama al método privado draw(g) que contiene toda la lógica de dibujo personalizado del juego.

El método privado draw(Graphics g) realiza todo el renderizado del estado actual del juego. Primero obtiene el tamaño de la cuadrícula de GameModel y lo almacena en una variable local gridSize para evitar múltiples llamadas al método estático. Luego convierte el objeto Graphics en Graphics2D, que es una versión más avanzada que proporciona mejor control sobre las operaciones de renderizado. Activa el antialiasing estableciendo RenderingHints.KEY_ANTIALIASING en VALUE_ANTIALIAS_ON, lo que hace que los bordes de las formas dibujadas se vean más suaves y de mayor calidad.


#### GameFrame.java

GameFrame es la ventana principal de la aplicación y extiende JFrame, que es la clase de Swing para crear ventanas de aplicación. Esta clase es responsable de crear y configurar la ventana que contendrá el panel del juego y de proporcionar el marco visual para toda la aplicación.

El atributo gamePanel de tipo GamePanel almacena una referencia al panel donde se dibuja el juego. Esta referencia permite a GameFrame proporcionar acceso al panel a otras clases, particularmente al Controlador, que necesita registrar listeners de eventos en el panel.

El constructor GameFrame(GameModel model) configura toda la ventana de la aplicación. Primero establece el título de la ventana llamando a setTitle() con el texto "Snake Game - MVC Architecture", que aparecerá en la barra de título de la ventana. Este título indica claramente el propósito de la aplicación y menciona la arquitectura utilizada.

El constructor llama a setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE), lo que especifica que cuando el usuario cierra la ventana usando el botón de cerrar, la aplicación debe terminar completamente y salir del proceso de Java. Sin esta configuración, cerrar la ventana solo la ocultaría pero dejaría la aplicación ejecutándose en segundo plano.

Llama a setResizable(false) para evitar que el usuario pueda redimensionar la ventana. Esto es importante porque el juego está diseñado para dimensiones específicas del tablero, y permitir el redimensionamiento


Jose Granado CI:27.714.265
