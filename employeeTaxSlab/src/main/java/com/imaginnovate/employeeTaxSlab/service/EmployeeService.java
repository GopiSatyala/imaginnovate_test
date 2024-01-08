package com.imaginnovate.employeeTaxSlab.service;

import java.util.List;

import com.imaginnovate.employeeTaxSlab.dto.EmployeeDTO;
import com.imaginnovate.employeeTaxSlab.dto.EmployeeTaxDetailsDTO;

public interface EmployeeService {
	
	public String saveEmployeeDetails(EmployeeDTO empDTO);
	public List<EmployeeTaxDetailsDTO> getEmployeeTaxDetails();

}
