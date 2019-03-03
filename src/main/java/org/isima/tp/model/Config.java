package org.isima.tp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Config {

  @Id
  private String key;

  @Column
  private byte[] value;

  @Column(length = 1000)
  private String decryptedValue;

  public String getKey() {
    return key;
  }

  public Config setKey(String key) {
    this.key = key;
    return this;
  }

  public String getValue() {
    String result = "";
    if (value != null) {
      result += "0x";
      for (int i = 0; i < value.length; i++) {
        result += String.format("%02X", value[i]);
      }
    }
    return result;
  }

  public Config setValue(byte[] value) {
    this.value = value;
    return this;
  }

  public String getDecryptedValue() {
    return decryptedValue;
  }

  public Config setDecryptedValue(String decryptedValue) {
    this.decryptedValue = decryptedValue;
    return this;
  }
}
