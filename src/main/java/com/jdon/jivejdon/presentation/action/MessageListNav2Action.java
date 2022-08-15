package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.presentation.form.MessageListForm;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.UtilValidate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <%-- /forum/messageNavList.shtml == > MessageListNavAction ==> navf.jsp ==>
 * (urlrewrite.xml)/([0-9]+)/([0-9]+) --%>
 *
 * @author banq
 */
public class MessageListNav2Action extends Action {
	private final static Logger logger = LogManager.getLogger(MessageListNavAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MessageListForm messageListForm = (MessageListForm) FormBeanUtil.lookupActionForm(request, "messageListForm"); // same
		// as struts-config-message.xml
		if (messageListForm == null) {
			logger.error(" MessageListNavAction error : messageListForm is null");
			return mapping.findForward("failure");
		}

		String pMessageId = request.getParameter("pMessage");
		String messageId = request.getParameter("message");
		if ((pMessageId == null) || (!UtilValidate.isInteger(pMessageId)) || messageId == null) {
			logger.error(" MessageListNavAction error : pMessageId or messageId is null");
			return mapping.findForward("failure");
		}

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService",
				this.servlet.getServletContext());

		ForumMessage forumMessageParent = forumMessageService.getMessage(Long.parseLong(pMessageId));
		if (forumMessageParent == null) {
			logger.error(" not locate pMessageId = " + pMessageId);
			return mapping.findForward("failure");
		}

		ForumThread thread = forumMessageParent.getForumThread();
		long threadId = thread.getThreadId();
		// AddReplyMessageZ will update state
		L