
package com.jdon.jivejdon.presentation.action.query;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadApprovedNewList;
import com.jdon.jivejdon.spi.component.mapreduce.ThreadDigList;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class ThreadDigSortedListAction extends ModelListAction {
	private ForumMessageQueryService forumMessageQueryService;
	private ThreadApprovedNewList threadApprovedNewList;

	public ForumMessageQueryService getForumMessageQueryService() {
		if (forumMessageQueryService == null)
			forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService
					("forumMessageQueryService", this.servlet.getServletContext());
		return forumMessageQueryService;
	}

	public ThreadApprovedNewList getThreadApprovedNewList() {
		if (threadApprovedNewList == null)
			threadApprovedNewList = (ThreadApprovedNewList) WebAppUtil
					.getComponentInstance("threadApprovedNewList", this.servlet.getServletContext
							());

		return threadApprovedNewList;
	}

	@Override
	public PageIterator getPageIterator(HttpServletRequest httpServletRequest, int start, int
			count) {

		ThreadDigList messageDigList = getThreadApprovedNewList().getThreadDigList();