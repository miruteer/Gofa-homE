package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.spi.component.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.presentation.filter.SpamFilterTooFreq;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * response the ajax request for getting dig count
 * 
 * @author oojdon
 * 
 */
public class MessageDigAction extends Action {

	private CustomizedThrottle customizedThrottle;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// remove search robot
		Pattern robotPattern = (Pattern) this.servlet.getServletContext().getAttribute(SpamFilterTooFreq.BOTNAME);
		if (robotPattern != null && isPermittedRobot(request, robotPattern)) {
			response.sendError(404);
			return null;
		}

		String messageId = request.getParameter("messageId");
		if (UtilValidate.isEmpty(messageId)) {
			response.sendError(404);
			return null;
		}

		if (!checkSpamHit(messageId, request)) {
			response.sendError(404);
			return null;
		}

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil
				.getService("forumMessageService", this.servlet.getServletContext(