package com.imaginnovate.employeeTaxSlab.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imaginnovate.employeeTaxSlab.dto.EmployeeDTO;
import com.imaginnovate.employeeTaxSlab.entity.EmployeeEntity;
import com.imaginnovate.employeeTaxSlab.repo.EmployeeRepo;
import com.imaginnovate.employeeTaxSlab.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepo employeeRepo;

	@Override
	public String saveEmployeeDetails(EmployeeDTO empDto) {
		return employeeRepo.save(convertToEntity(empDto)).getEmpId();
		
	}
	
	
	
	private EmployeeEntity convertToEntity(EmployeeDTO empDto) {
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmpId(empDto.getEmpId());
		employeeEntity.setFirstName(empDto.getFirstName());
		employeeEntity.setLastName(empDto.getLastName());
		employeeEntity.setEmail(empDto.getEmail());
		employeeEntity.setPhoneNumber(empDto.getPhoneNumber());
		employeeEntity.setSalary(empDto.getSalary());
		return employeeEntity;
	}

}
