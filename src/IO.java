import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IO
{
    public static String readAllBytesFromFileAsString(String filePath)
    {
        String content = "";

        try
        {
            content = Files.readString(Path.of(filePath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return content;
    }

    public static byte[] readAllBytesFromFile(String filePath)
    {
        try
        {
            return Files.readAllBytes(Paths.get(filePath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
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
                i += 4;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return codes;
    }

    public static void writeBytesToFile(byte[] bytes, String filePath)
    {
        File outputFile = new File(filePath);
        try
        {
            OutputStream os = new FileOutputStream(outputFile);
            os.write(bytes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void writeStringToFile(String outputString, String filePath)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(outputString);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
