package top.myfss.lawedu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import top.myfss.lawedu.bean.config;
import top.myfss.lawedu.service.*;
import top.myfss.lawedu.util.FileOptUtil;
import top.myfss.lawedu.util.JsonUtil;
import top.myfss.lawedu.util.SystemIDUtil;
import top.myfss.lawedu.util.Tools;

/**
 * 教师管理
 * @author zuozhiwei
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("teacher")
public class TeacherController {

	@Resource
	private TeacherService teacherService;
	@Resource
	private CommonDatabaseService commonDatabaseService;
	@Resource
    UserService userService;

	private FileUploadService fileUploadService = new FileUploadService();
	private SystemIDUtil systemIDUtil = new SystemIDUtil();
	private FileOptUtil fileOptUtil = new FileOptUtil();

    /**
     * 教师上传视频课程
     * @param request
     * @param courseName 课程名称
     * @param file 文件
     * @param categoryId 分类id
     * @param description 课程描述
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "uploadCourseVideo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String uploadCourseVideo(HttpServletRequest request,
                                   @RequestParam(value = "courseName" ,required = false) String courseName,
                                   @RequestParam(value = "file",required = false) MultipartFile file,
                                   @RequestParam(value = "categoryId",required = false) String categoryId,
                                   @RequestParam(value = "description",required = false) String description
    ) throws Exception {
        // 取session中的用户id
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();
        // 上传单个文件
        String[] types = {};
        String fileStatus = "";
        try {
            fileStatus = fileUploadService.fileSingleUpload(file, config.baseDir+config.videoDir, userId, 1024000, types);
        } catch (Exception e) {
            return e.getMessage();
        }
        // 生成视频课程id
        String courseVideoID = systemIDUtil.getCourseVideoID();
        // 设置参数插入数据库
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", "'" + courseVideoID + "'");
        param.put("name", "'" + courseName + "'");
        param.put("url", "'" + fileStatus + "'");
        param.put("teacherId", "'" + userId + "'");
        param.put("description", "'" + description + "'");
        param.put("addTime", "now()");

        commonDatabaseService.insertData("course_video", param);
        userService.insertLog(userId,"教师上传视频课程");
        return "success";
    }

    /**
     * 教师添加作业任务
     * @param request
     * @param courseVideoId 视频课程id
     * @param assignmentTask 作业任务内容
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addAssignmentTask", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addAssignmentTask(HttpServletRequest request,
                                    @RequestParam(value = "courseVideoId" ,required = false) String courseVideoId,
                                    @RequestParam(value = "assignmentTask",required = false) String assignmentTask)
            throws Exception {
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("assignmentTask", assignmentTask);
        commonDatabaseService.updateStringData("course_video","id",courseVideoId,param);
        userService.insertLog(userId,"教师添加作业任务");
        return "success";
    }

    /**
     * 教师获取作业列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getAssignmentList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getAssignmentList(HttpServletRequest request,
                                    @RequestParam(value = "courseVideoId" ,required = false) String courseVideoId)
            throws Exception {
        List<Map<String,Object>> assignmentList = teacherService.getAssignmentList(courseVideoId);
        int sum = assignmentList.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sum", sum);
        map.put("list",assignmentList);
        return JsonUtil.toJSON(map);
    }

    /**
     * 教师给作业打分
     * @param request
     * @param score 分数
     * @param assignmentId 作业id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "markAssignment", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String markAssignment(HttpServletRequest request,
                                 @RequestParam(value = "score" ,required = false) String score,
                                 @RequestParam(value = "assignmentId" ,required = false) String assignmentId)
            throws Exception {
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("score", score);
        commonDatabaseService.updateStringData("assignment","id",assignmentId,param);
        userService.insertLog(userId,"教师给作业打分");
        return "success";
    }

    /**
     * 下载作业
     * @param request
     * @param response
     * @param assignmentUrl 作业下载链接
     * @param assignmentName 作业名称
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "downloadAssignment", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public void downloadAssignment(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "assignmentUrl") String assignmentUrl,
                                   @RequestParam(value = "assignmentName") String assignmentName)
            throws Exception {
        fileOptUtil.download(response,request,config.baseDir+config.assignmentDir+assignmentUrl,assignmentName);
    }

    /**
     * 添加招标项目
     * @param request
     * @param projectName 项目名称
     * @param description 项目描述
     * @param beginTime 开始日期
     * @param endTime 结束日期
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addBidProject", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addBidProject(HttpServletRequest request,
                                @RequestParam(value = "projectName") String projectName,
                                @RequestParam(value = "description") String description,
                                @RequestParam(value = "beginTime") String beginTime,
                                @RequestParam(value = "endTime") String endTime)
            throws Exception {
        // 取session中的用户id
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();

        String projectID = systemIDUtil.getProjectID();

        Map<String,Object> param = new HashMap<String,Object>();
        Tools.putMapParaEmpty(param,"id","'"+projectID+"'");
        Tools.putMapParaEmpty(param,"projectName","'"+projectName+"'");
        Tools.putMapParaEmpty(param,"description","'"+description+"'");
        Tools.putMapParaEmpty(param,"addUser","'"+userId+"'");
        Tools.putMapParaEmpty(param,"beginTime","str_to_date(" + "'" + beginTime + "','%Y-%m-%d %T')");
        Tools.putMapParaEmpty(param,"endTime","str_to_date(" + "'" + endTime + "','%Y-%m-%d %T')");
        Tools.putMapParaEmpty(param,"addTime","now()");
        commonDatabaseService.insertData("project",param);
        return "success";
    }

    /**
     * 审核申请的学生
     * @param request
     * @param projectUserId 项目用户列表id
     * @param check 状态(0-待审核；1-审核通过；2-不通过)
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "checkApplyBidProject", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String checkApplyBidProject(HttpServletRequest request,
                                       @RequestParam(value = "projectUserId") String projectUserId,
                                       @RequestParam(value = "check") String check)
            throws Exception {
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();
        Map<String,Object> param = new HashMap<String,Object>();
        Tools.putMapParaEmpty(param,"checkStatus",check);
        if ("1".equals(check)) {
            Tools.putMapParaEmpty(param,"checkOpinion","恭喜你通过审核，你可以联系老师开始你的项目了！");
        }
        if ("2".equals(check)) {
            Tools.putMapParaEmpty(param,"checkOpinion","对不起，你的申请没有通过，你可以选择其他项目！");
        }

        commonDatabaseService.updateStringData("project_user","id",projectUserId,param);
        userService.insertLog(userId,"教师审核项目成员");
        return "success";
    }

    /**
     * 获取招标项目列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getBidProjectList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getBidProjectList(HttpServletRequest request,
                                    @RequestParam(value = "status",required = false) String status)
            throws Exception {
        // 取session中的用户id
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();

        List<Map<String, Object>> bidProjectList = teacherService.getBidProjectList(userId,status);
        int sum = bidProjectList.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sum", sum);
        map.put("list", bidProjectList);
        return JsonUtil.toJSON(map);
    }

    /**
     * 获取教师详情
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getTeacherInfo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getTeacherInfo(HttpServletRequest request)
            throws Exception {
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();

        return JsonUtil.toJSON(teacherService.getTeacherInfo(userId));
    }

    /**
     * 教师更新个人信息
     * @param request
     * @param userName
     * @param birth
     * @param teachLevel
     * @param gender
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "updateTeacherInfo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String updateTeacherInfo(HttpServletRequest request,
                                    @RequestParam(value = "userName",required = false) String userName,
                                    @RequestParam(value = "birth",required = false) String birth,
                                    @RequestParam(value = "teachLevel",required = false) String teachLevel,
                                    @RequestParam(value = "gender",required = false) String gender)
            throws Exception {
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();
        Map<String,Object> param = new HashMap<String,Object>();
        Tools.putMapParaEmpty(param,"userName","'" + userName + "'");
        Tools.putMapParaEmpty(param,"teachLevel","'" + teachLevel + "'");
        Tools.putMapParaEmpty(param,"gender","'" + gender + "'");
        Tools.putMapParaEmpty(param,"birth","str_to_date(" + "'" + birth + "','%Y-%m-%d %T')");
        commonDatabaseService.updateData("info_teacher","id",userId,param);
        param.remove("teachLevel");
        commonDatabaseService.updateData("user","id",userId,param);
        return "success";
    }

}
