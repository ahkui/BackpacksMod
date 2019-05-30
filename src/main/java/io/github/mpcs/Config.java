package io.github.mpcs;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.lang.reflect.Type;

public class Config {
    private JsonObject data;
    private final String FILE;

    public Config(String file) {
        this.FILE = file;
    }

    public int getInt(String key) {
        return data.get(key).getAsInt();
    }

    public void setInt(String key, int value) {
        data.addProperty(key, value);
    }

    public String getString(String key) {
        return data.get(key).getAsString();
    }

    public void setString(String key, String value) {
        data.addProperty(key, value);
    }

    public boolean getBool(String key) {
        return data.get(key).getAsBoolean();
    }

    public void setBool(String key, boolean value) {
        data.addProperty(key, value);
    }

    public JsonObject getJsonObject(String key) {
        return data.get(key).getAsJsonObject();
    }

    public void setJsonObject(String key, JsonObject value) {
        data.add(key, value);
    }

    public JsonArray getJsonArray(String key) {
        return data.get(key).getAsJsonArray();
    }

    public void setJsonArray(String key, JsonArray value) {
        data.add(key, value);
    }

    public void load() {
        File f = new File("config/" + FILE + ".json");

        if (f.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                Gson gson = new Gson();
                Type listType = new TypeToken<JsonObject>() {}.getType();
                data = gson.fromJson(bufferedReader, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            save();
            load();
        }
    }

    public void save() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();

        File f = new File("config/");
        if (!f.exists()) f.mkdir();

        f = new File("config/" + FILE + ".json");
        if (f.exists()) f.delete();

        try {
            FileWriter writer = new FileWriter("config/" + FILE + ".json");
            writer.write(gson.toJson(data == null ? new JsonObject() : data));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean exists() {
        File f = new File("config/" + FILE + ".json");
        if(f.exists())
            return true;
        return false;
    }
}
