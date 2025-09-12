// Tarea 1 Arbol Binario

public class Tarea1 {

   static class Nodo{
        int valor;
        Nodo Izquierdo, Derecho;

        public Nodo (int valor){
            this.valor =valor;
            this.Izquierdo = null;
            this.Derecho = null;
        }
    }

    static class Arbol{

        private Nodo raiz;

        public Arbol(){
            raiz = null;
        }

        public boolean estaVacio(){
            return raiz == null;
        }

        public void insertar(int valor){
            raiz = insertarNodo(raiz, valor);
        }

        private Nodo insertarNodo(Nodo actual, int valor){
            if (actual == null){
                return new Nodo(valor);
            }

            if (valor < actual.valor){
                actual.Izquierdo = insertarNodo (actual.Izquierdo, valor);
            } else if (valor > actual.valor){
                actual.Derecho = insertarNodo(actual.Derecho, valor);
            }
            return actual;
        
    }

    public Nodo buscarNodo(int valor){
        return buscarNodoRec(raiz, valor);
    }

    private Nodo buscarNodoRec(Nodo actual, int valor){
        if (actual == null || actual.valor == valor){
            return actual;
        }

        if (valor < actual.valor){
            return buscarNodoRec(actual.Izquierdo, valor);
        } else {
            return buscarNodoRec(actual.Derecho, valor);
        }
    }

    public void ImprimirenOrden(){
        ImprimirEnOrdenRec(raiz);
        System.out.println();
    }

    private void ImprimirEnOrdenRec(Nodo actual){
        if (actual != null){
            ImprimirEnOrdenRec(actual.Izquierdo);
            System.out.print(actual.valor + " ");
            ImprimirEnOrdenRec(actual.Derecho);
        }
    }
    }

    public static void main(String[] args) {
        Arbol arbol = new Arbol();

        arbol.insertar(100);
        arbol.insertar(50);
        arbol.insertar(30);
        arbol.insertar(70);
        arbol.insertar(20);
        arbol.insertar(40);
        arbol.insertar(60);
        arbol.insertar(90);
        arbol.insertar(80);
        arbol.insertar(10);

        System.out.println("Impresion en orden:");
        arbol.ImprimirenOrden();

        int valorABuscar = 40;
        Nodo resultado = arbol.buscarNodo(valorABuscar);
        if (resultado != null){
            System.out.println("Nodo con valor " + valorABuscar + " encontrado.");
        } else {
            System.out.println("Nodo con valor " + valorABuscar + " no encontrado.");
        }
    }
    
}
        