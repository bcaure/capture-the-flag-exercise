package org.isima.tp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.SendGrid;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cloud")
public class Mail {

  private static final Logger LOG = LoggerFactory.getLogger(Mail.class);

  @Bean
  public SendGrid mailSender() {

    String vcapServicesJson = System.getenv("VCAP_SERVICES");
    ObjectMapper mapper = new ObjectMapper();
    SendGrid sendgrid = null;
    try {
      JsonNode actualObj = mapper.readTree(vcapServicesJson);
      JsonNode sendgridNode = actualObj.get("sendgrid");
      JsonNode credentialsNode = sendgridNode.elements().next().get("credentials");
      LOG.error(credentialsNode.get("password").asText());
      sendgrid = new SendGrid(credentialsNode.get("username").asText(), credentialsNode.get("password").asText());
    } catch (IOException e) {
      LOG.error(null, e);
    }

    return sendgrid;
  }

}
