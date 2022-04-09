/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.api.impl.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Pattern;

import com.jdon.annotation.Service;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.controller.pool.Poolable;
import com.jdon.jivejdon.api.property.TagService;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.HotKeys;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.query.specification.TaggedThreadListSpec;
import com.jdon.jivejdon.domain.model.thread.ThreadTagsVO;
import com.jdon.jivejdon.domain.model.util.OneOneDTO;
import com.jdon.jivejdon.infrastructure.repository.MessageRepository;
import com.jdon.jivejdon.infrastructure.repository.property.HotKeysRepository;
import com.jdon.jivejdon.infrastructure.repository.property.TagRepository;
import com.jdon.jivejdon.util.Constants;
import com.jdon.util.UtilValidate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service("othersService")
public class TagServiceImp implements TagService, Poolable {
	private final static Logger logger = LogManager.getLogger(TagServiceImp.class);
	private final static Pattern illEscape = Pattern
			.compile("[^\\/|^\\<|^\\>|^\\=|^\\?|^\\\\|^\\s|^\\(|^\\)|^\\{|^\\}|^\\[|^\\]|^\\+|^\\*]*");
	private final HotKeysRepository hotKeysRepository;
	private final TagRepository tagRepository;
	private final MessageRepository messageRepository;

	public TagServiceImp(HotKeysRepository hotKeysFactory, TagRepository tagRepository,
			MessageRepository messageRepository) {
		super();
		this.hotKeysRepository = hotKeysFactory;
		this.tagRepository = tagRepository;
		this.messageRepository = messageRepository;
	}

	public Collection tags(String s) {
		Collection tags = new ArrayList();
		if (UtilValidate.isEmpty(s))
			return tags;
		if (s.length() > 10)
			return tags;
		if (!illEscape.matcher(s).matches())
			return tags;

		for (Long tagID : tagRepository.searchTitle(s)) {
			ThreadTag tag = tagRepository.getThreadTag(tagID);
			tags.add(tag);
		}
		return tags;
	}

	public PageIterator getTaggedThread(TaggedThreadListSpec taggedThreadListSpec, int start, int count) {
		return tagRepository.getTaggedThread(taggedThreadListSpec, start, count);
	}

	public PageIterator getTaggedRandomThreads(TaggedThreadListSpec taggedThreadListSpec, int start, int c