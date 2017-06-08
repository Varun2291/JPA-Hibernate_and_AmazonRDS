package SWE645_Assignment4_varun;

/*
 * Name: Varun Rajavelu
 * This file contains the controller and logic to calculate the mean and SD.
 * It invokes the appropriate xhmtl file to display
 * It Store the values into the Amazon RDS MySql database
 * It retrieve and deletes records from the database
 * It checks if survey date is before the sem start date
 * */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.annotation.Resource;

import org.hibernate.*;

public class StudentService {
	@PersistenceUnit EntityManagerFactory emfObj;
	
	public String SaveStudentData(StudentBean student){
		
		// Create entity manager using 'SWE645_Assignment4_varun' present in persistence.xml
		emfObj = Persistence.createEntityManagerFactory("SWE645_Assignment4_varun");
		EntityManager emObj = emfObj.createEntityManager();
	
		// Begin a transaction and save the student details
		try{
			emObj.getTransaction().begin();
            StudentBean stu_obj = new StudentBean(student);
            emObj.persist(stu_obj); 
            emObj.getTransaction().commit();
		}
		catch (Exception e) {
			emObj.getTransaction().rollback();
            e.printStackTrace();
        }
        finally{
        	emfObj.close();
        }
		
		return "Done";
	}
	
	// Method to return the details of the student
	public ArrayList<StudentBean> getStudents() throws SQLException, ClassNotFoundException, IOException {
		
		// Create entity manager using 'SWE645_Assignment4_varun' present in persistence.xml
		emfObj = Persistence.createEntityManagerFactory("SWE645_Assignment4_varun");
		EntityManager emObj_1 = emfObj.createEntityManager();
		
		// Query to get all the student details
		TypedQuery<StudentBean> query1 =
				  emObj_1.createQuery("SELECT s FROM StudentBean s", StudentBean.class);
		  
		List<StudentBean> results = query1.getResultList();
		  
		return (ArrayList<StudentBean>) results;
	}
	
	// Method to search the db based on the first, last, city and state
	public ArrayList<StudentBean> searchRecord(StudentBean student) {

		// Create entity manager using 'SWE645_Assignment4_varun' present in persistence.xml
		emfObj = Persistence.createEntityManagerFactory("SWE645_Assignment4_varun");
		EntityManager em = emfObj.createEntityManager();
		String queryString = "from StudentBean s where s.firstName = :firstName or s.lastName  = :lastName  or s.city = :city or s.state = :state";
		javax.persistence.Query queryObj = em.createQuery(queryString);
		
		queryObj.setParameter("firstName", student.getFirstName());
		queryObj.setParameter("lastName", student.getLastName());
		queryObj.setParameter("city", student.getCity());
		queryObj.setParameter("state", student.getState());
		
		List<StudentBean> resultSet = queryObj.getResultList();
		return (ArrayList<StudentBean>) resultSet;
		
	}
	
	// Method to delete a student from the DB
	public void deleteStudent(StudentBean student) {
		
		// Create entity manager using 'SWE645_Assignment4_varun' present in persistence.xml		
		emfObj = Persistence.createEntityManagerFactory("SWE645_Assignment4_varun");
		EntityManager emObj = emfObj.createEntityManager();
		emObj.getTransaction().begin();
		StudentBean studentRec = emObj.merge(student);
		emObj.remove(studentRec);	// Delete the student from the DB
		emObj.getTransaction().commit();
		emObj.close();
	}
	
	// Method to compute the average of the numbers; 
	// It takes in a string and converts it into numbers before calculating
	public double ComputeMean(String data){
		double result = 0;	// Variable to store the final result
		double sum;			// Variable to store the sum of the numbers
		int totalNum;		// Variable to store the total numbers that the user has entered


		if(!data.isEmpty() || data != "" || data != null){
			String[] numbers = data.split(",");	// Split the string into numbers based on a delimiter

			totalNum = numbers.length;		// Calculate the total numbers

			sum = 0;
			for(int iterator = 0; iterator < totalNum; iterator++)
				sum += Integer.parseInt(numbers[iterator]);			// Calculate the sum of numbers

			result = (sum / totalNum);		//  Calculate the Mean of the numbers
		}
		return result;
	}

	// Method to compute the Standard Deviation of the numbers;
	// It takes in a string and converts it into numbers before calculating
	public double ComputeStandardDeviation(String data){
		double result = 0; 		// Variable to store the final result
		double mean;			// Variable to store the Mean of the numbers
		double sum;				// Variable to store the sum of the numbers
		double variance; 

		mean = ComputeMean(data);		// Call the Compute Mean

		int totalNum;		// Variable to store the total numbers entered by the user
		String[] numbers = data.split(",");	// Split the string into numbers based on a delimiter

		totalNum = numbers.length;		// Calculate the total numbers

		sum = 0;
		for(int iterator = 0; iterator < totalNum; iterator++)
			// Get the Sum of square of the difference of the mean
			sum += ((Integer.parseInt(numbers[iterator]) - mean) * (Integer.parseInt(numbers[iterator]) - mean));

		variance = (sum / totalNum);	// Calculate the Variance
		result = Math.sqrt(variance);	// Calculate the Standard Deviation

		return result;
	}
}
