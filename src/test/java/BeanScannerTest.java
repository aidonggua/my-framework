import mf.scan.BeanScanner;
import org.junit.Test;

import java.io.IOException;

public class BeanScannerTest {

    @Test
    public void testScan() throws IOException {
        new BeanScanner().scan("./");
    }
}
