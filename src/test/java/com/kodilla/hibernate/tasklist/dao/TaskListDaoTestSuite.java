package com.kodilla.hibernate.tasklist.dao;

import com.kodilla.hibernate.task.Task;
import com.kodilla.hibernate.task.TaskFinancialDetails;
import com.kodilla.hibernate.tasklist.TaskList;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskListDaoTestSuite {
    @Autowired
    private TaskListDao taskListDao;

    @Test
    public void testFindByListName() {
        // Given
        TaskList task1 = new TaskList("Tasks in progress", "Working on my computer");
        taskListDao.save(task1);
        String listName = task1.getListName();

        // When
        List<TaskList> actualTasks = taskListDao.findByListName(listName);

        //Then
        try {
            Assert.assertEquals(1, actualTasks.size());
            Assert.assertTrue(actualTasks.contains(task1));
            Assert.assertEquals("Tasks in progress", task1.getListName());
            Assert.assertEquals("Working on my computer", task1.getDescription());
        } finally {

            //CleanUp
            int id = actualTasks.get(0).getId();
            taskListDao.delete(id);
        }
    }

    @Test
    public void testTaskListDaoSaveWithTasks() {
        //Given
        Task task = new Task("Test: Learn Hibernate", 14);
        Task task2 = new Task("Test: Write some entities", 3);
        TaskFinancialDetails tfd = new TaskFinancialDetails(new BigDecimal(20), false);
        TaskFinancialDetails tfd2 = new TaskFinancialDetails(new BigDecimal(10), false);
        task.setTaskFinancialDetails(tfd);
        task2.setTaskFinancialDetails(tfd2);
        TaskList taskList = new TaskList("To do List", "To do tasks");
        taskList.getTasks().add(task);
        taskList.getTasks().add(task2);
        task.setTaskList(taskList);
        task2.setTaskList(taskList);

        //When
        taskListDao.save(taskList);
        int id = taskList.getId();

        //Then
        try {
            Assert.assertNotEquals(0, id);
        } finally {

        //CleanUp
        taskListDao.delete(id);
        }
    }
}
