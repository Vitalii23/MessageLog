package Logging;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogWriter {

    static Logger logger = Logger.getLogger("MyLog");
    static FileHandler fileHandler;

    public void write(Exception e){
        File file = new File("./Exception/error.log");
        File directory = file.getParentFile();
        if (null != directory){
            directory.mkdir();
        }

        try {
            fileHandler = new FileHandler("./Exception/error.log");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        logger.log(Level.WARNING, "Error:" + e.getMessage() + "\n");
    }
}
