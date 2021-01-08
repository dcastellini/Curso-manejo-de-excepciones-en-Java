# Curso manejo de excepciones en Java

[Link del curso](https://www.udemy.com/course/entendiendo-java-manejo-de-excepciones/)

##Enunciado

Se trata de un programa que va a gestionar el tablero de récords de un juego, RecordsManager, lo llamaremos.

Este programa leerá los registros de todos los jugadores de un fichero de texto (“puntuaciones.txt”), en el que en cada línea, tendrá el nombre del jugador y su puntuación máxima, separados por un espacio.

Si el nombre de algún jugador es demasiado corto (menos de 6 caracteres) se añadirá a dicho nombre un número aleatorio de las cifras que le falten para alcanzar dicha longitud, y se informará al usuario del nuevo nombre.

Si la puntuación de algún jugador es demasiado baja (menos de 1.000 puntos) se descartará, y no será incluída en la salida.

Una vez preparada la salida, se le mostrará al usuario por pantalla, y se le pedirá confirmar los datos. Si los confirma, se volcará el resultado en un fichero de salida (“records.txt”).
