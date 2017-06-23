package top.myfss.lawedu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by zuozhiwei on 2017/5/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/applicationContext.xml"})
public class UserServiceTest {
    @Resource
    UserService userService;
    @Test
    public void getLawQuestionList() throws Exception {
        System.out.println(userService.getLawQuestionList("u-1705270436283295","u-1705270447551677"));
    }

}