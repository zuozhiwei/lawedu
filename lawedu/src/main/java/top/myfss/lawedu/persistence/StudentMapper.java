package top.myfss.lawedu.persistence;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface StudentMapper {

	List<Map<String, Object>> getAssignmentList(Map<String, Object> param);

    List<Map<String,Object>> getExamQuestionList(Map<String, Object> param);

    List<Map<String,Object>> getExamInfo(Map<String, Object> param);

    List<Map<String,Object>> getCertificateInfo(Map<String, Object> param);

    List<Map<String,Object>> getBidProjectList(Map<String, Object> param);

    List<Map<String,Object>> getStudentInfo(Map<String, Object> param);

    List<Map<String,Object>> checkExamQuestion(Map<String, Object> param);
}
