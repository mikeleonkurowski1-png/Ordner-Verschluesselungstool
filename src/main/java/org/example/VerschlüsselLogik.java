package org.example;

import org.example.ZufälligerSchlüssel;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.CipherInputStream;

public class VerschlüsselLogik {

    public void FileVerschlüsseln(File quellfile,File zielfile, String passwort) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        Key aktuellerKey = ZufälligerSchlüssel.TextzuKey(passwort);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aktuellerKey);

        try (FileInputStream fis = new FileInputStream(quellfile); //InputStream
             FileOutputStream fos = new FileOutputStream(zielfile); //OutputStream
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)){ // VerschlüsselungStream
            byte[] buffer = new byte[1024]; //Immer schrittweise 1024 bytes verschlüsseln statt alle auf einmal.
            int len;
            while ((len = fis.read(buffer)) != -1) //Liest schrittweise 1024 bytes aus bis alle ausgelesen sind (-1)
            {
                cos.write(buffer, 0, len); //Verschlüsselt diese 1024 bytes und schreibt sie direkt
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void FileEntschlüsseln(File quellFile, File ZielFile, String passwort) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        Key aktuellerKey = ZufälligerSchlüssel.TextzuKey(passwort);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aktuellerKey);

        try (FileInputStream fis = new FileInputStream(quellFile);
             FileOutputStream fos = new FileOutputStream(ZielFile);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1){
                cos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void OrdnerVerschlüsseln(File quellordner, File zielOrdner, String passwort) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        if (!zielOrdner.exists()) {
            zielOrdner.mkdir();
        }

        if (quellordner.isDirectory()){
            File[] dateien = quellordner.listFiles();

            if (dateien != null){

                for (File file : dateien){

                    File neuesZiel = new File(zielOrdner, file.getName());

                    if (file.isDirectory()){
                        OrdnerVerschlüsseln(file, neuesZiel,  passwort); //Rekursiver Aufruf, falls ein Unterordner existiert
                    } else {
                        File verschlüsseltesZiel = new File(zielOrdner, file.getName() + ".enc");
                        FileVerschlüsseln(file, verschlüsseltesZiel, passwort);

                    }
                }
            }
        }
    }

    public void OrdnerEntschlüsseln(File quellordner, File zielOrdner, String passwort) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        if (!zielOrdner.exists()) {
            zielOrdner.mkdir();
        }

        if (quellordner.isDirectory()){
            File[] dateien = quellordner.listFiles();

            if (dateien != null){

                for (File file : dateien){
                    File neuesZiel = new File(zielOrdner, file.getName());

                    if (file.isDirectory()){
                        OrdnerEntschlüsseln(file, neuesZiel, passwort);
                    } else {
                        String name = file.getName();
                        if (name.endsWith(".enc")){
                            name = name.substring(0, name.length() - 4);
                        }
                        File entschlüsseltesZiel = new File(zielOrdner, name);
                        FileEntschlüsseln(file, entschlüsseltesZiel, passwort);
                    }
                }
            }
        }
    }
}
