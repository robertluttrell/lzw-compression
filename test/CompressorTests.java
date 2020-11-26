import org.junit.Test;
import org.junit.Assert;

public class CompressorTests
{
    @Test
    public void test01()
    {
        String s = "BABAABAAA";
        Compressor c = new Compressor(s, 4096);
        c.compress();
        System.out.println("");
    }
}
