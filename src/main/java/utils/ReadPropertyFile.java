package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {

    public static Properties prop = new Properties();

    /**
     * readConfFile is reading env.properties File & loading it into prop object.
     */

    public static void readConfFile() {
        try {
            FileInputStream ip = new FileInputStream("src/main/resources/env.properties");
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
