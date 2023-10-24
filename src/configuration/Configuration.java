package configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    public static ConfigurationData parseConfigFile(String filePath) throws FileNotFoundException, IOException {
        Properties backupWaveProperties = new Properties();
        backupWaveProperties.load(new FileInputStream(filePath));

        int inPort = Integer.parseInt(backupWaveProperties.getProperty("inPort"));
        int outPort = Integer.parseInt(backupWaveProperties.getProperty("outPort"));
        String banner = backupWaveProperties.getProperty("banner");
        String networkInterface = backupWaveProperties.getProperty("network_interface");

        return new ConfigurationData(inPort, outPort, banner, networkInterface);
    }
}
