package com.anirudh.ws.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
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
import javax.ws.rs.core.StreamingOutput;

import com.anirudh.ws.domain.Customer;
import com.anirudh.ws.util.Util;


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
		return "Hello";
	}
	
	@POST
	@Consumes("application/xml")
	public Response createCustomer(InputStream is){
		Customer customer = new Customer();
		customer.setId(idCounter.incrementAndGet());
		customerDB.put(customer.getId(),customer);
		return Response.created(URI.create("/customers/"
				+customer.getId())).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces("application/xml") // This annotation tells JAX-RS which content-type GET reponse will be.
	//PathParam will take care to convert String id coming from URL into int.
	public StreamingOutput getCustomer(@PathParam("id") int id){
		Customer cust1 = new Customer();
		cust1.setId(1);
		cust1.setFirstName("frst1");
		cust1.setLastName("last1");
		customerDB.put(1, cust1);
		
		final Customer customer = customerDB.get(id);
		if(customer == null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return new StreamingOutput(){
			public void write(OutputStream outputStream) throws IOException,
					WebApplicationException {
				Util.outputCustomer(outputStream, customer);
			}
		};
	}
	
	@PUT
	@Path("{id}")
	@Consumes("application/xml")
	public void updateCustomer(@PathParam("id") int id, InputStream is){
		
		Customer updated = Util.readCustomer(is);
		Customer current = customerDB.get(id);
		if(current == null){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		//copy attributes from updated to current
		current.setFirstName(updated.getFirstName());
		current.setLastName(updated.getLastName());
		current.setStreet(updated.getStreet());
		current.setCity(updated.getCity());
		current.setZip(updated.getZip());
		current.setCountry(updated.getCountry());
		
	}

}
