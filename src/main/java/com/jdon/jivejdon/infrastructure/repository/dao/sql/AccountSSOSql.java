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
package com.jdon.jivejdon.infrastructure.repository.dao.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jdon.jivejdon.domain.model.account.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.PasswordassitVO;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class AccountSSOSql {
	private final static Logger logger = LogManager.getLogger(AccountSSOSql.class);

	private JdbcTempSource jdbcTempSSOSource;
	private SequenceDao sequenceDao;

	/**
	 * @param jdbcTempSSOSource
	 */
	public AccountSSOSql(JdbcTempSource jdbcTempSSOSource, SequenceDao sequenceDao) {
		this.jdbcTempSSOSource = jdbcTempSSOSource;
		this.sequenceDao = sequenceDao;
	}

	public String getRoleNameByUserId(String userId) {
		logger.debug("enter getRoleName for userId=" + userId);
		String SQL = "SELECT RL.name FROM role as RL,  users_roles as RU WHERE RU.roleid = RL.roleid  and RU.userId = ?";
		//String SQL = "SELECT name FROM role where ro