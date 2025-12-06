package primeroenanchura;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Arbol {
    Nodo raiz;
    int nodosGenerados = 1;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo realizarBusquedaEnAnchura(String objetivo){
        long inicio = System.nanoTime();
        nodosGenerados = 1;
        Queue<Nodo> cola = new LinkedList<Nodo>();
        HashSet<String> visitados = new HashSet<String>();
        cola.add(raiz);
        visitados.add(raiz.estado);
        boolean encontrado = false;
        Nodo actual = null;
        
        while(!encontrado && cola.size() > 0){
            actual = cola.poll();
            System.out.println("Procesando: "+actual.estado);
            //Función objetivo
            if(actual.estado.equals(objetivo)){
                encontrado = true;
            }else{
                List<String> sucesores = actual.obtenerSucesores();
                for(String sucesor: sucesores){
                    nodosGenerados++;
                    if(visitados.contains(sucesor))
                        continue;
                    System.out.println("Agergando a cola: "+sucesor);
                    cola.add(new Nodo(sucesor, actual));
                    visitados.add(sucesor);
                    
                }
            }
            //Procesar
        }
        //Utilizar las variables de clase
        long fin = System.nanoTime();
        long duracion = fin - inicio;
        
        System.out.println("Nodos generados: " + nodosGenerados);
        System.out.println("Tiempo de ejecución: " + (duracion / 1_000_000_000.0) + " seg");
        return actual;
    }

    public Nodo realizarBusquedaEnProfundidad(String objetivo){
    long inicio = System.nanoTime();
    nodosGenerados = 1;
    Stack<Nodo> pila = new Stack<>();
    HashSet<String> visitados = new HashSet<>();

    pila.push(raiz);
    visitados.add(raiz.estado);

    Nodo actual = null;
    boolean encontrado = false;

    while(!pila.isEmpty() && !encontrado){
        actual = pila.pop();
        System.out.println("Procesando: " + actual.estado);

        if(actual.estado.equals(objetivo)){
            encontrado = true;
        } else {
            List<String> sucesores = actual.obtenerSucesores();
            for(String sucesor : sucesores){
                nodosGenerados++;
                if(visitados.contains(sucesor))
                    continue;

                System.out.println("Agregando a pila: " + sucesor);
                pila.push(new Nodo(sucesor, actual));
                visitados.add(sucesor);
            }
        }
    }
    long fin = System.nanoTime();
    long duracion = fin - inicio;
        
    System.out.println("Nodos generados: " + nodosGenerados);
    System.out.println("Tiempo de ejecución: " + (duracion / 1_000_000_000.0) + " seg");
    return actual;
}

public Nodo realizarBusquedaCostoUniforme(String objetivo) {
    long inicio = System.nanoTime();
    nodosGenerados = 1;
    PriorityQueue<Nodo> cola = new PriorityQueue<>();
    HashSet<String> visitados = new HashSet<>();

    cola.add(raiz);
    visitados.add(raiz.estado);

    Nodo actual = null;
    boolean encontrado = false;

    while (!cola.isEmpty() && !encontrado) {
        actual = cola.poll();
        System.out.println("Procesando => " + actual.estado + " con costo: " + actual.costo);

        if (actual.estado.equals(objetivo)) {
            encontrado = true;
        } else {
            List<String> sucesores = actual.obtenerSucesores();
            for (String sucesor : sucesores) {
                nodosGenerados++;
                if (visitados.contains(sucesor))
                    continue;

                Nodo nuevo = new Nodo(sucesor, actual);
                System.out.println("Agregando => " + sucesor + " con costo: " + nuevo.costo);
                cola.add(nuevo);
                visitados.add(sucesor);
            }
        }
    }

    long fin = System.nanoTime();
    long duracion = fin - inicio;    
    System.out.println("Nodos generados: " + nodosGenerados);
    System.out.println("Tiempo de ejecución: " + (duracion / 1_000_000_000.0) + " seg");
    return actual;
}

public Nodo realizarBusquedaEnProfundidadLimitada(String objetivo, int limite) {
    long inicio = System.nanoTime();
    nodosGenerados = 1;
    Stack<Nodo> pila = new Stack<>();
    HashSet<String> visitados = new HashSet<>();

    raiz.profundidad = 0;
    pila.push(raiz);
    visitados.add(raiz.estado);

    Nodo actual = null;
    boolean encontrado = false;

    while (!pila.isEmpty() && !encontrado) {
        actual = pila.pop();
        System.out.println("Procesando => " + actual.estado + " en profundidad: " + actual.profundidad);

        if (actual.estado.equals(objetivo)) {
            encontrado = true;
        } else if (actual.profundidad < limite) {
            List<String> sucesores = actual.obtenerSucesores();
            for (String sucesor : sucesores) {
                nodosGenerados++;
                if (visitados.contains(sucesor))
                    continue;

                Nodo nuevo = new Nodo(sucesor, actual);
                nuevo.profundidad = actual.profundidad + 1;
                

                System.out.println("Agregando a pila => " + sucesor + " con profundidad: " + nuevo.profundidad);
                pila.push(nuevo);
                visitados.add(sucesor);
            }
        }
    }

    long fin = System.nanoTime();
    long duracion = fin - inicio;   
    System.out.println("Nodos generados: " + nodosGenerados);
    System.out.println("Tiempo de ejecución: " + (duracion / 1_000_000_000.0) + " seg");
    return encontrado ? actual : null;

}

public Nodo realizarBusquedaCostoUniformeHeuristica(String objetivo) {
    long inicio = System.nanoTime();
    nodosGenerados = 1;
    PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
    HashSet<String> visitados = new HashSet<>();

    cola.add(raiz);
    visitados.add(raiz.estado);

    Nodo actual = null;
    boolean encontrado = false;

    while (!cola.isEmpty() && !encontrado) {
        actual = cola.poll();
        System.out.println("Procesando => " + actual.estado + " con costo: " + actual.costo);

        if (actual.estado.equals(objetivo)) {
            encontrado = true;
        } else {
            List<String> sucesores = actual.obtenerSucesores();
            for (String sucesor : sucesores) {
                nodosGenerados++;
                if (visitados.contains(sucesor))
                    continue;

                Nodo nuevo = new Nodo(sucesor, actual);
                nuevo.f = nuevo.costo + nuevo.heuristicaManhattan(objetivo);
                cola.add(nuevo);
                visitados.add(sucesor);
            }
        }
    }

    long fin = System.nanoTime();
    long duracion = fin - inicio;    
    System.out.println("Nodos generados: " + nodosGenerados);
    System.out.println("Tiempo de ejecución: " + (duracion / 1_000_000_000.0) + " seg");
    return actual;
}


}
