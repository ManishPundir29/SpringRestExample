package com.journaldev.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journaldev.spring.dao.EmpDao;
import com.journaldev.spring.model.Employee;

@Service
public class EmpService {
	
	@Autowired
	EmpDao empdao;
	
	
	public Employee getDummyEmpDetails(){
		return empdao.getDummyEmpDetails();
	}


	public Employee getEmpDetailsById(int empId) {
		return empdao.getEmpDetailsById(empId);
	}


	public List<Employee> getAllEmpDetails() {
		return empdao.getAllEmpDetails();
	}


	public int createEmp(Employee emp) {
		return empdao.createEmp( emp);
		
	}


	public int deleteEmpById(int empId) {
		return (Integer) empdao.deleteEmpById(empId);
		
	}
}
