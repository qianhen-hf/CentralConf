package com.huang.centralconf.client.util;

public interface HttpResponseParser<T> {

	T parse(String content);
}
