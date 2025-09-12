// Tarea 3 Algoritmo Dijkstra

public class Tarea3 {
    static int distanciaMinima(int dist[], boolean visitado[], int n){
        int min = Integer.MAX_VALUE, indiceMin = -1;
        for (int v = 0; v < n; v++){
            if (!visitado [v] && dist[v] <= min){
                min = dist[v];
                indiceMin = v;
            }
        }
        return indiceMin;
    }

    static void dijkstra(int grafo[][], int origen){
        int n = grafo.length;
        int dist[] = new int[n];
        boolean visitado[] = new boolean[n];

        for (int i= 0; i< n; i ++){
            dist[i] = Integer.MAX_VALUE;
            visitado[i] = false;
        }

        dist[origen] = 0;

        for (int contador = 0; contador < n -1;contador++){
            int u = distanciaMinima(dist, visitado, n);
            visitado[u] = true;

            for (int v = 0; v < n; v++){
                if (!visitado[v] && grafo[u][v] != 0 && 
                    dist[u] != Integer.MAX_VALUE && 
                    dist[u] + grafo[u][v] < dist[v]) {
                    dist[v] = dist[u] + grafo[u][v];
                }
            }
        }
        System.out.println("Distancias mas cortas desde el nodo origen " + origen + ":");
        for (int i = 0; i < n; i++){
            System.out.println("Hacia el nodo " + i + " la distancia es: " + dist[i]);
        }

    } 

    public static void main(String[] args){
            int grafo[][] = new int[][]{
                {0, 7, 9, 0, 0, 14},
                {7, 0, 10, 15, 0, 0},
                {9, 10, 0, 11, 0, 2},
                {0, 15, 11, 0, 6, 0},
                {0, 0, 0, 6, 0, 9},
                {14, 0, 2, 0, 9, 0}
            };
            dijkstra(grafo, 0);
        }
}
