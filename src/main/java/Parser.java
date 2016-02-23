import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Joe on 2/22/16.
 */
public class Parser {
    String input;
    static ArrayList<String> lineList = new ArrayList<String>();
    static ArrayList<Item> itemList = new ArrayList<Item>();
    static int numItems,numErrors;

    public Parser() throws Exception {
        input = Main.readRawDataToString();
        generateLineList(input);
        numItems = lineList.size();
        deleteErrors();
        numErrors = numItems - lineList.size();
        populateItemList();
        printer();
    }

    private static void generateLineList(String input) {
        String[] arrayOfItems = input.split("##");
        for (String x : arrayOfItems) {
            lineList.add(x);
        }
    }

    private static void deleteErrors() {
        for (int x = 0; x < lineList.size(); x++) {
            if (!checkValidity(lineList.get(x))) {
                lineList.remove(x);
            }
        }
    }

    private static boolean checkValidity(String itemLineToCheck) {
        if (itemLineToCheck.matches("(?i)name\\W(m.+k|br.+d|c.+s|a.+s)\\Wprice\\W\\d+\\.\\d\\d\\Wtype\\Wfood\\Wex.+tion\\W\\d{1,2}\\/\\d\\d\\/\\d{4}")) {
            return true;
        }
        return false;
    }

    private static void populateItemList(){
        for(String line:lineList){
            addItem(line);
        }
    }

    private static void addItem(String itemLine) {
        String item = getFoodType(itemLine);
        String price = getPrice(itemLine);
        String expiration = getExpiration(itemLine);
        itemList.add(new Item(item, price, expiration));
    }

    public static String getFoodType(String line) {
        Matcher matcher = Pattern.compile("(?i)\\w+(?=\\Wprice)").matcher(line);
        while (matcher.find()) {
            if (matcher.group().length() != 0) {
                return matcher.group().trim();
            }
        } return null;
    }

    public static String getPrice(String line) {
        Matcher matcher = Pattern.compile("\\d+\\.\\d\\d").matcher(line);
        while (matcher.find()) {
            if (matcher.group().length() != 0) {
                return matcher.group().trim();
            }
        } return null;
    }

    public static String getExpiration(String line) {
        Matcher matcher = Pattern.compile("\\d{1,2}\\/\\d\\d\\/\\d{4}").matcher(line);
        while (matcher.find()) {
            if (matcher.group().length() != 0) {
                return matcher.group().trim();
            }
        }
        return null;
    }

    private static void printer(){
        printOccurrences("Milk");
        printOccurrences("Bread");
        printOccurrences("Cookies");
        printOccurrences("Apples");
        System.out.format("%nErrors \t\tseen: %s times",numErrors);
    }

    private static void printOccurrences(String itemName){
        int itemCount = getItemCount(itemName);
        System.out.format("name:\t%s\t\tseen: %d times\n" +
                "============= \t \t =============",itemName,itemCount);
        System.out.println(getBigPriceStr(itemName)+"\n");
    }
    private static int getItemCount(String itemName){
        int itemCount=0;
        for(Item item:itemList){
            if(item.getItem().equals(itemName)){itemCount++;}
        }
        return itemCount;
    }
    private static int getPriceCount(String itemName, String price) {
        int priceCount = 0;
        for (Item item : itemList) {
            if (item.getItem().equals(itemName) && item.getPrice().equals(price)) {
                priceCount++;
            }
        }
        return priceCount;
    }
    private static String getBigPriceStr(String itemName){
        ArrayList<String> pricesCounted = new ArrayList<String>(); pricesCounted.add(" "); String stringToReturn="";
        for(Item item:itemList){
            if(item.getItem().equals(itemName)&&!pricesCounted.contains(item.getPrice())){
                String priceLine = String.format("%n%s\n-------------\t\t -------------",getPriceString(itemName,item.getPrice()));
                stringToReturn+= priceLine;
                pricesCounted.add(item.getPrice());
            }
        }
        return stringToReturn;
    }
    private static String getPriceString(String itemName, String price){
        int count=getPriceCount(itemName,price);
        return String.format("Price:\t"+price+"\t\tseen: %s times",price,count);
    }

}
