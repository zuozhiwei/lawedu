package top.myfss.lawedu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import top.myfss.lawedu.bean.config;
import top.myfss.lawedu.service.CommonDatabaseService;
import top.myfss.lawedu.service.FileUploadService;
import top.myfss.lawedu.service.StudentService;
import top.myfss.lawedu.service.UserService;
import top.myfss.lawedu.util.FileOptUtil;
import top.myfss.lawedu.util.JsonUtil;
import top.myfss.lawedu.util.SystemIDUtil;
import top.myfss.lawedu.util.Tools;

/**
 * 学生管理
 * @author zuozhiwei
 */

@Controller
@RequestMapping("student")
public class StudentController {

	@Resource
	StudentService studentService;
	@Resource
	CommonDatabaseService commonDatabaseService;
	@Resource
	UserService userService;

	FileUploadService fileUploadService = new FileUploadService();
	SystemIDUtil systemIDUtil = new SystemIDUtil();
	FileOptUtil fileOptUtil = new FileOptUtil();

	/**
	 * 提交作业
	 * @param request
	 * @param name 作业名称
	 * @param file 作业文件
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "uploadAssignment", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String uploadAssignment(HttpServletRequest request,
								   @RequestParam(value = "name") String name,
								   @RequestParam(value = "courseVideoId") String courseVideoId,
                                   @RequestParam(value = "file") MultipartFile file
									) throws Exception {
		// 取session中的用户id
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("userId").toString();
		// 上传单个文件
		String[] types = {};
		String fileStatus = "";
		try {
			fileStatus = fileUploadService.fileSingleUpload(file, userId,config.baseDir+config.assignmentDir, 1024000, types);
		} catch (Exception e) {
			return e.getMessage();
		}
		// 获取作业id
		String assignmentID = systemIDUtil.getAssignmentID();
		String[] fileName = fileStatus.split("/");
		// 设置参数插入数据库
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", "'" + assignmentID + "'");
		param.put("name", "'" + fileName[2] + "'");
		param.put("fileUrl", "'" + fileStatus + "'");
		param.put("fromId", "'" + userId + "'");
		param.put("courseVideoId", "'" + courseVideoId + "'");
		param.put("addTime", "now()");

		commonDatabaseService.insertData("assignment", param);
		userService.insertLog(userId,"提交作业");
		return "success";
	}
	
	/**
	 * 获取作业列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getAssignmentList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getAssignmentList(HttpServletRequest request) throws Exception {
		// 取session中的用户id
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("userId").toString();
		List<Map<String,Object>> assignmentList = studentService.getAssignmentList(userId);
		int sum = assignmentList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list",assignmentList);
		return JsonUtil.toJSON(map);
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
    @RequestMapping(value = "downloadAssignment", produces = "application/json; charset=utf-8")
    public void downloadAssignment(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "assignmentUrl") String assignmentUrl,
                                     @RequestParam(value = "assignmentName") String assignmentName
                                     )throws Exception {
		fileOptUtil.download(response,request,config.baseDir+config.assignmentDir+assignmentUrl,assignmentName);
    }

	/**
	 * 学生提问
	 * @param request
	 * @param content 提问内容
	 * @param courseId 课程id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addQuestion", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String addQuestion(HttpServletRequest request,
							  @RequestParam(value = "content",required = false) String content,
							  @RequestParam(value = "courseId",required = false) String courseId)
			throws Exception {
		// 取session中的用户id
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("userId").toString();
		// 获取提问id
		String questionID = systemIDUtil.getQuestionID();
		// 设置参数插入数据库
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", "'" + questionID + "'");
		param.put("content", "'" + content + "'");
		param.put("toId", "'" + courseId + "'");
		param.put("fromId", "'" + userId + "'");
		param.put("addTime", "now()");

		commonDatabaseService.insertData("ask", param);
		userService.insertLog(userId,"课堂提问");
		return  "success";
	}

    /**
     * 获取考试题目列表
     * @param request
     * @param examId 考试id
     * @return
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping(value = "getExamQuestionList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getExamQuestionList(HttpServletRequest request,
                                      @RequestParam(value = "examId") String examId)
            throws Exception {
		List<Map<String,Object>> examQuestionList = studentService.getExamQuestionList(examId);
		int sum = examQuestionList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list",examQuestionList);
		return JsonUtil.toJSON(map);
	}

    /**
     * 提交考试结果
     * @param request
     * @param examId 考试id
     * @param score 分数
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "submitExamAnswer", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String submitExamAnswer(HttpServletRequest request,
                              @RequestParam(value = "examId") String examId,
                              @RequestParam(value = "score") String score)
            throws Exception {
        // 取session中的用户id
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();
        // 设置参数插入数据库
		String id = systemIDUtil.getUserExamID();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("examId", "'" + examId + "'");
        param.put("userId", "'" + userId + "'");
        param.put("score", "'" + score + "'");
        param.put("id", "'" + id + "'");

        commonDatabaseService.insertData("user_exam", param);
        userService.insertLog(userId,"提交考试成绩");
        return  "success";
    }

    /**
     * 获取考试结果信息
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getExamInfo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getExamInfo(HttpServletRequest request)
            throws Exception {
        // 取session中的用户id
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();

        List<Map<String,Object>> examQuestionList = studentService.getExamInfo(userId);
        int sum = examQuestionList.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sum", sum);
        map.put("list",examQuestionList);
        return JsonUtil.toJSON(map);
    }

    /**
     * 获取证书信息
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getCertificateInfo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getCertificateInfo(HttpServletRequest request)
            throws Exception {
        // 取session中的用户id
        HttpSession httpSession = request.getSession();
        String userId = httpSession.getAttribute("userId").toString();

        List<Map<String,Object>> examQuestionList = studentService.getCertificateInfo(userId);
        int sum = examQuestionList.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sum", sum);
        map.put("list",examQuestionList);
        return JsonUtil.toJSON(map);
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

		List<Map<String, Object>> bidProjectList = studentService.getBidProjectList(userId,status);
		int sum = bidProjectList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list", bidProjectList);
		return JsonUtil.toJSON(map);
	}

	/**
	 * 学生申请招标项目
	 * @param request
	 * @param projectId 招标项目id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "applyBidProject", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String applyBidProject(HttpServletRequest request,
								  @RequestParam(value = "projectId",required = false) String projectId)
			throws Exception {
		// 取session中的用户id
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("userId").toString();
		String projectUserID = systemIDUtil.getProjectUserID();

		// 设置参数插入数据库
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("projectId", projectId);
		param.put("userId", userId);
		param.put("id", projectUserID);
		commonDatabaseService.insertStringData("project_user",param);
		return "success";
	}

	/**
	 * 获取学生个人信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getStudentInfo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getStudentInfo(HttpServletRequest request)
			throws Exception {
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("userId").toString();
		Map<String,Object> result = studentService.getStudentInfo(userId);
		return JsonUtil.toJSON(result);
	}

	/**
	 * 学生修改用户信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateUser", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String updateUser(HttpServletRequest request,
							 @RequestParam(value = "userName",required = false) String userName,
							 @RequestParam(value = "gender",required = false) String gender,
							 @RequestParam(value = "birth",required = false) String birth)
			throws Exception {
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("userId").toString();
		Map<String,Object> param  = new HashMap<String,Object>();
		Tools.putMapParaEmpty(param,"userName",userName);
		Tools.putMapParaEmpty(param,"gender",gender);
		Tools.putMapParaEmpty(param,"birth",birth);
		commonDatabaseService.updateStringData("user","id",userId,param);
		userService.insertLog(userId,"学生修改用户信息");
		return "success";
	}

	/**
	 * 判断题目正误
	 * @param request
	 * @param questionID
	 * @return error或success
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "checkExamQuestion", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String checkExamQuestion(HttpServletRequest request,
							 @RequestParam(value = "questionID",required = false) String questionID,
							 @RequestParam(value = "answer",required = false) String answer)
			throws Exception {
		String status = studentService.checkExamQuestion(questionID,answer);
		return status;
	}
}
