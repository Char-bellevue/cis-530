/*
Azizian, S. (2026). CIS 530 Server-Side Development. Bellevue University, all rights reserved.
Modified by C. Natarajan 2026.
*/
package com.week3.restaip.week3.restapi.dao;

import com.week3.restaip.week3.restapi.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Retrieves every employee record currently stored in the database.
     * @return a List of all Employee entities found in the database.
     */
    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> query =
                entityManager.createQuery("from Employee", Employee.class); // build the JPQL query
        return query.getResultList();
    } // end findAll

    /**
     * Looks up a single employee by primary key using the EntityManager.
     * @param id int, the primary key of the employee to retrieve.
     * @return an Optional containing the matching Employee, or an empty
     * Optional if no employee with that id exists.
     */
    @Override
    public Optional<Employee> findById(int id) {
        Employee employee = entityManager.find(Employee.class, id); // may return null if not found
        return Optional.ofNullable(employee);
    } // end findById

    /**
     * Inserts a new employee or updates an existing one using merge, which
     * handles both cases depending on whether the entity's id is set.
     * @param employee Employee, the entity to create or update.
     * @return the managed Employee entity after the save operation.
     */
    @Override
    public Employee save(Employee employee) {
        return entityManager.merge(employee); // merge covers insert and update
    } // end save

    /**
     * Removes the given employee entity from the database. If the entity
     * is detached (not currently tracked by the persistence context), it is
     * merged back in first so that remove() can operate on a managed instance.
     * @param employee Employee, the entity to delete.
     * @return void, no value is returned; the record is removed from persistence.
     */
    @Override
    public void delete(Employee employee) {
        Employee managedEmployee = entityManager.contains(employee)
                ? employee
                : entityManager.merge(employee); // re-attach a detached entity before removing it
        entityManager.remove(managedEmployee);
    } // end delete

} // end EmployeeDAOImpl
