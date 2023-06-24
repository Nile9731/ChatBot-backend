import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtility {
    public static Properties read(String fileName) throws IOException {
        FileInputStream fis =null;
        Properties prop=null;
        try{
            fis=new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis!=null){
                fis.close();}
        }
        return prop;
    }
}
