import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IO
{
    public static String readAllBytesFromFile(String filePath)
    {
        String content = "";

        try
        {
            content = new String(Files.readAllBytes(Paths.get(filePath)));

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return content;
    }

    public static List<Integer> readCodesFromFile(String filePath)
    {
        List<Integer> codes = new ArrayList<>();

        try
        {
            byte[] rawBytes = Files.readAllBytes(Paths.get(filePath));
            int i = 0;
            while (i < rawBytes.length)
            {
                byte[] fourBytes = Arrays.copyOfRange(rawBytes, i, i + 4);
                int newInt = ByteBuffer.wrap(fourBytes).getInt();
                codes.add(newInt);
                System.out.println(newInt);
                i += 4;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return codes;
    }

    public static void writeCodesToFile(List<Integer> codes, String filePath)
    {
        String outputPath = filePath + ".compress";
        try
        {
            OutputStream os = new FileOutputStream(new File(outputPath));
            for (int i : codes)
            {
                byte[] bytes = ByteBuffer.allocate(4).putInt(i).array();
                System.out.println(Arrays.toString(bytes));
                os.write(bytes);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeStringToFile(String outputString, String filePath)
    {
        String outputPath = filePath + ".decompress";
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
            writer.write(outputString);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
