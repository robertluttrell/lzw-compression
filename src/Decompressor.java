import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Decompressor
{
    private List<Byte> input;
    private int buffer;
    private int numBitsInBuffer;
    private int numBits;
    private int maxTableSize;
    private String output;
    private HashMap<Integer, String> table;
    private int nextCode;

    public Decompressor(List<Byte> input, int maxTableSize)
    {
        this.input = input;
        this.maxTableSize = maxTableSize;
        this.buffer = 0;
        this.numBitsInBuffer = 0;
    }

    private void initializeTable()
    {
        table = new HashMap<>();
        for (int i = 0; i <= 255; i++)
        {
            char c = (char) i;
            table.put(i, String.valueOf(c));
        }
        nextCode = 256;
        numBits = 9;
    }

    public void decompress()
    {
        initializeTable();
        int oldCode = input.get(0);
        StringBuilder builder = new StringBuilder();
        builder.append(table.get(oldCode));
        int i = 1;
        String s;
        String c = table.get(oldCode);

        while (i < input.size())
        {
            if (table.size() >= maxTableSize)
                initializeTable();

            int newCode = input.get(i);
            if (!(table.containsKey(newCode)))
            {
                s = table.get(oldCode);
                s = s + c;
            }

            else
                s = table.get(newCode);
            builder.append(s);
            c = String.valueOf(s.charAt(0));
            table.put(nextCode, table.get(oldCode) + c);
            oldCode = newCode;
            nextCode++;
            i++;
        }
        output = builder.toString();
    }

    public String getOutput() { return this.output; }
}
