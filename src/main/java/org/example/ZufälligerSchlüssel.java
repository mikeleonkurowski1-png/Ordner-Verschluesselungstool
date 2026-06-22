package org.example;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ZufälligerSchlüssel {
    public static Key getKeyFromKeyGenerator() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Key Schlüssel = keyGenerator.generateKey();
        return Schlüssel;
    }
}
