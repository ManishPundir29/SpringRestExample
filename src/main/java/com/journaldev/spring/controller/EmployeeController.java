package com.journaldev.spring.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journaldev.spring.dao.EmpDao;
import com.journaldev.spring.model.Employee;
import com.journaldev.spring.services.EmpService;

/**
 * Handles requests for the Employee service.
 */
@Controller
public class EmployeeController {
	
	
	@Autowired
	EmpService empservice;


	@Autowired
	EmpDao empDao;

	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	//Map to store employees, ideally we should use database
	Map<Integer, Employee> empData = new HashMap<Integer, Employee>();
	
	@RequestMapping(value = EmpRestURIConstants.DUMMY_EMP, method = RequestMethod.GET)
	public @ResponseBody Employee getDummyEmployee() {
		logger.info("Start getDummyEmployee");
		/*create a dummy employee object*/
		Employee emp=empservice.getDummyEmpDetails();
		/*Employee emp = new Employee();
		emp.setId(9999);
		emp.setName("Dummy");
		emp.setCreatedDate(new Date());*/
		empData.put(emp.getId(), emp);
		return emp;
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_EMP, method = RequestMethod.GET)
	public @ResponseBody Employee getEmployee(@PathVariable("id") int empId) {
		logger.info("Start getEmployee. ID="+empId);
		Employee emp=empservice.getEmpDetailsById(empId);
		return emp;
	}
	
	@RequestMapping(value = EmpRestURIConstants.GET_ALL_EMP, method = RequestMethod.GET)
	public @ResponseBody List<Employee> getAllEmployees() {
		logger.info("Start getAllEmployees.");
		List<Employee> emps = empservice.getAllEmpDetails();
		
		/*Set<Integer> empIdKeys = empData.keySet();
		Set<Integer> empIdKeys = empData.keySet();
		for(Integer i : empIdKeys){
			emps.add(empData.get(i));
		}*/
		return emps;
	}
	
	@RequestMapping(value = EmpRestURIConstants.CREATE_EMP, method = RequestMethod.POST)
	public ResponseEntity<?> createEmployee(@RequestBody Employee emp) {
		logger.info("Start createEmployee.");
		emp.setCreatedDate(new Date());
		int x=empservice.createEmp(emp);
		System.out.println(":::"+x);
		/*empData.put(emp.getId(), emp);*/
		if(x==-1) {
			String message="Error. DETAIL: id="+emp.getId()+" already exists.";
			return new ResponseEntity<String>(message, HttpStatus.OK);
		}
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}
	
	@RequestMapping(value = EmpRestURIConstants.DELETE_EMP, method = RequestMethod.PUT)
	public @ResponseBody Employee deleteEmployee(@PathVariable("id") int empId) {
		logger.info("Start deleteEmployee.");
		/*Employee emp = empData.get(empId);*/
		Employee emp=empservice.getEmpDetailsById(empId);
		empservice.deleteEmpById(empId);
		/*empData.remove(empId);*/
		return emp;
	}
	
}
