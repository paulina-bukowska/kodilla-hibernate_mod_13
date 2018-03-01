package com.kodilla.hibernate.manytomany.facade;

import com.kodilla.hibernate.manytomany.Company;
import com.kodilla.hibernate.manytomany.Employee;
import com.kodilla.hibernate.manytomany.dao.CompanyDao;
import com.kodilla.hibernate.manytomany.dao.EmployeeDao;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.serial.SerialException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeCompanyFacade {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private CompanyDao companyDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeCompanyFacade.class);

    public List<Employee> searchEmployeeByPartLastName() {
        LOGGER.info("Searching employees...");
        List<Employee> matchesEmployees = new ArrayList<>();
        matchesEmployees = employeeDao.retrieveEmployeesByPartLastName();
        if(matchesEmployees.size() == 0) {
            LOGGER.info("Cannot find employees matching your query");
        }
        return matchesEmployees;
    }

    public List<Company> searchCompanyByPartName() {
        LOGGER.info("Searching companies...");
        List<Company> matchesCompanies = new ArrayList<>();
        matchesCompanies = companyDao.retrieveCompaniesByPartName();
        if(matchesCompanies.size() == 0) {
            LOGGER.info("Cannot find companies matching your query");
        }
        return matchesCompanies;
    }
}

