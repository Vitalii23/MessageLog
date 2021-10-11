import org.ini4j.Ini;
import org.ini4j.IniPreferences;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.prefs.Preferences;

public class Test {

    public static void main(String[] args) {
        try{
            File file = new File("./Test/", "config.ini");
            File directory = file.getParentFile();
            if (null != directory){
                directory.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(file);
            Wini ini = new Wini(file);
            ini.put("sleepy", "age", 55);
            ini.put("sleepy", "weight", 45.6);
            fos.flush();
            fos.close();
            ini.store();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
