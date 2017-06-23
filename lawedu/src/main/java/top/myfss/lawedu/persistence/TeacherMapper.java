package top.myfss.lawedu.persistence;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface TeacherMapper {


    List<Map<String,Object>> getAssignmentList(Map<String, Object> param);

    List<Map<String,Object>> getBidProjectList(Map<String, Object> param);

    List<Map<String,Object>> getTeacherInfo(Map<String, Object> param);
}
