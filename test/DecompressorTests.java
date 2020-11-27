import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DecompressorTests
{
    @Test
    public void test01()
    {
        List<Integer> input = Arrays.asList(66, 65, 256, 257, 65, 260);
        Decompressor d = new Decompressor(input, 4096);
        d.decompress();
        Assert.assertEquals("BABAABAAA", d.getOutput());
    }
}
