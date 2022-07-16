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

import org.apache.logging.log4j.*;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.infrastructure.repository.builder.MessageInitFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.ForumDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public abstract class ForumDaoSql implements ForumDao {
	private final static Logger logger = LogManager.getLogger(ForumDaoSql.class);

	protected PageIteratorSolver pageIteratorSolver;

	private JdbcTempSource jdbcTempSource;

	private PropertyDaoSql propertyDaoSql;

	private Constants constants;

	private MessageInitFactory messageFactory;

	public ForumDaoSql(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, MessageInitFactory messageFactory, Constants constants) {
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.propertyDaoSql = new PropertyDaoSql(jdbcTempSource, containerUtil);
		this.constants = constants;
		this.messageFactory = messageFactory;
	}

	public void clearCache() {
		pageIteratorSolver.clearCache();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.ForumDao#getForum(java.lang.String)
	 */
	public Forum getForum(Long id) {
		logger.debug("enter getForum for id:" + id);
		String LOAD_FORUM = "SELECT forumID, name, description, modDefaultThreadVal, "
				+ "modDefaultMsgVal, modMinThreadVal, modMinMsgVal, modifiedDate, " + "creationDate FROM jiveForum WHERE forumID=?";
		List queryParams = new ArrayList();
		queryParams.add(id);

		Forum ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_FORUM);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret = messageFactory.createForumCore(map);
				ret.setPropertys(propertyDaoSql.getProperties(Constants.FORUM, id));
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.ForumDao#createForum(com.jdon.jivejdon.domain.model.Forum)
	 */
	public void createForum(Forum forum) {
		try {
			String ADD_FORUM = "INSERT INTO jiveForum(forumID, name, description, modDefaultThreadVal, "
					+ "modDefaultMsgVal, modMinThreadVal, modMinMsgVal, modifiedDate, creationDate)" + " VALUES (?,?,?,?,?,?,?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(forum.getForumId());
			queryParams.add(forum.getName());
			queryParams.add(forum.getDescription());
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			queryParams.add(saveDateTime);
			forum.setModifiedDate(displayDateTime);
			queryParams.add(saveDateTime);
			forum.setCreationDate(displayDateTime);

			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_FORUM);
			clearCache();

			propertyDaoSql.saveProperties(Constants.FORUM, forum.getForumId(), forum.getPropertys());
		} catch (Exception e) {
			logger.error(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.dao.ForumDao#updateForum(com.jdon.jivejdon.domain.model.Forum)
	 */
	public void updateForum(Forum forum) {
		try {
			String SAVE_FORUM = "UPDATE jiveForum SET name=?, description=?, modDefaultThreadVal=?, "
					+ "modDefaultMsgVal=?, modMinThreadVal=?, modMinMsgVal=?, " + "modifiedDate=? WHERE forumID=?";
			List queryParams = new ArrayList();
			queryParams.add(forum.getName());
			queryParams.add(forum.getDescription());
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));
			queryParams.add(new Integer(0));

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			queryParams.add(saveDateTime);
			forum.setModifiedDate(displayDateTime);

			queryParams.add(forum.getForumId());
			jdbcTempSource.getJdbcTemp().operate(queryParams, SAVE_FORUM);
			clearCache();

			propertyDaoSql.deleteProperties(Constants.FORUM, foru