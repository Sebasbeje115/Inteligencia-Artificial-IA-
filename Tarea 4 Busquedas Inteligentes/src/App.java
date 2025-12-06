package primeroenanchura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String a[]){
        String estadoInicial ="41372586 ";
        String estadoFinal =" 12345678";
        int opcion = 0;

        Scanner leer = new Scanner(System.in);
        Arbol arbol = new Arbol(new Nodo(estadoInicial, null));
        Nodo resultado = null;
        
        do { 
            System.out.println("--- Menu de opciones ---");
            System.out.println(" 1-Busqueda en anchura \n 2-Busqueda en profundidad \n 3-Busqueda de costo uniforme \n 4-Busqueda en profundidad limitada \n 5-Busqueda con heuristica \n 6-Salir");
            System.out.print("Seleccionar una opción valida: ");
            opcion = leer.nextInt();
            
            switch (opcion) {
                case 1:
                   resultado = arbol.realizarBusquedaEnAnchura(estadoFinal);
                   imprimirCamino(resultado);
                    break;
                case 2:
                    resultado = arbol.realizarBusquedaEnProfundidad(estadoFinal);
                    imprimirCamino(resultado);
                    break;
                case 3:
                    resultado = arbol.realizarBusquedaCostoUniforme(estadoFinal);
                    imprimirCamino(resultado);
                    break;
                case 4: 
                    resultado = arbol.realizarBusquedaEnProfundidadLimitada(estadoFinal, 45);
                    imprimirCamino(resultado);

                    break;
                case 5:
                    resultado = arbol.realizarBusquedaCostoUniformeHeuristica(estadoFinal);
                    imprimirCamino(resultado);
                    break;
                case 6:
                    System.out.println("Acaba de finalizar el programa");
                    break;
                default:
                    System.out.println("Ingresar una opcion valida");
                    break;
                
            }
        } while (opcion != 6);
        leer.close();
    }

    private static void imprimirCamino(Nodo resultado) {
        List<String> camino = new ArrayList<>();
        Nodo actual = resultado;

        while (actual != null) {
            camino.add(actual.estado);
            actual = actual.padre;
        }
        Collections.reverse(camino);

        int paso = 1;
        System.out.println("\n--- Secuencia de pasos ---");
        for (String estado : camino) {
            System.out.println("Paso: " + (paso++));
            imprimirTablero(estado);
            System.out.println();
        }
    }

    private static void imprimirTablero(String estado) {

        for (int i = 0; i < estado.length(); i++) {
            System.out.print(estado.charAt(i) + " ");
            if ((i + 1) % 3 == 0) { 
                System.out.println();
            }
        }
    }

}
