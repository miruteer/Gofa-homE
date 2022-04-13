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
package com.jdon.jivejdon.domain.event;

import com.jdon.jivejdon.domain.model.attachment.UploadFile;

import java.util.Collection;

public class UploadFilesAttachedEvent {

	private final Long messageId;

	private final Collection<UploadFile> uploads;

	public UploadFilesAttachedEvent(Long messageId, Collection<UploadFile> uploads) {
		super();
		this.messageId = messageId;
		this.uploads = uploads;
	}

	public Long getMessageId() {
		return messageId;
	}

	public Collection<UploadFile> getUploads() {
		return uploads;
	}

}
