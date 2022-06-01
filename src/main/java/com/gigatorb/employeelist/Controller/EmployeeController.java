package com.gigatorb.employeelist.Controller;
import com.gigatorb.employeelist.Exception.ResourceNotFoundException;
import com.gigatorb.employeelist.model.Employee;
import com.gigatorb.employeelist.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")

public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employee

    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }




    //create a employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {

        return employeeRepository.save(employee);
    }




    //get method by id rest api

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee not exist hear id :" + id));
    return ResponseEntity.ok(employee);
    }


    //update employee by id
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee not exist hear id :" + id));

        employee.setFirstname(employeeDetails.getFirstname());
        employee.setLastname(employeeDetails.getLastname());
        employee.setEmailId(employeeDetails.getEmailId());
        Employee updateEmployee= employeeRepository.save(employee);
        return ResponseEntity.ok(updateEmployee);

    }

    //delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity< Map<String,Boolean>> deleteEmployee(@PathVariable Long id){
    Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("employee not exist hear id :" + id));

    employeeRepository.delete(employee);
    Map<String,Boolean> response =new HashMap<>();
    response.put("deleted" ,Boolean.TRUE);
    return ResponseEntity.ok(response);
    }

}
