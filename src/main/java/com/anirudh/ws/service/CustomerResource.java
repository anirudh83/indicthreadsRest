package com.anirudh.ws.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.anirudh.ws.domain.Customer;


/**
 * 
 * @author anirudh
 *
 */
@Path("/customers")
public class CustomerResource {
	
	private Map<Integer,Customer> customerDB = new ConcurrentHashMap<Integer,Customer>();
	
	private AtomicInteger idCounter = new AtomicInteger();
	
	@GET
	@Produces("text/html")
	public String getIndex(){
		return "this is customers restful services.Add Id to retrieve customer";
	}
	
	@POST
	@Consumes("application/xml")
	public void createCustomer(Customer customer){
		customer.setId(idCounter.incrementAndGet());
		customerDB.put(customer.getId(),customer);
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml") // This annotation tells JAX-RS which content-type GET reponse will be.
	//PathParam will take care to convert String id coming from URL into int.
	public Customer getCustomer(@PathParam("id") int id){
		makeMockCustomer();
		return customerDB.get(id);
	}
	
	
	/** This will not work as we have not added Jackson support */
	@GET
	@Path("{id}/json")
	@Produces("application/json") // This annotation tells JAX-RS which content-type GET reponse will be.
	//PathParam will take care to convert String id coming from URL into int.
	public Customer getCustomerJSon(@PathParam("id") int id){
		makeMockCustomer();
		return customerDB.get(id);
	}
	
	
	private void makeMockCustomer() {
		Customer cust1 = new Customer();
		cust1.setId(1);
		cust1.setFirstName("frst1");
		cust1.setLastName("last1");
		cust1.setCity("New York");
		customerDB.put(1, cust1);
	}
	
	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void updateCustomer(@PathParam("id") int id,Customer customer){
		
		Customer current = customerDB.get(id);
		if(current == null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		//copy attributes from updated to current
		current.setFirstName(customer.getFirstName());
		current.setLastName(customer.getLastName());
		current.setStreet(customer.getStreet());
		current.setCity(customer.getCity());
		current.setZip(customer.getZip());
		current.setCountry(customer.getCountry());
		
	}

}
