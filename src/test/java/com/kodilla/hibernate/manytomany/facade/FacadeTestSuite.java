package com.kodilla.hibernate.manytomany.facade;

import com.kodilla.hibernate.manytomany.Company;
import com.kodilla.hibernate.manytomany.Employee;
import com.kodilla.hibernate.manytomany.dao.CompanyDao;
import com.kodilla.hibernate.manytomany.dao.EmployeeDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacadeTestSuite {
    @Autowired
    CompanyDao companyDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EmployeeCompanyFacade facade;

    @Test
    public void testFacade() {
        //Given
        Employee giancarloGuerrero = new Employee("Giancarlo", "Guerrero");
        Employee madelynnCarson = new Employee("Madelynn", "Carson");
        Employee aimeeMurphy = new Employee("Aimee", "Murphy");

        Company tictockInternational = new Company("Tictock International");
        Company goldUnwind = new Company("Gold Unwind");

        tictockInternational.getEmployees().add(madelynnCarson);
        tictockInternational.getEmployees().add(giancarloGuerrero);
        goldUnwind.getEmployees().add(aimeeMurphy);
        goldUnwind.getEmployees().add(giancarloGuerrero);

        giancarloGuerrero.getCompanies().add(tictockInternational);
        giancarloGuerrero.getCompanies().add(goldUnwind);
        madelynnCarson.getCompanies().add(tictockInternational);
        aimeeMurphy.getCompanies().add(goldUnwind);

        companyDao.save(tictockInternational);
        int tictockInternationalId = tictockInternational.getId();
        companyDao.save(goldUnwind);
        int goldUnwindId = goldUnwind.getId();

        //When
        List<Employee> matchesEmployees = facade.searchEmployeeByPartLastName();
        List<Company> matchesCompanies = facade.searchCompanyByPartName();

        //Then
        try {
            Assert.assertEquals(1, matchesEmployees.size());
            Assert.assertEquals(1, matchesCompanies.size());
        } finally {
            //CleanUp
            if (companyDao.exists(tictockInternationalId)) {
                companyDao.delete(tictockInternationalId);
            }
            if (companyDao.exists(goldUnwindId)) {
                companyDao.delete(goldUnwindId);
            }
        }
    }
}