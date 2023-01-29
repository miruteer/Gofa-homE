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
package com.jdon.jivejdon.spi.pubsub.reconstruction.impl;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.infrastructure.repository.builder.ForumAbstractFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDao;

@Consumer("reloadMessageVO")
public class ReloadMessageVO implements DomainEventHandler {

	private final ForumAbstractFactory forumAbstractFactory;
	private final MessageDao messageDao;

	public ReloadMessageVO(ForumAbstractFactory forumAbstractFactory, MessageDao messageDao) {
		super();
		this.forumAbstractFactory = forumAbstractFactory;
		this.messageDao = messageDao;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		Long messageId = (Long) event.getDomainMessage().getEventSource();
		ForumMessage forumMessage = forumAbstractFactory.getMessage(messageId);
		MessageVO messageVO = messageDao.getMessageVOCore(forumMessage);
		event.getDomainMessage().setEventResult(messageVO);

	}

}
