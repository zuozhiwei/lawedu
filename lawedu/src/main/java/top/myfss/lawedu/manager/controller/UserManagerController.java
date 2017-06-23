package top.myfss.lawedu.manager.controller;

import org.springframework.web.bind.annotation.RequestMethod;
import top.myfss.lawedu.manager.service.UserManagerService;
import top.myfss.lawedu.param.DataTableSendParam;
import top.myfss.lawedu.service.CommonDatabaseService;
import top.myfss.lawedu.service.UserService;
import top.myfss.lawedu.util.JsonUtil;
import top.myfss.lawedu.util.SystemIDUtil;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.myfss.lawedu.util.Tools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("manager")
public class UserManagerController {

    @Resource
    UserManagerService userManagerService;
    @Resource
    CommonDatabaseService commonDatabaseService;
    @Resource
    UserService userService;

    SystemIDUtil systemIDUtil = new SystemIDUtil();

    /**
     * 用户登录
     * @param request
     * @param mobile 手机号
     * @param password 密码
     * @return （mobileError：手机号错误；success：登录成功；passwordError：密码错误；error:网络错误）
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "loginCheck", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String loginCheck(HttpServletRequest request,
                             @RequestParam(value = "mobile") String mobile,
                             @RequestParam(value = "password") String password
    ) throws Exception {
        List<Map<String,Object>> userResult = userManagerService.checkUserLogin(mobile, password);
        if(null == userResult || userResult.size() <= 0){
            return "error";
        }
        request.getSession().setAttribute("userId",userResult.get(0).get("id"));
        request.getSession().setAttribute("userName",userResult.get(0).get("userName"));

        getMenu(request);
        userService.insertLog(userResult.get(0).get("id").toString(),"管理员登录");
        return "success";
    }

    /**
     * 获取菜单
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getMenu", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getMenu(HttpServletRequest request) throws Exception {
        List<Map<String,Object>> menuList = userManagerService.getMenuList();
        request.getSession().setAttribute("menuList",menuList);
        return "success";
    }

    /**
     * 获取用户列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getUserList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getUserList(HttpServletRequest request) throws Exception {
        HttpSession httpSession = request.getSession();
        if(null==httpSession.getAttribute("userId")) {
            return "accessError";
        }
        List<Map<String,Object>> userList = userManagerService.getUserList();
        return JsonUtil.toJSON(userList);
    }

    /**
     * 获取课程类别列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getCategoryList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getCategoryList(HttpServletRequest request) throws Exception {
        HttpSession httpSession = request.getSession();
        if(null==httpSession.getAttribute("userId")) {
            return "accessError";
        }
        List<Map<String,Object>> userList = userManagerService.getCategoryList();
        return JsonUtil.toJSON(userList);
    }

    /**
     * 修改用户信息
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateUser", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateUser(HttpServletRequest request,
                                     @RequestParam(value = "id",required = false) String id,
                                     @RequestParam(value = "userName",required = false) String userName,
                                     @RequestParam(value = "password",required = false) String password,
                                     @RequestParam(value = "mobile",required = false) String mobile,
                                     @RequestParam(value = "idCard",required = false) String idCard,
                                     @RequestParam(value = "checkMark",required = false) String checkMark,
                                     @RequestParam(value = "addTime",required = false) String addTime,
                                     @RequestParam(value = "role",required = false) String role,
                                     @RequestParam(value = "gender",required = false) String gender,
                                     @RequestParam(value = "birth",required = false) String birth)
            throws Exception {
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();
        Map<String,Object> param  = new HashMap<String,Object>();
        Tools.putMapParaEmpty(param,"userName",userName);
        Tools.putMapParaEmpty(param,"password",password);
        Tools.putMapParaEmpty(param,"mobile",mobile);
        Tools.putMapParaEmpty(param,"idCard",idCard);
        Tools.putMapParaEmpty(param,"checkMark",checkMark);
        Tools.putMapParaEmpty(param,"addTime",addTime);
        Tools.putMapParaEmpty(param,"role",role);
        Tools.putMapParaEmpty(param,"gender",gender);
        Tools.putMapParaEmpty(param,"birth",birth);
        commonDatabaseService.updateStringData("user","id",id,param);
        userService.insertLog(userId,"管理员修改用户信息");
        return "success";
    }

    /**
     * 删除用户
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "deleteUser", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String deleteUser(HttpServletRequest request,
                             @RequestParam(value = "id",required = false) String id) throws Exception {
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();
        commonDatabaseService.deleteSingleData("user","id",id);
        userService.insertLog(userId,"管理员删除用户");
        return "success";
    }

    /**
     * 添加课程（理论课程和实践课程）
     * @param request
     * @param type (theory-理论课程；practice-实践课程)
     * @param name
     * @param categoryId
     * @param content
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addCourseTheory", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addCourseTheory(HttpServletRequest request,
                                     @RequestParam(value = "type") String type,
                                     @RequestParam(value = "name") String name,
                                     @RequestParam(value = "categoryId") String categoryId,
                                     @RequestParam(value = "url") String url,
                                     @RequestParam(value = "content") String content)
            throws Exception {
        String table = "";
        String courseID = "";
        if ("theory".equals(type)) {
            courseID = systemIDUtil.getCourseTheoryID();
            table = "course_theory";
        }
        if ("practice".equals(type)) {
            courseID = systemIDUtil.coursePracticeID();
            table = "course_practice";
        }
        Map<String,Object> param  = new HashMap<String,Object>();
        param.put("id","'"+courseID+"'");
        param.put("name","'"+name+"'");
        param.put("categoryId","'"+categoryId+"'");
        param.put("url","'"+url+"'");
        param.put("content","'"+content+"'");
        param.put("addTime","now()");
        commonDatabaseService.insertData(table,param);
        return "success";
    }
    /**
     * 更新项目
     * @param request
     * @param id  
     * @param projectName
     * @param addTime
     * @param beginTime
     * @param endTime
     * @param description
     * @param userName
     * @param status
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateProject", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateProject(HttpServletRequest request,
                                     @RequestParam(value = "id") String id,
                                     @RequestParam(value = "projectName") String projectName,
                                     @RequestParam(value = "addTime") String addTime,
                                     @RequestParam(value = "beginTime") String beginTime,
                                     @RequestParam(value = "endTime") String endTime,
                                     @RequestParam(value = "description") String description,
                                     @RequestParam(value = "userName") String userName,
                                     @RequestParam(value = "status") String status)
            throws Exception{
    	Map<String, Object> param = new HashMap<>();
    	param.put("projectName", projectName);
    	param.put("addTime", addTime);
    	param.put("beginTime", beginTime);
    	param.put("endTime", endTime);
    	param.put("description", description);
    	param.put("status", status);
    	commonDatabaseService.updateStringData("project", "id", id, param);
    	return "success";
    }
    
    @ResponseBody
    @RequestMapping(value = "projectDelete", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String projectDelete(HttpServletRequest request,
                                     @RequestParam(value = "id") String id){
    	commonDatabaseService.deleteSingleData("project", "id", id);
    	return "success";
    }

    /**
     * 更新课程
     * @param request
     * @param type (theory-理论课程；practice-实践课程)
     * @param name
     * @param categoryId
     * @param content
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateCourse", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateCourse(HttpServletRequest request,
                                  @RequestParam(value = "type") String type,
                                  @RequestParam(value = "id") String id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "content") String content,
                                  @RequestParam(value = "categoryId") String categoryId,
                                  @RequestParam(value = "url") String url,
                                  @RequestParam(value = "addTime") String addTime)
            throws Exception {
        String table = "";
        String courseID = "";
        if ("theory".equals(type)) {
            table = "course_theory";
        }
        if ("practice".equals(type)) {
            table = "course_practice";
        }
        Map<String,Object> param  = new HashMap<String,Object>();
        param.put("name",name);
        param.put("categoryId",categoryId);
        param.put("content",content);
        param.put("addTime",addTime);
        param.put("url",url);
        commonDatabaseService.updateStringData(table,"id",id,param);
        return "success";
    }
    /**
     * 删除课程
     * @param request
     * @param id
     * @param type  (theory-理论课程；practice-实践课程)
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "courseDelete", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String courseDelete(HttpServletRequest request,
                             @RequestParam(value = "id",required = false) String id,
                             @RequestParam(value = "type",required = false) String type) throws Exception {
    	String table = "";
    	 if ("theory".equals(type)) {
             table = "course_theory";
         }
         if ("practice".equals(type)) {
             table = "course_practice";
         }
         if ("video".equals(type)) {
        	 table = "course_video";
		}
         commonDatabaseService.deleteSingleData(table, "id", id);
        return "success";
    }
    
    
    /**
     * 更新视频课程
     * @param request
     * @param id
     * @param name
     * @param videoUrl
     * @param categoryId
     * @param description
     * @param addTime
     * @param assignmentTask
     * @param url
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateCourseVideo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateCourseVideo(HttpServletRequest request,
                                  @RequestParam(value = "id") String id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "videoUrl") String videoUrl,
                                  @RequestParam(value = "categoryId") String categoryId,
                                  @RequestParam(value = "description") String description,
                                  @RequestParam(value = "addTime") String addTime,
                                  @RequestParam(value = "assignmentTask") String assignmentTask,
                                  @RequestParam(value = "url") String url){
    	Map<String, Object> param = new HashMap<>();
    	param.put("name", name);
    	param.put("videoUrl", videoUrl);
    	param.put("categoryId", categoryId);
    	param.put("description", description);
    	param.put("addTime", addTime);
    	param.put("assignmentTask", assignmentTask);
    	param.put("url", url);
    	commonDatabaseService.updateStringData("course_video", "id", id, param);
    	return "success";
    }
    
    
    /**
     * 添加视频课程
     * @param request
     * @param name
     * @param teacherId
     * @param videoUrl
     * @param categoryId
     * @param description
     * @param assignmentTask
     * @param url
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addCourseVideo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addCourseVideo(HttpServletRequest request,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "teacherId") String teacherId,
                                  @RequestParam(value = "videoUrl") String videoUrl,
                                  @RequestParam(value = "categoryId") String categoryId,
                                  @RequestParam(value = "description") String description,
                                  @RequestParam(value = "assignmentTask") String assignmentTask,
                                  @RequestParam(value = "url") String url){
    	String id = systemIDUtil.getCourseVideoID();
    	Map<String, Object> param = new HashMap<>();
    	param.put("id", "'"+id+"'");
    	param.put("name", "'"+name+"'");
    	param.put("videoUrl", "'"+videoUrl+"'");
    	param.put("teacherId", "'"+teacherId+"'");
    	param.put("description", "'"+description+"'");
    	param.put("assignmentTask", "'"+assignmentTask+"'");
    	param.put("url", "'"+url+"'");
    	param.put("addTime", "now()");
    	commonDatabaseService.insertData("course_video", param);
    	return "success";
    }
    
    
    
    /**
     * 获取考试信息列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getExamList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getExamList(HttpServletRequest request){
    	HttpSession httpSession = request.getSession();
        if(null==httpSession.getAttribute("userId")) {
            return "accessError";
        }
        List<Map<String,Object>> examList = userManagerService.getExamList();
        return JsonUtil.toJSON(examList);

    }
    
    
    /**
     * 添加考试
     * @param request
     * @param name
     * @param certificateId
     * @param certificateName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addExam", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addExam(HttpServletRequest request,
    		 @RequestParam(value = "name") String name,
    		 @RequestParam(value = "certificateId") String certificateId,
    		 @RequestParam(value = "certificateName") String certificateName
    		){
    	String id = systemIDUtil.getExamID();
    	Map<String, Object> param = new HashMap<>();
    	param.put("id", id);
    	param.put("name", name);
    	param.put("certificateId", certificateId);
    	param.put("certificateName", certificateName);
    	commonDatabaseService.insertStringData("exam_list", param);
    	return "success";
    }
    
    
    /**
     * 更新考试
     * @param request
     * @param id
     * @param name
     * @param certificateId
     * @param certificateName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateExam", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateExam(HttpServletRequest request,
    		 @RequestParam(value = "id") String id,
    		 @RequestParam(value = "name") String name,
    		 @RequestParam(value = "certificateId") String certificateId,
    		 @RequestParam(value = "certificateName") String certificateName
    		){
    	Map<String, Object> param = new HashMap<>();
    	param.put("name", name);
    	param.put("certificateId", certificateId);
    	param.put("certificateName", certificateName);
    	commonDatabaseService.updateStringData("exam_list", "id", id, param);
    	return "success";
    }
    /**
     * 删除考试
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "examDelete", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String examDelete(HttpServletRequest request,
    		 @RequestParam(value = "id") String id){
    	commonDatabaseService.deleteSingleData("exam_list", "id", id);
    	return "success";
    }
    
    
    
    /**
     * 获取作业列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAssignmentList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getAssignmentList(HttpServletRequest request){
    	HttpSession httpSession = request.getSession();
        if(null==httpSession.getAttribute("userId")) {
            return "accessError";
        }
        List<Map<String,Object>> assignmentList = userManagerService.getAssignmentList();
        return JsonUtil.toJSON(assignmentList);
        }
    
    /**
     * 添加作业
     * @param request
     * @param name
     * @param fromId
     * @param courseVideoId
     * @param fileUrl
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addAssignment", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addAssignment(HttpServletRequest request,
    		 @RequestParam(value = "name") String name,
    		 @RequestParam(value = "studentId") String fromId,
    		 @RequestParam(value = "courseVideoId") String courseVideoId,
    		 @RequestParam(value = "fileUrl") String fileUrl
    		){
    	String id = systemIDUtil.getAssignmentID();
    	Map<String, Object> param = new HashMap<>();
    	param.put("id", "'"+id+"'");
    	param.put("name", "'"+name+"'");
    	param.put("fromId", "'"+fromId+"'");
    	param.put("courseVideoId", "'"+courseVideoId+"'");
    	param.put("fileUrl", "'"+fileUrl+"'");
    	param.put("addTime", "now()");
    	commonDatabaseService.insertData("assignment", param);
    	return "success";
    }
    
    
    /**
     * 更新作业
     * @param request
     * @param id
     * @param name
     * @param score
     * @param addTime
     * @param fileUrl
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateAssignment", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateAssignment(HttpServletRequest request,
    		 @RequestParam(value = "id") String id,
    		 @RequestParam(value = "name") String name,
    		 @RequestParam(value = "score") String score,
    		 @RequestParam(value = "addTime") String addTime,
    		 @RequestParam(value = "fileUrl") String fileUrl
    		){
    	Map<String, Object> param = new HashMap<>();
    	param.put("name", name);
    	param.put("score", score);
    	param.put("addTime", addTime);
    	param.put("fileUrl", fileUrl);
    	commonDatabaseService.updateStringData("assignment", "id", id, param);
    	return "success";
    }
    
    
    /**
     * 删除作业
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteAssignment", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String deleteAssignment(HttpServletRequest request,
    		 @RequestParam(value = "id") String id){
    	commonDatabaseService.deleteSingleData("assignment", "id", id);
    	return "success";
    }
    
    /**
     * 获取成果列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getArticleList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getAjrticleList(HttpServletRequest request){
    	HttpSession httpSession = request.getSession();
        if(null==httpSession.getAttribute("userId")) {
            return "accessError";
        }
        List<Map<String,Object>> articleList = userManagerService.getArticleList();
        return JsonUtil.toJSON(articleList);
        }
    
    
    /**
     * 添加成果
     * @param request
     * @param name
     * @param addUser
     * @param description
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addArticle", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addArticle(HttpServletRequest request,
    		 @RequestParam(value = "name") String name,
    		 @RequestParam(value = "addUser") String addUser,
    		 @RequestParam(value = "description") String description
    		){
    	String id = systemIDUtil.getArticleID();
    	Map<String, Object> param = new HashMap<>();
    	param.put("id", "'"+id+"'");
    	param.put("name", "'"+name+"'");
    	param.put("addUser", "'"+addUser+"'");
    	param.put("description", "'"+description+"'");
    	param.put("addTime", "now()");
    	commonDatabaseService.insertData("article", param);
    	return "success";
    }
    
    /**
     * 更新成果
     * @param request
     * @param id
     * @param name
     * @param addUser
     * @param addTime
     * @param description
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateArticle", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateArticle(HttpServletRequest request,
    		 @RequestParam(value = "id") String id,
    		 @RequestParam(value = "name") String name,
    		 @RequestParam(value = "addUser") String addUser,
    		 @RequestParam(value = "addTime") String addTime,
    		 @RequestParam(value = "description") String description
    		){
    	Map<String, Object> param = new HashMap<>();
    	param.put("name", name);
    	param.put("addUser", addUser);
    	param.put("addTime", addTime);
    	param.put("description", description);
    	commonDatabaseService.updateStringData("article", "id", id, param);
    	return "success";
    }
    
    /**
     * 删除成果
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "articleDelete", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String articleDelete(HttpServletRequest request,
    		 @RequestParam(value = "id") String id){
    	commonDatabaseService.deleteSingleData("article", "id", id);
    	return "success";
    }
    
    
    /**
     * 添加项目
     * @param request
     * @param projectName
     * @param beginTime
     * @param endTime
     * @param description
     * @param addUser
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addProject", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addProject(HttpServletRequest request,
                                     @RequestParam(value = "projectName") String projectName,
                                     @RequestParam(value = "beginTime") String beginTime,
                                     @RequestParam(value = "endTime") String endTime,
                                     @RequestParam(value = "description") String description,
                                     @RequestParam(value = "addUser") String addUser)
            throws Exception{
    	String id = systemIDUtil.getProjectID();
    	Map<String, Object> param = new HashMap<>();
    	param.put("id", "'"+id+"'");
    	param.put("projectName", "'"+projectName+"'");
    	param.put("beginTime", "'"+beginTime+"'");
    	param.put("endTime", "'"+endTime+"'");
    	param.put("description", "'"+description+"'");
    	param.put("addUser", "'"+addUser+"'");
    	param.put("addTime", "now()");
    	param.put("status", "'0'");

    	commonDatabaseService.insertData("project", param);
    	return "success";
    }
    
    
    /**
     * 获取成绩证书列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getUserCertificatesList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getUserCertificatesList(HttpServletRequest request){
    	HttpSession httpSession = request.getSession();
        if(null==httpSession.getAttribute("userId")) {
            return "accessError";
        }
        List<Map<String,Object>> userCertificatesList = userManagerService.getUserCertificatesList();
        return JsonUtil.toJSON(userCertificatesList);
    }

    /**
     * 获取课程分类列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getCourseCategoryList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getCourseCategoryList(HttpServletRequest request) throws Exception {
        List<Map<String, Object>> courseList = userManagerService.getCourseCategoryList();
        int sum = courseList.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sum", sum);
        map.put("list", courseList);
        return JsonUtil.toJSON(map);
    }

    /**
     * 添加课程分类
     * @param request
     * @param id
     * @param name
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addCourseCategory", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addCourseCategory(HttpServletRequest request,
                             @RequestParam(value = "id") String id,
                             @RequestParam(value = "name") String name)
            throws Exception{
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("name", name);
        commonDatabaseService.insertStringData("category", param);
        return "success";
    }

    /**
     * 删除课程分类表
     * @param request
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "courseCategoryDelete", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String courseCategoryDelete(HttpServletRequest request,
                                       @RequestParam(value = "id") String id)
            throws Exception{
        commonDatabaseService.deleteSingleData("category","id",id);
        return "success";
    }

    /**
     * 修改课堂提问
     * @param request
     * @param id
     * @param fromId
     * @param toId
     * @param content
     * @param addTime
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateAsk", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateAsk(HttpServletRequest request,
                                    @RequestParam(value = "id") String id,
                                    @RequestParam(value = "fromId") String fromId,
                                    @RequestParam(value = "toId") String toId,
                                    @RequestParam(value = "content") String content,
                                    @RequestParam(value = "addTime") String addTime)
            throws Exception{
        Map<String, Object> param = new HashMap<>();
        param.put("fromId", fromId);
        param.put("toId", toId);
        param.put("content", content);
        param.put("addTime", addTime);
        commonDatabaseService.updateStringData("ask","id",id,param);
        return "success";
    }

    /**
     * 删除课堂问题
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteAsk", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String deleteAsk(HttpServletRequest request,
                                @RequestParam(value = "id") String id){
        commonDatabaseService.deleteSingleData("ask", "id", id);
        return "success";
    }
    /**
     * 修改用户考试成绩
     * @param request
     * @param id
     * @param examId
     * @param score
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateUserCertificates", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateUserCertificates(HttpServletRequest request,
                             @RequestParam(value = "id") String id,
                             @RequestParam(value = "examId") String examId,
                             @RequestParam(value = "score") String score,
                             @RequestParam(value = "userId") String userId)
            throws Exception{
    	Map<String, Object> param = new HashMap<>();
    	param.put("id", id);
    	param.put("examId", examId);
    	param.put("score", score);
    	param.put("userId", userId);
    	commonDatabaseService.updateStringData("user_exam", "id", id, param);
    	return "success";
    }
    
    /**
     * 删除学生考试成绩
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteUserCertificates", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String deleteUserCertificates(HttpServletRequest request,
                             @RequestParam(value = "id") String id){
    	commonDatabaseService.deleteSingleData("user_exam", "id", id);
    	return "success";
    }
    
    
    /**
     * 添加学生成绩
     * @param request
     * @param examId
     * @param score
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addUserCertificates", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addUserCertificates(HttpServletRequest request,
                             @RequestParam(value = "examId") String examId,
                             @RequestParam(value = "score") String score,
                             @RequestParam(value = "userId") String userId)
            throws Exception{
    	String id = systemIDUtil.getExamResultID();
    	Map<String,Object> param = new HashMap<>();
    	param.put("id", id);
    	param.put("examId", examId);
    	param.put("score", score);
    	param.put("userId", userId);
    	commonDatabaseService.insertStringData("user_exam", param);
    	return "success";
    }
    
    
    /**
     * 获取留言列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getMessageList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getMessageList(HttpServletRequest request)
            throws Exception{
    	HttpSession httpSession = request.getSession();
        if(null==httpSession.getAttribute("userId")) {
            return "accessError";
        }
        List<Map<String,Object>> messageList = userManagerService.getMessageList();
        return JsonUtil.toJSON(messageList);
    }
    
    
    /**
     * 更新留言
     * @param request
     * @param id
     * @param fromId
     * @param content
     * @param toId
     * @param addTime
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateMessage", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateMessage(HttpServletRequest request,
    		@RequestParam(value = "id") String id,
    		@RequestParam(value = "fromId") String fromId,
    		@RequestParam(value = "content") String content,
    		@RequestParam(value = "toId") String toId,
    		@RequestParam(value = "addTime") String addTime)
            throws Exception{
    	Map<String, Object> param = new HashMap<>();
    	param.put("id", id);
    	param.put("fromId", fromId);
    	param.put("content", content);
    	param.put("toId", toId);
    	param.put("addTime", addTime);
    	commonDatabaseService.updateStringData("message", "id", id, param);
    	return "success";
    	}
    
    
    /**
     * 删除留言
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteMessage", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String deleteMessage(HttpServletRequest request,
    		@RequestParam(value = "id") String id){
    	commonDatabaseService.deleteSingleData("message", "id", id);
    	return "success";
    }

    /**
     * 获取系统日志
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getLogList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getLogList(HttpServletRequest request)
            throws Exception{
        List<Map<String,Object>> logList = userManagerService.getLogList();
        return JsonUtil.toJSON(logList);
    }

    /**
     * 删除日志
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteLog", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String deleteLog(HttpServletRequest request,
                                @RequestParam(value = "id") String id)
            throws Exception {
        commonDatabaseService.deleteSingleData("sys_log", "id", id);
        return "success";
    }

    /**
     * 获取咨询问题答案
     * @param request
     * @param id 问题id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getReplyLawQuestion", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getReplyLawQuestion(HttpServletRequest request,
                            @RequestParam(value = "id") String id)
            throws Exception {
        return JsonUtil.toJSON(userService.getReplyLawQuestionList(id));
    }

    /**
     * 获取菜单信息列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getMenuInfoList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getMenuInfoList(HttpServletRequest request) throws Exception {
        List<Map<String,Object>> menuList = userManagerService.getMenuList();
        return JsonUtil.toJSON(menuList);
    }

    /**
     * 更新菜单
     * @param request
     * @param id 菜单id
     * @param menuName 菜单名称
     * @param menuPage 菜单页面
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateMenu", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateMenu(HttpServletRequest request,
                             @RequestParam(value = "id") String id,
                             @RequestParam(value = "menuName") String menuName,
                             @RequestParam(value = "menuPage") String menuPage)
            throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("menuName", menuName);
        param.put("menuPage", menuPage);
        commonDatabaseService.updateStringData("menu", "id", id, param);
        return "success";
    }

    /**
     * 增加菜单
     * @param request
     * @param menuName 菜单名称
     * @param menuPage 菜单页面
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addMenu", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addMenu(HttpServletRequest request,
                             @RequestParam(value = "menuName") String menuName,
                             @RequestParam(value = "menuPage") String menuPage)
            throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("menuName", menuName);
        param.put("menuPage", menuPage);
        commonDatabaseService.insertStringData("menu",param);
        return "success";
    }

    /**
     * 删除菜单
     * @param request
     * @param id 菜单id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "deleteMenu", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String deleteMenu(HttpServletRequest request,
                          @RequestParam(value = "id") String id)
            throws Exception {
        commonDatabaseService.deleteSingleData("menu","id",id);
        return "success";
    }

    /**
     * 获取考试菜单
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getExamSelect", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getExamSelect(HttpServletRequest request) throws Exception {
        List<Map<String,Object>> examList = userManagerService.getExamList();
        request.getSession().setAttribute("examList",examList);
        return "success";
    }

    /**
     * 修改题目
     * @param request
     * @param id
     * @param no
     * @param question
     * @param answera
     * @param answerb
     * @param answerc
     * @param answerd
     * @param answer
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateExamQuestion", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateExamQuestion(HttpServletRequest request,
                             @RequestParam(value = "id") String id,
                             @RequestParam(value = "no") String no,
                             @RequestParam(value = "question") String question,
                             @RequestParam(value = "answera") String answera,
                             @RequestParam(value = "answerb") String answerb,
                             @RequestParam(value = "answerc") String answerc,
                             @RequestParam(value = "answerd") String answerd,
                             @RequestParam(value = "answer") String answer)
            throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("no", no);
        param.put("question", question);
        param.put("answera", answera);
        param.put("answerb", answerb);
        param.put("answerc", answerc);
        param.put("answerd", answerd);
        param.put("answer", answer);
        commonDatabaseService.updateStringData("exam_question", "id", id, param);
        return "success";
    }

    /**
     * 添加考试题目
     * @param request
     * @param no
     * @param question
     * @param answera
     * @param answerb
     * @param answerc
     * @param answerd
     * @param answer
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addExamQuestion", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addExamQuestion(HttpServletRequest request,
                                     @RequestParam(value = "examId") String examId,
                                     @RequestParam(value = "no") String no,
                                     @RequestParam(value = "question") String question,
                                     @RequestParam(value = "answera") String answera,
                                     @RequestParam(value = "answerb") String answerb,
                                     @RequestParam(value = "answerc") String answerc,
                                     @RequestParam(value = "answerd") String answerd,
                                     @RequestParam(value = "answer") String answer)
            throws Exception {
        String id = systemIDUtil.getExamQuestionID();
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("examId", examId);
        param.put("no", no);
        param.put("question", question);
        param.put("answera", answera);
        param.put("answerb", answerb);
        param.put("answerc", answerc);
        param.put("answerd", answerd);
        param.put("answer", answer);
        commonDatabaseService.insertStringData("exam_question", param);
        return "success";
    }

}
