
package com.jdon.jivejdon.infrastructure.repository.subscription;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.SequenceDao;
import com.jdon.jivejdon.infrastructure.repository.dao.SubscriptionDao;

import java.sql.SQLException;

@Component
public class SubscriptionRepositoryDao implements SubscriptionRepository {

	private SubscriptionDao subscriptionDao;
	private AccountFactory accountFactory;

	private SequenceDao sequenceDao;
	private SubscribedFactory subscribedFactory;

	public SubscriptionRepositoryDao(SubscriptionDao subscriptionDao, AccountFactory accountFactory, SequenceDao sequenceDao,
			SubscribedFactory subscribedFactory) {
		super();
		this.subscriptionDao = subscriptionDao;
		this.accountFactory = accountFactory;
		this.sequenceDao = sequenceDao;
		this.subscribedFactory = subscribedFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.subscription.SubscriptionRepository#createSubscription
	 * (com.jdon.jivejdon.domain.model.subscription.Subscription)
	 */
	public void createSubscription(Subscription subscription) {
		try {
			Long subscriptionId = this.sequenceDao.getNextId(Constants.SUBSCRIPTION);
			subscription.setSubscriptionId(subscriptionId);
			subscriptionDao.createSubscription(subscription);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.subscription.SubscriptionRepository#getSubscription(java
	 * .lang.Long)
	 */
	public Subscription getSubscription(Long id) {
		Subscription subscription = subscriptionDao.getSubscription(id);
		return getFullSub(subscription);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.subscription.SubscriptionRepository#getFullSub(com.jdon
	 * .jivejdon.model.subscription.Subscription)
	 */
	public Subscription getFullSub(Subscription subscription) {
		Account account = accountFactory.getFullAccount(subscription.getAccount());
		subscription.setAccount(account);
		subscribedFactory.embedFull(subscription);
		return subscription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.subscription.SubscriptionRepository#deleteSubscription
	 * (com.jdon.jivejdon.domain.model.subscription.Subscription)
	 */
	public void deleteSubscription(Subscription subscription) {
		subscriptionDao.deleteSubscription(subscription.getSubscriptionId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.infrastructure.repository.subscription.SubscriptionRepository#getSubscriptionDao()
	 */
	public SubscriptionDao getSubscriptionDao() {
		return subscriptionDao;
	}

}