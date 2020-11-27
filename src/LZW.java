import org.apache.commons.cli.*;


public class LZW
{

    private static void compressFiles(String[] fileNames)
    {
        System.out.println("Compress:");
        for (String s : fileNames)
            System.out.println(s);
        /* TODO: iterative call to Compressor.compress(). Print stats */
    }

    private static void decompressFiles(String[] fileNames)
    {
        System.out.println("Decompress:");
        for (String s : fileNames)
            System.out.println(s);
        /* TODO: iterative call to Decompressor.decompress() */
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
