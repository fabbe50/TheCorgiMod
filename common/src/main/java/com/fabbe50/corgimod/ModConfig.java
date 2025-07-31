package com.fabbe50.corgimod;


import com.fabbe50.corgimod.config.*;
import dev.architectury.platform.Platform;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ModConfig {
    public static ModConfig INSTANCE = new ModConfig();
    private static File configFile;

    private static final Map<String, IConfigOption<?, ?>> configOptions = new HashMap<>();

    public static void register() {
        configFile = new File(Platform.getConfigFolder().toFile(), "thecorgimod.properties");
        load(configFile);
    }

    public static File getConfigFile() {
        return configFile;
    }

    public static void load(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fis);

            for (String key : configOptions.keySet()) {
                IConfigOption<?, ?> config = configOptions.get(key);
                config.readData(properties);
            }

            fis.close();
        } catch (IOException e) {
            try {
                save(file);
                load(file);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void save(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, false);

        for (String key : configOptions.keySet()) {
            IConfigOption<?, ?> config = configOptions.get(key);
            config.writeData(fos);
        }

        fos.close();
    }

    static {
        addConfig(new BooleanOption("allowUraniumTNTBoosting", true));
        addConfig(new IntegerOption("maxWanderingDistance", 10, 1, 100));
        addConfig(new BooleanOption("redWireFrameWhenCorgiTooFarFromStayPosition", true));
        addConfig(new FloatOption("tamedCorgiMaxHealth", 40f, 1f, 500f));
        addConfig(new FloatOption("tamedCorgiAttackDamage", 8f, 1f, 500f));
        addConfig(new FloatOption("tamedCorgiMovementSpeed", 0.4f, 1f, 500f));
        addConfig(new FloatOption("wildCorgiMaxHealth", 16f, 1f, 500f));
        addConfig(new FloatOption("wildCorgiAttackDamage", 4f, 1f, 500f));
        addConfig(new FloatOption("wildCorgiMovementSpeed", 0.3f, 1f, 500f));
        addConfig(new IntegerOption("passiveCorgiSpawnWeight", 5, 1, 500));
        addConfig(new IntegerOption("creeperCorgiSpawnWeight", 25, 1, 500));
        addConfig(new IntegerOption("enderCorgiSpawnWeight", 8, 1, 500));
        addConfig(new IntegerOption("skeletonCorgiSpawnWeight", 25, 1, 500));
        addConfig(new IntegerOption("zombieCorgiSpawnWeight", 35, 1, 500));
        addConfig(new IntegerOption("zombieCorgiJockeySpawnChance", 205, 1, 1000));
        addConfig(new BooleanOption("doCorgiParticles", true));
        addConfig(new IntegerOption("businessCorgiVillagerDiscount", 50, 1, 100));
        addConfig(new IntegerOption("spreadLoveAbilityRange", 8, 1, 100));
        addConfig(new IntegerOption("spreadLoveAbilityMaxEntityCount", 16, 1, 500));
        addConfig(new IntegerOption("bodyguardCorgiMaxHealth", 20, 1, 500));
        addConfig(new IntegerOption("bodyguardCorgiTamedMaxHealth", 60, 1, 500));
        addConfig(new IntegerOption("bodyguardCorgiAttackDamage", 8, 1, 500));
        addConfig(new IntegerOption("bodyguardCorgiTamedAttackDamage", 12, 1, 500));
        addConfig(new BooleanOption("fabbe50CorgiDoRandomDrops", true));
        addConfig(new BooleanOption("fabbe50CorgiDoRandomDropEvents", true));
        addConfig(new FloatOption("pirateCorgiBoatSpeedModifier", 1.5f, 0f, 10f));
        addConfig(new DoubleOption("spyCorgiRange", 16d, 0d, 64d));
        addConfig(new IntegerOption("spyCorgiExposeTime", 5, 0, 1200));
        addConfig(new IntegerOption("farmerCorgiGrowthBoostRadius", 8, 1, 64));
        addConfig(new BreedingModeOption("breedingMode", BreedingModeOption.BreedingMode.PARENTS));
        addConfig(new NamingModeOption("namingMode", NamingModeOption.NamingMode.RANDOM_NAMES));
        addConfig(new BooleanOption("nameOnTame", true));
    }

    private static <T, R extends AbstractConfigListEntry<T>> void addConfig(IConfigOption<T, R> configOption) {
        configOptions.put(configOption.getKey(), configOption);
    }

    public static <T> IConfigOption<T, ?> getValue(String key) {
        return (IConfigOption<T, ?>) configOptions.get(key);
    }

    public static Map<String, IConfigOption<?, ?>> getConfigOptions() {
        return configOptions;
    }

    public static void writeData(FileOutputStream fos, String key, String value) throws IOException {
        fos.write((key + "=" + value).getBytes());
        fos.write("\n".getBytes());
    }

    public static boolean readBoolean(Properties properties, String key, boolean defaultValue) {
        return ((String)properties.computeIfAbsent(key, object -> String.valueOf(defaultValue))).equalsIgnoreCase("true");
    }

    public static float readFloat(Properties properties, String key, float defaultValue) {
        return Float.parseFloat((String) properties.computeIfAbsent(key, object -> String.valueOf(defaultValue)));
    }

    public static double readDouble(Properties properties, String key, double defaultValue) {
        return Double.parseDouble((String) properties.computeIfAbsent(key, object -> String.valueOf(defaultValue)));
    }

    public static int readInt(Properties properties, String key, int defaultValue) {
        return Integer.parseInt((String) properties.computeIfAbsent(key, object -> String.valueOf(defaultValue)));
    }

    public static String readString(Properties properties, String key, String defaultValue) {
        return (String) properties.computeIfAbsent(key, object -> defaultValue);
    }
}
