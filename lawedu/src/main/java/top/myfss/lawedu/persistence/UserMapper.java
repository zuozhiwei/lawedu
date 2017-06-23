package top.myfss.lawedu.persistence;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface UserMapper {
	
    List<Map<String, String>> checkUserLogin(HashMap<String, String> param);

	List<Map<String, String>> checkMobile(HashMap<String, String> param);

	List<Map<String, Object>> getCourseTheoryList();

	List<Map<String, Object>> getCourseVideoList();

	List<Map<String, Object>> getCoursePracticeList();

	List<Map<String, Object>> getCourseTheoryInfo(Map<String, Object> param);

    List<Map<String,Object>> getCoursePracticeInfo(Map<String, Object> param);

    List<Map<String,Object>> getCourseVideoInfo(Map<String, Object> param);

    List<Map<String,Object>> getQuestionList(Map<String, Object> param);

	List<Map<String, Object>> getArticleList(Map<String, Object> param);

    List<Map<String,Object>> getLawyerList();

	List<Map<String,Object>> getLawQuestionList(Map<String, Object> param);

	List<Map<String,Object>> getNewsList();

    List<Map<String,Object>> getReplyLawQuestionList(Map<String, Object> param);
}
