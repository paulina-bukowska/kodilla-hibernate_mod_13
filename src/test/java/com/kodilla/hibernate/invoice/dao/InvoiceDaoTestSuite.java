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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceDaoTestSuite {
    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ProductDao productDao;

    @Test
    public void testInvoiceDaoSave() {
        //Given
        Product game = new Product("Board game");
        Product bear = new Product("Teddy bear");
        Product car = new Product("Black car");

        Item item1 = new Item(new BigDecimal("120.50"), 3);
        Item item2 = new Item(new BigDecimal("320.80"), 6);
        Item item3 = new Item(new BigDecimal("672.20"), 2);
        Item item4 = new Item(new BigDecimal("322.10"), 1);

        item1.setProduct(game);
        item2.setProduct(bear);
        item3.setProduct(car);
        item4.setProduct(car);

        Invoice invoice = new Invoice("IV/132/2017");

        invoice.getItems().add(item1);
        invoice.getItems().add(item2);
        invoice.getItems().add(item3);
        invoice.getItems().add(item4);

        item1.setInvoice(invoice);
        item2.setInvoice(invoice);
        item3.setInvoice(invoice);
        item4.setInvoice(invoice);

        //When
        invoiceDao.save(invoice);
        int idInvoice = invoice.getId();

        Invoice readInvoice = invoiceDao.findOne(idInvoice);
        long howManyRecordsInInvoice = invoiceDao.count();
        long howManyRecordsInItem = itemDao.count();
        long howManyRecordsInProduct = productDao.count();
        List<Product> listOfProducts = (List<Product>) productDao.findAll();

        //Then
            try {
                Assert.assertEquals(idInvoice, readInvoice.getId());
                Assert.assertEquals(1, howManyRecordsInInvoice);
                Assert.assertEquals(4, howManyRecordsInItem);
                Assert.assertEquals(3, howManyRecordsInProduct);
                Assert.assertFalse(invoiceDao.exists(0));
                Assert.assertFalse(itemDao.exists(0));
                Assert.assertFalse(productDao.exists(0));
                Assert.assertEquals("Teddy bear", bear.getName());
                Assert.assertEquals(bear, item2.getProduct());
                Assert.assertTrue(invoice.getItems().contains(item3));
                Assert.assertTrue(listOfProducts.contains(game));
                Assert.assertTrue(listOfProducts.contains(bear));
                Assert.assertTrue(listOfProducts.contains(car));

            } finally {
                //CleanUp
                invoiceDao.delete(idInvoice);
            }
    }
}
