//package com.amigoscode.service.impl;////import com.amigoscode.entity.Address;//import com.amigoscode.entity.Employee;//import com.amigoscode.exception.CustomException;//import com.amigoscode.repository.AddressRepository;//import com.amigoscode.repository.EmployeeRepository;//import org.springframework.dao.DataAccessException;//import org.springframework.stereotype.Service;//import org.springframework.transaction.annotation.Propagation;//import org.springframework.transaction.annotation.Transactional;////@Service//@Transactional(rollbackFor = CustomException.class)//public class EmployeeService {////    private final EmployeeRepository employeeRepository;//    private final AddressRepository addressRepository;////    public EmployeeService(EmployeeRepository employeeRepository, AddressRepository addressRepository) {//        this.employeeRepository = employeeRepository;//        this.addressRepository = addressRepository;//    }////    public void saveAddress(Address address) throws CustomException {//        try {////        } catch (DataAccessException e) {//            throw new CustomException("Failed to save address", e);//        }//    }////    @Transactional(propagation = Propagation.REQUIRED)//    public void updateEmployeeWithAddress (Employee employee, Address address) throws CustomException{//        try {//            employee.setAddress(address);//            employeeRepository.save(employee);//        } catch (DataAccessException e) {//            throw new CustomException("Failed to update employee with address: ", e);//        }//    }//////}