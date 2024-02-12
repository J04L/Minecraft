import java.util.Arrays;
import java.util.Scanner;

public class Item {
    protected static Scanner scanLine = new Scanner(System.in);
    protected String[] nameTable;
    public static final int numTypes = 3;
    protected String name;
    protected int stack;
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
        Item[] items = {new Block(name, lot), new Throwable(name, lot), new Tool(name, lot)};
        for (Item item : items) {
            if (item.nameIsValid(name)) return item;
        }
        return items[0];
    }

    public static Item getType(int numRandom){
        Item[] items = {new Block(), new Tool(), new Throwable()};
        return items[numRandom];
    }
    @Override
    public String toString() {
        return "[" + itemType + "] >> " + Arrays.toString(nameTable) + "//" + stack;
    }

    public static void menu(){
        System.out.println("----TABLA DE ITEMS------------------------------------------------------------------------------------------------------");
        Item[] items = {new Block(), new Throwable(), new Tool()};
        for(Item item : items){
            System.out.println(item);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
    public static boolean validName(String itemName){
        Item[] items = {new Block(), new Tool(), new Throwable()};
        for (Item item : items) {
            if (item.nameIsValid(itemName)) return true;
        }
        return false;
    }
    public static String readItem(String itemName){
        if (validName(itemName)) return itemName;
        else {
            System.out.print("-----[ERROR] No existe el item " + itemName+"\nnombre> ");
            return readItem(scanLine.nextLine());
        }
    }
}
