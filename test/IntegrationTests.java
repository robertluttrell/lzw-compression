import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class IntegrationTests
{
    @Test
    public void test01()
    {
        String s = "AAA";
        Compressor c = new Compressor(s, 4096);
        c.compress();
        List<Integer> codes = c.getOutput();
        Decompressor d = new Decompressor(codes, 4096);
        d.decompress();
        String decompressed = d.getOutput();
        Assert.assertEquals(s, decompressed);
    }
}
