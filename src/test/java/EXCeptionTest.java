import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

public class EXCeptionTest {

    @ExtendWith(IgnoreIOException.class)
    @Test
    public void firstTest() throws IOException{
        throw new IOException("IO Exception");
    }
    @Test
    public void secondTest() throws IOException{
        throw new IOException("My IO Exception");
    }
}
