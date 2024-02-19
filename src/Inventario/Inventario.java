package Inventario;

import Items.Inflamable;
import Items.Item;
import Main.Main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Inventario {
    private Scanner scan = new Scanner(System.in);
    private Scanner scanLine = new Scanner(System.in);
    public int maxCapacidad =7;
    private final ArrayList<Item> inventario = new ArrayList<>();
    public void shoseOption(){
        boolean exit = false;
        while(!exit){
            menu();
            int option = Main.readInt("> ");
            switch (option){
                case 1:
                    Item.menu();
                    System.out.print("nombre> ");
                    Item itemAdd = Item.createItem(scanLine.nextLine(), Main.readInt("cantidad> "));
                    int allItemsAdded = add(itemAdd);
                    if (allItemsAdded != 0){
                        System.out.println(allItemsAdded / itemAdd.getStack() + " stacks y " +
                                allItemsAdded % itemAdd.getStack() + " items de " + itemAdd.getName() + " añadidos\n");
                    }
                    break;
                case 2:
                    showInvetory();
                    System.out.print("nombre> ");
                    Item itemRemove = Item.createItem(scanLine.nextLine(), Main.readInt("cantidad> "));
                    int allLotRemoved = remove(itemRemove);
                    if(allLotRemoved!= 0){
                        System.out.println(allLotRemoved / itemRemove.getStack() + " stacks y " + allLotRemoved % itemRemove.getStack() +
                                " items de " + itemRemove.getName() + " eliminados\n");
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
    }
    public void useItem(String nombre){
        Item item = getLastItem(nombre);
        if (item == null) System.out.println("-----[ERROR] No hay " + nombre);
        else item.use();
    }
    public void quemarItem(){
        boolean quemado = false;
        for(Item item : inventario){
            quemado = item instanceof Inflamable;
            if (quemado) {
                item = getLastItem(item.getName());
                ((Inflamable) item).quemar();
                if(item.getLot() ==0) inventario.remove(item);
                break;
            }
        }
        if (!quemado) System.out.println("-----[ERROR] No hay ningún item inflamable");

    }
    public int add(Item item){
        //item --> auxiliar para almacenar el nombre y el lot añadidos por el usuario

        int allItemsAdded = 0;
        //contador cuantos items se han añadido

        Item lastItem = getLastItem(item.getName());
        //coge del inventario el item con ese nombre que no está lleno...

        //y lo rellena
        if (lastItem!=null && lastItem.getLot()<item.getStack()) {
            //si existe un último item que no esté lleno...

            int ultLotToAdd = Math.min(item.getStack()-lastItem.getLot(), item.getLot()); //escoge cuanto añadir
            lastItem.addLot(ultLotToAdd);//añade lo escogido
            item.removeLot(ultLotToAdd);//retira lo añadido

            allItemsAdded += ultLotToAdd;
        }
        else if(maxCapacidad- inventario.size() ==0){
            //el inventario está lleno y no se ha encontrado un item que se pueda rellenar...
            System.out.println("-----[ERROR] Inventario lleno, no puedes añadir " + item.getName());
        }

        while (item.getLot() > 0 && maxCapacidad- inventario.size() >0) {
            //termina cuando ya no que nada que añadir o se ha llenado el inventario

            int itemsToAdd = Math.min(item.getStack(), item.getLot()); //escoge cuanto añadir
            if (itemsToAdd > 0) {
                inventario.add(Item.createItem(item.getName(), itemsToAdd));
                //crea item con la cantidad escogida
            }
            item.removeLot(itemsToAdd);//retira lo añadido

            allItemsAdded += itemsToAdd;
        }
        return allItemsAdded; //devuelve la cantidad de items añadidos
    }
    public void randomFillOut(){
        if(maxCapacidad - inventario.size() !=0){
            Random random = new Random();
            int reps = random.nextInt((maxCapacidad - inventario.size())) + 1;
            //se rellena un número aleatorio de veces, máximo = capacidad
            randomFillOut(reps);
        }
    }
    public void randomFillOut(int reps){
        Random random = new Random();
        if (maxCapacidad- inventario.size() ==0) System.out.println("-----[ERROR] Inventario lleno");
        for(int i = 0; i<reps && maxCapacidad- inventario.size() >0; i++){
            String nombreRandom = Item.listaItems[random.nextInt(Item.listaItems.length)];
            Item item = Item.createItem(nombreRandom);
            int lot = random.nextInt(item.getStack())+1;
            //cantidad a rellenar máximo = stack
            item.addLot(lot);
            add(item);
            //añade un item con el nombre aleatorio y la cantidad aleatoria
        }
    }
    public void showInvetory(){
        System.out.println("---------INVENTARIO--------------------------------------------------------------------------------");
        if (!inventario.isEmpty()){
            for (Item item : inventario) {
                System.out.print(item.getName() + "/" + item.getLot() + "   ");
            }
            System.out.println(maxCapacidad- inventario.size() ==0? "|FULL|": "");
        }else System.out.println("vacío");
        System.out.println("---------------------------------------------------------------------------------------------------");
    }

    public int remove(Item item){
        int allLotRemoved =0;

        while(item.getLot() > 0){
            Item itemToRemove = getLastItem(item.getName());
            //coge del inventario un item con ese nombre empezando por el que no está rellenado

            if (itemToRemove==null) {
                //no hay items con ese nombre en el inventario, a lo mejor ya has eliminado todos o...
                if (allLotRemoved ==0) System.out.println("-----[ERROR] No hay " + item.getName());
                //no se ha encontrado en el inventario ningún item con el nombre introducido
                break;
            }
            int itemsToRemove = Math.min(itemToRemove.getLot(), item.getLot()); //escoge qué cantidad eliminar
            itemToRemove.removeLot(itemsToRemove); //elimina la cantidad escogida
            item.removeLot(itemsToRemove);//retira la cantidad eliminada

            allLotRemoved += itemsToRemove;

            if (itemToRemove.getLot() == 0) {
                //si el item está vacío lo elimina
                inventario.remove(itemToRemove);
            }
        }
        return  allLotRemoved;
    }
    private Item getLastItem(String itemName){
        int stack = Item.createItem(itemName).getStack();
        if (!inventario.isEmpty()){
            Item item = null;
            for (Item i : inventario) {
                if (i.getName().equalsIgnoreCase(itemName)) {
                    //si en el inventario se encuentra en el inventario un item con el nombre introducido...

                    if (i.getLot() < stack) return i;
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

    private void menu() {

        System.out.println("""
                1 --> añadir
                2 --> eliminar
                3 --> mostrar inventario
                4 --> exit""");
    }
}