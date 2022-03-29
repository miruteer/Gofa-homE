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
package com.jdon.jivejdon.api.impl.account;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.account.PasswordassitVO;
import com.jdon.jivejdon.domain.model.auth.Role;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountRepository;
import com.jdon.jivejdon.infrastructure.repository.acccount.Userconnector;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.dao.util.OldUpdateNewUtil;
import com.jdon.jivejdon.infrastructure.repository.property.PropertyFactory;
import com.jdon.jivejdon.api.account.AccountService;
import com.jdon.jivejdon.api.util.JtaTransactionUtil;
import com.jdon.util.Debug;
import com.jdon.util.task.TaskEngine;
import org.apache.commons.validator.EmailValidator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public abstract class AccountServiceImp implements AccountService {
	private final static String module = AccountServiceImp.class.getName();

	protected AccountFactory accountFactory;

	protected AccountRepository accountRepository;

	private SequenceDao sequenceDao;

	protected JtaTransactionUtil jtaTransactionUtil;

	private OldUpdateNewUtil oldUpdateNewUtil;

	private PropertyFactory propertyFactory;

	private Userconnector userconnector;

	public AccountServiceImp(AccountFactory accountFactory, AccountRepository accountRepository, SequenceDao sequenceDao,
			JtaTransactionUtil jtaTransactionUtil, PropertyFactory propertyFactory, Userconnector userconnector) {
		this.accountFactory = accountFactory;
		this.accountRepository = accountRepository;
		this.sequenceDao = sequenceDao;
		this.jtaTransactionUtil = jtaTransactionUtil;
		this.propertyFactory = propertyFactory;
		this.userconnector = userconnector;
		// this.oldUpdateNewUtil = oldUpdateNewUtil;

	}

	public Account getAccountByName(String username) {
		return accountFactory.getFullAccountForUsername(username);
	}

	public Account getAccountByEmail(String email) {
		return accountFactory.getFullAccountForEmail(email);
	}

	public PageIterator getAccountByNameLike(String username, int start, int count) {
		return accountRepository.getAccountByNameLike(username, start, count);
	}

	public Account getAccount(Long userId) {
		return accountFactory.getFullAccount(userId.toString());
	}

	/**
	 * init the account
	 */
	public Account initAccount(EventModel em) {
		return new Account();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.framework.samples.jpetstore.service.AccountService#insertAccount
	 * (com.jdon.framework.samples.jpetstore.domain.Account)
	 */

	public void createAccount(EventModel em) {
		Account account = (Account) em.getModelIF();
		Debug.logVerbose("createAccount username=" + account.getUsername(), module);
		try {
			if (accountFactory