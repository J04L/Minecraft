package Items;
import java.util.Scanner;
import Items.Blocks.*;
import Items.Throwables.*;
import Items.Tools.*;
import Main.Main;

public abstract class Item {
    //almacena los tipos que lo heredan
    public static final String[] listaItems = {"Madera", "Piedra", "Huevo", "Ender Perl", "Espada", "Pico"};
    protected static Scanner scanLine = new Scanner(System.in);
    //almacena los nombres de los items para cada tipo
    protected String name;
    //nombre del item ejemplo: madera, pala, huevo...
    protected int stack;
    //máxima cantidad de lot por item
    protected int lot;
    public Item(int stack, String name){
        this.stack = stack;
        setNombre(name);
    }
    public Item(int stack, String name, int lot) {
        this.stack = stack;
        setNombre(name);
        addLot(lot);
    }
    public String getName() {
        return name;
    }
    public int getStack() {
        return stack;
    }
    public int getLot() {
        return lot;
    }
    public int setLot(int lot){
        while (lot <= 0){
            System.out.println("-----[ERROR] Cantidad inválida");
            lot = Main.readInt("cantidad> ");
        }
        return lot;
    }
    public void addLot(int lot){
        this.lot += setLot(lot);
    }
    public void removeLot(int lot){
        this.lot -= setLot(lot);
    }
    public void use(){lot--;}
    public static Item createItem(String nombre){
        return switch (nombre.toLowerCase()) {
            case "madera" -> new Madera();
            case "piedra" -> new Piedra();
            case "huevo" -> new Huevo();
            case "ender perl" -> new EnderPerl();
            case "pico" -> new Pico();
            case "espada" -> new Espada();
            default -> {
                System.out.print("-----[ERROR] El item " + nombre + " no existe\nnombre> ");
                yield createItem(scanLine.nextLine());
            }
        };
    }
    public static Item createItem(String nombre, int lot){
        return switch (nombre.toLowerCase()) {
            case "madera" -> new Madera(lot);
            case "piedra" -> new Piedra(lot);
            case "huevo" -> new Huevo(lot);
            case "ender perl" -> new EnderPerl(lot);
            case "pico" -> new Pico(lot);
            case "espada" -> new Espada(lot);
            default -> {
                System.out.print("-----[ERROR] El item " + nombre + " no existe\nnombre> ");
                yield createItem(scanLine.nextLine(), lot);
            }
        };
    }
    @Override
    public String toString() {
        return "[" + name + "] >> " + stack + "   ";
    }
    public static void menu(){
        int i =1;
        for (String nombre : listaItems){
            Item item = createItem(nombre);
            System.out.print(item + (i%2==0? "\n": ""));
            i++;
        }
    }
    public void setNombre(String itemName){
        boolean existe = false;
        while(!existe){
            int i = 0;
            while (i < listaItems.length) {
                existe = listaItems[i].equalsIgnoreCase(itemName);
                if (existe) break;
                i++;
            }
            if (existe) this.name = itemName;
            else {
                System.out.print("-----[ERROR] No existe el item " + itemName + "\nnombre> ");
                itemName = scanLine.nextLine();
            }
        }

    }
}
