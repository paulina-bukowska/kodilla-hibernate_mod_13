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
    public void testFacade() throws ValidationException {
        //Given
        Employee giancarloGuerrero = new Employee("Giancarlo", "Guermuro");
        Employee madelynnCarson = new Employee("Madelynn", "Caerson");
        Employee aimeeMurphy = new Employee("Aimee", "Murphy");

        Company tictockInternational = new Company("Tictok Sisternational");
        Company goldUnwind = new Company("Gold Unwind");
        Company afterglowSystems = new Company("Afterglok Systems");

        tictockInternational.getEmployees().add(madelynnCarson);
        tictockInternational.getEmployees().add(giancarloGuerrero);
        goldUnwind.getEmployees().add(aimeeMurphy);
        goldUnwind.getEmployees().add(giancarloGuerrero);
        afterglowSystems.getEmployees().add(aimeeMurphy);

        giancarloGuerrero.getCompanies().add(tictockInternational);
        giancarloGuerrero.getCompanies().add(goldUnwind);
        madelynnCarson.getCompanies().add(tictockInternational);
        aimeeMurphy.getCompanies().add(goldUnwind);
        aimeeMurphy.getCompanies().add(afterglowSystems);

        companyDao.save(tictockInternational);
        int tictockInternationalId = tictockInternational.getId();
        companyDao.save(goldUnwind);
        int goldUnwindId = goldUnwind.getId();
        companyDao.save(afterglowSystems);
        int afterglowSystemsId = afterglowSystems.getId();

        //When
        List<Employee> matchesEmployeesWithEr = facade.searchEmployeeByPartLastName("kott");
        List<Employee> matchesEmployeesWithMur = facade.searchEmployeeByPartLastName("mur");
        List<Company> matchesCompaniesWithTer = facade.searchCompanyByPartName("ter");
        List<Company> matchesCompaniesWithLdAndSpace = facade.searchCompanyByPartName("ld ");
        List<Company> matchesCompaniesTestCapitalLetter = facade.searchCompanyByPartName("ok s");

        //Then
        try {
            Assert.assertEquals(0, matchesEmployeesWithEr.size());
            Assert.assertEquals(2, matchesEmployeesWithMur.size());
            Assert.assertEquals(2, matchesCompaniesWithTer.size());
            Assert.assertEquals(1, matchesCompaniesWithLdAndSpace.size());
            Assert.assertEquals(2, matchesCompaniesTestCapitalLetter.size());
        } finally {
            //CleanUp
            if (companyDao.exists(tictockInternationalId)) {
                companyDao.delete(tictockInternationalId);
            }
            if (companyDao.exists(goldUnwindId)) {
                companyDao.delete(goldUnwindId);
            }
            if (companyDao.exists(afterglowSystemsId)) {
                companyDao.delete(afterglowSystemsId);
            }
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowSomeException() throws Exception {
        // Given
        // When
        facade.searchEmployeeByPartLastName("hy");

        // Then
        Assert.fail("This method should throw SomeException");
    }
}