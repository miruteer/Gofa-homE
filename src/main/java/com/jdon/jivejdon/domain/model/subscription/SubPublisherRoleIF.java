
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
package com.jdon.jivejdon.domain.model.subscription;

import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.domain.model.subscription.event.SubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.ThreadSubscribedCreateEvent;

public interface SubPublisherRoleIF {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.domain.model.subscription.SubPublisherRoleIF#subscriptionNotify
	 * (com.jdon.jivejdon.domain.model.subscription.subscribed.Subscribed)
	 */
	@Send("subscriptionSender")
	public abstract DomainMessage subscriptionNotify(SubscribedNotifyEvent subscribedNotifyEvent);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.domain.model.subscription.SubPublisherRoleIF#createSubscription
	 * (com.jdon.jivejdon.domain.model.ForumMessage)
	 */
	@Send("createSubscription")
	public abstract DomainMessage createSubscription(ThreadSubscribedCreateEvent threadSubscribedEvent);

}