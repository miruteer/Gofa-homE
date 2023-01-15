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
package com.jdon.jivejdon.spi.pubsub.reconstruction.impl;

import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.jdon.domain.dci.RoleAssigner;
import com.jdon.jivejdon.domain.model.attachment.UploadFile;
import com.jdon.jivejdon.infrastructure.repository.dao.UploadFileDao;
import com.jdon.jivejdon.infrastructure.repository.property.UploadRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Component
public class LoadUploadFiles {
	private final static Logger logger = LogManager.getLogger(LoadUploadFiles.class);

	private final UploadRepository uploadRepository;

	private final UploadFileDao uploadFileDao;

	private final RoleAssigner roleAssigner;

	public LoadUploadFiles(UploadRepository uploadRepository, RoleAssigner roleAssigner, UploadFileDao uploadFileDao) {
		super();
		this.uploadRepository = uploadRepository;
		this.roleAssigner = roleAssigner;
		this.uploadFileDao = uploadFileDao;
	}

	@OnEvent("loadUploadFile")
	public UploadFile loadOneUploadFile(String parentId) {
		UploadFile uploadFile = null;
		try {
			Collection uploads = uploadRepository.getUploadFiles(parentId);

			Iterator iter = uploads.iterator();
			if (iter.hasNext()) {
				uploadFile = (UploadFile) iter.next();
				roleAssigner.assignDomainEvents(uploadFile);
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return uploadFile;
	}

	@OnEvent("loadUploadFiles")
	public Collection loadUploadFiles(String parentId) {
		Collection<UploadFile> uploa