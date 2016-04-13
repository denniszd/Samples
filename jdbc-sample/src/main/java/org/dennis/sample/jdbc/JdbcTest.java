package org.dennis.sample.jdbc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
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
        Assert.assertNotEquals(id,0);
    }

    @Test
    public void verify() {
//        Assert.assertTrue(userDao.verify("Dennis","112233445566"));
        Assert.assertTrue(userDao.verify("Dennis","' OR '1'='1"));
    }

    @After
    public void destroy() {
        userDao = null;
    }
}
