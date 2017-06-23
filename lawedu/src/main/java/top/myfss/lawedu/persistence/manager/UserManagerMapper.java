package top.myfss.lawedu.persistence.manager;

import java.util.List;
import java.util.Map;


public interface UserManagerMapper {

    List<Map<String,Object>> checkUserLogin(Map<String, Object> param);

    List<Map<String,Object>> getUserList();

    List<Map<String,Object>> getCategoryList();

	List<Map<String, Object>> getExamList();

	List<Map<String, Object>> getAssignmentList();

	List<Map<String, Object>> getArticleList();

	List<Map<String, Object>> getUserCertificatesList();

    List<Map<String,Object>> getCourseCategoryList();

	List<Map<String, Object>> getMessageList();


	List<Map<String,Object>> getMenuList();

	List<Map<String,Object>> getLogList();
}
