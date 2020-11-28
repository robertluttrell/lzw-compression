import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Compressor
{
    private String input;
    private ArrayList<Integer> output;
    private int maxTableSize;
    private HashMap<String, Integer> table;
    private int nextCode;

    public Compressor(String input, int maxTableSize)
    {
        this.input = input;
        this.maxTableSize = maxTableSize;
        this.output = new ArrayList<>();
    }

    private void initializeTable()
    {
        table = new HashMap<>();
        for (int i = 0; i <= 255; i++)
        {
            char c = (char) i;
            table.put(String.valueOf(c), i);
        }
        nextCode = 256;
    }

    public void compress()
    {
        String s = "";
        int i = 0;
        String ch;

        initializeTable();

        while (i < input.length())
        {
            if (table.size() >= maxTableSize)
                initializeTable();

            ch = String.valueOf(input.charAt(i));
            if (ch.equals("")) {
                i++;
                continue;
            }
            if (table.containsKey(s + ch))
                s = s + ch;
            else
            {
                output.add(table.get(s));
                table.put(s + ch, nextCode);
                s = ch;
                nextCode++;
            }
            i++;
        }
        output.add(table.get(s));
    }

    public List<Integer> getOutput() { return this.output; }
}
