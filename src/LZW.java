import org.apache.commons.cli.*;


public class LZW
{

    public static void main(String[] args)
    {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        Option mode = Option.builder("mode")
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

        options.addOption(mode);
        options.addOption(inputFiles);
        HelpFormatter formatter = new HelpFormatter();

        try
        {
            CommandLine line = parser.parse(options, args);
            if (!(line.getOptionValue("mode").equals("compress") || line.getOptionValue("mode").equals("decompress")))
                formatter.printHelp("lzw", options);
        }
        catch (ParseException e)
        {
            formatter.printHelp("lzw", options);
        }


    }
}
