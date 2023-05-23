package net.ckranz.mc.mcapi.filesystem;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;


public class McFile {
    private final File file;
    private final YamlConfiguration yamlFile;
    private static final HashMap<File, McFile> files = new HashMap<>();


    private McFile(File file) {
        this.file = file;
        try {
            yamlFile = YamlConfiguration.loadConfiguration((new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_16)));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        files.put(file, this);
    }

    public static McFile create(String path) {
        File tempFile = new File(path);
        if(!tempFile.exists()) {
            try {
                tempFile.getParentFile().mkdirs();
                tempFile.createNewFile();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(files.containsKey(tempFile)) {
            return files.get(tempFile);
        } else {
            return new McFile(tempFile);
        }
    }

    public static McFile create(String name, McDirectory parent) {
        return create(parent.getFile().getAbsolutePath() + "/" + name);
    }


    public String getName() {
        return file.getName();
    }

    public String getPath() {
        return file.getAbsolutePath();
    }

    public McDirectory getParent() {
        return McDirectory.create(file.getParent());
    }

    public File getFile() {
        return file;
    }

    public void delete() {
        file.delete();
        files.remove(file);
    }


    public HashMap<String, Object> read() {
        HashMap<String, Object> content = new HashMap<>();
        for(String location : yamlFile.getKeys(false)) {
            content.put(location, yamlFile.get(location));
        }
        return content;
    }

    public Object read(String location) {
        return yamlFile.get(location);
    }

    public void write(HashMap<String, Object> content) {
        for(String location : content.keySet()) {
            write(location, content.get(location));
        }
    }

    public void write(String location, Object content) {
        yamlFile.set(location, content);
        try {
            yamlFile.save(file);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(String location) {
        write(location, null);
    }

    public void clear() {
        try {
            new FileWriter(file, false).close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String toString() {
        return getName();
    }
}