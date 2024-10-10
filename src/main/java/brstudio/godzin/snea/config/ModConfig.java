package brstudio.godzin.snea.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ModConfig {
    public static Configuration config;


    // Setar valores a configurar

    //

    private static final String CONFIG_FILE = "config/snea.cfg";

    public static void loadConfig() {
        config = new Configuration(new File(CONFIG_FILE));
        try {
            config.load();
        } catch (Exception e) {
            System.out.printf("Error loading config file: %s\n", CONFIG_FILE);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
