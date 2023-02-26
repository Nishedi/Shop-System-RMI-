package servermain;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

public class ReadSave {
    public static ArrayList<String> read(URL url) {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader ReadF = null;
            ReadF = new BufferedReader(new InputStreamReader(url.openStream()));
            String numstring = ReadF.readLine();
            try {
                while (numstring != null) {
                    list.add(numstring);
                    numstring = ReadF.readLine();
                }
            } catch (Exception er) {
                return null;
            }
            ReadF.close();
        }catch (Exception x) {
            return null;
        }
        return list;
    }
    public static void save(ArrayList<String> data, String filename) throws IOException {
        PrintWriter zapis = new PrintWriter(filename);
        for(String s:data){
            zapis.println(s);
        }
        zapis.close();
    }
}
