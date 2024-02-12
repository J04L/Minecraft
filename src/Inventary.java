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
        //item --> auxiliar para almacenar el nombre y el lot añadidos por el usuario

        int allItemsAdded = 0;
        //contador cuantos items se han añadido

        Item lastItem = getLastItem(item.name, item.stack);
        //coge del inventario el item con ese nombre que no está lleno...

        //y lo rellena
        if (lastItem!=null && lastItem.lot<item.stack) {
            //si existe un último item que no esté lleno...

            int ultLotToAdd = Math.min(item.stack-lastItem.lot, item.lot); //escoge cuanto añadir
            lastItem.lot += ultLotToAdd;//añade lo escogido
            item.lot -= ultLotToAdd; //retira lo añadido

            allItemsAdded += ultLotToAdd;
        }
        else if(inventory.size()==capacity){
            //el inventario está lleno y no se ha encontrado un item que se pueda rellenar...
            System.out.println("-----[ERROR] Inventario lleno, no puedes añadir " + item.name);
        }

        while (item.lot > 0 && inventory.size()<capacity) {
            //termina cuando ya no que nada que añadir y se ha llenado el inventario

            int itemsToAdd = Math.min(item.stack, item.lot); //escoge cuanto añadir
            if (itemsToAdd > 0) {
                inventory.add(Item.createItem(item.name, itemsToAdd));
                //crea item con la cantidad escogida
            }
            item.lot -= itemsToAdd;//retira lo añadido

            allItemsAdded += itemsToAdd;
        }
        return allItemsAdded; //devuelve la cantidad de items añadidos
    }
    private static void randomFillOut(){
        Random random = new Random();
        int reps = random.nextInt(capacity)+1;
        //se rellena un número aletorio de veces, máxmo = capacidad

        for(int i=0; i<reps; i++){
            Item item = Item.itemsList[random.nextInt(Item.itemsList.length)];
            //item auxiliar que almacena uno de los 3 tipos aleatorios

            int nameRandom = random.nextInt(item.nameTable.length);
            //posición del nombre en la lista de nombres del tipo

            int lot = random.nextInt(item.stack)+1;
            //cantidad a rellenar máximo = stack

            add(Item.createItem(item.nameTable[nameRandom], lot));
            //añade un item con el nombre aleatorio y la cantidad aleatoria
        }
    }
    private static void showInvetory(){
        System.out.println("---------INVENTARIO--------------------------------------------------------------------------------");
        if (!inventory.isEmpty()){
            for (Item item : inventory) {
                System.out.print(item.name + "/" + item.lot + "   ");
            }
            System.out.println(inventory.size() == capacity? "|FULL|": "");
        }else System.out.println("vacío");
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    public static int remove(Item item){
        int allLotRemoved =0;

        while(item.lot > 0){
            Item itemToRemove = getLastItem(item.name, item.stack);
            //coge del inventario un item con ese nombre empezando por el que no está rellenado

            if (itemToRemove==null) {
                //no hay items con ese nombre en el inventario, a lo mejor ya has eliminado todos o...
                if (allLotRemoved ==0) System.out.println("-----[ERROR] No hay " + item.name);
                //no se ha encontrado en el inventario ningún item con el nombre introducido
                break;
            }
            int itemsToRemove = Math.min(itemToRemove.lot, item.lot); //escoge qué cantidad eliminar
            itemToRemove.lot -= itemsToRemove; //elimina la cantidad escogida
            item.lot -= itemsToRemove;//retira la cantidad eliminada

            allLotRemoved += itemsToRemove;

            if (itemToRemove.lot == 0) {
                //si el item está vacío lo elimina
                inventory.remove(itemToRemove);
            }
        }
        return  allLotRemoved;
    }
    private static Item getLastItem(String itemName,int stack){
        if (!inventory.isEmpty()){
            Item item = null;
            for (Item i : inventory) {
                if (i.name.equals(itemName)) {
                    //si en el inventario se encuentra en el inventario un item con el nombre introducido...

                    if (i.lot < stack) return i;
                    //si el item encontrado no está lleno lo devuelve

                    item = i;
                    //si al final no se ha encontrado ninguno que no esté lleno....
                }
            }
            return item;
            //devolveremos un item con ese nombre pero lleno
            //o
            //null si no se ha encontrado ningún item en el inventario con el nombre introducido
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