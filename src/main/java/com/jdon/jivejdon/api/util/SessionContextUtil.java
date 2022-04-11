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
package com.jdon.jivejdon.api.util;

import com.jdon.jivejdon.domain.model.account.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.container.visitor.data.SessionContext;
import com.jdon.container.visitor.data.SessionContextSetup;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.util.ContainerUtil;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class SessionContextUtil {
	private final static Logger logger = LogManager.getLogger(SessionContextUtil.class);
	public static final String ACCOUNT = "Account";

	protected final AccountFactory accountFactory;
	private ContainerUtil containerUtil;

	/**
	