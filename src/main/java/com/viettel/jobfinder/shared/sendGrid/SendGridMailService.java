package com.viettel.jobfinder.shared.sendGrid;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.viettel.jobfinder.modules.employee.Employee;
import com.viettel.jobfinder.modules.employer.Employer;
import com.viettel.jobfinder.modules.job.Job;

@Service
public class SendGridMailService {
  private static final String CONTENT_TYPE_TEXT_HTML = "text/html";

  private static final String KEY_X_MOCK = "X-Mock";

  private static final String SEND_GRID_ENDPOINT_SEND_EMAIL = "mail/send";

  @Value("${send_grid.api_key}")
  private String sendGridApiKey;

  @Value("${send_grid.from_email}")
  private String sendGridFromEmail;

  @Value("${send_grid.from_name}")
  private String sendGridFromName;

  public void sendAcceptedMail(Employer employer, Job job, Employee employee) {
    String employeeName = employee.getUser().getFullName();
    String employeeEmail = employee.getUser().getEmail();
    String jobTitle = job.getTitle();
    String companyName = employer.getUser().getFullName();
    String companyEmail = employer.getUser().getEmail();
    String content = "<p><span style=\"font-size: 18px;\">Dear " + employeeName + ",</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">We are thrilled to inform you that your application for the <strong>"
        + companyName + "</strong> position at <strong>" + jobTitle
        + "</strong> has been <strong>ACCEPTED</strong>!</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">On behalf of Job Finder team, we want to extend our warmest congratulations to you.</span></p>\n"
        +
        "<p><span style=\"font-size: 18px;\">Your application and qualifications have stood out among the pool of candidates, and as a result, <strong>"
        + companyName
        + "</strong> would like to invite you for an interview. This is a significant achievement and a testament to your skills, experience, and potential fit within the company.</span></p>\n"
        +
        "<p><span style=\"font-size: 18px;\">Once again, congratulations on this well-deserved opportunity! Your advancement to the interview stage is a significant milestone, and we wish you the best of luck as you prepare. Embrace this moment and showcase your true potential.</span></p>\n"
        +
        "<p><span style=\"font-size: 18px;\">Best regards,</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Job Finder Team</span></p>";
    sendMail("Notification from " + jobTitle + " - " + companyName, content, List.of(employeeEmail),
        List.of(companyEmail), null);
  }

  public void sendRejectedMail(Employer employer, Job job, Employee employee) {
    String employeeName = employee.getUser().getFullName();
    String employeeEmail = employee.getUser().getEmail();
    String jobTitle = job.getTitle();
    String companyName = employer.getUser().getFullName();
    String companyEmail = employer.getUser().getEmail();
    String content = "<p><span style=\"font-size: 18px;\">Dear " + employeeName + ",</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Firstly we wanted to express our sincere appreciation for your recent application for the <strong>"
        + jobTitle + "</strong> position at <strong>" + companyName + "</strong>.</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">We regret to inform that you <strong>have not been selected</strong> for this job.</span></p>\n"
        +
        "<p><span style=\"font-size: 18px;\">Please do not be discouraged by this result. Your qualifications are commendable, and we believe the right opportunity is waiting for you. We encourage you to continue your job search and pursue positions that align with your aspirations.</span></p>\n"
        +
        "<p><span style=\"font-size: 18px;\">We wish you every success in finding your ideal job.</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Best regards,</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Job Finder Team</span></p>";
    sendMail("Notification from " + jobTitle + " - " + companyName, content, List.of(employeeEmail),
        List.of(companyEmail), null);
  }

  public void sendWelcomeMail(String toEmail) {
    String content = "<p><span style=\"font-size: 18px;\">Dear " + toEmail + ",</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Welcome to join Job Finder System! We are thrilled to have you as a new member of our community. With our platform, you will have access to a wide range of job opportunities and valuable resources to support your job search.</span></p>\n"
        +
        "<p><span style=\"font-size: 18px;\">We are committed to helping you find your ideal job and succeed in your career. Feel free to explore all the features and resources available on our platform.</span></p>\n"
        +
        "<p><span style=\"font-size: 18px;\">Thank you once again for joining with us. We look forward to assisting you in your job search journey and witnessing your career growth.</span></p>\n"
        +
        "<p><span style=\"font-size: 18px;\">Best regards,</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Job Finder Team</span></p>";
    sendMail("Welcome to Job Finder System", content, List.of(toEmail), null, null);
  }

  public void sendApplySuccessfulMail(Employer employer, Job job, Employee employee) {
    String employeeName = employee.getUser().getFullName();
    String employeeEmail = employee.getUser().getEmail();
    String jobTitle = job.getTitle();
    String companyName = employer.getUser().getFullName();
    String companyEmail = employer.getUser().getEmail();
    String content = "<p><span style=\"font-size: 18px;\">Dear " + employeeName + ",</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">You have applied successfully to <strong>"
        + jobTitle + "</strong> position at <strong>" + companyName + "</strong>.</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Please waiting for the response from your company</p>\n"
        +
        "<p><span style=\"font-size: 18px;\">We wish you every success in finding your ideal job.</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Best regards,</span></p>\n" +
        "<p><span style=\"font-size: 18px;\">Job Finder Team</span></p>";
    sendMail("Notification from " + jobTitle + " - " + companyName, content, List.of(employeeEmail),
        List.of(companyEmail), null);
  }

  public void sendMail(String subject, String content, List<String> sendToEmails, List<String> ccEmails,
      List<String> bccEmails) {
    Mail mail = buildMailToSend(subject, content, sendToEmails, ccEmails, bccEmails);
    send(mail);
  }

  private void send(Mail mail) {
    SendGrid sg = new SendGrid(sendGridApiKey);
    sg.addRequestHeader(KEY_X_MOCK, "true");

    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint(SEND_GRID_ENDPOINT_SEND_EMAIL);
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private Mail buildMailToSend(String subject, String contentStr, List<String> sendToEmails, List<String> ccEmails,
      List<String> bccEmails) {
    Mail mail = new Mail();

    Email fromEmail = new Email();
    fromEmail.setName(sendGridFromName);
    fromEmail.setEmail(sendGridFromEmail);

    mail.setFrom(fromEmail);

    mail.setSubject(subject);

    Personalization personalization = new Personalization();

    // Add sendToEmails
    if (sendToEmails != null) {
      for (String email : sendToEmails) {
        Email to = new Email();
        to.setEmail(email);
        personalization.addTo(to);
      }
    }

    // Add ccEmail
    if (ccEmails != null) {
      for (String email : ccEmails) {
        Email cc = new Email();
        cc.setEmail(email);
        personalization.addCc(cc);
      }
    }

    // Add bccEmail
    if (bccEmails != null) {
      for (String email : bccEmails) {
        Email bcc = new Email();
        bcc.setEmail(email);
        personalization.addBcc(bcc);
      }
    }
    mail.addPersonalization(personalization);

    Content content = new Content();
    content.setType(CONTENT_TYPE_TEXT_HTML);
    content.setValue(contentStr);
    mail.addContent(content);
    return mail;
  }
}
