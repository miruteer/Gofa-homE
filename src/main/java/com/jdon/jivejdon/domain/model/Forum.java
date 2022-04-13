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
package com.jdon.jivejdon.domain.model;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.annotation.model.OnCommand;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.event.TopicMessagePostedEvent;
import com.jdon.jivejdon.domain.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.domain.model.subscription.event.ForumSubscribedNotifyEvent;
import com.jdon.jivejdon.spi.pubsub.publish.ThreadEventSourcingRole;
import com.jdon.jivejdon.spi.pubsub.reconstruction.LazyLoaderRole;
import com.jdon.jivejdon.util.Constants;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
public class Forum {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Long forumId;

	private String name;
	private String description;
	private String creationDate;

	/**
	 * the forum modified date, not the message modified date in the forum,
	 */
	private String modifiedDate;
	private Collection propertys;

	/**
	 * @link aggregation
	 */

	private volatile AtomicReference<ForumState> forumState;

	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public SubPublisherRoleIF publisherRole;
	//
	// @Inject
	// private ForumStateFactory forumStateManager;

	@Inject
	public ThreadEventSourcingRole eventSourcingRole;

	@OnCommand("postTopicMessageCommand")
	public void postMessage(PostTopicMessageCommand postTopicMessageCommand) {
		// fill the business rule for post a topic message
		if (isRepeatedMessage(postTopicMessageCommand)) {
			System.err.println("repeat message error: " + postTopicMessageCommand.getMessageVO().getSubject());
			return;
		}
		DomainMessage domainMessage = eventSourcingRole.saveTopicMessage(postTopicMessageCommand);
		ForumMessage rootForumMessage = (ForumMessage) domainMessage.getBlockEventResult();
		if (rootForumMessage != null) {// make sure repostiory finish save new message
			threadPosted(rootForumMessage);
			eventSourcingRole.topicMessagePosted(new TopicMessagePostedEvent(rootForumMessage));
		}
	}

	private boolean isRepeatedMessage(PostTopicMessageCommand postTopicMessageCommand) {
		if (this.forumState.get().getLatestPost() == null)
			return false;
		return this.forumState.get().getLatestPost()
				.isSubjectRepeated(postTopicMessageCommand.getMessageVO().getSubject()) ? true : false;

	}

	public void threadPosted(ForumMessage rootForumMessage) {
		forumState.get().addThreadCount();
		forumState.get().setLatestPost(rootForumMessage);
		this.publisherRole.subscriptionNotify(new ForumSubscribedNotifyEvent(this.forumId, rootForumMessage));
	}

	public Forum() {
		// init state
		forumState = new AtomicReference(new ForumState(this));
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
