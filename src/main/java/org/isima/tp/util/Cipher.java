package org.isima.tp.util;


import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Cipher {

  private static final Logger LOGGER = LoggerFactory.getLogger(Cipher.class);

  @Autowired
  private ApplicationContext ctx;

  private SecretKeySpec skeyspec;
  private javax.crypto.Cipher crypt;

  @PostConstruct
  void init() throws NoSuchPaddingException, NoSuchAlgorithmException {
    String secret = Arrays.stream(ctx.getBeanDefinitionNames())
        .filter(s -> s.startsWith("dataSource") && s.length() == 30).findFirst().orElse("xxx");
    skeyspec = new SecretKeySpec(secret.getBytes(), "Blowfish");
    crypt = javax.crypto.Cipher.getInstance("Blowfish");
  }

  public byte[] cipher(String strClearText) {
    byte[] result = null;
    try {
      crypt.init(javax.crypto.Cipher.ENCRYPT_MODE, skeyspec);
      result = crypt.doFinal(strClearText.getBytes());
    } catch (Exception e) {
      LOGGER.error(null, e);
    }
    return result;
  }

  public String uncipher(byte[] encrypted) {
    String result = null;
    try {
      crypt.init(javax.crypto.Cipher.DECRYPT_MODE, skeyspec);
      byte[] decrypted = crypt.doFinal(encrypted);
      result = new String(decrypted);
    } catch(Exception e) {
      LOGGER.error(null, e);
    }
    return result;
  }
}

