import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Joe on 2/22/16.
 */
public class ParserTest {
    String inputToTest = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016";
    @Test
    public void testGetFoodType() throws Exception {
        String actual = Parser.getFoodType(inputToTest);
        assertTrue("getFoodType() failed, actual: "+actual,Parser.getFoodType(inputToTest).equals("Milk"));
        System.out.println("getFoodType() Passed");
    }

    @Test
    public void testGetPrice() throws Exception {
        assertTrue("getPrice() failed",Parser.getPrice(inputToTest).equals("3.23"));
        System.out.println("getPrice() Passed");
    }

    @Test
    public void testGetExpiration() throws Exception {
        assertTrue("getExpiration() failed",Parser.getExpiration(inputToTest).equals("1/25/2016"));
        System.out.println("getExpiration() Passed");
    }
    //ITEM.PARSEITEMSTRING?
}