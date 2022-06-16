/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.domain.service;

import com.jdon.annotation.model.Owner;
import com.jdon.annotation.model.Receiver;
import com.jdon.annotation.model.Send;
import com.jdon.controller.events.EventModel;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.domain.command.PostRepliesMessageCommand;
import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.command.ReviseForumMessageCommand;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.infrastructure.dto.AnemicMessageDTO;

import java.util.Optional;

public interface MessageDomainService {

	/**
	 * get the full forum in forumMessage, and return it.
	 */
	public abstract AnemicMessageDTO initMessage(EventModel em);

	public abstract AnemicMessageDTO initReplyMessage(EventModel em);

	/*
	 * return a full ForumMessage need solve the relations with Forum
	 * ForumThread parentMessage
	 */
	public abstract ForumMessage getMessage(Long messageId);

	/**
	 * return a full ForumThread one ForumThread has one rootMessage need solve
	 * the realtion with Forum rootForumMessage latestPost
	 * 
	 * @param threadId
	 * @return
	 */
	public abstract Optional<ForumThread> getThread(Long threadId);

	@Send("postTopicMessageCommand")
	public DomainMessage post(@Owner long forumId, @Receiver Forum forum, PostTopicMessageCommand postTopicMessageCommand) ;

	@Send("postRepliesMessageCommand")
	public DomainMessage addreply(@Owner long threadId, @Receiver ForumMessage parentforumMessage, PostRepliesMessageCommand postRepliesMessageCommand);

	@Send("reviseForumMessageCommand")
	public DomainMessage revise(@Owner long threadId, @Receiver ForumMessage oldforumMessage, ReviseForumMessageCommand reviseForumMessageCommand);

	/*
	 * delete a message and not inlcude its childern
	 */
	public abstract void deleteMessage(AnemicMessageDTO delforumMessage) throws Exception;

	public abstract void deleteUserMessages(String username) throws Exception;

	public abstract ForumMessageQueryService getForumMessageQueryService();

	public abstract void setForumMessageQueryService(ForumMessageQueryService forumMessageQueryService);

}