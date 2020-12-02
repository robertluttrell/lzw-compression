import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Decompressor
{
    private List<Byte> input;
    private int numBytesInFile;
    private List<Byte> outputList;
    private int buffer;
    private int numBitsInBuffer;
    private int numBytesRead;
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
        this.numBytesRead = 0;
        this.outputList = new ArrayList<>();
        this.numBytesInFile = input.size();
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

    private void addCodeToBuffer()
    {
        buffer <<= 8;
        int oneByteMask = 0xFF;

        if (numBytesRead < numBytesInFile)
            buffer += (input.get(numBytesRead) & oneByteMask);

        numBytesRead++;
        numBitsInBuffer += 8;
    }

    private int getCodeFromBuffer()
    {
        int code = buffer >> (numBitsInBuffer - numBits);
        buffer -= (code << (numBitsInBuffer - numBits));
        numBitsInBuffer -= numBits;
        return code;
    }

    private int getCode()
    {
        while (numBitsInBuffer < numBits)
            addCodeToBuffer();

        return getCodeFromBuffer();
    }

    public void decompress()
    {
        initializeTable();
//        int oldCode = input.get(0);
        int oldCode = getCode();
        StringBuilder builder = new StringBuilder();
        builder.append(table.get(oldCode));
        int i = 1;
        String s;
        String c = table.get(oldCode);

        while (numBytesRead < numBytesInFile)
        {
            if (table.size() >= maxTableSize)
                initializeTable();

            if (nextCode >= Math.pow(2.0, numBits))
                numBits += 1;

            int newCode = getCode();
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
