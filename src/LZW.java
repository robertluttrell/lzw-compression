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

        options.addOption(mode);
        HelpFormatter formatter = new HelpFormatter();

        try
        {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("mode"))
                if (!(line.getOptionValue("mode").equals("compress")))
                    formatter.printHelp("lzw", options);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


    }
}
