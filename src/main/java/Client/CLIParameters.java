package Client;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Parameters(separators = ":") //space works too
public class CLIParameters {

    @Parameters(commandDescription = "Get executes a HTTP GET request for a given URL.")
    public static class GET
    {

        @Parameter(description = "URL.", required = true)
        private String URL;

        @Parameter(names = "-v", description = "Prints the detail of the response such as protocol, status, and headers.")
        private Boolean verbose = false;

        @DynamicParameter(names = "-h", description = "Associates headers to HTTP Request with the format 'key:value'.", assignment = ":")
        private Map<String, String> headers = new HashMap<String, String>();

        @Parameter(names = "-o", description = "Prints the detail of the response to the file.")
        private String filename;

        public String getURL()
        {
            return URL;
        }

        public Boolean getVerbose()
        {
            return verbose;
        }
        public Map<String, String> getHeaders()
        {
            return headers;
        }
        public String getFilename()
        {
            return filename;
        }


        @Override
        public String toString() {
            return "\n URL = " + URL +
                    "\n Verbose = " + verbose +
                    "\n Headers = " + headers
                    ;
        }

    }



    @Parameters(commandDescription = "Post executes a HTTP POST request for a given URL with inline data or from file.")
    public static class POST
    {
        @Parameter(description = "Prints a json string based on the given URL.", required = true)
        private String URL;

        @Parameter(names = "-v", description = "Prints the detail of the response such as protocol, status, and headers.")
        private Boolean verbose = false;

        @DynamicParameter(names = "-h", description = "Associates headers to HTTP Request with the format 'key:value'.", assignment = ":")
        private Map<String, String> headers = new HashMap<String, String>();

        @Parameter(names = "-d", description = "Associates an inline data to the body HTTP POST request.")
        private String data;

        @Parameter(names = "-f", description = "Associates the content of a file to the body HTTP POST request.")
        private File file;

        @Parameter(names = "-o", description = "Prints the detail of the response to the file.")
        private String filename;

        public String getURL()
        {
            return URL;
        }
        public Boolean getVerbose()
        {
            return verbose;
        }
        public Map<String, String> getHeaders()
        {
            return headers;
        }

        public String getData()
        {
            return data;
        }

        public File getFile()
        {
            return file;
        }
        public String getFilename()
        {
            return filename;
        }


        @Override
        public String toString() {
            return "\n URL = " + URL +
                    "\n Verbose = " + verbose +
                    "\n Headers = " + headers +
                    "\n Data = " + data +
                    "\n File = " + file
                    ;
        }
    }

    @Parameters(commandDescription = "httpc is a curl-like application but supports HTTP protocol only.")
    public static class Help
    {
        @Parameter(names = "get", description = "Get executes a HTTP GET request for a given URL.")
        private GET getValue;
        @Parameter(names = "post", description = "Post executes a HTTP POST request for a given URL with inline data or from file.")
        private POST postValue;


    }




}