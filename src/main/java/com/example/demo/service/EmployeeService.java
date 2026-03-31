package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.exception.DuplicateEmployeeEmailException;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id)
			.orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	public Employee createEmployee(Employee employee) {
		if (employeeRepository.existsByEmail(employee.getEmail())) {
			throw new DuplicateEmployeeEmailException(employee.getEmail());
		}
		return employeeRepository.save(employee);
	}

	public Employee updateEmployee(Long id, Employee updatedEmployee) {
		Employee existingEmployee = getEmployeeById(id);
		String updatedEmail = updatedEmployee.getEmail();

		if (!existingEmployee.getEmail().equals(updatedEmail)
			&& employeeRepository.existsByEmail(updatedEmail)) {
			throw new DuplicateEmployeeEmailException(updatedEmail);
		}

		existingEmployee.setFirstName(updatedEmployee.getFirstName());
		existingEmployee.setLastName(updatedEmployee.getLastName());
		existingEmployee.setEmail(updatedEmail);
		existingEmployee.setDepartment(updatedEmployee.getDepartment());

		return employeeRepository.save(existingEmployee);
	}

	public void deleteEmployee(Long id) {
		Employee employee = getEmployeeById(id);
		employeeRepository.delete(employee);
	}
}
