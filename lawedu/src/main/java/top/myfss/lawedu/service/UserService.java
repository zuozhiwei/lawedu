package top.myfss.lawedu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.myfss.lawedu.persistence.CommonDatabaseMapper;
import top.myfss.lawedu.persistence.UserMapper;
import top.myfss.lawedu.util.JsonUtil;
import top.myfss.lawedu.util.SystemIDUtil;
import top.myfss.lawedu.util.Tools;

@Service
public class UserService {

	@Autowired
	private UserMapper usermapper;
	@Autowired
	private CommonDatabaseMapper commonDatabaseMapper;
	@Resource
	CommonDatabaseService commonDatabaseService;

	SystemIDUtil systemIDUtil = new SystemIDUtil();

	/**
	 * 用户登录验证
	 * @param request
	 * @param mobile
	 * @param password
	 * @return	mobileError 手机号错误；passwordError：密码错误；success：登录成功
	 */
	public String checkUserLogin(HttpServletRequest request, String mobile, String password) {
		HttpSession httpSession = request.getSession();
		Map<String, Object> login = new HashMap<String, Object>();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("mobile", mobile);
		param.put("password", password);
		if ("mobileEmpty".equals(checkMobile(mobile))) {
			login.put("info","mobileError");
			return JsonUtil.toJSON(login);
		}
		List<Map<String, String>> resultMap = usermapper.checkUserLogin(param);
		if (resultMap != null) {
			if (resultMap.size() > 0) {
				httpSession.setAttribute("userId", resultMap.get(0).get("id"));
				httpSession.setAttribute("role", resultMap.get(0).get("role"));
				login.put("info", resultMap.get(0).get("userName"));
				login.put("role", resultMap.get(0).get("role"));
				insertLog(resultMap.get(0).get("id"),"登录");
				return JsonUtil.toJSON(login);
			} else {
				login.put("info","passwordError");
			}
		}
		return JsonUtil.toJSON(login);
	}

	/**
	 * 手机号查重
	 * @param mobile
	 * @return
	 */
	public String checkMobile(String mobile) {
		String returnCode = "mobileEmpty";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("mobile", mobile);
		List<Map<String, String>> userResult = usermapper.checkMobile(param);
		if (userResult != null && userResult.size() > 0) {
			returnCode = "mobileExist";
		} else {
			returnCode = "mobileEmpty";
		}
		System.out.println(returnCode);
		return returnCode;
	}

	/**
	 * 获取理论课程列表
	 * @return
	 */
	public List<Map<String, Object>> getCourseTheoryList() {
		return usermapper.getCourseTheoryList();
	}

	/**
	 * 获取视频课程列表
	 * @return
	 */
	public List<Map<String, Object>> getCourseVideoList() {
		return usermapper.getCourseVideoList();
	}

	/**
	 * 获取实践课程列表
	 * @return
	 */
	public List<Map<String, Object>> getCoursePracticeList() {
		return usermapper.getCoursePracticeList();
	}

	/**
	 * 获取理论课程详情
	 * @return
	 */
	public List<Map<String, Object>> getCourseTheoryInfo(String courseTheoryId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("courseTheoryId", courseTheoryId);
		return usermapper.getCourseTheoryInfo(param);
	}

	/**
	 * 获取实践课程详情
	 * @return
	 */
    public List<Map<String,Object>> getCoursePracticeInfo(String coursePracticeId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("coursePracticeId", coursePracticeId);
		return usermapper.getCoursePracticeInfo(param);
    }

	/**
	 * 获取视频课程详情
	 * @return
	 */
	public List<Map<String,Object>> getCourseVideoInfo(String courseVideoId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("courseVideoId", courseVideoId);
		return usermapper.getCourseVideoInfo(param);
	}

    public List<Map<String,Object>> getQuestionList(String courseId) {
        Map<String, Object> param = new HashMap<String, Object>();
        Tools.putMapParaEmpty(param,"courseId",courseId);
        return usermapper.getQuestionList(param);
    }

	public List<Map<String, Object>> getArticleList(String userID) {
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("userID", userID);
		return usermapper.getArticleList(param);
	}

    public List<Map<String,Object>> getLawyerList() {
		return usermapper.getLawyerList();
    }

	/**
	 * 获取咨询问题列表
	 * @param fromId 提问人
	 * @param toId 接收人
	 * @return
	 */
	public List<Map<String,Object>> getLawQuestionList(String fromId, String toId) {
		Map<String, Object> param = new HashMap<String, Object>();
		Tools.putMapParaEmpty(param,"toId",toId);
		Tools.putMapParaEmpty(param,"fromId",fromId);
		List<Map<String, Object>> result = usermapper.getLawQuestionList(param);

		if (null != result && result.size()>0) {
			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> selectParam = new HashMap<String, Object>();
				Tools.putMapParaEmpty(selectParam,"id",result.get(i).get("id").toString());
				List<Map<String, Object>>  replyItem = usermapper.getReplyLawQuestionList(selectParam);
				result.get(i).put("reply",replyItem);
			}
		}
		return result;
	}

	/**
	 * 获取咨询问题回复列表
	 * @param id 问题id
	 * @return
	 */
	public List<Map<String,Object>> getReplyLawQuestionList(String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		Tools.putMapParaEmpty(param,"id",id);
		return usermapper.getReplyLawQuestionList(param);
	}

	public List<Map<String,Object>> getNewsList() {
		return usermapper.getNewsList();
	}

	/**
	 * 插入系统日志
	 * @param userId	用户id
	 * @param Info 操作记录
	 */
	public void insertLog(String userId,String Info) {
		Map<String, Object> param = new HashMap<String, Object>();
		Tools.putMapParaEmpty(param,"userId","'" + userId + "'");
		Tools.putMapParaEmpty(param,"Info","'" + Info + "'");
		Tools.putMapParaEmpty(param,"addTime","now()");
		commonDatabaseService.insertData("sys_log",param);
	}

}
