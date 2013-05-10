package com.yjw.sql.adapter;

public abstract class WhereAdapter {
	abstract String condition();
	@Override
	public String toString() {
		return condition();
	}	
}
