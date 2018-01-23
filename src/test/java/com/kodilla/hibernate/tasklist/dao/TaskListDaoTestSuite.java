package com.kodilla.hibernate.tasklist.dao;

import com.kodilla.hibernate.tasklist.TaskList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
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
        Assert.assertEquals(1, actualTasks.size());
        //Assert.assertTrue(actualTasks.contains(task1));
        Assert.assertEquals("Tasks in progress", task1.getListName());
        Assert.assertEquals("Working on my computer", task1.getDescription());

        //CleanUp
        int id = actualTasks.get(0).getId();
        taskListDao.delete(id);
    }
}
