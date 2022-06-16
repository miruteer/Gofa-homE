package com.jdon.jivejdon.infrastructure.dto;

import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.command.ReviseForumMessageCommand;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.MessagePropertysVO;

/**
 * ForumMessage DTO, it is a anemic model used for presentation MessageForm and
 * for persistence MessageCore of jiveMessage database table
 */
public class AnemicMessageDTO {
    private Long messageId;
    private String creationDate;
    private long modifiedDate;
    private ForumThread forumThread;
    private Forum forum;
    private Account account;
    private MessageVO messageVO;
    private AnemicMessageDTO parentMessage;
    // for upload files
    pri