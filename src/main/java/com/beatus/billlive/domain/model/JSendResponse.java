package com.beatus.billlive.domain.model;

public class JSendResponse<T> {
	
	private String status;
	private T data;
	
	public JSendResponse() {
	}
	
	public JSendResponse(String status, T data) {
		this.status = status;
		this.data = data;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
