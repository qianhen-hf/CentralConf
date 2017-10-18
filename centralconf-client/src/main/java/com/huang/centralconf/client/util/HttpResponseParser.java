package com.huang.centralconf.client.util;

@SuppressWarnings("ALL")
public interface HttpResponseParser<T> {

	T parse(String content);
}
