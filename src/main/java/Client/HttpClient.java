package Client;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.beust.jcommander.Parameter;
import static java.util.Arrays.asList;
import static java.util.Arrays.parallelSetAll;

public class HttpClient {

    private Socket socket = null;
    private BufferedReader in = null;
    private OutputStream  out = null;
    private String request;
    private String header;
    private String all_headers="";

 public HttpClient(String method, String _url, int port, boolean verbose, Map<String, String> _headers, String data, String filename) throws MalformedURLException {

        //URL decoding
        URL url = new URL(_url);
        String host = url.getHost();
        String path = url.getPath();
        String query = url.getQuery();
        String file = url.getFile();
        //
        //
        //
        // System.out.println(file);

        //Associate with headers -h
        ArrayList<String> header= new ArrayList<String>();

        for (Map.Entry<String, String> entry : _headers.entrySet()) {
          header.add(entry.getKey()+": "+entry.getValue());
        }

        for(String entry: header)
        {
            all_headers += entry+"\r\n";
        }

        //Establish a socket connection
        try {
            socket = new Socket(host, port);
            System.out.println("Socket Connected");
            out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));

            if (method.toUpperCase().equals("GET"))

            {
                request = "GET " + path+"?"+query + " HTTP/1.0\r\n"
                        + "Host: " + host+"\r\n" + all_headers+ "\r\n\r\n" ;
            } else if (method.toUpperCase().equals("POST")) {
                request = "POST " + path+"?"+query+ " HTTP/1.0\r\n"
                        + "Host: " + host+"\r\n" + all_headers+ "\r\n\r\n";
            } else {
                System.out.println("HTTP method is not valid");
            }

            out.write(request.getBytes());

            out.flush();


            // read server response
            String line;
            if (verbose)
            {
                while((line = in.readLine()) != null)
                {
                    System.out.println(line);
                 }

            }

            if (!verbose & (query!=null))
            {
                StringBuilder stringBuilder = new StringBuilder("UTF8");
                try {
                    String ls = System.getProperty("line.separator");

                    while ((line = in.readLine()) != null) {
                        stringBuilder.append(line).append(ls);

                    }

                } catch (IOException e) {

                    System.err.println(e.getMessage());
                    System.exit(1);

                }
                String res = stringBuilder.substring(stringBuilder.indexOf("{"));
                System.out.println(res);


            }

            else
                {

                while ((line = in.readLine()) != null)
                {
                    System.out.println(line);
                }
                }

             if (filename!=null)
             {
                 File newFile = new File(filename);
                 BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
                 StringBuilder stringBuilder = new StringBuilder();
                 try {
                     String ls = System.getProperty("line.separator");

                     while ((line = in.readLine()) != null) {
                         stringBuilder.append(line).append(ls);

                     }

                 } catch (IOException e) {

                     System.err.println(e.getMessage());
                    // System.exit(1);

                 }
                 bufferedWriter.write(stringBuilder.toString());
                 bufferedWriter.flush();

            }

            in.close();
            out.close();
            socket.close();
             }

        catch(IOException e)
            {
                e.printStackTrace();
            }


    }

}