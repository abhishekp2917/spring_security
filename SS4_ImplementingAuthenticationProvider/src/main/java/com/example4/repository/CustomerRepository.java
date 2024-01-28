/*
    CustomerRepository to interact with customer(or user) table of the DB which is being used to store the creds of all
    the customer
*/

package com.example4.repository;

import com.example4.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository
public class CustomerRepository {

    @Autowired
    private SessionFactory sessionFactory;

    // method to fetch Customer details based on username if it exists
    public Customer getCustomerByUsername(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        Path<String> usernameField = root.get("username");
        Predicate predicate1 = criteriaBuilder.equal(usernameField, username);
        criteriaQuery.where(predicate1);
        TypedQuery<Customer> query = session.createQuery(criteriaQuery);
        Customer customer = query.getSingleResult();
        transaction.commit();
        session.close();
        return customer;
    }

    // method to check if Customer exists based on username
    public boolean customerExists(String username) {
        Customer customer = null;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        Path<String> usernameField = root.get("username");
        Predicate predicate1 = criteriaBuilder.equal(usernameField, username);
        criteriaQuery.where(predicate1);
        TypedQuery<Customer> query = session.createQuery(criteriaQuery);
        customer = query.getSingleResult();
        transaction.commit();
        session.close();
        return (customer!=null);
    }

    // method to save Customer record inside customer table
    public Customer save(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(customer);
        transaction.commit();
        session.close();
        return customer;
    }
}
