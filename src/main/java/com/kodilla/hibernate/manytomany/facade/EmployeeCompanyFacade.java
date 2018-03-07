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

    @Autowired
    private Validation validation;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeCompanyFacade.class);

    public List<Employee> searchEmployeeByPartLastName(String partLastName) throws ValidationException{
        LOGGER.info("Searching employees...");
            List<Employee> matchesEmployees = new ArrayList<>();
            String validatedPartName = validation.validate(partLastName);
            matchesEmployees = employeeDao.retrieveEmployeesByPartLastName(validatedPartName);
            if(matchesEmployees.size() == 0) {
                LOGGER.info("Cannot find employees matching your query");
            }
            LOGGER.info("Searching employees has been finished.");
            return matchesEmployees;
    }

    public List<Company> searchCompanyByPartName(String partName) throws ValidationException {
        LOGGER.info("Searching companies...");
        List<Company> matchesCompanies = new ArrayList<>();
        String validatedName = validation.validate(partName);
        matchesCompanies = companyDao.retrieveCompaniesByPartName(validatedName);
        if(matchesCompanies.size() == 0) {
            LOGGER.info("Cannot find companies matching your query");
        }
        LOGGER.info("Searching companies has been finished.");
        return matchesCompanies;
    }
}

