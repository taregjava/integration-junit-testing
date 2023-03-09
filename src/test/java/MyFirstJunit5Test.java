import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyFirstJunit5Test {

    @Test
    void myFirstTest(){

        String message = " 1+1 should be equals to 2";
        System.out.println(message);
        assertEquals(2, 1+1, message);
    }
}
