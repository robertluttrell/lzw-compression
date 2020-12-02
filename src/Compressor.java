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
    private byte[] outputArr;
    private int maxTableSize;
    private HashMap<String, Integer> table;
    private int nextCode;
    private int numBitsInBuffer;
    private List<Integer> rawCodes;

    public Compressor(String input, int maxTableSize)
    {
        this.input = input;
        this.maxTableSize = maxTableSize;
        this.output = new ArrayList<>();
        this.buffer = 0;
        this.numBitsInBuffer = 0;
        this.rawCodes = new ArrayList<>();
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
        int mask = (int) Math.pow(2, 8) - 1;
        byte newByte = (byte) (byteInLSB & mask);
        //TODO: this is the wrong operation
        buffer -= (newByte << (numBitsInBuffer - 8));
        numBitsInBuffer -= 8;

        output.add(newByte);
    }

    private void writeLastByteFromBuffer()
    {
        int mask = (int) Math.pow(2, 8) - 1;
        int lastBitsMSBByte = buffer << (8 - numBitsInBuffer);
        byte lastByte = (byte) (lastBitsMSBByte & mask);
        output.add(lastByte);
    }

    private void addCodeToBuffer(int code)
    {
        buffer <<= numBits;
        buffer += code;
        numBitsInBuffer += numBits;
    }


    private void addCodeToOutput(int code)
    {
        rawCodes.add(code);
        while (numBitsInBuffer > 8)
            writeByteFromBuffer();

        addCodeToBuffer(code);
    }

    private void emptyBufferToOutput()
    {
        while (numBitsInBuffer > 8)
            writeByteFromBuffer();

        writeLastByteFromBuffer();
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
        emptyBufferToOutput();
        outputArr = LZWUtils.byteListToByteArr(output);
    }


    public List<Byte> getOutput() { return this.output; }
    public byte[] getOutputArr() { return this.outputArr; }
}
