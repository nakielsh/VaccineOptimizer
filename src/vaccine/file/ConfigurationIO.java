package vaccine.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationIO {
    public void loadFromFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));

        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            //...
        }
        reader.close();

    }

}
