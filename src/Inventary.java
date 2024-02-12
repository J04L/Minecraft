import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Inventary {
    public static Scanner scan = new Scanner(System.in);
    public static Scanner scanLine = new Scanner(System.in);
    public static int capacity =7;
    private static final ArrayList<Item> inventory = new ArrayList<>();
    public static void main(String[] args) {
        boolean exit = false;
        randomFillOut();
        while(!exit){
            menu();
            int option = readInt("> ");
            switch (option){
                case 1:

                    Item.menu();
                    Item itemAdd = Item.createItem(setItemName(), setLot());
                    int allItemsAdded = add(itemAdd);
                    if (allItemsAdded != 0){
                        System.out.println(allItemsAdded / itemAdd.stack + " stacks y " +
                                allItemsAdded % itemAdd.stack + " items de " + itemAdd.name + " añadidos\n");
                    }
                    break;
                case 2:
                    showInvetory();
                    Item itemRemove = Item.createItem(setItemName(), setLot());
                    int allLotRemoved = remove(itemRemove);
                    if(allLotRemoved!= 0){
                        System.out.println(allLotRemoved / itemRemove.stack + " stacks y " + allLotRemoved % itemRemove.stack +
                                " items de " + itemRemove.name + " eliminados\n");
                    }
                    break;
                case 3:
                    showInvetory();
                    break;
                case 4:
                    exit = true;
                break;
                default:
                    System.out.println("-----[ERROR] Fuera de rango");
                    break;
            }
        }
        System.out.println("Fin del programa");
    }
    public static String setItemName(){
        System.out.print("nombre> ");
        return Item.readItem(scanLine.nextLine());
    }
    public static int setLot(){
        int lot = readInt("cantidad> ");
        if (lot<0){
            System.out.println("-----[ERROR] Cantidad inválida");
            return setLot();
        }
        return lot;
    }
    public static int add(Item item){

        int allItemsAdded = 0;

        Item lastItem = getLastItem(item.name, item.stack);
        if (lastItem!=null && lastItem.lot<item.stack) {
            int ultLotToAdd = Math.min(item.stack-lastItem.lot, item.lot);
            allItemsAdded += ultLotToAdd;
            lastItem.lot += ultLotToAdd;
            item.lot -= ultLotToAdd;
        }
        else if(inventory.size()==capacity){
            System.out.println("-----[ERROR] Inventario lleno, no puedes añadir " + item.name);
        }
        //lot 88

        while (item.lot > 0 && inventory.size()<capacity) {
            int itemsToAdd = Math.min(item.stack, item.lot);
            item.lot -= itemsToAdd; //6

            if (itemsToAdd > 0) {
                inventory.add(Item.createItem(item.name, itemsToAdd));
            }
            allItemsAdded += itemsToAdd;
        }
        return allItemsAdded;
    }
    private static void randomFillOut(){
        Random random = new Random();
        int reps = random.nextInt(capacity)+1;
        for(int i=0; i<reps; i++){
            Item item = Item.getType(random.nextInt(Item.numTypes));

            int nameRandom = random.nextInt(item.nameTable.length);
            int lot = random.nextInt(item.stack)+1;

            for (String name : item.nameTable) {
                if (item.nameTable[nameRandom].equals(name)) {
                    add(Item.createItem(name, lot));
                    break;
                }
            }
        }

    }
    private static void showInvetory(){
        System.out.println("---------INVENTARIO-----------------------------------------------------------------------------------------------------");
        if (!inventory.isEmpty()){
            for (Item item : inventory) {
                System.out.print(item.name + "/" + item.lot + "   ");
            }
            System.out.println();
        }else System.out.println("vacío");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }
    public static int remove(Item item){
        int allLotRemoved =0;

        while(item.lot > 0){
            Item itemToRemove = getLastItem(item.name, item.stack);
            if (itemToRemove==null) {
                if (allLotRemoved ==0) System.out.println("-----[ERROR] No hay " + item.name);
                break;
            }
            int itemsToRemove = Math.min(itemToRemove.lot, item.lot);
            itemToRemove.lot -= itemsToRemove;
            item.lot -= itemsToRemove;

            allLotRemoved += itemsToRemove;
            if (itemToRemove.lot == 0) {
                inventory.remove(itemToRemove);
            } //false
        }
        return  allLotRemoved;
    }
    private static Item getLastItem(String itemName,int stack){
        if (!inventory.isEmpty()){
            Item item = null;
            for (Item i : inventory) {
                if (i.name.equals(itemName)) {
                    if (i.lot < stack) return i;
                    item = i;
                }
            }
            return item;
        }
        return null;
    }

    private static void menu() {

        System.out.println("""
                1 --> añadir
                2 --> eliminar
                3 --> mostrar inventario
                4 --> exit""");
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
}