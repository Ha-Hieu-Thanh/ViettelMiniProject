package com.viettel.jobfinder.shared.sendGrid;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendGridMailController {
  @Autowired
  private SendGridMailService sendGridMailService;

  @GetMapping("/test-send-email")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void testSendEmail() {
    sendGridMailService.sendMail(
        "Test Send Email",
        "<h1 style=\"color: red;\">Hello world</h1>"
            + "<p>Xin chào tôi là Ha Hieu Thanh</p>",
        Collections.singletonList("hathithanhchuc20032003@gmail.com"),
        null,
        null);
  }
}
