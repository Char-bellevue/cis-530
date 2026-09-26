/*
Azizian, S. (2026). CIS 530 Server-Side Development. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026.
*/
package com.week3.restaip.week3.restapi.service;

import com.week3.restaip.week3.restapi.entity.Employee;

import java.util.List;

public interface EmployeeServiceInter {

    /**
     * Retrieves every employee record.
     * @return a List of all Employee entities.
     */
    List<Employee> findAll();

    /**
     * Retrieves a single employee by id, throwing if none is found.
     * @param id int, the primary key of the employee to retrieve.
     * @return the matching Employee entity.
     */
    Employee findById(int id);

    /**
     * Creates a new employee record.
     * @param employee Employee, the entity to persist.
     * @return the saved Employee entity, including its generated id.
     */
    Employee create(Employee employee);

    /**
     * Updates an existing employee's first name, last name, and email.
     * @param id int, the primary key of the employee to update.
     * @param employee Employee, an object carrying the new field values.
     * @return the updated Employee entity.
     */
    Employee update(int id, Employee employee);

    /**
     * Deletes the employee identified by the given id.
     * @param id int, the primary key of the employee to delete.
     * @return void, no value is returned; the record is removed from persistence.
     */
    void deleteById(int id);

} // end EmployeeServiceInter
