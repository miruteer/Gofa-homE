/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.presentation.form;

import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UI model
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class MessageForm extends BaseForm {
	public final static int subjectMaxLength = 140;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int bodyMaxLength = 81900;
	private Long messageId;

	private ForumThread forumThread;

	private Forum forum;

	private Account account;

	private AnemicMessageDTO parentMessage;

	// for upload files
	private AttachmentsVO attachment;;

	private boolean authenticated = true;// default true

	private MessageVO messageVO;

	// not let messageVo be Filtered , it should be save to DB.
	private boolean messageVOFiltered = true;

	private boolean masked;

	private boolean replyNotify;

	private String[] tagTitle;

	// modify

	/**
	 * for initMessage of the ForumMessageService
	 */
	public MessageForm() {
		messageVO = new MessageVO();
		forum = new Forum(); // for parameter forum.forumId=xxx
		forumThread = new ForumThread(null, null, forum);
		account = new Account();
		parentMessage = new AnemicMessageDTO();

	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public AnemicMessageDTO getParentMessage() {
		return parentMessage;
	}

	public void setParentMessage(AnemicMessageDTO parentMessage) {
		this.parentMessage = parentMessage;
	}

	public String getBody() {
		return messageVO.getBody();
	}

	public void setBody(String body) {
		messageVO = messageVO.builder().subject(this.getSubject()).body(body).build();
	}

	public String getSubject() {
		return messageVO.getSubject();
	}

	public void setSubject(String subject) {
		messageVO = messageVO.builder().subject(subject).body(this.getBody()).build();
	}

	public ForumThread getForumThread() {
		return forumThread;
	}

	public void setForumThread(ForumThread forum