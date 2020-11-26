import java.nio.file.Files;
import java.util.HashMap;

public class Compressor
{
    private byte[] bytes;
    private int tableSize;
    private HashMap<String, Integer> table;

    public Compressor(byte[] bytes, int tableSize)
    {
        this.bytes = bytes;
        this.tableSize = tableSize;
        initializeTable();
    }

    private void initializeTable()
    {
        table = new HashMap<>();
        for (int i = 0; i <= 255; i++)
        {
            char c = (char) i;
            table.put(String.valueOf(c), i);
        }
    }

    public void compress()
    {

    }



}
