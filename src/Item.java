import java.util.Arrays;
import java.util.Scanner;

public class Item {
    public static final Item[] itemsList = {new Block(), new Throwable(), new Tool()};
    //almacena los tipos que lo heredan
    protected static Scanner scanLine = new Scanner(System.in);
    protected String[] nameTable;
    //almacena los nombres de los items para cada tipo
    protected String name;
    //nombre del item ejemplo: madera, pala, huevo...
    protected int stack;
    //máxima cantidad de lot por item
    protected String itemType;

    protected int lot;

    public Item(String itemType, String[] nameTable, int stack) {
        this.itemType = itemType;
        this.nameTable = nameTable;
        this.stack = stack;
    }
    public Item(String itemType, String[] nameTable, int stack, String name, int lot){
        this.itemType = itemType;
        this.nameTable = nameTable;
        this.stack = stack;
        this.name = name;
        this.lot = lot;
    }
    protected boolean nameIsValid(String name){
        for (String n : nameTable){
            if (name.equalsIgnoreCase(n)){
                return true;
            }
        }
        return false;
    }
    public static Item createItem(String name, int lot){
        //busca el tipo y devuelve un item con el nombre y la cantidad introducida
        Item[] items = {new Block(name, lot), new Throwable(name, lot), new Tool(name, lot)};
        for (Item item : items) {
            if (item.nameIsValid(name)) return item;
        }
        return items[0];//esto nunca sucederá, el nombre está comprobado de ántes siempre
    }
    @Override
    public String toString() {
        return "[" + itemType + "] >> " + Arrays.toString(nameTable) + "//" + stack;
    }

    public static void menu(){
        System.out.println("----TABLA DE ITEMS------------------------------------------------------------------------------------------------------");

        for(Item item : itemsList){
            System.out.println(item);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
    public static String readItem(String itemName){
        for (Item item : itemsList){
            //comprueba si para cada tipo...
            if (item.nameIsValid(itemName)) return itemName;
            //si el nombre coincide con alguno de su lista y se coincide lo devuelve
        }
        System.out.print("-----[ERROR] No existe el item " + itemName+"\nnombre> ");
        return readItem(scanLine.nextLine());
        //si no se vuelve a hacer el método
    }
}
