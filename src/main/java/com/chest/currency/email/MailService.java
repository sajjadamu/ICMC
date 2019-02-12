package com.chest.currency.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {
	@Autowired
	private MailSender emailService; // MailSender interface defines a strategy
	// for sending simple mails

	public void resetPasswordEmail(String toAddress, String fromAddress, String subject, String msgBody) {

		SimpleMailMessage mailMsg = new SimpleMailMessage();
		mailMsg.setFrom(fromAddress);
		mailMsg.setTo(toAddress);
		mailMsg.setSubject(subject);
		mailMsg.setText(msgBody);
		try{
		emailService.send(mailMsg);
		}catch(Exception ex){
			//LOG this exception, do nothing
		}
	}
}