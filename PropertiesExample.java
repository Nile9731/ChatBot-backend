import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesExample {
    public static void main(String[] args) throws FileNotFoundException {
       //properties class are used to read values or retrieve values from properties file
        //key value format it is represented
        Properties properties= new Properties();
        FileInputStream fileInputStream= null;
        try{
            fileInputStream=new FileInputStream("project.properties");

            properties.load(fileInputStream);
            String serverip=properties.getProperty("serverIP");

            int port= Integer.parseInt(properties.getProperty("port"));
            System.out.println(serverip+" "+port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
