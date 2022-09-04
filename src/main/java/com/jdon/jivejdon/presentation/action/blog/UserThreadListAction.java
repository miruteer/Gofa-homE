
/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.presentation.action.blog;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.presentation.form.AccountProfileForm;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;

public class UserThreadListAction extends ModelListAction {
	private final static Logger logger = LogManager.getLogger(UserThreadListAction.class);
	private ForumMessageQueryService forumMessageQueryService;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	@Override
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		AccountProfileForm accountProfileForm = (AccountProfileForm) FormBeanUtil.lookupActionForm(request, "accountProfileForm");
		if (accountProfileForm == null) {
			logger.debug("not found accountProfileForm");
			return new PageIterator();
		}
		String userId = accountProfileForm.getUserId();

		PageIterator pi = getForumMessageQueryService().getThreadListByUser(userId, start, count);
		return pi;
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumThread thread = null;
		try {
			thread = getForumMessageQueryService().getThread((Long) key);
		} catch (Exception e) {
			logger.error("getThread error:" + e);
		}
		return thread;
	}

}