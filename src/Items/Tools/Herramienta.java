package Items.Tools;

import Items.Item;

public class Herramienta extends Item {
    private int durabilidad;
    public Herramienta(String name){
        super( 1, name);
        durabilidad = 5;
    }
    public Herramienta(String name, int lot){
        super( 1, name, lot);
        durabilidad = 5;
    }
    @Override
    public void use() {
        System.out.println("Usar " + name);
        durabilidad--;
    }

}
