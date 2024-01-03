package com.imaginnovate.employeeTaxSlab.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.employeeTaxSlab.dto.EmployeeDTO;
import com.imaginnovate.employeeTaxSlab.dto.EmployeeTaxDetailsDTO;
import com.imaginnovate.employeeTaxSlab.service.EmployeeService;

@RestController
@RequestMapping("employee")
public class EmployeeTaxSlabController {
	
	@Autowired
	EmployeeService empService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/addEmployee")
	public ResponseEntity<String> saveEmployeeDetails(@Valid @RequestBody EmployeeDTO emp) {
		if(empService.validateEmployeeDetails(emp)) {
			String employeeId = empService.saveEmployeeDetails(emp);
			return ResponseEntity.ok("Employee Created with ID :"+employeeId);
		}
		else {
			return ResponseEntity.ok("invalid employee details");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/getEmployeeTaxDetails")
	public ResponseEntity<List<EmployeeTaxDetailsDTO>> getEmployeeTaxDetails() {
		return ResponseEntity.ok(empService.getEmployeeTaxDetails());
	}
	

}
	
