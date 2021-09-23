package com.sk.config;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.netflix.zuul.http.ServletInputStreamWrapper;

public class CustomHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] bytes;

    public CustomHttpServletRequest(final HttpServletRequest request, byte[] bytes) {
        super(request);
        this.bytes = bytes;
    }

    @Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStreamWrapper(bytes);
	}

	@Override
	public int getContentLength() {
		return bytes.length;
	}

	@Override
	public long getContentLengthLong() {
		return bytes.length;
	}
	
	@Override
    public String getMethod() {
        return "POST";
    }

}

