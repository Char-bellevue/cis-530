/*
Azizian, S. (2026). CIS 530 Server-Side Development. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026.
*/
package com.week3.restaip.week3.restapi.control;

import com.week3.restaip.week3.restapi.entity.Employee;
import com.week3.restaip.week3.restapi.service.EmployeeServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeServiceInter employeeService;

    /**
     * Constructs the controller with its required EmployeeServiceInter collaborator.
     * @param employeeService EmployeeServiceInter, the service layer to inject.
     * @return not applicable; constructors do not return a value.
     */
    @Autowired
    public EmployeeController(EmployeeServiceInter employeeService) {
        this.employeeService = employeeService;
    } // end EmployeeController

    /**
     * Handles GET /employee and returns every employee record.
     * @return a ResponseEntity wrapping the List of all Employee entities, HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() { // GET /employee
        return ResponseEntity.ok(employeeService.findAll());
    } // end getAllEmployees

    /**
     * Handles GET /employee/{id} and returns a single employee by id.
     * @param id Integer, the primary key of the employee to retrieve, taken from the URL path.
     * @return a ResponseEntity wrapping the matching Employee entity, HTTP 200.
     */
    @GetMapping("{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable Integer id) { // GET /employee/{id}
        return ResponseEntity.ok(employeeService.findById(id));
    } // end findEmployeeById

    /**
     * Handles POST /employee and creates a new employee record.
     * @param employee Employee, the new employee data taken from the JSON request body.
     * @return a ResponseEntity with HTTP 201, a Location header pointing to the new
     * resource, and the saved Employee entity in the body.
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) { // POST /employee
        Employee savedEmployee = employeeService.create(employee); // persist and capture the generated id
        return ResponseEntity
                .created(URI.create("/employee/" + savedEmployee.getId()))
                .body(savedEmployee);
    } // end createEmployee

    /**
     * Handles PUT /employee/{id} and updates an existing employee record.
     * @param id Integer, the primary key of the employee to update, taken from the URL path.
     * @param employee Employee, the updated field values taken from the JSON request body.
     * @return a ResponseEntity wrapping the updated Employee entity, HTTP 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) { // PUT /employee/{id}
        return ResponseEntity.ok(employeeService.update(id, employee));
    } // end updateEmployee

    /**
     * Handles DELETE /employee/{id} and removes the matching employee record.
     * @param id Integer, the primary key of the employee to delete, taken from the URL path.
     * @return a ResponseEntity with no body and HTTP 204, confirming successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) { // DELETE /employee/{id}
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    } // end deleteEmployee

} // end EmployeeController
