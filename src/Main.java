import java.util.ArrayList;
class Item{
    String name;
    double amount;
    Item(String name, double amount){
        this.name=name;
        this.amount=amount;
    }
        }
        public class Main{
    public static void main (String[] args){
        ArrayList<Item>list=new ArrayList<>();
        list.add(new Item("AAA",100));
        list.add(new Item("BBB", 300));
        double total=0;
        for(Item item: list){
            total +=item.amount;
        }
        System.out.println(total);
    }
        }