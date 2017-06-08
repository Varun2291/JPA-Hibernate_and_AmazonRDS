package SWE645_Assignment4_varun;

/*
 * Name: Varun Rajavelu
 * This file acts like the Restful server. Given the input zip code, the method would concatenate and return the corresponding 
 * city and state as string. 
 * */

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("zips")
public class ZipCode {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)	
	@Path("{zipString}")
	public String ComputeZip(@PathParam("zipString") String zipString){
		String city = "";
		String state = "";
		
		// Iterate through the list
		for(Map.Entry<String, String> iterator : findZipCode().entrySet()){
			// Compare with the user entered value
			if(iterator.getKey().equals(zipString)){
				String[] temp = iterator.getValue().split("-");
		
				city = temp[0];		// 0th index will have the city
				state = temp[1];	// 1st index will have the city
			}
		}
		
		return city + "-" + state;
	}
	
	public Map<String, String> findZipCode() {
	 	Map<String, String> zipcode_arr = new HashMap<String, String>();
	 	zipcode_arr.put("22030", "Fairfax-VA");
	 	zipcode_arr.put("22301", "Tysons Corner-MD");
	 	zipcode_arr.put("22312", "Alexandria-VA");
		zipcode_arr.put("20148", "Ashburn-VA");

		return zipcode_arr;
	}

}
