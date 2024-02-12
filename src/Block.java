public class Block extends Item {
    protected Block(){
        super("Bloque", new String[]{"madera", "arena", "piedra","obsidiana"}, 64);
    }
    protected Block(String name, int lot){
        super("Bloque", new String[]{"madera", "arena", "piedra","obsidiana"}, 64, name, lot);
    }
}
