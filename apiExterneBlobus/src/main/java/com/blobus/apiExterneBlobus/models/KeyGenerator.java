package com.blobus.apiExterneBlobus.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.*;

@Data
@AllArgsConstructor

public class KeyGenerator {
        public static void writeToFileString(String pub, String priv) throws IOException {
        FileWriter pubkey = new FileWriter("RSA/pubkey.txt");
        FileWriter privkey = new FileWriter("RSA/privkey.txt");

        pubkey.write(pub);
        pubkey.close();
        privkey.write(priv);
        privkey.close();

    }
    public static String readFromFile(String path) throws IOException {
        File pubkey = new File(path);

        String fileContent = null;
        try (FileReader fr = new FileReader(pubkey)) {
            char[] chars = new char[(int) pubkey.length()];
            fr.read(chars);
            fileContent = new String(chars);
            //System.out.println(chars);
            //System.out.println(fileContent);
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String [] args) throws IOException {
            String ch=readFromFile("RSA/pubkey.txt");
            System.out.println(ch);
    }





    }

