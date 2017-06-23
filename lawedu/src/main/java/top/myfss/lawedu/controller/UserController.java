package top.myfss.lawedu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.myfss.lawedu.service.CommonDatabaseService;
import top.myfss.lawedu.service.TeacherService;
import top.myfss.lawedu.service.UserService;
import top.myfss.lawedu.util.JsonUtil;
import top.myfss.lawedu.util.SystemIDUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * @author zuozhiwei
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	UserService userService;
	@Resource
	CommonDatabaseService commonDatabaseService;
	@Resource
	TeacherService teacherService;

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
		String userResult = userService.checkUserLogin(request, mobile, password);
		return userResult;
	}

	/**
	 * 用户注册
	 * @param request
	 * @param mobile 手机号
	 * @param password 密码
	 * @param idCard 身份证号
	 * @param role 用户类型
	 * @param gender 性别
	 * @param birth 出生日期
	 * @param userName 用户姓名
	 * @return	mobileExist 手机号已存在
	 * 			success 注册成功
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addUser", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String addPurchaseUser(HttpServletRequest request,
								  @RequestParam(value = "mobile") String mobile,
								  @RequestParam(value = "password") String password,
								  @RequestParam(value = "idCard") String idCard,
								  @RequestParam(value = "role") String role,
								  @RequestParam(value = "gender") String gender,
								  @RequestParam(value = "birth") String birth,
								  @RequestParam(value = "userName") String userName)
			throws Exception {
		String id = systemIDUtil.getUserID();
		Map<String, Object> param = new HashMap<String, Object>();

		if ("mobileExist".equals(userService.checkMobile(mobile))) {
			return "mobileExist";
		}

		param.put("id", "'" + id + "'");
		param.put("mobile", "'" + mobile + "'");
		param.put("password", "'" + password + "'");
		param.put("idCard", "'" + idCard + "'");
		param.put("role", "'" + role + "'");
		param.put("gender", "'" + gender + "'");
		param.put("birth", "str_to_date(" + "'" + birth + "','%Y-%m-%d %T')");
		param.put("userName", "'" + userName + "'");
		param.put("addTime", "now()");
		commonDatabaseService.insertData("user", param);

		if ("2".equals(role)) {
			commonDatabaseService.insertData("info_teacher", param);
		}
		if ("4".equals(role)) {
			commonDatabaseService.insertData("info_lawyer", param);
		}

		userService.insertLog(id,"注册");
		return "success";
	}

	/**
	 * 注销登录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "logoutUser",method = RequestMethod.POST)
	public String logoutUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();// session失效
		return "success";
	}

	/**
	 * 获取课程分类列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getCourseTheoryList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getCourseTheoryList(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> courseList = userService.getCourseTheoryList();
		int sum = courseList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list", courseList);
		return JsonUtil.toJSON(map);
	}

	/**
	 * 获取实践课程列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getCoursePracticeList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getCoursePracticeList(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> courseList = userService.getCoursePracticeList();
		int sum = courseList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list", courseList);
		return JsonUtil.toJSON(map);
	}

	/**
	 * 获取视频课程列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getCourseVideoList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getCourseVideoList(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> courseList = userService.getCourseVideoList();
		int sum = courseList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list", courseList);
		return JsonUtil.toJSON(map);
	}

	/**
	 * 获取理论课程详情
	 * @param request
	 * @param courseTheoryId 理论课程id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getCourseTheoryInfo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getCourseTheoryInfo(HttpServletRequest request,
									  @RequestParam(value = "courseTheoryId") String courseTheoryId
	) throws Exception {
		List<Map<String, Object>> courseInfo = userService.getCourseTheoryInfo(courseTheoryId);
		return JsonUtil.toJSON(courseInfo.get(0));
	}

	/**
	 * 获取实践课程详情
	 * @param request
	 * @param coursePracticeId 实践课程id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getCoursePracticeInfo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getCoursePracticeInfo(HttpServletRequest request,
										@RequestParam(value = "coursePracticeId") String coursePracticeId
	) throws Exception {
		List<Map<String, Object>> courseInfo = userService.getCoursePracticeInfo(coursePracticeId);
		return JsonUtil.toJSON(courseInfo.get(0));
	}

	/**
	 * 获取视频课程详情
	 * @param request
	 * @param courseVideoId 视频课程id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getCourseVideoInfo", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getCourseVideoInfo(HttpServletRequest request,
									 @RequestParam(value = "courseVideoId") String courseVideoId
	) throws Exception {
		List<Map<String, Object>> courseInfo = userService.getCourseVideoInfo(courseVideoId);
		return JsonUtil.toJSON(courseInfo.get(0));
	}

	/**
	 * 获取提问列表
	 * @param request
	 * @param courseId 课程id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getQuestionList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getQuestionList(HttpServletRequest request,
								  @RequestParam(value = "courseId",required = false) String courseId
	) throws Exception {
		List<Map<String, Object>> questionList = userService.getQuestionList(courseId);
		int sum = questionList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list", questionList);
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
		List<Map<String, Object>> bidProjectList = teacherService.getBidProjectList(null,status);
		int sum = bidProjectList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list", bidProjectList);
		return JsonUtil.toJSON(map);
	}

	/**
	 * 获取律师列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getLawyerList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getLawyerList(HttpServletRequest request)
			throws Exception {
		List<Map<String, Object>> lawyerList = userService.getLawyerList();
		int sum = lawyerList.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", sum);
		map.put("list", lawyerList);
		return JsonUtil.toJSON(map);
	}

	/**
	 * 发表成果
	 * @param request
	 * @param name  成果名称
	 * @param description  成果描述
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "publishArticle", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String publishArticle(HttpServletRequest request,
			 @RequestParam(value = "name") String name,
			 @RequestParam(value = "description") String description
			)throws Exception{
		HttpSession session = request.getSession();
		String userID = session.getAttribute("userId").toString();
		String articleID = systemIDUtil.getArticleID();
		Map<String, Object> param = new HashMap<>();
		param.put("id", "'"+articleID+"'");
		param.put("name", "'"+name+"'");
		param.put("description", "'"+description+"'");
		param.put("addUser", "'"+userID+"'");
		param.put("addTime", "now()");
		commonDatabaseService.insertData("article", param);
		userService.insertLog(userID,"发表成果");
		return "success";
	}

	/**
	 * 获取成果列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getArticleList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
	public String getArticleList(HttpServletRequest request)throws Exception{
		HttpSession httpSession = request.getSession();
		String userID = httpSession.getAttribute("userId").toString();
		List<Map<String, Object>> result = userService.getArticleList(userID);
        int sum = result.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sum", sum);
        map.put("list", result);
        return JsonUtil.toJSON(map);
	}

    /**
     * 添加提问信息
     * @param request
     * @param lawyerID
     * @param content
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "addLawQuestion", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String addLawQuestion(HttpServletRequest request,
                                 @RequestParam(value = "lawyerID") String lawyerID,
                                 @RequestParam(value = "content") String content)
            throws Exception{
        HttpSession session = request.getSession();
        String userID = session.getAttribute("userId").toString();

        String lawQuestionID = systemIDUtil.getLawQuestionID();

        Map<String, Object> param = new HashMap<>();
        param.put("id", "'"+lawQuestionID+"'");
        param.put("fromId", "'"+userID+"'");
        param.put("toId", "'"+lawyerID+"'");
        param.put("content", "'"+content+"'");
        param.put("addTime", "now()");
        commonDatabaseService.insertData("message", param);
		userService.insertLog(userID,"添加提问信息");
        return "success";
    }

    /**
     * 获取咨询问题列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getLawQuestionList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getLawQuestionList(HttpServletRequest request,
                                     @RequestParam(value = "fromId", required = false) String fromId,
                                     @RequestParam(value = "toId", required = false) String toId)
            throws Exception{
		HttpSession httpSession = request.getSession();
		String userId = httpSession.getAttribute("userId").toString();

		if (null == fromId || "".equals(fromId)) {
			fromId = userId;
		}

        List<Map<String, Object>> result = userService.getLawQuestionList(fromId,toId);
        int sum = result.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sum", sum);
        map.put("list", result);
        return JsonUtil.toJSON(map);
    }

    /**
     * 获取新闻列表
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "getNewsList", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
    public String getNewsList(HttpServletRequest request)throws Exception{
        List<Map<String, Object>> result = userService.getNewsList();
        int sum = result.size();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sum", sum);
        map.put("list", result);
        return JsonUtil.toJSON(map);
    }


}