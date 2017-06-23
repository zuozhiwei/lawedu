package top.myfss.lawedu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by zuozhiwei on 2017/6/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:conf/applicationContext.xml"})
public class StudentServiceTest {
    @Resource
    StudentService studentService;
    @Test
    public void getAssignmentList() throws Exception {

    }

    @Test
    public void getExamQuestionList() throws Exception {
        System.out.println(studentService.getExamQuestionList("e001"));
    }

    @Test
    public void getExamInfo() throws Exception {

    }

    @Test
    public void getCertificateInfo() throws Exception {

    }

    @Test
    public void getBidProjectList() throws Exception {

    }

    @Test
    public void getStudentInfo() throws Exception {

    }

    @Test
    public void checkExamQuestion() throws Exception {
        System.out.println(studentService.checkExamQuestion("q001","a"));
    }

}