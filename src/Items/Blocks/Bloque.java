package Items.Blocks;
import Items.Item;

public class Bloque extends Item {
    protected Bloque(String name){
        super(64, name);
    }
    protected Bloque(String name, int lot){
        super(64, name, lot);
    }

    @Override
    public void use() {
        super.use();
        System.out.println("Poner " + name);
    }
}
