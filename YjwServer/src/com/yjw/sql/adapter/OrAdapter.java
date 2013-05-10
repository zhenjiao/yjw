package com.yjw.sql.adapter;

public class OrAdapter extends WhereAdapter {

	private WhereAdapter[] adapters;
	public OrAdapter(WhereAdapter...adapters) {
		this.adapters=adapters;
	}

	@Override
	String condition() {
		String s="";
		boolean start=true;
		for (WhereAdapter adapter:adapters){
			if (start){
				s+="("; 
				start=false;
			}
			else s+=" OR ";
			s+=adapter;
		}
		s+=")";
		return s;
	}

}
