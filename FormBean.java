package SWE645_Assignment4_varun;
/*
 * Name: Varun Rajavelu
 * This file is used for the following:
 * 1) Create objects for StudentBean and WinningResult
 * 2) Method to auto complete the Likelihood field in the survey page
 * 3) Calls other DB methods (Create, search and delete)
 * */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean (name="formBean")
public class FormBean {

	private StudentBean student = new StudentBean();
	private WinningResult raffleObj = new WinningResult();
	
	public FormBean(){
		
	}

	public StudentBean getStudent() {
		return student;
	}

	public void setStudent(StudentBean student) {
		this.student = student;
	}
	
	public WinningResult getRaffleObj() {
		return raffleObj;
	}

	public void setRaffleObj(WinningResult raffleObj) {
		this.raffleObj = raffleObj;
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
	
	// Method that is called from the Survey form.
	// Saves student details
	// Calcuates mean and SD
	// Calls Simple or winner acknowledgement form
	public String calculateRaffle() throws NullPointerException, IOException{

		FacesContext context = FacesContext.getCurrentInstance();
		StudentService studService = new StudentService();
		
		if(getStudent().getSemDate().before(getStudent().getSurveyDate())){
			FacesMessage errorMessage=
					new FacesMessage("Sem start date must be after Survey date");
					errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(null, errorMessage);
					return(null);
		}
		
		
		String savedStatus = null;
		try{
			// Save the current student details in the DB
			savedStatus = studService.SaveStudentData(getStudent());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(savedStatus.equalsIgnoreCase("done"))
			System.out.println("Saved");
		else
			System.out.println("Not Saved");
		
		double mean = studService.ComputeMean(getStudent().getData());
		
		// Set mean and SD
		raffleObj.setMean(mean);
		raffleObj.setStandardDeviation(studService.ComputeStandardDeviation(getStudent().getData()));
		
		// Check if mean is greater or less than 90 and call the
		// appropriate xhtml file
		if(mean > 90){
			return "WinnerAcknowledgement";
		}
		else{
			return "SimpleAcknowledgement";
		}
	}
	
	// Method to get the list of students
	public ArrayList<StudentBean> studentList() {
		ArrayList<StudentBean> studentlist = null;
		StudentService studentSerObj = new StudentService();
		
		try{
			// GEt the student list
			studentlist = studentSerObj.getStudents();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return studentlist;
	}
	
	// Method to search the DB to get the matching records
	public String searchDb(){
		StudentService studentSrv = new StudentService();
		ArrayList<StudentBean> record = null;
		
		record = studentSrv.searchRecord(getStudent());	// Get the list of all the matching records
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("search_result",record);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("search_result_size",record.size());
		
		return "SearchResults";
	}
	
	// Method to delete a student record
	public String DeleteRecord(StudentBean student){
		
		StudentService studentSrv = new StudentService();
		studentSrv.deleteStudent(student);
		
		// Message sent to ListSurvey page saying which student has been deleted
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("deleted_message","Student Record: \"" + student.getFirstName() + " " + student.getLastName() + "\", has been deleted from the database.");
		
		return "ListSurvey";
	}
}
