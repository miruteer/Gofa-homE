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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.account.PasswordassitVO;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountRepository;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountInitFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.AccountDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 * 
 */
@Introduce("modelCache")
public class AccountDaoSql implements AccountDao, AccountRepository {
	private final static Logger logger = LogManager.getLogger(AccountDaoSql.class);

	private JdbcTempSource jdbcTempSource;

	private PageIteratorSolver pageIteratorSolver;

	private AccountSSOSql accountSSOSql;

	private Constants constants;

	private AccountInitFactory accountInitFactory;

	public AccountDaoSql(JdbcTempSource jdbcTempSource, AccountSSOSql accountSSOSql, ContainerUtil containerUtil,
			AccountInitFactory accountInitFactory, Constants constants) {
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.accountSSOSql = accountSSOSql;
		this.accountInitFactory = accountInitFactory;
		this.constants = constants;
	}

	/*
	 * get the account from database, we cache the account object by using
	 * SessionContext
	 */
	@Around()
	public Account getAccount(String userId) {
		logger.debug("enter getAccount");
		String LOAD_USER_BY_ID = "select userID,username,passwordHash,name,nameVisible,email,emailVisible,"
				+ "rewardPoints,creationDate,modifiedDate from jiveUser where userID=?";
		List queryParams = new ArrayList();
		queryParams.add(new Long(userId));
		Account account = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_USER_BY_ID);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				logger.debug("found the account");
				Map map = (Map) iter.next();
				// String rolename =
				// this.accountSSOSql.getRoleNameByUserId(userId.toString());
				// if (UtilValidate.isEmpty(rolename)) {
				// logger.error("role is null, userid=" + userId);
				// }
				// account.setRoleName(rolename);
				account = accountInitFactory.create(map);

			}
		} catch (Exception se) {
			logger.error(se);
		}
		return account;
	}

	public Account getAccount(String username, String password) {
		String AUTHORIZE = "select userID from jiveUser where username=? and passwordHash=?";
		List queryParams = new ArrayList();
		queryParams.add(username);
		queryParams.add(password);
		Account account = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, AUTHORIZE);
			Iterator iter = list.iterator();
			String userId = null;
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				userId = ((Long) map.get("userID")).toString();
			}
			if ((userId != null && (userId.length() != 0)))
				account = getAccount(userId);

		} catch (Exception se) {
			logger.error(se);
		}
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.AccountDao#getAccountByName(java.lang.String)
	 */
	public Long fetchAccountByName(String username) {
		logger.debug("enter getAccountByName for username=" + username);
		String LOAD_USER_BY_USERNAME = "select userID from jiveUser where username=?";
		List queryParams = new ArrayList();
		queryParams.add(username);
		Long userId = null;
		try {
			Object result = jdbcTempSource.getJdbcTemp().querySingleObject(queryParams, LOAD_USER_BY_USERNAME);
			logger.debug(" getAccountByName for username=" + username + " result=" + result);
			if (result != null && result instanceof Long)
				userId = (Long) result;
			else {
				logger.error(" result error for username=" + username + " result=" + result);
			}
		} catch (Exception e) {
			logger.error("username=" + username + e);
		}
		return userId;
	}

	public Long fetchAccountByEmail(String email) {
		logger.debug("enter getAccountByName");
		String LOAD_USER_BY_EMAIL = "select userID from jiveUser where email=?";
		List queryParams = new ArrayList();
		queryParams.add(email);
		Long userId = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_USER_BY_EMAIL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				logger.debug("found the account");
				Map map = (Map) iter.next();
				userId = (Long) map.get("userID");

