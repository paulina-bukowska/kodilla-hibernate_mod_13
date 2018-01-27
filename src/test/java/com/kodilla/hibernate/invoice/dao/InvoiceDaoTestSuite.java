package com.kodilla.hibernate.invoice.dao;

import com.kodilla.hibernate.invoice.Invoice;
import com.kodilla.hibernate.invoice.Item;
import com.kodilla.hibernate.invoice.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceDaoTestSuite {
    @Autowired
    private InvoiceDao invoiceDao;

    @Test
    public void testInvoiceDaoSave() {
        //Given
        Product game = new Product("Board game");
        Product bear = new Product("Teddy bear");
        Product car = new Product("Black car");

        Item item1 = new Item(new BigDecimal("120.50"), 3);
        Item item2 = new Item(new BigDecimal("320.80"), 6);
        Item item3 = new Item(new BigDecimal("672.20"), 2);

        item1.setProduct(game);
        item2.setProduct(bear);
        item3.setProduct(car);

        Invoice invoice = new Invoice("IV/132/2017");

        invoice.getItems().add(item1);
        invoice.getItems().add(item2);
        invoice.getItems().add(item3);

        item1.setInvoice(invoice);
        item2.setInvoice(invoice);
        item3.setInvoice(invoice);

        //When
        invoiceDao.save(invoice);
        int idInvoice = invoice.getId();

        Invoice readInvoice = invoiceDao.findOne(idInvoice);
        long howManyRecordsInInvoice = invoiceDao.count();

        //Then
            try {
                Assert.assertEquals(idInvoice, readInvoice.getId());
                Assert.assertEquals(1, howManyRecordsInInvoice);
                Assert.assertFalse(invoiceDao.exists(0));
                Assert.assertEquals("Teddy bear", bear.getName());
                Assert.assertEquals(bear, item2.getProduct());
            } finally {
                //CleanUp
                invoiceDao.delete(idInvoice);
            }
    }
}
