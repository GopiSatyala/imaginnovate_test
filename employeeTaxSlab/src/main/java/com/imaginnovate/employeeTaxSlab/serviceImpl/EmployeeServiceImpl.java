package com.imaginnovate.employeeTaxSlab.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imaginnovate.employeeTaxSlab.dto.EmployeeDTO;
import com.imaginnovate.employeeTaxSlab.dto.EmployeeTaxDetailsDTO;
import com.imaginnovate.employeeTaxSlab.entity.EmployeeEntity;
import com.imaginnovate.employeeTaxSlab.repo.EmployeeRepo;
import com.imaginnovate.employeeTaxSlab.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	@Override
	public boolean validateEmployeeDetails(EmployeeDTO emp) {
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

	@Override
	public String saveEmployeeDetails(EmployeeDTO empDto) {
		return employeeRepo.save(convertToEntity(empDto)).getEmpId();
		
	}
	
	@Override
	public List<EmployeeTaxDetailsDTO> getEmployeeTaxDetails() {
		List<EmployeeEntity> empList = employeeRepo.findAll();
		return empList.stream().map((e) -> findEmployeeTax(e)).collect(Collectors.toList());
	}
	
	private EmployeeTaxDetailsDTO findEmployeeTax(EmployeeEntity e) {

		EmployeeTaxDetailsDTO taxDetailsDTO = convertToDTO(e);
		double empAnualSalary = e.getSalary() * 12;
		double taxamout;

		if (empAnualSalary <= 250000) {
			taxDetailsDTO.setTaxAmount(0);
		} else if (empAnualSalary > 250000 && empAnualSalary <= 500000) {
			taxamout = empAnualSalary - 250000;
			taxDetailsDTO.setTaxAmount((taxamout * 0.05));
		} else if (empAnualSalary > 500000 && empAnualSalary <= 1000000) {
			taxamout = empAnualSalary - 250000;
			if (taxamout <= 250000) {
				taxDetailsDTO.setTaxAmount((taxamout * 0.05));
			} else {
				taxDetailsDTO.setTaxAmount((250000 * 0.05) + (empAnualSalary - taxamout * 0.10));
			}
		} else {
			taxamout = empAnualSalary - 250000;
			if (taxamout > 1000000) {
				taxDetailsDTO.setTaxAmount((250000 * 0.05) + (empAnualSalary - taxamout * 0.10));
			} else if (taxamout < 1000000) {
				taxDetailsDTO.setTaxAmount((250000 * 0.05) + (500000 * 0.10) + (empAnualSalary - taxamout * 0.20));
			}
		}

		taxDetailsDTO.setCessAmount(cessApplied(empAnualSalary));
		
		return taxDetailsDTO;
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
	
	private EmployeeTaxDetailsDTO convertToDTO(EmployeeEntity empEntity) {
		EmployeeTaxDetailsDTO taxDetailsDTO = new EmployeeTaxDetailsDTO();
		taxDetailsDTO.setEmpId(empEntity.getEmpId());
		taxDetailsDTO.setFirstName(empEntity.getFirstName());
		taxDetailsDTO.setLastName(empEntity.getLastName());
		taxDetailsDTO.setEmail(empEntity.getEmail());
		taxDetailsDTO.setPhoneNumber(empEntity.getPhoneNumber());
		taxDetailsDTO.setSalary(empEntity.getSalary());
		return taxDetailsDTO;
	}
	
	private Double cessApplied(double income) {
		double cess = 0.0;

		if (income > 2500000) {
			cess = (income-2500000)*0.02;
		}
		return cess;
	}

}
