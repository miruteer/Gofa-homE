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
		this.repository = repository;
		this.factory = factory;
		this.accountFactory = accountFactory;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	/**
	 * 发送草稿箱中的消息
	 * 
	 * @throws Exception
	 * 
	 */
	public void sendInDraftMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");
		try {
			FromShortMessage msg = (FromShortMessage) em.getModelIF();
			if (userIsExists(msg.getMessageTo()) == null)
				throw new Exception("No such user!");
			// 这里发送的是已经写的消息，这里只是更新表中的数据
			msg.getShortMessageState().setHasSent(true);
			this.repository.updateShortMessage(msg);
		} catch (Exception e) {
			em.setErrors("发送的对象不存在，请检查拼写！");
		}
	}

	/**
	 * 发送草稿箱中的消息
	 * 
	 * @throws Exception
	 * 
	 */
	public void saveInDraftMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");
		try {
			FromShortMessage msg = (FromShortMessage) em.getModelIF();
			if (userIsExists(msg.getMessageTo()) == null)
				throw new Exception("No such user!");
			// 这里发送的是已经写的消息，这里只是更新表中的数据
			this.repository.updateShortMessage(msg);
		} catch (Exception e) {
			em.setErrors("发送的对象不存在，请检查拼写！");
		}
	}

	/**
	 * 删除草稿箱中的消息
	 * 
	 * @throws Exception
	 * 
	 */
	public void deleInDraftMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");
		try {
			deleteShortMessage(em);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			em.setErrors("删除操作出错！");
		}
	}

	/**
	 * 发送 新写的 消息 这是往表里增加新的数据
	 * 
	 */
	public void sendShortMessage(EventModel em) {
		logger.debug("ShortMessageServiceImp");
		try {
			FromShortMessage msg = (FromShortMessage) em.getModelIF();
			if (userIsExists(msg.getMessageTo()) == null)
				throw new Exception("No such user!");
			Account account = this.sessionContextUtil.getLoginAccount(sessionContext);
			msg.setAccount(account);
			msg.setMessageFrom(account.getUsername());
			if (!this.factory.sendShortMessage(msg))
				em.setErrors("shormessage.exceed.max");
		} catch (Exception e) {
			em.setErrors("errors.nouser");
		}
	}

	/**
	 * 保存消息
	 */
	public void saveShortMessage(EventModel em) {
		logger.debug("service: saveShortMessage()");
		try {
			FromShortMessage msg = (FromShortMessage) em.getModelIF();
			if (userIsExists(msg.getMessageTo()) == null)
				throw new Exception("No such user!");
			Account account = this.sessionContextUtil.getLoginAccount(sessionContext);
			msg.setAccount(account);
			if (!this.factory.saveShortMessage(msg))
				em.setErrors("shormessage.exceed.max");
		} catch (Exception e) {
			em.setErrors("errors.nouser");
		}
	}

	/**
	 * 
	 * @param userId
	 * @throws Exception
	 */
	private Account userIsExists(String userName) throws Exception {
		return factory.findTheUser(userName);
	}

	/**
	 * 删除消息
	 * 
	 * @throws Exception
	 */
	public void deleteShortMessage(EventModel em) throws Exception {
		logger.debug("deleteShortMessage");
		ShortMessage smf = (FromShortMessage) em.getModelIF();
		logger.debug("" + smf.getMsgId());
		smf = this.factory.findShortMessage(smf.getMsgId());
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		// check auth
		if ((smf.getMessageFrom().equals(loginaccount.getUsername())) || (smf.getMessageTo().equals(loginaccount.getUsername()))) {
			this.repository.deleteShortMessage(smf);
			loginaccount.reloadAccountSMState();
		}
	}

	public void deleteUserAllShortMessage() throws Exception {
		logger.debug("deleteShortMessage");
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		this.repository.deleteUserAllShortMessage(loginaccount.getUserId());
		loginaccount.reloadAccountSMState();
	}

	public void deleteUserRecAllShortMessage() throws Exception {
		logger.debug("deleteUserRecAllShortMessage");
		Account loginaccount = this.sessionContextUtil.getLoginAccount(sessionContext);
		this.repository.deleteUserRecAllShortMessage(loginaccount.getUsername());
		loginaccount.reloadAccountSMState();
	}

	/**
	 * 编辑消息
	 */
	public void updateShortMessage(EventModel em) {
		logger.debug("Sho