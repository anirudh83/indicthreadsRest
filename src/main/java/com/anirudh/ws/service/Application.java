package com.anirudh.ws.service;

import java.util.Collections;
import java.util.Set;

public abstract class Application extends javax.ws.rs.core.Application{
	private static final Set<Object> emptySet = Collections.emptySet();
	
	public abstract Set<Class<?>> getClasses();
	
	public Set<Object> getSingletons(){
		return emptySet;
	}

}
