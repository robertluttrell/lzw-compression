import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class CompressorTests
{
    @Test
    public void test01()
    {
        String s = "BABAABAAA";
        Compressor c = new Compressor(s, 4096);
        c.compress();
        List<Integer> expected = Arrays.asList(66, 65, 256, 257, 65, 260);
        Assert.assertEquals(expected, c.getOutput());
    }

    @Test
    public void test02()
    {
        String s = "AAA";
        Compressor c = new Compressor(s, 4096);
        c.compress();
        System.out.println(c.getOutput());
    }
}
