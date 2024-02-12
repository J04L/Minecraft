public class Tool extends Item {

    public Tool(){
        super("Herramientas", new String[]{"pico", "espada", "hacha", "pala"}, 1);
    }
    public Tool(String name, int lot){
        super("Herramientas", new String[]{"pico", "espada", "hacha", "pala"}, 1, name, lot);
    }
}
