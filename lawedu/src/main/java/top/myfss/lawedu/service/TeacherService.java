package top.myfss.lawedu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.myfss.lawedu.persistence.CommonDatabaseMapper;
import top.myfss.lawedu.persistence.StudentMapper;
import top.myfss.lawedu.persistence.TeacherMapper;
import top.myfss.lawedu.util.SystemIDUtil;
import top.myfss.lawedu.util.Tools;

@Service
public class TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private CommonDatabaseMapper commonDatabaseMapper;

	SystemIDUtil systemIDUtil = new SystemIDUtil();

	/**
	 * 获取作业列表
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getAssignmentList(String userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("fromId", userId);
		return teacherMapper.getAssignmentList(param);
	}

	public List<Map<String,Object>> getBidProjectList(String userId, String status) {
		Map<String,Object> param = new HashMap<String,Object>();
		Tools.putMapParaEmpty(param,"status",status);
		Tools.putMapParaEmpty(param,"addUser",userId);
		return teacherMapper.getBidProjectList(param);
	}

	/**
	 * 获取教师详情
	 * @param userId
	 * @return
	 */
    public Map<String,Object> getTeacherInfo(String userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		Tools.putMapParaEmpty(param,"userId",userId);
		return teacherMapper.getTeacherInfo(param).get(0);
    }
}
