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

import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.treepatterns.model.TreeModel;

import java.util.Optional;

@Component
public class TreeModelFactory {

	protected final ForumFactory forumAbstractFactory;
	private final MessageQueryDao messageQueryDao;

	public TreeModelFactory(MessageQueryDao messageQueryDao, ForumFactory forumAbstractFactory) {
		super();
		this.messageQueryDao = messageQueryDao;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	@OnEvent("loadTreeModel")
	public TreeModel loadTreeModel(Long threadId) {
		try {
			Optional<ForumThread> forumThreadOpt