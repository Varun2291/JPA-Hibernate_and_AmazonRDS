package SWE645_Assignment4_varun;

/*
 * Name: Varun Rajavelu
 * This file is a Student Bean that contains all the fields from the Survey page and its corresponding
 * Setters and Getters methods. It acts acts like a client for the restful, to update the city and state
 * */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.bean.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;


@ManagedBean (name="student")
public class Student {

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String telePhone;
	private String emailId;
	private Date surveyDate, semDate;
	private String data;
	private String[] thingstolike;
	private String interests;
	private String likelihood;
	private String comments;
	
	public Student(){
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
		
		Client client = ClientBuilder.newClient();
	//	WebTarget target = client.target("http://localhost:8080/Swe645_Assignment_4/webresources/zips/");
		WebTarget target = client.target("http://ec2-52-42-148-223.us-west-2.compute.amazonaws.com/Swe645_Assignment_4/webresources/zips/");
        WebTarget resourceWebTarget;
        resourceWebTarget = target.path(this.zip);
        
        Invocation.Builder invocationBuilder;
        invocationBuilder = resourceWebTarget.request(MediaType.TEXT_PLAIN);
        System.out.println(resourceWebTarget.getUri());
        Response response = invocationBuilder.get();
        
        String resp_string = response.readEntity(String.class);
        setCity(resp_string.split("-")[0]);
        setState(resp_string.split("-")[1]);       
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public Date getSurveyDate() {
		return surveyDate;
	}

	public String getSurveyDateToString(){
		return (DateUtils.formatDay(getSurveyDate()));
	}
	
	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public Date getSemDate() {
		return semDate;
	}

	public String getSemDateToString(){
		return (DateUtils.formatDay(getSemDate()));
	}
	
	public void setSemDate(Date semDate) {
		this.semDate = semDate;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String[] getThingstolike() {
		return thingstolike;
	}

	public void setThingstolike(String[] thingstolike) {
		this.thingstolike = thingstolike;
	}

	public String getThingstolikeString(){
		return Arrays.toString(thingstolike);
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getLikelihood() {
		return likelihood;
	}

	public void setLikelihood(String likelihood) {
		this.likelihood = likelihood;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public List<String> completeLikelihood(String langPrefix){
		List<String> matches = new ArrayList<String>();
		String[] likelihoodArray = {"Very Likely", "Likely", "Unlikely"}; 
		
		for(String possibleVal:likelihoodArray){
			if(possibleVal.toUpperCase().startsWith(langPrefix.toUpperCase()))
				matches.add(possibleVal);
		}
		
		return matches;
	}
}
