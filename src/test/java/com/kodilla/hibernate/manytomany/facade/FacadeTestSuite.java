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
        Employee johnSmith = new Employee("John", "Smith");
        Employee stephanieClarckson = new Employee("Stephanie", "Clarckson");
        Employee ryanTalley = new Employee("Ryan", "Talley");
        Employee lindaKovalsky = new Employee("Linda", "Kovalsky");
        Employee phoebePearson = new Employee("Phoebe", "Pearson");
        Employee dylanMurphy = new Employee("Dylan", "Murphy");
        Employee morganWalsh = new Employee("Morgan", "Walsh");
        Employee giancarloGuerrero = new Employee("Giancarlo", "Guerrero");
        Employee madelynnCarson = new Employee("Madelynn", "Carson");
        Employee aimeeMurphy = new Employee("Aimee", "Murphy");

        Company softwareMachine = new Company("Software Machine");
        Company dataMaesters = new Company("Data Maesters");
        Company greyMatter = new Company("Grey Matter");
        Company afterglowSystems = new Company("Afterglow Systems");
        Company tictockInternational = new Company("Tictock International");
        Company goldUnwind = new Company("Gold Unwind");

        softwareMachine.getEmployees().add(johnSmith);
        dataMaesters.getEmployees().add(stephanieClarckson);
        dataMaesters.getEmployees().add(lindaKovalsky);
        greyMatter.getEmployees().add(johnSmith);
        greyMatter.getEmployees().add(lindaKovalsky);
        greyMatter.getEmployees().add(phoebePearson);
        afterglowSystems.getEmployees().add(aimeeMurphy);
        tictockInternational.getEmployees().add(madelynnCarson);
        tictockInternational.getEmployees().add(giancarloGuerrero);
        goldUnwind.getEmployees().add(morganWalsh);
        goldUnwind.getEmployees().add(dylanMurphy);
        goldUnwind.getEmployees().add(phoebePearson);
        goldUnwind.getEmployees().add(johnSmith);
        goldUnwind.getEmployees().add(ryanTalley);

        johnSmith.getCompanies().add(softwareMachine);
        johnSmith.getCompanies().add(greyMatter);
        johnSmith.getCompanies().add(goldUnwind);
        stephanieClarckson.getCompanies().add(dataMaesters);
        lindaKovalsky.getCompanies().add(dataMaesters);
        lindaKovalsky.getCompanies().add(greyMatter);
        ryanTalley.getCompanies().add(goldUnwind);
        phoebePearson.getCompanies().add(greyMatter);
        phoebePearson.getCompanies().add(goldUnwind);
        dylanMurphy.getCompanies().add(goldUnwind);
        morganWalsh.getCompanies().add(goldUnwind);
        giancarloGuerrero.getCompanies().add(tictockInternational);
        madelynnCarson.getCompanies().add(tictockInternational);
        aimeeMurphy.getCompanies().add(afterglowSystems);

        companyDao.save(softwareMachine);
        int softwareMachineId = softwareMachine.getId();
        companyDao.save(dataMaesters);
        int dataMaestersId = dataMaesters.getId();
        companyDao.save(greyMatter);
        int greyMatterId = greyMatter.getId();
        companyDao.save(afterglowSystems);
        int afterglowSystemsId = afterglowSystems.getId();
        companyDao.save(tictockInternational);
        int tictockInternationalId = tictockInternational.getId();
        companyDao.save(goldUnwind);
        int goldUnwindId = goldUnwind.getId();

        //When
        List<Employee> matchesEmployees = facade.searchEmployeeByPartLastName("ar");
        List<Company> matchesCompanies = facade.searchCompanyByPartName("ter");

        //Then
        try {
            Assert.assertEquals(3, matchesEmployees.size());
            Assert.assertEquals(4, matchesCompanies.size());
        } finally {
            //CleanUp
            companyDao.delete(softwareMachineId);
            if (companyDao.exists(dataMaestersId)) {
                companyDao.delete(dataMaestersId);
            }
            if (companyDao.exists(greyMatterId)) {
                companyDao.delete(greyMatterId);
            }
            if (companyDao.exists(afterglowSystemsId)) {
                companyDao.delete(afterglowSystemsId);
            }
            if (companyDao.exists(tictockInternationalId)) {
                companyDao.delete(tictockInternationalId);
            }
            if (companyDao.exists(goldUnwindId)) {
                companyDao.delete(goldUnwindId);
            }
        }
    }
}