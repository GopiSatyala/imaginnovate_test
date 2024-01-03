package com.imaginnovate.employeeTaxSlab.service;

import java.util.List;

import com.imaginnovate.employeeTaxSlab.dto.EmployeeDTO;
import com.imaginnovate.employeeTaxSlab.dto.EmployeeTaxDetailsDTO;

public interface EmployeeService {
	
	public boolean validateEmployeeDetails(EmployeeDTO emp);
	public String saveEmployeeDetails(EmployeeDTO empDTO);
	public List<EmployeeTaxDetailsDTO> getEmployeeTaxDetails();

}
