package com.blobus.apiExterneBlobus.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Data
@AllArgsConstructor

public class KeyGenerator {

        private static boolean isDirEmpty(final Path directory) throws IOException {
            try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
                return !dirStream.iterator().hasNext();
            }
        }
        public static void writeToFileString(String pub, String priv) throws IOException {


        if (isDirEmpty(Path.of("RSA"))){

            FileWriter pubkey = new FileWriter("RSA/pubkey");
            FileWriter privkey = new FileWriter("RSA/privkey");
            pubkey.write(pub);
            pubkey.close();
            privkey.write(priv);
            privkey.close();
        }

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
            //byte[] bytes= Files.readAllBytes(p)
           // String chaine=new String((int) pubkey.length());
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






    }

