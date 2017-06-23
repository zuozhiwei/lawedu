package top.myfss.lawedu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.myfss.lawedu.persistence.CommonDatabaseMapper;
import top.myfss.lawedu.persistence.StudentMapper;
import top.myfss.lawedu.util.SystemIDUtil;
import top.myfss.lawedu.util.Tools;

@Service
public class StudentService {

	@Autowired
	private StudentMapper studentMapper;
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
		return studentMapper.getAssignmentList(param);
	}

    /**
     * 获取考试题目列表
     * @param examId 考试id
     * @return
     */
    public List<Map<String,Object>> getExamQuestionList(String examId) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("examId", examId);
        return studentMapper.getExamQuestionList(param);
    }

    /**
     * 获取考试结果信息
     * @param userId 用户id
     * @return
     */
    public List<Map<String,Object>> getExamInfo(String userId) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userId", userId);
        return studentMapper.getExamInfo(param);
    }

    /**
     * 获取证书信息
     * @param userId 用户id
     * @return
     */
    public List<Map<String,Object>> getCertificateInfo(String userId) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userId", userId);
        param.put("score", "60");
        return studentMapper.getCertificateInfo(param);
    }

    public List<Map<String,Object>> getBidProjectList(String userId, String status) {
        Map<String,Object> param = new HashMap<String,Object>();
        Tools.putMapParaEmpty(param,"status",status);
        Tools.putMapParaEmpty(param,"userId",userId);
        return studentMapper.getBidProjectList(param);
    }

    public Map<String,Object> getStudentInfo(String userId) {
        Map<String,Object> param = new HashMap<String,Object>();
        Tools.putMapParaEmpty(param,"userId",userId);
        return studentMapper.getStudentInfo(param).get(0);
    }

    /**
     * 判断题目正误
     * @param questionID
     * @param answer
     * @return
     */
    public String checkExamQuestion(String questionID, String answer) {
        Map<String,Object> param = new HashMap<String,Object>();
        Tools.putMapParaEmpty(param,"questionID",questionID);
        Tools.putMapParaEmpty(param,"answer",answer);
        List<Map<String,Object>> result = studentMapper.checkExamQuestion(param);
        if (null == result || result.size() <= 0) {
            return "error";
        }
        return "success";
    }
}
