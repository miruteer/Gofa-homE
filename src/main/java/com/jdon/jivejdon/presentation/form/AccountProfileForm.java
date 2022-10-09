package com.jdon.jivejdon.presentation.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.property.Property;

public class AccountProfileForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private Account account;

	private String signature;

	private Collection propertys;

	private int max