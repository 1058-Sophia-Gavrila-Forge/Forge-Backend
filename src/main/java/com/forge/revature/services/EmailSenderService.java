package com.forge.revature.services;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.forge.revature.models.Portfolio;
import com.forge.revature.models.User;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendStatusEmail(User user, Portfolio portfolio) {
		
		try {
			if(portfolio.isReviewed() && !portfolio.isSubmitted()) {
				MimeMessage message = mailSender.createMimeMessage();
				message.setFrom("forge.test@outlook.com");
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));	
	        	if(portfolio.isApproved()) {
	        		String htmlApproved = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta name=\"x-apple-disable-message-reformatting\"><title></title><!--[if mso]> <noscript> <xml> <o:OfficeDocumentSettings> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml> </noscript><![endif]--><style>table,td,div,h1,p {font-family: Arial, sans-serif;}</style></head><body style=\"margin:0;padding:0;\"><table role=\"presentation\"style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff; \"><tr><td align=\"center\" style=\"padding:0;\"><table role=\"presentation\"style=\"width:602px;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0;text-align:left;\"><tr><td align=\"center\" style=\"padding:40px 0 0 0;background: #121212; color:#ffffff;\"><table role=\"presentation\"style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td align=\"right\" style=\"padding:20px 10px\"><img src=\"images/rev-logo.png\" alt=\"\" width=\"300\"style=\"height:auto;display:block;\"></td></tr><tr><td align=\"left\" style=\"padding:0 0 0 30px;background: #121212; color:#ffffff;\">"+message.getSubject()+" | RevaturePro</td></tr><tr><td style=\"min-width:100%;background: #121212;\"><hrstyle=\"height:5px; width:100%; border-width:0; color:#F26925; background-color:#F26925\"></td></tr></table></td></tr><tr><td style=\"padding:36px 30px 42px 30px;background: #121212; color:#ffffff;\"><table role=\"presentation\"style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td style=\"padding:0;\"><h1>"+message.getSubject()+" | RevaturePro</h1><h3>Hello "+user.getFName()+" "+user.getLName()+"</h3><p>your trainer believes your portfolio, "+portfolio.getName()+", is ready and has been approved. Congratulations!</p><br>Use this link to browse your portfolios:<a href=\"http://track.revature.com/track/click/30470371/app.revature.com?p=eyJzIjoiOGowbmRJTGctN1Ryd2FKcHk2ejRoYkVrQmJnIiwidiI6MSwicCI6IntcInVcIjozMDQ3MDM3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL2FwcC5yZXZhdHVyZS5jb21cXFwvcHJvZmlsZVxcXC90YWJcXFwvcG9ydGZvbGlvc1wiLFwiaWRcIjpcImI5ODZlM2FmZTc3YTQwNmNiZjg3ZjM1OTkwYjc3MDU0XCIsXCJ1cmxfaWRzXCI6W1wiNTNlMmJlZmY4YjRkYTVlNzcyNzQxNWI3MzRiMDQ2ODU2ODJlMWE0MlwiXX0ifQ\"target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\"data-linkindex=\"1\">https://app.revature.com/profile/tab/portfolios</a><p></p><br><br>Regards<br><strong>RevaturePro</strong><p></p><br><ahref=\"http://track.revature.com/track/click/30470371/www.revature.com?p=eyJzIjoiZE5fT294MHNuY3BDS3JBXzdzaVFkLXNWZmxRIiwidiI6MSwicCI6IntcInVcIjozMDQ3MDM3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3d3dy5yZXZhdHVyZS5jb21cIixcImlkXCI6XCJiOTg2ZTNhZmU3N2E0MDZjYmY4N2YzNTk5MGI3NzA1NFwiLFwidXJsX2lkc1wiOltcImYzMzAwNzEwM2Y4MmM1NjdhOTcwODk3OGE1MjI3MjczN2I1YmFkOGNcIl19In0\"target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\"data-linkindex=\"2\">https://www.revature.com</a></td></tr></table></td></tr><tr><td style=\"padding:0;background: #121212; color:#ffffff;\"><table role=\"presentation\"style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td style=\"padding:10px 0;width:100%;\" align=\"center\"><table role=\"presentation\"style=\"border-collapse:collapse;border:0;border-spacing:0;\"><tr><td style=\"padding:0 0 0 10px;width:38px;\"><a href=\"https://www.facebook.com/revature/\" target=\"_blank\"><imgsrc=\"images/facebook-logo.png\" alt=\"Facebook\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://twitter.com/WeAreRevature\" target=\"_blank\"><imgsrc=\"images/twitter-logo.png\" alt=\"Twitter\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://www.linkedin.com/company/revature/mycompany/\"target=\"_blank\"><img src=\"images/linkedin-logo.png\"alt=\"LinkedIn\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://www.instagram.com/teamrevature/\"target=\"_blank\"><img src=\"images/instagram-logo.png\"alt=\"Instagram\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://www.youtube.com/channel/UCGBKIv2WIS2utCQwJFqYFAg/videos\"target=\"_blank\"><img src=\"images/youtube-logo.png\" alt=\"Youtube\"width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://www.tiktok.com/@revature\" target=\"_blank\"><imgsrc=\"images/tiktok-logo.png\" alt=\"Tiktok\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://revature.com/\" target=\"_blank\"><imgsrc=\"images/linkto-icon.png\" alt=\"Revature\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"mailto:support@revature.com\" target=\"_blank\"><imgsrc=\"images/mailto-icon.png\" alt=\"Mailto\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td></tr><tr><td style=\"min-width:100%;background: #121212;\"><hrstyle=\"height:5px; width:100%; border-width:0; color:#F26925; background-color:#F26925\"></td></tr><tr><td style=\"padding:20px;min-width:100%;background: #121212;font-size: small;\"align=\"center\"><p style=\"color: #D3D3D3;\">Copyright &copysr; 2021 Revature, All rightsreserved.</p><br><p><b>Our mailing address:</b></p><p style=\"text-decoration: underline;color: #D3D3D3;\">11730 Plaza America Dr,Suite 205, Reston VA 20190, USA</p><p style=\"text-decoration: underline;color: #D3D3D3;\">W: https://revature.com |P: 703 570 8181 | E: info@revature.com </p></td></tr></table></td></tr></table></td></tr></table></body></html>";
	        		message.setSubject("Portfolio Submission Approved");
	        		message.setContent(htmlApproved, "text/html");
	        	}
	        	else {
	        		Iterator map = portfolio.getFlags().entrySet().iterator();
	        		StringBuilder mapAsString = new StringBuilder("<h3 style=\"text-align:left\">Feedback</h3><ulstyle=\"text-align:left\">");
	        		while(map.hasNext()) {
	        			Map.Entry pair = (Map.Entry)map.next();
	        			if(pair.getValue().toString().length() > 0) {
	        				System.out.println(pair.getValue().toString().length());
	        				mapAsString.append("<li>"+pair.getKey()+" : "+pair.getValue()+"</li>");
	        			}
	        				
	        		}
	        		mapAsString.append("</ul>");
	        		String feedback = mapAsString.toString();
	        		message.setSubject("There's an issue with your submitted portfolio");
	        		String htmlDenied = "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta name=\"x-apple-disable-message-reformatting\"><title></title><!--[if mso]> <noscript> <xml> <o:OfficeDocumentSettings> <o:PixelsPerInch>96</o:PixelsPerInch> </o:OfficeDocumentSettings> </xml> </noscript><![endif]--><style>table,td,div,h1,p {font-family: Arial, sans-serif;}</style></head><body style=\"margin:0;padding:0;\"><table role=\"presentation\"style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;background:#ffffff; \"><tr><td align=\"center\" style=\"padding:0;\"><table role=\"presentation\"style=\"width:602px;border-collapse:collapse;border:1px solid #cccccc;border-spacing:0;text-align:left;\"><tr><td align=\"center\" style=\"padding:40px 0 0 0;background: #121212; color:#ffffff;\"><table role=\"presentation\"style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td align=\"right\" style=\"padding:20px 10px\"><img src=\"images/rev-logo.png\" alt=\"\" width=\"300\"style=\"height:auto;display:block;\"></td></tr><tr><td align=\"left\" style=\"padding:0 0 0 30px;background: #121212; color:#ffffff;\">"+message.getSubject()+" | RevaturePro</td></tr><tr><td style=\"min-width:100%;background: #121212;\"><hrstyle=\"height:5px; width:100%; border-width:0; color:#F26925; background-color:#F26925\"></td></tr></table></td></tr><tr><td style=\"padding:36px 30px 42px 30px;background: #121212; color:#ffffff;\"><table role=\"presentation\"style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td style=\"padding:0;\"><h1>"+message.getSubject()+" | RevaturePro</h1><h3>Hello "+user.getFName()+" "+user.getLName()+"</h3><p>your trainer believes your portfolio, "+portfolio.getName()+", could use more work.</p>"+feedback+"<br>Use this link to browse your portfolios:<a href=\"http://track.revature.com/track/click/30470371/app.revature.com?p=eyJzIjoiOGowbmRJTGctN1Ryd2FKcHk2ejRoYkVrQmJnIiwidiI6MSwicCI6IntcInVcIjozMDQ3MDM3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL2FwcC5yZXZhdHVyZS5jb21cXFwvcHJvZmlsZVxcXC90YWJcXFwvcG9ydGZvbGlvc1wiLFwiaWRcIjpcImI5ODZlM2FmZTc3YTQwNmNiZjg3ZjM1OTkwYjc3MDU0XCIsXCJ1cmxfaWRzXCI6W1wiNTNlMmJlZmY4YjRkYTVlNzcyNzQxNWI3MzRiMDQ2ODU2ODJlMWE0MlwiXX0ifQ\"target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\"data-linkindex=\"1\">https://app.revature.com/profile/tab/portfolios</a><p></p><br><br>Regards<br><strong>RevaturePro</strong><p></p><br><ahref=\"http://track.revature.com/track/click/30470371/www.revature.com?p=eyJzIjoiZE5fT294MHNuY3BDS3JBXzdzaVFkLXNWZmxRIiwidiI6MSwicCI6IntcInVcIjozMDQ3MDM3MSxcInZcIjoxLFwidXJsXCI6XCJodHRwczpcXFwvXFxcL3d3dy5yZXZhdHVyZS5jb21cIixcImlkXCI6XCJiOTg2ZTNhZmU3N2E0MDZjYmY4N2YzNTk5MGI3NzA1NFwiLFwidXJsX2lkc1wiOltcImYzMzAwNzEwM2Y4MmM1NjdhOTcwODk3OGE1MjI3MjczN2I1YmFkOGNcIl19In0\"target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\"data-linkindex=\"2\">https://www.revature.com</a></td></tr></table></td></tr><tr><td style=\"padding:0;background: #121212; color:#ffffff;\"><table role=\"presentation\"style=\"width:100%;border-collapse:collapse;border:0;border-spacing:0;\"><tr><td style=\"padding:10px 0;width:100%;\" align=\"center\"><table role=\"presentation\"style=\"border-collapse:collapse;border:0;border-spacing:0;\"><tr><td style=\"padding:0 0 0 10px;width:38px;\"><a href=\"https://www.facebook.com/revature/\" target=\"_blank\"><imgsrc=\"images/facebook-logo.png\" alt=\"Facebook\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://twitter.com/WeAreRevature\" target=\"_blank\"><imgsrc=\"images/twitter-logo.png\" alt=\"Twitter\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://www.linkedin.com/company/revature/mycompany/\"target=\"_blank\"><img src=\"images/linkedin-logo.png\"alt=\"LinkedIn\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://www.instagram.com/teamrevature/\"target=\"_blank\"><img src=\"images/instagram-logo.png\"alt=\"Instagram\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://www.youtube.com/channel/UCGBKIv2WIS2utCQwJFqYFAg/videos\"target=\"_blank\"><img src=\"images/youtube-logo.png\" alt=\"Youtube\"width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://www.tiktok.com/@revature\" target=\"_blank\"><imgsrc=\"images/tiktok-logo.png\" alt=\"Tiktok\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"https://revature.com/\" target=\"_blank\"><imgsrc=\"images/linkto-icon.png\" alt=\"Revature\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td><td style=\"padding:0 0 0 25px;width:38px;\"><a href=\"mailto:support@revature.com\" target=\"_blank\"><imgsrc=\"images/mailto-icon.png\" alt=\"Mailto\" width=\"38\"style=\"height:auto;display:block;border:0;\" /></a></td></tr></table></td></tr><tr><td style=\"min-width:100%;background: #121212;\"><hrstyle=\"height:5px; width:100%; border-width:0; color:#F26925; background-color:#F26925\"></td></tr><tr><td style=\"padding:20px;min-width:100%;background: #121212;font-size: small;\"align=\"center\"><p style=\"color: #D3D3D3;\">Copyright &copysr; 2021 Revature, All rightsreserved.</p><br><p><b>Our mailing address:</b></p><p style=\"text-decoration: underline;color: #D3D3D3;\">11730 Plaza America Dr,Suite 205, Reston VA 20190, USA</p><p style=\"text-decoration: underline;color: #D3D3D3;\">W: https://revature.com |P: 703 570 8181 | E: info@revature.com </p></td></tr></table></td></tr></table></td></tr></table></body></html>";
	        		message.setContent(htmlDenied, "text/html");
	        	}
		        mailSender.send(message);
				System.out.println(message.getSubject());	
			}
			

		}catch (MessagingException response) {
            response.printStackTrace();
        }
		
		
	}
}
