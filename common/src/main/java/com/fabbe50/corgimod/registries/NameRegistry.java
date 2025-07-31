package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.config.NamingModeOption;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameRegistry {
    private static NameRegistry INSTANCE;

    private static final String[] names = new String[] {"data/thecorgimod/corgi_names/female-dog-names.json", "data/thecorgimod/corgi_names/male-dog-names.json"};
    private static final List<NameData> nameData = new ArrayList<>();

    public static void init() {
        INSTANCE = new NameRegistry();
        for (String nameFile : names) {
            try {
                InputStream inputStream = getFileFromResource(nameFile);
                String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                Gson converter = new Gson();
                Type type = new TypeToken<List<String>>(){}.getType();
                List<String> list = converter.fromJson(jsonString, type);
                for (String name : list) {
                    nameData.add(new NameData(name, nameFile.contains("female")));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static InputStream getFileFromResource(String fileName) {
        ClassLoader classLoader = INSTANCE.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    public List<NameData> getNameData() {
        return nameData;
    }

    static Random random = new Random();
    public static String getRandomName(boolean female) {
        List<NameData> nameDataList = INSTANCE.getNameData();
        int size = nameDataList.size();
        while (true) {
            NameData nameData1 = nameDataList.get(random.nextInt(size));
            if (female && nameData1.female()) {
                return nameData1.name();
            } else if (!female && !nameData1.female()) {
                return nameData1.name();
            }
        }
    }

    public static void nameCorgi(Corgi corgi) {
        NamingModeOption.NamingMode activeNamingMode = ModConfig.<NamingModeOption.NamingMode>getValue("namingMode").getValue();
        if (activeNamingMode.equals(NamingModeOption.NamingMode.DEFAULT_NAMES)) {
            corgi.setCustomName(Component.literal(corgi.getVariantType().getFormattedName()));
        } else if (activeNamingMode.equals(NamingModeOption.NamingMode.RANDOM_NAMES)) {
            corgi.setCustomName(Component.literal(NameRegistry.getRandomName(corgi.isFemale())));
        }
    }

    public record NameData(String name, boolean female) {
    }
}
