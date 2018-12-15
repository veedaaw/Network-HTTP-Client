package Client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;

public class ConsoleParser {

    final CLIParameters.GET get = new CLIParameters.GET();
    final CLIParameters.POST post = new CLIParameters.POST();
    final CLIParameters.Help help = new CLIParameters.Help();
    private static ConsoleParser demo;



    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {

       demo = new ConsoleParser();
       demo.handleInputArgs(args);



    }

    void handleInputArgs(String args[]) throws MalformedURLException, URISyntaxException {


        JCommander jCommander = new JCommander().newBuilder().addCommand("get", get).addCommand("post", post)
                .addCommand("help", help).build();
        jCommander.setProgramName("httpc");

        try {
            jCommander.parse(args);
            //helpCommander.parse(args);
        } catch (ParameterException exception) {
            System.out.println(exception.getMessage());
            showUsage(jCommander);
        }

        demo.run(args, jCommander);
    }

   void showUsage(JCommander jCommander) {
       jCommander.usage();
       System.exit(0);
    }

    void run(String[] args, JCommander jCommander) throws MalformedURLException, URISyntaxException {
        System.out.println("Running ...");

        if(jCommander.getParsedCommand().equals("get"))
        {
            HttpClient connection = new HttpClient("get", get.getURL(), 5050, get.getVerbose(), get.getHeaders(), null, get.getFilename());
        }
        if(jCommander.getParsedCommand().equals("post"))
        {
           HttpClient connection = new HttpClient("post", post.getURL(), 5050, post.getVerbose(), post.getHeaders(), post.getData(), post.getFilename());
        }

        if(jCommander.getParsedCommand().equals("help"))
        {
            System.out.println(jCommander.getCommandDescription("help"));
            System.out.println("Usage:\n" +
                    "    httpc command [arguments]\n" +
                    "The commands are:\n" +
                    "    get     executes a HTTP GET request and prints the response.\n" +
                    "    post    executes a HTTP POST request and prints the response.\n" +
                    "    help    prints this screen. \n" +
                    "Use \"httpc help [command]\" for more information about a command.");

            
        }


    }
}

