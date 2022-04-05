/*
 * Copyright (c) 2008 Ge Xinying
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jdon.jivejdon.api.impl.shortmessage;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.annotation.intercept.Poolable;
import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.annotation.model.OnEvent;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.spi.component.shortmessage.ShortMessageFactory;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.shortmessage.FromShortMessage;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.shortmessage.ToShortMessage;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.shortmessage.ShortMessageRepository;
import com.jdon.jivejdon.infrastructure.repository.builder.ForumAbstractFactory;
import com.jdon.jivejdon.api.shortmessage.ShortMessageService;
import com.jdon.jivejdon.api.util.SessionContextUtil;

/**
 * ShortMessageServiceImp.java
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * CreateData: 2008-5-20
 * </p>
 * 
 * @author GeXinying banq
 * @version 1.0
 */
@Poolable
@Component("shortMessageService")
public class ShortMessageServiceImp implements ShortMessageService {
	private final static Logger logger = LogManager.getLogger(ShortMessageServiceImp.class);

	private SessionContext sessionContext;

	private final SessionContextUtil sessionContextUtil;

	protected final ShortMessageRepository repository;

	protected final ShortMessageFactory factory;

	protected final AccountFactory accountFactory;

	protected final ForumAbstractFactory forumAbstractFactory;

	public ShortMessageServiceImp(SessionContextUtil sessionContextUtil, ShortMessageRepository repository, ShortMessageFactory factory,
			AccountFactory accountFactory, ForumAbstractFactory forumAbstractFactory) {
		this.sessionContextUtil = sessionContextUtil;
		this.repository =