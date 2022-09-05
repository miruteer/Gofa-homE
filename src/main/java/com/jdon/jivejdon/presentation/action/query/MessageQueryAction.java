package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.*;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.query.MultiCriteria;
import com.jdon.jivejdon.domain.model.query.QueryCriteria;
import com.jdon.jivejdon.presentation.form.QueryForm;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

/**
 * used for threadViewQuery.shtml?queryType=messageQueryAction
 * 
 * @author banq
 * 
 */
public class MessageQueryAction extends ModelListAction {
	private final static Logger logger = LogManager.getLogger(ThreadQueryAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		logger.debug("enter MessageQueryAction ....");
		QueryForm qForm = (QueryForm) FormBeanUtil.lookupActionForm(request, "queryForm");
		if (qForm == null || UtilValidate.isEmpty(qForm.getFromDate()) || UtilValidate.isEmpty(qForm.getToDate())
				|| UtilValidate.isEmpty(qForm.getUsername())) {
			logger.error(" ThreadQueryForm is null, please at first call com.jdon.jivejdon.presentation.action.query.QueryViewAction");
			return new PageIterator();
		}
		// save queryCriteria for html:link multi params
		request.setAttribute("paramMaps", qForm.getParamMaps());

		QueryCriteria queryCriteria = create(qForm);
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService",
				this.servlet.getServletContext());
		return forumMessageQueryService.getMessages(queryCriteria, start, count);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", this.servlet.getServletContext());
		logger.debug(" key calss type = " + key.getClass().getName());
		// at first call getXXX, this will activer jdon f