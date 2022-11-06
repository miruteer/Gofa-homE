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
package com.jdon.jivejdon.spi.component.account;

import static net.oauth.OAuth.HMAC_SHA1;
import static net.oauth.OAuth.OAUTH_SIGNATURE_METHOD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.ParameterStyle;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient3.HttpClient3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weibo4j.org.json.JSONObject;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.spi.component.weibo.UserConnectorAuth;
import com.jdon.jivejdon.domain.model.account.OAuthUserVO;
import com.jdon.jivejdon.infrastructure.repository.acccount.Userconnector;
import com.jdon.util.UtilValidate;

@Component("googleOAuthSubmitter")
public class GoogleOAuthSubmitter {
	private final static Logger logger = LogManager.getLogger(GoogleOAuthSubmitter.class);

	public static final OAuthClient CLIENT = new OAuthClient(new HttpClient3());

	protected Userconnector userconnector;

	protected final GoolgeOAuthParamVO goolgeOAuthParamVO;

	public GoogleOAuthSubmitter(Userconnector userconnector, GoolgeOAuthParamVO goolgeOAuthParamVO) {
		super();
		this.userconnector = userconnector;
		this.goolgeOAuthParamVO = goolgeOAuthParamVO;
	}

	public void saveSinaWeiboAuth(UserConnectorAuth userConnectorAuth) {
		if (userConnectorAuth.getAccessToken() != null) {
			userconnector.saveUserConnectorAuth(userConnectorAuth);
		}

	}

	public OAuthAccessor request(String backUrl) {
		OAuthConsumer consumer = new OAuthConsumer(backUrl, goolgeOAuthParamVO.CONSUMER_KEY, goolgeOAuthParamVO.CONSUMER_SECRET,
				goolgeOAuthParamVO.oAuthServiceProvider);
		OAuthAccessor accessor = new OAuthAccessor(consumer);
		try {
			List<OAuth.Parameter> parameters = OAuth.newList(OAuth.OAUTH_CALLBACK, backUrl);
			parameters.add(new OAuth.Parameter("scope", goolgeOAuthParamVO.scope));
			CLIENT.getRequestTokenResponse(accessor, null, parameters);
		} catch (Exception e) {
			logger.error("request error:" + e);
		}
		return accessor;
	}

	public OAuthAccessor requstAccessToken(OAuthAccessor accessor, String oauthVerifier) throws Exception {
		OAuthMessage result = null;
		try {
			List<OAuth.Parameter> parameters = OAuth.newList(OAuth.OAUTH_VERIFIER, oauthVerifier