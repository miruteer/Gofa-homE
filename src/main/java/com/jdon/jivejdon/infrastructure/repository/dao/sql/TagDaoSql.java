/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.TaggedThreadListSpec;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.dao.TagDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.model.query.PageIteratorSolver;

public class TagDaoSql implements TagDao {
	private final static Logger logger = LogManager.getLogger(TagDaoSql.class);

	private PageIteratorSolver pageIteratorSolver;

	private JdbcTempSource jdbcTempSource;

	private SequenceDao sequenceDao;

	public TagDaoSql(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil, SequenceDao sequenceDao) {
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
		this.jdbcTempSource = jdbcTempSource;
		this.sequenceDao = sequenceDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.sql.TagDao#createThreadTag(com.jdon.jivejdon.domain.model
	 * .ThreadTag)
	 */
	public void createThreadTag(ThreadTag threadTag) throws Exception {
		try {
			String ADD_SQL = "INSERT INTO tag(tagID, title, assonum)" + " VALUES (?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(threadTag.getTagID());
			queryParams.add(threadTag.getTitle());
			queryParams.add(threadTag.getAssonum());

			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SQL);
			clearCache();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.sql.TagDao#delThreadTag(com.jdon.jivejdon.domain.model
	 * .ForumThread)
	 */
	public void delThreadTag(Long threadID) throws Exception {
		String SQL = "DELETE FROM threadTag WHERE threadID=?";
		List queryParams = new ArrayList();
		queryParams.add(threadID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
		clearCache();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.sql.TagDao#addThreadTag(com.jdon.jivejdon.domain.model
	 * .ThreadTag, com.jdon.jivejdon.domain.model.ForumThread)
	 */
	public void addThreadTag(Long tagID, Long threadID) throws Exception {
		String SQL = "INSERT INTO threadTag(threadTagID, threadID, tagID)" + " VALUES (?,?,?)";
		List queryParams = new ArrayList();
		Long threadTagID = sequenceDao.getNextId(Constants.OTHERS);
		queryParams.add(threadTagID);
		queryParams.add(threadID);
		queryParams.add(tagID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
		clearCache();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.dao.sql.TagDao#delThreadTag(com.jdon.jivejdon.domain.model
	 * .ThreadTag, com.jdon.jivejdon.domain.model.ForumThread)
	 */
	public void delThreadTag(Long tagID, Long threadID) throws Exception {
		String SQL = "DELETE FROM threadTag WHERE threadID=? and tagID =?";
		List queryParams = new ArrayList();
		queryParams.add(threadID);
		queryParams.add(tagID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);
		clearCache();
	}

	public void deleteThreadTag(Long tagID) throws Exception {
		String SQL = "DELETE FROM tag WHERE  tagID =?";
		List queryParams = new ArrayList();
		queryParams.add(tagID);
		jdbcTempSource.getJdbcTemp().operate(queryParams, SQL);

		String SQL2 = "DELETE FROM threadTag WHERE  tagID =?";
		List queryParams2 = new ArrayList();
		queryParams2.add(tagID);
		jdbcTempSource.getJdbcTemp().operate(queryParams2, SQL2);

		clearCache();
	}

	public boolean checkThreadTagRelation(Long tagID, Long threadID) {
		String LOAD_SQL = "SELECT threadTagID FROM threadTag WHERE threadID=? and tagID =?";
		List queryParams = new ArrayList();
		queryParams.add(threadID);
		queryParams.add(tagID);
		boolean ret = false;
		try {
			List list