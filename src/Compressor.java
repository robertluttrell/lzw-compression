import jdk.jfr.Unsigned;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Compressor
{
    private String input;
    private int buffer;
    private int numBits;
    private ArrayList<Byte> output;
    private int maxTableSize;
    private HashMap<String, Integer> table;
    private int nextCode;
    private int numBitsInBuffer;

    public Compressor(String input, int maxTableSize)
    {
        this.input = input;
        this.maxTableSize = maxTableSize;
        this.output = new ArrayList<>();
        this.buffer = 0;
        this.numBitsInBuffer = 0;
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
        numBits = 9;
    }

    private void writeByteFromBuffer()
    {
        int byteInLSB = buffer >> (numBitsInBuffer - 8);
        int mask;

    }

    private void addCodeToBuffer(int code)
    {
        buffer <<= numBits;
        buffer += code;
        numBitsInBuffer += numBits;
    }


    private void addCodeToOutput(int code)
    {
        while (numBitsInBuffer > 8)
            writeByteFromBuffer();

        addCodeToBuffer(code);

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

            if (nextCode > Math.pow(2.0, numBits))
                numBits += 1;

            ch = String.valueOf(input.charAt(i));
            if (ch.equals("")) {
                i++;
                continue;
            }
            if (table.containsKey(s + ch))
                s = s + ch;
            else
            {
                addCodeToOutput(table.get(s));
                table.put(s + ch, nextCode);
                s = ch;
                nextCode++;
            }
            i++;
        }
        addCodeToOutput(table.get(s));
    }

    public List<Byte> getOutput() { return this.output; }
}
