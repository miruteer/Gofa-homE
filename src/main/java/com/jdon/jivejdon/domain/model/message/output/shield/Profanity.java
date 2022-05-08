/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.domain.model.message.output.shield;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.StringTokenizer;
import java.util.function.Function;

/**
 * A ForumMessageFilter that filters out user-specified profanity.
 */
public class Profanity implements Function<MessageVO, MessageVO> {
	private final static Logger logger = LogManager.getLogger(Profanity.class);

	/**
	 * Array of all the bad words to filter.
	 */
	private String[] words = null;

	/**
	 * Comma delimited list of words to filter.
	 */
	private String wordList = null;

	/**
	 * Indicates if case of words should be ignored.
	 */
	private boolean ignoringCase = true;

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 *
	 */
	public MessageVO apply(MessageVO messageVO) {
		if (words == null)
			return messageVO;
		String subject = messageVO.getSubject();
		String body = messageVO.getBody();
		boolean found = false;
		for (int i = 0; i < words.length; i++) {
			if (subject.indexOf(words[i]) != -1 || body.indexOf(words[i]) != -1) {
				found = true;
				break;
			}
		}
		if (found) {
			// messageVO.setSubject(filterProfanity(messageVO.getSubject()));
			// messageVO.setBody(filterProfanity(messageVO.getBody()));
//			message.setMasked(true);
			return messageVO.builder().subject(Bodymasking.maskLocalization).body
					(Bodymasking.maskLocalization).build();

		} else
			return messageVO;

	}

	/**
	 * Returns ture if the filter will ignore case when filtering out profanity.
	 * For example, when "ignore case" is tur