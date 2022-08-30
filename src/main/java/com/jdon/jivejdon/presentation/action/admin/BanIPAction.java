package com.jdon.jivejdon.presentation.action.admin;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.spi.component.block.IPBanListManagerIF;
import com.jdon.util.UtilValidate;

public class BanIPAction extends Action {
	private final static Logger logger = LogManager.getLogger(BanIPAction.class);
	public fin