package com.blobus.apiExterneBlobus.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;
import java.nio.file.Files;

@Data
@AllArgsConstructor

public class KeyGenerator {
        public static void writeToFileString(String pub, String priv) throws IOException {
        FileWriter pubkey = new FileWriter("RSA/pubkey");
        FileWriter privkey = new FileWriter("RSA/privkey");
        pubkey.write(pub);
        pubkey.close();
        privkey.write(priv);
        privkey.close();

    }
    public void writeToFile(String path, byte[] key) throws IOException {
            path="RSA/pubkey";
        File f = new File(path);
        f.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }
    public static String readFromFile(String path) throws IOException {
        File pubkey = new File(path);

        String fileContent = null;
        try (FileReader fr = new FileReader(pubkey)) {
            char[] chars = new char[(int) pubkey.length()];
           fr.read(chars);
            fileContent = new String(chars);
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    }

