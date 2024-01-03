package com.imaginnovate.employeeTaxSlab.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.employeeTaxSlab.dto.EmployeeDTO;
import com.imaginnovate.employeeTaxSlab.service.EmployeeService;

@RestController
@RequestMapping("employee")
public class EmployeeTaxSlabController {
	
	@Autowired
	EmployeeService empService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/addEmployee")
	public ResponseEntity<String> saveEmployeeDetails(@Valid @RequestBody EmployeeDTO emp) {
		if(validateEmployeeDetails(emp)) {
			String employeeId = empService.saveEmployeeDetails(emp);
			return ResponseEntity.ok("Employee Created with ID :"+employeeId);
		}
		else {
			return ResponseEntity.ok("invalid employee details");
		}
	}

	private boolean validateEmployeeDetails(EmployeeDTO emp) {
		if(emp.getEmpId()==null && emp.getEmpId().isEmpty()) {
			return false;
		}
		if(emp.getFirstName()==null && emp.getFirstName().isEmpty()) {
			return false;
		}
		if(emp.getLastName()==null && emp.getLastName().isEmpty()) {
			return false;
		}
		if(emp.getEmail()==null && emp.getEmail().isEmpty()) {
			return false;
		}
		if(emp.getPhoneNumber()==null && emp.getPhoneNumber().size()<0) {
			return false;
		}
		if(emp.getDOJ()==null) {
			return false;
		}
		else {
			return false;
		}
	}

}
	
