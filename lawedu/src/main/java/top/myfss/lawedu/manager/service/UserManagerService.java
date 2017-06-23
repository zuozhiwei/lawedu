package top.myfss.lawedu.manager.service;

import top.myfss.lawedu.persistence.manager.UserManagerMapper;
import top.myfss.lawedu.util.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 管理端用户
 * Created by Allen on 2017/4/7.
 */
@Service
public class UserManagerService {

    @Autowired
    UserManagerMapper userManagerMapper;


    public List<Map<String,Object>> checkUserLogin(String mobile, String password) {
        Map<String,Object> param = new HashMap<String,Object>();
        Tools.putMapParaEmpty(param,"mobile",mobile);
        Tools.putMapParaEmpty(param,"password",password);
        Tools.putMapParaEmpty(param,"role","0");
        return userManagerMapper.checkUserLogin(param);
    }

    public List<Map<String,Object>> getUserList() {
        return userManagerMapper.getUserList();
    }

    public List<Map<String,Object>> getCategoryList() {
        return userManagerMapper.getCategoryList();
    }

	public List<Map<String, Object>> getExamList() {
		// TODO Auto-generated method stub
		return userManagerMapper.getExamList();
	}

	public List<Map<String, Object>> getAssignmentList() {
		// TODO Auto-generated method stub
		return userManagerMapper.getAssignmentList();
	}

	public List<Map<String, Object>> getArticleList() {
		// TODO Auto-generated method stub
		return userManagerMapper.getArticleList();
	}

	public List<Map<String, Object>> getUserCertificatesList() {
		// TODO Auto-generated method stub
		return userManagerMapper.getUserCertificatesList();
	}

    public List<Map<String,Object>> getCourseCategoryList() {
        return userManagerMapper.getCourseCategoryList();
    }

	public List<Map<String, Object>> getMessageList() {
		// TODO Auto-generated method stub
		return userManagerMapper.getMessageList();
	}

    public List<Map<String,Object>> getLogList() {
        return userManagerMapper.getLogList();
    }

    public List<Map<String,Object>> getMenuList() {
        return userManagerMapper.getMenuList();
    }
}
