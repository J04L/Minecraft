package Items.Throwables;

import Items.Item;

public class Lanzable extends Item {
    public Lanzable(String name){
        super(16, name);
    }
    public Lanzable(String name, int lot){
        super(16, name, lot);
    }

    @Override
    public void use(){
        super.use();
        System.out.println("Lanzar " + name);
    }
}
