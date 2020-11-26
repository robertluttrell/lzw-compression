import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Compressor
{
    private String input;
    private ArrayList<Integer> output;
    private int tableSize;
    private HashMap<String, Integer> table;
    private int nextCode;

    public Compressor(String input, int tableSize)
    {
        this.input = input;
        this.tableSize = tableSize;
        this.output = new ArrayList<>();
        initializeTable();
        this.nextCode = 256;
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
        String s = "";
        int i = 0;
        String ch;

        while (i < input.length())
        {
            ch = String.valueOf(input.charAt(i));
            if (table.containsKey(s + ch))
                s = s + ch;
            else
            {
                output.add(table.get(s));
                table.put(s + ch, nextCode);
                s = ch;
                nextCode++;
            }
        }
        output.add(table.get(s));
        /*
        String p = String.valueOf(input.charAt(0));
        String c = "";

        for (int i = 1; i < input.length(); i++)
        {
            c = String.valueOf(input.charAt(i));

            if (table.containsKey(p + c))
                p = p + c;
            else
            {
                output.add(table.get(p));
                table.put(p + c, nextCode);
                p = c;
            }
            nextCode++;
        }
        output.add(table.get(p));
         */
    }



}
