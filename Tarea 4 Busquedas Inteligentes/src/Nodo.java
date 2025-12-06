package primeroenanchura;

import java.util.ArrayList;
import java.util.List;

public class Nodo implements Comparable<Nodo>{
    String estado;
    Nodo padre;
    int profundidad;
    int costo;
    int f;

    public Nodo(String estado, Nodo padre) {
        this(estado, padre, (padre == null ? 0 : padre.costo + 1));
    }

    public Nodo(String estado, Nodo padre, int costo) {
        this.estado = estado;
        this.padre = padre;
        this.costo = costo;
    }

    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.costo, otro.costo);
    }

    public int heuristicaManhattan(String objetivo) {
    int suma = 0;
    for (int i = 0; i < estado.length(); i++) {
        char c = estado.charAt(i);
        if (c != ' ') {
            int indiceObjetivo = objetivo.indexOf(c);
            suma += Math.abs(fila(i) - fila(indiceObjetivo)) + Math.abs(columna(i) - columna(indiceObjetivo));
        }
    }
    return suma;
    }


    private int fila(int indice) {
        return indice / 3;
    }

    private int columna(int indice) {
        return indice % 3;
    }

    public List<String> obtenerSucesores() {

        List<String> successors = new ArrayList<String>();

        switch (estado.indexOf(" ")) {
            case 0: {
                successors.add(estado.replace(estado.charAt(0), '*').replace(estado.charAt(1), estado.charAt(0)).replace('*', estado.charAt(1)));
                successors.add(estado.replace(estado.charAt(0), '*').replace(estado.charAt(3), estado.charAt(0)).replace('*', estado.charAt(3)));
                break;
            }
            case 1: {
                successors.add(estado.replace(estado.charAt(1), '*').replace(estado.charAt(0), estado.charAt(1)).replace('*', estado.charAt(0)));
                successors.add(estado.replace(estado.charAt(1), '*').replace(estado.charAt(2), estado.charAt(1)).replace('*', estado.charAt(2)));
                successors.add(estado.replace(estado.charAt(1), '*').replace(estado.charAt(4), estado.charAt(1)).replace('*', estado.charAt(4)));
                break;
            }
            case 2: {

                successors.add(estado.replace(estado.charAt(2), '*').replace(estado.charAt(1), estado.charAt(2)).replace('*', estado.charAt(1)));
                successors.add(estado.replace(estado.charAt(2), '*').replace(estado.charAt(5), estado.charAt(2)).replace('*', estado.charAt(5)));
                break;
            }
            case 3: {
                successors.add(estado.replace(estado.charAt(3), '*').replace(estado.charAt(0), estado.charAt(3)).replace('*', estado.charAt(0)));
                successors.add(estado.replace(estado.charAt(3), '*').replace(estado.charAt(4), estado.charAt(3)).replace('*', estado.charAt(4)));
                successors.add(estado.replace(estado.charAt(3), '*').replace(estado.charAt(6), estado.charAt(3)).replace('*', estado.charAt(6)));
                break;
            }
            case 4: {
                successors.add(estado.replace(estado.charAt(4), '*').replace(estado.charAt(1), estado.charAt(4)).replace('*', estado.charAt(1)));
                successors.add(estado.replace(estado.charAt(4), '*').replace(estado.charAt(3), estado.charAt(4)).replace('*', estado.charAt(3)));
                successors.add(estado.replace(estado.charAt(4), '*').replace(estado.charAt(5), estado.charAt(4)).replace('*', estado.charAt(5)));
                successors.add(estado.replace(estado.charAt(4), '*').replace(estado.charAt(7), estado.charAt(4)).replace('*', estado.charAt(7)));
                break;
            }
            case 5: {
                successors.add(estado.replace(estado.charAt(5), '*').replace(estado.charAt(2), estado.charAt(5)).replace('*', estado.charAt(2)));
                successors.add(estado.replace(estado.charAt(5), '*').replace(estado.charAt(4), estado.charAt(5)).replace('*', estado.charAt(4)));
                successors.add(estado.replace(estado.charAt(5), '*').replace(estado.charAt(8), estado.charAt(5)).replace('*', estado.charAt(8)));
                break;
            }
            case 6: {
                successors.add(estado.replace(estado.charAt(6), '*').replace(estado.charAt(3), estado.charAt(6)).replace('*', estado.charAt(3)));
                successors.add(estado.replace(estado.charAt(6), '*').replace(estado.charAt(7), estado.charAt(6)).replace('*', estado.charAt(7)));
                break;

            }
            case 7: {
                successors.add(estado.replace(estado.charAt(7), '*').replace(estado.charAt(4), estado.charAt(7)).replace('*', estado.charAt(4)));
                successors.add(estado.replace(estado.charAt(7), '*').replace(estado.charAt(6), estado.charAt(7)).replace('*', estado.charAt(6)));
                successors.add(estado.replace(estado.charAt(7), '*').replace(estado.charAt(8), estado.charAt(7)).replace('*', estado.charAt(8)));
                break;
            }
            case 8: {
                successors.add(estado.replace(estado.charAt(8), '*').replace(estado.charAt(5), estado.charAt(8)).replace('*', estado.charAt(5)));
                successors.add(estado.replace(estado.charAt(8), '*').replace(estado.charAt(7), estado.charAt(8)).replace('*', estado.charAt(7)));
                break;
            }
        }
        return successors;
    }
}
