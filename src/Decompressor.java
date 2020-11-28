import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Decompressor
{
    private List<Integer> input;
    private int maxTableSize;
    private String output;
    private HashMap<Integer, String> table;
    private int nextCode;

    public Decompressor(List<Integer> input, int maxTableSize)
    {
        this.input = input;
        this.maxTableSize = maxTableSize;
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
    }

    public void decompress()
    {
        initializeTable();
        int oldCode = input.get(0);
        StringBuilder buffer = new StringBuilder();
        buffer.append(table.get(oldCode));
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
            buffer.append(s);
            c = String.valueOf(s.charAt(0));
            table.put(nextCode, table.get(oldCode) + c);
            oldCode = newCode;
            nextCode++;
            i++;
        }
        output = buffer.toString();
    }

    public String getOutput() { return this.output; }
}
