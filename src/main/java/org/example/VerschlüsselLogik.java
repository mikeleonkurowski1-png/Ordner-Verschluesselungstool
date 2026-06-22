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

    Key key;
    //Try Catch um den in ZufälligerSchlüssel generierten SChlüssel hier aufrufen zu können.
    {
        try {
            key = ZufälligerSchlüssel.getKeyFromKeyGenerator();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public void Verschlüsseln(File file, String passwort) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(file); //InputStream
             FileOutputStream fos = new FileOutputStream(file.getAbsolutePath() + ".enc"); //OutputStream
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
    public void Entschlüsseln(File verschlüsselteFile, String passwort) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(verschlüsselteFile.getAbsolutePath());
             FileOutputStream fos = new FileOutputStream(verschlüsselteFile.getAbsolutePath() + ".decrypted");
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
}
