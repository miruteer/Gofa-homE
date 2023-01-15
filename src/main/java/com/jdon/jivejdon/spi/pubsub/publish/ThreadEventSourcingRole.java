
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
package com.jdon.jivejdon.spi.pubsub.publish;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.command.PostTopicMessageCommand;
import com.jdon.jivejdon.domain.event.TopicMessagePostedEvent;

/**
 * sub-class is implemented by domain pubsub that is in
 * com.jdon.jivejdon.domainevent.subscriber.write.ThreadRole
 * 
 * @author banq
 *
 */
public interface ThreadEventSourcingRole {

	DomainMessage saveTopicMessage(PostTopicMessageCommand command);

	DomainMessage topicMessagePosted(TopicMessagePostedEvent topicMessagePostedEvent);


}