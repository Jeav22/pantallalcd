
import java.util.Arrays;
import java.util.stream.Stream;

public class ImpresorLCD {

    private String[][] matrizDigito;
    private int size, POSICION_X = 0, POSICION_Y = 1;
    static final String CARACTER_VERTICAL = "|", CARACTER_HORIZONTAL = "-";
    // Puntos fijos
    private int[] pf1, pf2, pf3, pf4, pf5;

    public ImpresorLCD() {
        // Inicializa variables
        this.pf1 = new int[2];
        this.pf2 = new int[2];
        this.pf3 = new int[2];
        this.pf4 = new int[2];
        this.pf5 = new int[2];
    }

    /**
     *
     * Metodo encargado de procesar la entrada que contiene el size del segmento
     * de los digitos y los digitos a imprimir
     *
     * @param comando Entrada que contiene el size del segmento de los digito y
     * el numero a imprimir
     * @param espacioDig Espacio Entre digitos
     */
    public void procesar(String comando, int espacioDig) {
        String[] parametros;
        int tamaño;

        if (!comando.contains(",")) {
            throw new IllegalArgumentException("Cadena " + comando
                    + " no contiene caracter ,");
        }

        parametros = comando.split(",");

        // Valida la cantidad de parametros
        if (parametros.length > 2) {
            throw new IllegalArgumentException("Cadena " + comando
                    + " contiene mas caracter ,");
        }

        // Valida la cantidad de parametros
        if (parametros.length < 2) {
            throw new IllegalArgumentException("Cadena " + comando
                    + " no contiene los parametros requeridos");
        }

        // Valida que el parametro size sea un numerico
        if (isNumeric(parametros[0])) {
            tamaño = Integer.parseInt(parametros[0]);
            // Realiza la impresion del numero
            imprimirNumero(tamaño, parametros[1], espacioDig);
        }
    }

    /**
     *
     * Metodo encargado de imprimir un numero
     *
     * @param size Tamaño Segmento Digitos
     * @param numeroImp Numero a Imprimir
     * @param espacio Espacio Entre digitos
     */
    private void imprimirNumero(int size, String numeroImp, int espacio) {
        String[][] pantallaLCD;
        String[][] matrizEsp;
        char[] digitos;
        this.size = size;

        // Calcula el numero de filas cada digito
        int filasDigito = (2 * this.size) + 3;
        // Calcula el numero de columna de cada digito
        int columnasDigito = this.size + 2;

        pantallaLCD = new String[0][0];
        this.matrizDigito = new String[filasDigito][columnasDigito];
        matrizEsp = new String[filasDigito][espacio];

        // crea el arreglo de digitos
        digitos = numeroImp.toCharArray();

        // Inicializa matriz de espaciado
        for (String[] row : matrizEsp) {
            Arrays.fill(row, " ");
        }

        calcularPuntosFijos(filasDigito, columnasDigito);

        for (char digito : digitos) {
            if (!Character.isDigit(digito)) {
                throw new IllegalArgumentException("Caracter " + digito
                        + " no es un digito");
            }

            int numero = Integer.parseInt(String.valueOf(digito));

            generarDigito(numero);

            if (pantallaLCD.length == 0) {
                pantallaLCD = this.matrizDigito.clone();
                pantallaLCD = concat(pantallaLCD, matrizEsp);
            } else {
                pantallaLCD = concat(pantallaLCD, this.matrizDigito);
                pantallaLCD = concat(pantallaLCD, matrizEsp);
            }
        }

        for (String[] row : pantallaLCD) {
            for (int j = 0; j < pantallaLCD[0].length; j++) {
                System.out.print(row[j]);
            }
            System.out.println();
        }
    }

    /**
     *
     * Metodo encargado de validar si el size es numerico
     *
     * @param cadena Cadena
     */
    static boolean isNumeric(String cadena) {
        try {
            int tam = Integer.parseInt(cadena);

            // se valida que el size este entre 1 y 10
            if (tam < 1 || tam > 10) {
                throw new IllegalArgumentException("El parametro size [" + tam
                        + "] debe estar entre 1 y 10");
            }
            return true;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Cadena " + cadena
                        + " no es un entero");
        }
    }

    /**
     *
     * Metodo encargado calcular los puntos fijos correspondientes al inicio
     * de cada segmento dentro de la matrizDigito
     *
     * @param filaTam Tamaño de fila digito
     * @param columnaTam Tamaño de columna digito
     */
    private void calcularPuntosFijos(int filaTam, int columnaTam) {
        this.pf1[0] = 0;
        this.pf1[1] = 0;

        this.pf2[0] = (filaTam / 2);
        this.pf2[1] = 0;

        this.pf3[0] = (filaTam - 1);
        this.pf3[1] = 0;

        this.pf4[0] = (columnaTam - 1);
        this.pf4[1] = (filaTam / 2);

        this.pf5[0] = 0;
        this.pf5[1] = (columnaTam - 1);
    }

    /**
     *
     * Metodo encargado de definir los segmentos que componen un digito y a
     * partir de los segmentos adicionar la representacion del digito a la
     * matriz
     *
     * @param numero Digito
     */
    private void generarDigito(int numero) {
        // Inicializa matriz de digito
        for (String[] row : this.matrizDigito) {
            Arrays.fill(row, " ");
        }

        // Establece los segmentos de cada numero
        switch (numero) {
            case 1:
                adicionarSegmento(this.pf5, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                break;
            case 2:
                adicionarSegmento(this.pf1, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf5, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf2, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf2, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf3, POSICION_X, CARACTER_HORIZONTAL);
                break;
            case 3:
                adicionarSegmento(this.pf1, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf5, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf2, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf3, POSICION_X, CARACTER_HORIZONTAL);
                break;
            case 4:
                adicionarSegmento(this.pf1, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf2, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf5, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                break;
            case 5:
                adicionarSegmento(this.pf1, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf1, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf2, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf3, POSICION_X, CARACTER_HORIZONTAL);
                break;
            case 6:
                adicionarSegmento(this.pf1, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf1, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf2, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf2, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf3, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                break;
            case 7:
                adicionarSegmento(this.pf1, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf5, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                break;
            case 8:
                adicionarSegmento(this.pf1, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf2, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf5, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf1, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf2, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf3, POSICION_X, CARACTER_HORIZONTAL);
                break;
            case 9:
                adicionarSegmento(this.pf1, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf5, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf1, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf2, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf3, POSICION_X, CARACTER_HORIZONTAL);
                break;
            case 0:
                adicionarSegmento(this.pf1, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf2, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf5, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf4, POSICION_Y, CARACTER_VERTICAL);
                adicionarSegmento(this.pf1, POSICION_X, CARACTER_HORIZONTAL);
                adicionarSegmento(this.pf3, POSICION_X, CARACTER_HORIZONTAL);
                break;
            default:
                break;
        }
    }

    /**
     *
     * Metodo encargado de añadir una linea a la matriz de Impresion
     *
     * @param matriz Matriz Impresion
     * @param punto Punto Pivote
     * @param posFija Posicion Fija
     * @param caracter Caracter Segmento
     */
    private void adicionarSegmento(int[] punto, int posFija, String caracter) {
        if (posFija == POSICION_X) {
            for (int y = 1; y <= this.size; y++) {
                int valor = punto[1] + y;
                this.matrizDigito[punto[0]][valor] = caracter;
            }
        } else {
            for (int i = 1; i <= this.size; i++) {
                int valor = punto[0] + i;
                this.matrizDigito[valor][punto[1]] = caracter;
            }
        }
    }

    /**
     *
     * Metodo encargado de concatenar las matrices de los digitos a imprimir
     *
     * @param a Matriz inicial
     * @param b Matriz a concatenar
     * @return Matriz resultado de la concatenacion horizontal
     */
    public static String[][] concat(String[][] a, String[][] b) {
        String[][] arr = new String[a.length][a[0].length + b[0].length];
        for (int i = 0; i < a.length; i++) {
            arr[i] = Stream.of(a[i], b[i]).flatMap(Stream::of).toArray(String[]::new);
        }
        return arr;
    }

}
