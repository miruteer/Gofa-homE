package com.jdon.jivejdon.domain.command;

import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.attachment.AttachmentsVO;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.property.MessagePropertysVO;

public class PostTopicMessageCommand {

    private final Long messageId;
    private final Forum forum;
    private final Account account;
    private final MessageVO messageVO;
    private final AttachmentsVO attachment;;
    private final MessagePropertysVO messagePropertysVO;
    private final String[] tagTitle;

    public PostTopicMessageCommand(Long messageId, Forum forum, Account account, MessageVO messageVO, AttachmentsVO attachment, MessagePropertysVO
            messagePropertysVO, String[] tagTitle) {
        this.messageId = messageId;
        this.forum = forum;
        this.account = account;
        this.messageVO = messageVO;
        this.attachment = attachment;
        this.messagePropertysVO = messagePropertysVO;
        this.tagTitle = tagTitle;
    }

    public Lo