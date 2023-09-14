package Utils;

import org.revature.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Resources {
/*    public static String getSqlFile(String fileName, ClassLoader classLoader) {
        InputStream inputStream = classLoader.getResourceAsStream("/" + fileName);
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
        return fileName;
    }*/

    public static InputStream getFile(String fileName) {
        return Resources.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static String readResource(String fileName, ClassLoader classLoader) throws IOException {
        try (InputStream is = classLoader.getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
