/*
Azizian, S. (2026). CIS 530 Server-Side Development. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026.
*/
package com.week3.restaip.week3.restapi.dao;

import com.week3.restaip.week3.restapi.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {

    /**
     * Retrieves every employee record currently stored in the database.
     * @return a List of all Employee entities found in the database.
     */
    List<Employee> findAll();

    /**
     * Looks up a single employee by primary key.
     * @param id int, the primary key of the employee to retrieve.
     * @return an Optional containing the matching Employee, or an empty
     * Optional if no employee with that id exists.
     */
    Optional<Employee> findById(int id);

    /**
     * Inserts a new employee or updates an existing one, depending on
     * whether the employee's id is null or already present.
     * @param employee Employee, the entity to create or update.
     * @return the managed Employee entity after the save operation.
     */
    Employee save(Employee employee); // create or update

    /**
     * Removes the given employee entity from the database.
     * @param employee Employee, the entity to delete.
     * @return void, no value is returned; the record is removed from persistence.
     */
    void delete(Employee employee);

} // end EmployeeDAO
