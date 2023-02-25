package com.fabbe50.corgimod.handlers;

import com.fabbe50.corgimod.CorgiMod;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NameHandler {
    private final String[] names = new String[] {"data/corgimod/names/female-dog-names.json", "data/corgimod/names/male-dog-names.json"};
    private final List<NameData> nameData = new ArrayList<>();

    public void init() {
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

    private InputStream getFileFromResource(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
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
        List<NameData> nameDataList = CorgiMod.getNameHandler().getNameData();
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

    record NameData(String name, boolean female) {
    }
}
