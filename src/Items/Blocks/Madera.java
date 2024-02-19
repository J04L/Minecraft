package Items.Blocks;
import Items.Inflamable;
public class Madera extends Bloque implements Inflamable{
    public Madera(){
        super("Madera");
    }
    public Madera(int lot){
        super("Madera", lot);
    }
    @Override
    public void quemar() {
        System.out.println("Madera quemada...");
        lot--;
    }
}
