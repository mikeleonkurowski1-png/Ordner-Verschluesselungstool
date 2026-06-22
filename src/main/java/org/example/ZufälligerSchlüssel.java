package org.example;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class ZufälligerSchlüssel {
    public static String getKeyFromKeyGenerator() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Key Schlüssel = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(Schlüssel.getEncoded());
    }

    public static Key TextzuKey(String keyText){
        byte[] decodedKey = Base64.getDecoder().decode(keyText);
        return new SecretKeySpec(decodedKey, "AES");
    }
}
