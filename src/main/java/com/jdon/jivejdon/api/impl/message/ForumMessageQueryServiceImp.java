package com.jdon.jivejdon.api.impl.message;

import com.jdon.annotation.Service;
import com.jdon.annotation.Singleton;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.spi.component.query.HotThreadQueryManager;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.MessageSearchSpec;
import com.jdon.jivejdon.domain.model.query.MultiCriteria;
import com.jdon.jivejdon.domain.model.query.QueryCriteria;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.builder.ForumAbstractFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.visitor.TreeNodePicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Singleton
@Service("forumMessageQueryService")
public class ForumMessageQueryServiceImp implements ForumMessageQueryService {
	private final static Logger logger = LogManager.getLogger(ForumMessageQueryServiceImp.class);

	protected final MessageQueryDao messageQueryDao;

	protected final AccountFactory accountFactory;

	protected final HotThreadQueryManager queryManager;

	protected final ForumFactory forumBuilder;

	protected final ForumAbstractFactory forumAbstractFactory;

	public ForumMessageQueryServiceImp(MessageQueryDao messageQueryDaoy, AccountFactory accountFactory, HotThreadQueryManager queryManager,
			ForumFactory forumBuilder, ForumAbstractFactory forumAbstractFactory) {
		this.accountFactory = accountFactory;
		this.queryManager = queryManager;
		this.messageQueryDao = messageQueryDaoy;
		this.forumBuilder = forumBuilder;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public ForumMessage getMessage(Long messageId) {
		if (messageId == null)
			return null;
		return forumAbstractFactory.getMessage(messageId);

	}

	/**
	 * get a message Collection of the parentMessage. we use TreeModel
	 * implements it .
	 */
	public PageIterator getRecursiveMessages(Long messageId, int start, int count) {
		logger.debug("enter getRecursiveMessages");
		logger.debug("enter getRecursiveMessages, start=" + start + " count=" + count);
		ForumMessage forumMessage = forumBuilder.getMessage(messageId);
		if (forumMessage == null) {
			logger.error("the messageId  don't existed: " + messageId);
			return new PageIterator();
		}
		List sublist = null;
		try {
			List childernList = getRecursiveChildren(forumMessage);
			// 2. get a sub list from the all List by start and count
			logger.debug(" get the sub-collection for start=" + start + " childernList size" + childernList.size());
			int end = start + count;
			sublist = childernList.subList(start, (end < childernList.size()) ? end : childernList.size());
			return new PageIterator(childernList.size(), sublist.toArray());
		} catch (Exception e) {
			logger.error(e);
		}
		return new PageIterator();
	}

	protected List getRecursiveChildren(ForumMessage forumMessage) {
		List list = null;
		try {
			TreeVisitor messagePicker = new TreeNodePicker();
			forumMessage.getForumThread().acceptTreeModelVisitor(forumMessage.getMessageId(), messagePicker);
			list = ((TreeNodePicker) messagePicker).getResult();
			list.remove(forumMessage.getMessageId()); // remove the parent
		} catch (Exception e) {
			logger.error(e);
		}
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co