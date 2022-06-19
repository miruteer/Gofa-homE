
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
package com.jdon.jivejdon.infrastructure.repository.builder;

import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageDao;
import com.jdon.jivejdon.infrastructure.repository.dao.PropertyDao;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.property.TagRepository;
import com.jdon.jivejdon.util.ContainerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ForumAbstractFactory implements ForumFactory {
	private final static Logger logger = LogManager.getLogger(ForumAbstractFactory.class);

	public final ForumDirector forumDirector;
	public final MessageDirectorIF messageDirectorIF;
	public final ThreadDirectorIF threadDirectorIF;

	protected final ContainerUtil containerUtil;

	private final SequenceDao sequenceDao;

	private final Map<Long,Integer> nullthreads ;

	// define in component.xml

	public ForumAbstractFactory(MessageDirectorIF messageDirectorIF,ThreadDirectorIF threadDirectorIF, ForumDirector forumDirector,
								ContainerUtil containerUtil, SequenceDao sequenceDao, MessageDao
										messageDao, TagRepository tagRepository, PropertyDao propertyDao) {
		this.containerUtil = containerUtil;
		this.messageDirectorIF = messageDirectorIF;
		this.messageDirectorIF.setThreadDirector(threadDirectorIF);
		this.sequenceDao = sequenceDao;
		this.forumDirector = forumDirector;
		this.threadDirectorIF = threadDirectorIF;
		this.nullthreads = lruCache(100);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.builder.ForumFactory#getForum(java.lang.
	 * Long)
	 */
	public Forum getForum(Long forumId) {
		return  forumDirector.getForum(forumId);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.builder.ForumFactory#getMessage(java.lang
	 * .Long)
	 */
	public ForumMessage getMessage(Long messageId) {
		if (messageId == null){
			return null;
		}
		ForumMessage forumMessage = null;
		try{
		    forumMessage =  messageDirectorIF.getMessage(messageId);
		} catch (Exception e) {
			logger.error("messageId="+ messageId + " is null");
		}
		return forumMessage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.builder.ForumFactory#getThread(java.lang
	 * .Long)
	 */
	public Optional<ForumThread> getThread(Long threadId) {
		Object nullthread = nullthreads.get(threadId);
		if (nullthread != null){
			int nullthreadt = (Integer)nullthread;
			if( nullthreadt >10) {
				logger.error("repeat no forumId=" + threadId);
				return Optional.ofNullable(null);
			}
		}
		ForumThread forumThread = null;
		try {
			forumThread = threadDirectorIF.getThread(threadId);
		} catch (Exception e) {
			logger.error("threadId="+ threadId + " is null");
		}finally {
			if (forumThread == null)
				nullthreads.merge(threadId,1, (prev, one) -> prev + one);
		}
		return Optional.ofNullable(forumThread);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.builder.ForumFactory#reloadThreadState(com
	 * .jdon.jivejdon.model.ForumThread)
	 */
	public void reloadThreadState(ForumThread forumThread) throws Exception {
		try {
			forumThread.getState().projectStateFromEventSource();
//			threadBuilder.buildState(forumThread, forumThread.getRootMessage(), messageDirector);
//
//			Forum forum = getForum(forumThread.getForum().getForumId());
//			forumBuilder.buildState(forum);

		} catch (Exception e) {
			String error = e + " refreshAllState forumThread=" + forumThread.getThreadId();
			logger.error(error);
			throw new Exception(error);
		}
	}


	public Long getNextId(final int idType) throws Exception {
		try {
			return sequenceDao.getNextId(idType);
		} catch (Exception e) {
			String error = e + " getNextId ";
			logger.error(error);
			throw new Exception(error);
		}
	}


	private static <K,V> Map<K,V> lruCache(final int maxSize) {
		return Collections.synchronizedMap(new LinkedHashMap<K,V>(maxSize*4/3, 0.75f, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
				return size() > maxSize;
			}
		});
	}

}