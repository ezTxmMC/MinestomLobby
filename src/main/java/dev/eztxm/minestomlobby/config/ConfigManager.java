package dev.eztxm.minestomlobby.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ConfigManager {
    private final String configPath;
    private final String configName;
    private final JSONObject config;

    public ConfigManager(String path, String configName) {
        this.configName = configName;
        this.configPath = path;
        File configFile;
        if (path != null) {
            File folder = new File(path);
            configFile = new File(path + "/" + configName);
            if (!folder.exists()) folder.mkdir();
        } else {
            configFile = new File(configName);
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                JSONObject velocity = new JSONObject();
                velocity.put("Secret", "");
                this.config = new JSONObject();
                this.config.put("Server-Address", "");
                this.config.put("Port", 25565);
                this.config.put("View-Distance", 12);
                this.config.put("Simulation-Distance", 8);
                this.config.put("Proxy", false);
                this.config.put("Proxy-Type", "Bungeecord");
                this.config.put("Velocity", velocity);
                saveConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            String configJson;
            if (path != null) {
                configJson = readFile(path + "/" + configName);
            } else {
                configJson = readFile(configName);
            }
            this.config = new JSONObject(configJson);
        }
    }

    public Object getValue(String key) {
        return this.config.get(key);
    }

    public void setValue(String key, Object value) {
        this.config.put(key, value);
        saveConfig();
    }

    public Object getValueFromJSONObject(String objectName, String key) throws JSONException {
        JSONObject jsonObject = this.config.optJSONObject(objectName);
        if (jsonObject == null) {
            throw new JSONException("JSONObject '" + objectName + "' does not exist in the config.");
        }
        return jsonObject.get(key);
    }

    public void addValueToList(String key, Object value) throws JSONException {
        JSONArray jsonArray = this.config.getJSONArray(key);
        jsonArray.put(value);
        saveConfig();
    }

    public void removeValueFromList(String key, Object value) throws JSONException {
        JSONArray jsonArray = this.config.getJSONArray(key);
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.getString(i).equals(value)) {
                jsonArray.remove(i);
                break;
            }
        }
        saveConfig();
    }

    private void saveConfig() {
        if (configPath != null) {
            try (FileWriter file = new FileWriter(configPath + "/" + configName)) {
                file.write(this.config.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (FileWriter file = new FileWriter(configName)) {
                file.write(this.config.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String readFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringBuilder.toString();
    }
}
