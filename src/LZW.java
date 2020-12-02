import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;


public class LZW
{

    private static void printStats(String originalPath, String compressedPath)
    {
        File origFile = new File(originalPath);
        File compressedFile = new File(compressedPath);

        long origLength = origFile.length();
        long compressedLength = compressedFile.length();

        System.out.println("Compressed file: " + originalPath);
        System.out.println("Original size: " + origLength + " bytes");
        System.out.println("Compress size: " + compressedLength + " bytes");
        System.out.println("Compression ratio: " + (double) compressedLength / origLength);
    }

    private static void compressFiles(String[] fileNames)
    {
        for (String fileName : fileNames)
        {
            String input = IO.readAllBytesFromFileAsString(fileName);
            Compressor c = new Compressor(input, 409600000);
            c.compress();
            IO.writeBytesToFile(c.getOutputArr(), fileName + ".compress");
            printStats(fileName, fileName + ".compress");
        }
    }

    private static void decompressFiles(String[] fileNames)
    {
        for (String fileName : fileNames)
        {
            byte[] inputArr = IO.readAllBytesFromFile(fileName);
            List<Byte> inputList = LZWUtils.byteArrToByteList(inputArr);
            Decompressor d = new Decompressor(inputList, 409600000);
            d.decompress();
            IO.writeStringToFile(d.getOutput(), fileName + ".decompress");
        }
    }

    public static void main(String[] args)
    {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        Option modeOption = Option.builder("mode")
                .argName("mode")
                .desc("Set the mode to either compress or decompress")
                .hasArg()
                .required()
                .build();

        Option inputFiles = Option.builder("files")
                .argName("input files")
                .desc("File(s) to be compressed or decompressed")
                .hasArgs()
                .required()
                .build();

        options.addOption(modeOption);
        options.addOption(inputFiles);
        HelpFormatter formatter = new HelpFormatter();

        try
        {
            CommandLine line = parser.parse(options, args);
            String mode = line.getOptionValue("mode");
            String[] files = line.getOptionValues("files");

            if (mode.equals("compress"))
                compressFiles(files);
            else if (mode.equals("decompress"))
                decompressFiles(files);
            else
                formatter.printHelp("lzw", options);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            formatter.printHelp("lzw", options);
        }
    }
}
