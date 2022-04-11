package com.jdon.jivejdon.auth;

import com.jdon.jivejdon.domain.model.auth.PermissionRule;

public class OperationAuthorization {
	private PermissionRule permissionRule;

	private PermissionXmlParser permissionXmlParser;

	public OperationAuthorization(PermissionXmlParser permissionXmlParser) {
		this.permiss