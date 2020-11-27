import org.apache.commons.cli.*;

import java.util.List;


public class LZW
{

    private static void compressFiles(String[] fileNames)
    {
        for (String fileName : fileNames)
        {
            String input = IO.readAllBytesFromFile(fileName);
            Compressor c = new Compressor(input, 4096);
            c.compress();
            IO.writeCodesToFile(c.getOutput(), fileName);

        }
    }

    private static void decompressFiles(String[] fileNames)
    {
        for (String fileName : fileNames)
        {
            List<Integer> input = IO.readCodesFromFile(fileName);
            Decompressor d = new Decompressor(input, 4096);
            d.decompress();
            IO.writeStringToFile(d.getOutput(), fileName);
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
