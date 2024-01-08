package com.imaginnovate.employeeTaxSlab.serviceImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
	public String saveEmployeeDetails(EmployeeDTO empDto) {
		return employeeRepo.save(convertToEntity(empDto)).getEmpId();

	}

	@Override
	public List<EmployeeTaxDetailsDTO> getEmployeeTaxDetails() {
		List<EmployeeEntity> empList = employeeRepo.findAll();
		return empList.stream().map((e) -> findEmployeeTax(e)).collect(Collectors.toList());
	}

	private EmployeeTaxDetailsDTO findEmployeeTax(EmployeeEntity emp) {
		EmployeeTaxDetailsDTO taxDetailsDTO = convertToDTO(emp);
		double empAnualSalary = getEmpAnualSalaryBasedOnDOJ(emp);
		return calculateTax(taxDetailsDTO,empAnualSalary);
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

	
	//Calculate Annual Salary Based on the employee joining date
	private double getEmpAnualSalaryBasedOnDOJ(EmployeeEntity emp) {

		double empAnualSalary = 0;
		double numberOfFinancialDays=0;

		LocalDate currentDate = LocalDate.now();

		// if the current year is less then March financial year would be (currentYear-1 - currentYear)
		if (currentDate.getMonthValue() < 4) {
			if (emp.getDOJ().after(new Date((currentDate.getYear() - 1), 04, 1)) && emp.getDOJ().before(new Date(currentDate.getYear(), 03, 31))) {

				numberOfFinancialDays = ChronoUnit.DAYS.between(fromDateToLocalDate(emp.getDOJ()),fromDateToLocalDate(new Date(currentDate.getYear(), 03, 31)));
				
				empAnualSalary = (emp.getSalary() / 30) * numberOfFinancialDays;

			} else {
				empAnualSalary = emp.getSalary() * 12;
			}

		}
		// if the current year is greater then March financial year would be (currentYear - currentYear+1)
		else {
			if (emp.getDOJ().after(new Date(currentDate.getYear(), 04, 1)) && emp.getDOJ().before(new Date((currentDate.getYear() + 1), 03, 31))) {

				numberOfFinancialDays = ChronoUnit.DAYS.between(fromDateToLocalDate(emp.getDOJ()),fromDateToLocalDate(new Date((currentDate.getYear() + 1), 03, 31)));
				
				empAnualSalary = (emp.getSalary() / 30) * numberOfFinancialDays;

			} else {
				empAnualSalary = emp.getSalary() * 12;
			}
		}

		return empAnualSalary;
	}
	
	//Calculate tax for Annual Salary based on the tax slab 
	private EmployeeTaxDetailsDTO calculateTax(EmployeeTaxDetailsDTO taxDetailsDTO, double empAnualSalary) {

		double taxamout = 0;
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

	//Calculate cess 
	private Double cessApplied(double income) {
		double cess = 0.0;

		if (income > 2500000) {
			cess = (income - 2500000) * 0.02;
		}
		return cess;
	}
	
	public LocalDate fromDateToLocalDate(Date date) {
	    return date.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}

}
