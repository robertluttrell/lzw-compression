import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Decompressor
{
    private List<Integer> input;
    private int tableSize;
    private String output;
    private HashMap<Integer, String> table;
    private int nextCode;

    public Decompressor(List<Integer> input, int tableSize)
    {
        this.input = input;
        this.tableSize = tableSize;
        this.nextCode = 256;
    }

    private void initializeTable()
    {
        table = new HashMap<>();
        for (int i = 0; i <= 255; i++)
        {
            char c = (char) i;
            table.put(i, String.valueOf(c));
        }
    }

    public void decompress()
    {
        initializeTable();
        int oldCode = input.get(0);
        output = table.get(oldCode);
        int i = 1;
        String s;
        String c = "";

        while (i < input.size())
        {
            int newCode = input.get(i);
            if (!(table.containsKey(newCode)))
            {
                s = table.get(oldCode);
                s = s + c;
            }

            else
                s = table.get(newCode);
            output += s;
            c = String.valueOf(s.charAt(0));
            table.put(nextCode, table.get(oldCode) + c);
            oldCode = newCode;
            nextCode++;
            i++;
        }
    }

    public String getOutput() { return this.output; }
}
