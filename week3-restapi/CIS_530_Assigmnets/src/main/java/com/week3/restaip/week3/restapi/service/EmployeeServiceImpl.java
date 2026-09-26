/*
Azizian, S. (2026). CIS 530 Server-Side Development. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026.
*/
package com.week3.restaip.week3.restapi.service;

import com.week3.restaip.week3.restapi.dao.EmployeeDAO;
import com.week3.restaip.week3.restapi.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeServiceInter {

    private final EmployeeDAO employeeDAO;

    /**
     * Constructs the service with its required EmployeeDAO collaborator.
     * @param employeeDAO EmployeeDAO, the data access object to inject.
     * @return not applicable; constructors do not return a value.
     */
    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    } // end EmployeeServiceImpl

    /**
     * Retrieves every employee record by delegating to the DAO.
     * @return a List of all Employee entities.
     */
    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    } // end findAll

    /**
     * Retrieves a single employee by id, throwing a RuntimeException if the
     * employee does not exist so that callers get a clear failure signal.
     * @param id int, the primary key of the employee to retrieve.
     * @return the matching Employee entity.
     */
    @Override
    public Employee findById(int id) {
        return employeeDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found" + id)); // fail fast if missing
    } // end findById

    /**
     * Creates a new employee record inside a transaction.
     * @param employee Employee, the entity to persist.
     * @return the saved Employee entity, including its generated id.
     */
    @Override
    @Transactional
    public Employee create(Employee employee) {
        return employeeDAO.save(employee);
    } // end create

    /**
     * Updates an existing employee. The current record is fetched first so
     * that only the intended fields are overwritten, then the change is saved.
     * @param id int, the primary key of the employee to update.
     * @param employee Employee, an object carrying the new field values.
     * @return the updated Employee entity.
     */
    @Override
    @Transactional
    public Employee update(int id, Employee employee) {
        Employee existingEmployee = findById(id); // load the current record before changing it
        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());

        return employeeDAO.save(existingEmployee);
    } // end update

    /**
     * Deletes the employee identified by the given id. The employee is
     * fetched first so a missing id fails consistently with findById(),
     * then the DAO removes it from the database inside a transaction.
     * @param id int, the primary key of the employee to delete.
     * @return void, no value is returned; the record is removed from persistence.
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        findById(id); // throws a RuntimeException if the employee does not exist, kept for consistent error handling
        employeeDAO.deleteById(id); // remove the record via EntityManager
    } // end deleteById

} // end EmployeeServiceImpl