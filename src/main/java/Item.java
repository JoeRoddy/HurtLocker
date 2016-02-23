public class Item {
    private String item;
    private String price;
    private String expiration;
    public Item(String item,String price, String expiration) {
        this.price=price;
        this.item=parseItemString(item);
        this.expiration=expiration;
    }
    private static String parseItemString(String itemToDetermine){
        if(itemToDetermine.matches("(?i)m.+k")){ return "Milk";}
        else if (itemToDetermine.matches("(?i)br.+d")){return "Bread";}
        else if (itemToDetermine.matches("(?i)c.+s")){return "Cookies";}
        else {return "Apples";}
    }

    public String getItem() {
        return item;
    }

    public String getPrice() {
        return price;
    }
}
