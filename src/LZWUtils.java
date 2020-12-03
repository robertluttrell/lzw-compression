import java.util.ArrayList;
import java.util.List;

public class LZWUtils
{
    public static byte[] byteListToByteArr(List<Byte> byteList)
    {
        byte[] byteArr = new byte[byteList.size()];

        for (int i = 0; i < byteList.size(); i++)
            byteArr[i] = byteList.get(i);

        return byteArr;
    }

    public static List<Byte> byteArrToByteList(byte[] byteArr)
    {
        List<Byte> byteList = new ArrayList<>();

        for (byte b : byteArr)
            byteList.add(b);

        return byteList;
    }
}
