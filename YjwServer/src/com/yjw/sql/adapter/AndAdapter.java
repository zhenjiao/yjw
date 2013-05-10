package com.yjw.sql.adapter;

public class AndAdapter extends WhereAdapter{

	private WhereAdapter[] adapters;
	public AndAdapter(WhereAdapter...adapters) {
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
			else s+=" AND ";
			s+=adapter;
		}
		s+=")";
		return s;
	}
}
