package top.myfss.lawedu.util;

import java.util.Random;


/**
 * id工具
 * @author zuozhiwei
 *
 */
public class SystemIDUtil {

	/**
	 * 获取咨询问题ID
	 * lq-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getLawQuestionID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "lq"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 获取用户考试成绩ID
	 * ue-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getUserExamID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "ue"+"-"+time+getFixLenthString(4);
		return id;
	}


	/**
	 * 获取考试题目ID
	 * q-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getExamQuestionID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "q"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 获取考试ID
	 * exam-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getExamID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "exam"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	
	/**
	 * 获取考试成绩ID
	 * er-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getExamResultID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "er"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 获取项目成员ID
	 * pu-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getProjectUserID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "pu"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 获取用户ID
	 * u-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getUserID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "u"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 获取作业ID
	 * a-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getAssignmentID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "a"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 获取视频课程ID
	 * cv-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getCourseVideoID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "cv"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 获取理论课程ID
	 * ct-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getCourseTheoryID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "ct"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 获取实践课程ID
	 * cp-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String coursePracticeID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "cp"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 获取提问ID
	 * q-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getQuestionID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "q"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 获取用户ID
	 * p-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getProjectID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "p"+"-"+time+getFixLenthString(4);
		return id;
	}

	/**
	 * 管理员ID
	 * admin-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getAdminID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "admin"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 成果ID
	 * article-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getArticleID(){
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "article"+"-"+time+getFixLenthString(4);
		return id;
	}
	

	/**
	 * 短信验证码生成器
	 * @return
	 * 4位
	 */
	public String getSMSCode(){
		return getFixLenthString(4);
	}
	
	/**
	 * 任意长度伪随机数生成器
	 * @param strLength
	 * @return
	 */
	public static String getFixLenthString(int strLength) {
	    Random rm = new Random();  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	    String fixLenthString = String.valueOf(pross);  
	    return fixLenthString.substring(1, strLength + 1);  
	}
	
}
