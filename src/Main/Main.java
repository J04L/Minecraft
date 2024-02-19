package Main;

import Inventario.Inventario;
import Items.Item;

import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static Scanner scanLine = new Scanner(System.in);
    private static final Inventario inventario = new Inventario();
    /*
    1. Añadir al inventario (aleatorio) pedir el nº de items a introducir. Después crear los items aleatorios

2. Lanzar, escribir el tipo de item a lanzar

3. Utilizar pico (nuevo, usado) usado poner el rango ejemplo > 5

3. Utilizar espada (nuevo, usado) usado poner el rango ejemplo > 5

4 Quemar item (buscar un item que se pueda quemar y quemarlo) mostrar el mensaje

3. Mostrar inventario (poner el nº de cada objeto) o mostrar todos los items de una clase (herramientas o materiales y que los liste)
     */
    public static void main(String[] args){
        int opcion;
        do{
            menu();
            opcion = readInt("> ");
            switch (opcion) {
                case 1:
                    inventario.randomFillOut();
                    inventario.shoseOption();
                    break;
                case 2:
                    int repes = readInt("cantidad> ");
                    inventario.randomFillOut(repes);
                    break; //rellenar aleatoriamente
                case 3:
                    inventario.showInvetory();
                    System.out.println("nombre> ");
                    inventario.useItem(scanLine.nextLine());
                    break; //usar
                case 4:
                    inventario.showInvetory();
                    inventario.quemarItem();
                    break; //quemar
                case 5:
                    inventario.showInvetory();
                    break; //mostrar inventario
                case 6: break;//exit
                default:
                    System.out.println("-----[ERROR] Fuera de rango");
                    break;
            }
        }while (opcion != 6);

    }
    public static int readInt(String cuestion){
        while (true){
            try{
                System.out.print(cuestion);
                return Integer.parseInt(scan.next());
            }catch (Exception ex){
                System.out.println("-----[ERROR] No se esperaba una letra");
            }
        }
    }
    private static void menu(){
        System.out.println("""
                1 --> inventario
                2 --> rellenar aleatoriamente
                3 --> usar
                4 --> quemar
                5 --> mostrar inventario
                6 --> exit""");
    }
}
