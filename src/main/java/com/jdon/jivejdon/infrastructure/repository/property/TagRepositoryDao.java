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
package com.jdon.jivejdon.infrastructure.repository.property;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.TaggedThreadListSpec;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.dao.TagDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TagRepositoryDao implements TagRepository {

	private TagDao tagDao;

	private SequenceDao sequenceDao;

	public TagRepositoryDao(TagDao tagDao, SequenceDao sequenceDao) {
		this.tagDao = tagDao;
		this.sequenceDao = sequenceDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#searchTitle(java.lang.String)
	 */
	public Collection<Long> searchTitle(String s) {
		return tagDao.searchTitle(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#getTaggedThread(com.jdon.jivejdon
	 * .model.query.specification.TaggedThreadListSpec, int, int)
	 */
	public PageIterator getTaggedThread(TaggedThreadListSpec taggedThreadListSpec, int start, int count) {
		return tagDao.getTaggedThread(taggedThreadListSpec, start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.infrastructure.repository.property.TagRepository#getThreadTags(int, int)
	 */
	public PageIterator getThreadTags(int start, int count) {
		return tagDao.getThreadTags(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#getThreadTag(java.lang.Long)
	 */
	public ThreadTag getThreadTag(Long tagID) {
		ThreadTag tag = tagDao.getThreadTag(tagID);
		return tag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.property.TagRepository#addTagTitle(java.lang.Long,
	 * java.lang.String[])
	 */
	public void insertTagTitle(Long threadId, String[] tagTitle) throws Exception {
		if ((tagTitle == null) || (tagTitle.length == 0)) {
			return;
		}
		for (int i = 0; i < tagTitle.length; i++) {
			if ((tagTitle[i] != null) && (tagTitle[i].length() != 0))
				insert(threadId, tagTitle[i]);
		}

	}

	private void insert(Long fourmThreadId, String tagTitle) throws Exception {
		// tagTitle = tagTitle.replace(" ", "").toLowerCase();
		ThreadTag threadTag = tagDao.getThreadTagByTitle(tagTitle);
		if (threadTag != null) {
			// if threadTag is not belong to the thread, add one to it.
			if (!tagDao.checkThreadTagRelation(threadTag.getTagID(), fourmThreadId)) {
				threadTag.setAssonum(threadTag.getAssonum() + 1);
				updateThreadTag(threadTag);
				tagDao.addThr