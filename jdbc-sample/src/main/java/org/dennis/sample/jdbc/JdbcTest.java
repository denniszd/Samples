package org.dennis.sample.jdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author deng.zhang
 * @since 1.0.0 2016-04-12 10:58
 */
public class JdbcTest {
    private UserDao userDao;

    @Before
    public void init() {
        userDao = new UserDao();
    }

    @Test
    public void save() {
        User user = new User();
        Random random = new Random();
        user.setName("Dennis" + random.nextInt(10));
        user.setGender("male");
        user.setBirthday(new Date());
        user.setEmail("dennis@163.com");
        user.setPassword("112233445566");

        int id = userDao.save(user);
        Assert.assertNotEquals(id, 0);
    }

    @Test
    public void saveUsers() {
        int size = 1000000;
        List<User> users = new ArrayList<User>(size);
        for (int i = 0; i < size; i++) {
            User user = new User();
            Random random = new Random();
            user.setName("Dennis" + i + random.nextInt(10));
            user.setGender("male");
            user.setBirthday(new Date());
            user.setEmail("dennis@163.com");
            user.setPassword("112233445566");

            users.add(user);
        }
        userDao.saveUsers(users);
    }

    @Test
    public void testSaveBatch() {
        long startTime = System.currentTimeMillis();
        int size = 1000000;
        List<User> users = new ArrayList<User>(size);
        for (int i = 0; i < size; i++) {
            User user = new User();
            Random random = new Random();
            user.setName("Dennis" + i + random.nextInt(10));
            user.setGender("male");
            user.setBirthday(new Date());
            user.setEmail("dennis@163.com");
            user.setPassword("112233445566");

            users.add(user);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时： " + (endTime - startTime) + "ms");
        userDao.saveBatch(users);
        System.out.println("保存耗时： " + (System.currentTimeMillis() - endTime) + "ms");
    }

    @Test
    public void testQueryAll() {
        List<User> users = userDao.queryAll();
        Assert.assertNotNull(users);
    }

    @Test
    public void verify() {
//        Assert.assertTrue(userDao.verify("Dennis","112233445566"));
        Assert.assertTrue(userDao.verify("Dennis", "' OR '1'='1"));
    }

    @After
    public void destroy() {
        userDao = null;
    }
}
