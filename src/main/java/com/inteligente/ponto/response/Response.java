package com.inteligente.ponto.response;

import java.util.List;
import java.util.ArrayList;

public class Response<T> {
	private T Data;
	private List<String> Errors;

	public Response() {

	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		this.Data = data;
	}

	public List<String> getErrors() {
		if (this.Errors == null) {
			this.Errors = new ArrayList<String>();
		}

		return this.Errors;
	}

	public void setErrors(List<String> errors) {
		this.Errors = errors;
	}
}