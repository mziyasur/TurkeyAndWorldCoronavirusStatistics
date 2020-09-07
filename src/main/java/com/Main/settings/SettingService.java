package com.Main.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class SettingService {
    private final File SETTINGS_FILE = new File("settings.json");
    private Gson gson = new GsonBuilder().create();

    public SettingModel getConfiguration() throws Exception{
        if(!SETTINGS_FILE.exists()){
            createSettingsFile();
        }
    return getConfigurationFromFile();
    }

    private SettingModel getConfigurationFromFile() throws IOException {
        SettingModel settingModel = new SettingModel();
        try(Reader reader = new FileReader(SETTINGS_FILE)){
            return gson.fromJson(reader,SettingModel.class);
        }
    }

    private void createSettingsFile() throws IOException {
        SettingModel settingModel = new SettingModel();
        try(Writer writer = new FileWriter(SETTINGS_FILE,false)){
            gson.toJson(settingModel,writer);
        }
    }
}
