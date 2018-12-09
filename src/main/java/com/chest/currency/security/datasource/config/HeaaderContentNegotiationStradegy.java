package com.chest.currency.security.datasource.config;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;

public class HeaaderContentNegotiationStradegy implements ContentNegotiationStrategy {

	@Override
	public List<MediaType> resolveMediaTypes(NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException {
		// TODO Auto-generated method stub
		webRequest.getDescription(false);
		return null;
	}

}
