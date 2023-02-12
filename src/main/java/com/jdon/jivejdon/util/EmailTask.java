package com.jdon.jivejdon.util;

import com.jdon.jivejdon.spi.component.email.EmailHelper;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.naming.InitialContext;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * A task to send email.
 * 
 * @see EmailHelper
 */
public class EmailTask extends Thread {
	public final static String NOHTML_FORMAT = "text/plain";
	public final static String HTML_FORMAT = "text/html";

	// Session used by the javamail classes
	private Session session;
	private String JAVAMAIL_JNDINAME;
	// List of messages
	private List messages = null;

	public EmailTask(String JAVAMAIL_JNDINAME) {
		this();
		this.JAVAMAIL_JNDINAME = JAVAMAIL_JNDINAME;
	}

	/**
	 * Reads mail properties creates a JavaMail session that will be used to
	 * send all mail.
	 */
	public EmailTask(String host, String port, String debug, String user, String password, String from) {
		this();
		Properties mailProps = new Properties();
		if (host != null) {
			mailProps.setProperty("mail.smtp.host", host);
		}
		// check the port for errors (if specified)
		if (port != null && !port.equals("")) {
			try {
				// no errors at this point, so add the port as a property
				mailProps.setProperty("mail.smtp.port", p