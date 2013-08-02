package com.anirudh.ws.service;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author anirudh
 *
 */
public class ShoppingApplication extends javax.ws.rs.core.Application {
	
	private Set<Object> singeltons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	
	public ShoppingApplication(){
		singeltons.add(new CustomerResource());
	}
	
	public Set<Class<?>> getClasses(){
		return empty;
	}
	
	public Set<Object> getSingletons(){
		return singeltons;
	}

}
