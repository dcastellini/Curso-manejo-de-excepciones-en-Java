package ar.com.exepciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class RecordsManager {

    private static final String INPUT_FILE = "puntuaciones.txt";
    private static final String OUTPUT_FILE = "records.txt";
    private static final int MIN_NAME_LENGTH = 6;
    private static final int MIN_SCORE = 1000;

    private static Random r = new Random();

    public static void main(String[] args) {

        /// (9) Cerramos el scanner del fichero de entrada
        // usando el try con recursos nos aseguramos de que el scanner se cierra
        try (Scanner sInput = new Scanner(new File(INPUT_FILE))) {
            // usaremos un map (o diccionario) para guardar los datos leídos
            // nombre como clave y puntuación como contenido
            Map<String, Integer> players = new HashMap<>();

            /// (1) Leer fichero de entrada
            // leemos el fichero de entrada
            // mientras tenga más lineas
            while (sInput.hasNextLine()) {
                // cogemos la siguiente línea
                String line = sInput.nextLine();
                // y la troceamos por los espacios
                String[] data = line.split(" ");
                try {
                    // el primer fragmento será el nombre del jugador
                    String name = data[0];
                    // y el segundo fragmento su puntuación
                    int score = Integer.parseInt(data[1]);
                    /// (7) Tratamos los problemas de validación del jugado
                    try {
                        validateName(name);
                    } catch (PlayerNameTooShortException pntse) {
                        System.out.print("ERROR: " + pntse.getMessage());
                        name = generateNewName(name);
                        System.out.println(" El nuevo nombre de usuario es " + name);
                    }
                    // los tratamos por separado para poder validar la puntuación
                    // después de haber arreglado el nombre
                    try {
                        validateScore(name, score);
                    } catch (ScoreTooLowException stle) {
                        System.out.print("ERROR: " + stle.getMessage());
                        System.out.println(" Jugador descartado");
                        continue; // nos saltamos este jugador, vamos a la siguiente línea
                    }
                    // añadimos este jugador al diccionario
                    players.put(name, score);
                    System.out.println("LOG: Línea tratada correctamente: " + name + " - " + score);
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("ERROR: La línea no contiene los datos esperados (" +
                            line + ")");
                    // y seguimos con el while
                }
            }

            /// (2) Mostrar por consola todos los datos guardados
            System.out.println("Datos procesados: ");
            for (String name : players.keySet()) {
                System.out.println(name + ":\t" + players.get(name));
            }

            /// (3) Pedir confirmación al usuario
            System.out.println("¿Son correctos? [S]i/[N]o");
            // leemos la respuesta del usuario de la entrada estándar
            /// (10) Cerramos el escáner de consola
            String answer;
            try (Scanner sConsole = new Scanner(System.in)) {
                answer = sConsole.next();
            }
            // y comprobamos si es afirmativa
            boolean confirmed = answer.equalsIgnoreCase("S");

            /// (4) Escribir a fichero
            if (confirmed) {
                System.out.println("Procedemos al volcado de datos del fichero...");
                // abrimos el fichero de salida para escribir en él
                /// (11) Cerramos el fichero de salida y controlamos IOE
                try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE)) {
                    // por cada uno de los jugadores en nuestro diccionario
                    for (String name : players.keySet()) {
                        // escribimos una línea en el fichero de salida
                        fos.write((name + " " + players.get(name) + "\n").getBytes());
                    }
                } catch (IOException ioe) {
                    System.err.println("No hemos podido escribir los resultados en el " +
                            "fichero porque algo ha fallado: " + ioe.getMessage());
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("No podemos ejecutar el programa porque no se encuentra " +
                    "el fichero de entrada esperado: " + INPUT_FILE);
        }
    }

    private static void validateName(String name)
            throws PlayerNameTooShortException {
        /// (5) Validamos la longitud del nombre
        if (name.length() < MIN_NAME_LENGTH) {
            throw new PlayerNameTooShortException(name);
        }
    }

    private static void validateScore(String name, int score)
            throws ScoreTooLowException {
        /// (6) Validamos la puntuación mínima
        if (score < MIN_SCORE) {
            throw new ScoreTooLowException(name, score);
        }
    }

    private static String generateNewName(String name) {
        int randomSize = MIN_NAME_LENGTH - name.length();
        for (int i = 0; i < randomSize; i ++) {
            int randomNum = r.nextInt(10);
            name += randomNum; // añadimos un número más al final
        }
        return name;
    }
}
