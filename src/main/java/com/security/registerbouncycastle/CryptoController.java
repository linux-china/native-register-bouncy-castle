package com.security.registerbouncycastle;

import jakarta.annotation.PostConstruct;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

@RestController
public class CryptoController {

    private static final String KEY = "539E71D45BAB1DBD38231F5210C42D1C071B4377F0E75E26435103D65027C460";

    @PostConstruct
    private void init() {
        if (null == Security.getProvider("BC")) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam(name = "val") String val) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
            SecretKeySpec key = new SecretKeySpec(Hex.decode(KEY), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encrypted = new String(Hex.encode(cipher.doFinal(val.getBytes())));
            return "Encrypted: " + encrypted + "\n";
        } catch (Exception e) {
            return "Error: " + e.getMessage() + "\n";
        }
    }

}
