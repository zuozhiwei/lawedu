package top.myfss.lawedu.service;

import top.myfss.lawedu.persistence.CommonDatabaseMapper;
import top.myfss.lawedu.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 通用数据库操作
 * @author zuozhiwei
 *
 */
@Service
public class CommonDatabaseService {

    @Autowired
    CommonDatabaseMapper mapper;

    /**
     * 单个条件通用修改
     * @param table 表
     * @param con 条件
     * @param conValue
     * @param key
     * @param value
     * @return 影响条数
     */
    public int updateSingleData(String table,String con,String conValue,String key,String value) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("table",table);
        param.put("con",con);
        param.put("conValue",conValue);
        param.put("key",key);
        param.put("value",value);
        return mapper.updateSingleData(param);
    }

    /**
     * 获取单个条件的单个数值
     * @param table
     * @param key select后值
     * @param con 条件字段
     * @param conValue 条件
     * @return
     */
    public Object getSingleData(String table,String key,String con,String conValue) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("table",table);
        param.put("key",key);
        param.put("con",con);
        param.put("conValue",conValue);
        List<Map<String, Object>> resultList = mapper.getSingleData(param);
        if(resultList.size() != 0){
            if(resultList.get(0) != null){
                return resultList.get(0).get(key);
            }
        }
        return null;
    }

    /**
     * 单个条件通用删除
     * @param table 表
     * @param con 条件
     * @param conValue
     * @return 影响条数
     */
    public int deleteSingleData(String table,String con,String conValue) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("table",table);
        param.put("con",con);
        param.put("conValue",conValue);
        return mapper.deleteSingleData(param);
    }

    /**
     * 获取数据上下porder,id
     * @param table 表名
     * @param idfield id字段
     * @param id 当前主键
     * @param dec 方向up&down
     * @param porder 字段
     * @return
     */
    public Map<String, String> getPorder(String table,String idfield,String id,String dec,String porder) {
        Map<String, String> result = new HashMap<String, String>();
        Map<String, Object> param = new HashMap<String, Object>();
        String comp="<";
        //从大到小排列
        if("down".equals(dec)){//取下一条
            dec="desc";
        } else {//取上一条
            dec="asc";
            comp=">";
        }
        param.put("table",table);
        param.put("idfield",idfield);
        param.put("id",id);
        param.put("comp",comp);
        param.put("dec",dec);
        param.put("porder",porder);
        List<Map<String, Object>> resultList = mapper.getPorder(param);
        if(resultList.size() != 0){
            result.put("id", resultList.get(0).get(idfield).toString());
            result.put("porder", resultList.get(0).get(porder).toString());
        }
        return result;
    }

    /**
     * 通用字符串插入,数据带''
     * @param table 表名
     * @param data 数据
     */
    public int insertStringData(String table,Map<String, Object> data) {
        Hashtable param = new Hashtable();
        param.put("data", data);
        param.put("table", table);
        return mapper.insertStringData(param);
    }

    /**
     * 通用字符串插入,数据带''
     * @param table 表名
     * @param data 数据
     */
    public int insertStringData(String table,List<Map<String, Object>> data) {
        Hashtable param = new Hashtable();
        Map<String, Object> firstItem = data.get(0);
        param.put("data", data);
        param.put("firstItem", firstItem);
        param.put("table", table);
        return mapper.insertStringDatas(param);
    }

    /**
     * 通用插入
     * @param table 表名
     * @param data 数据
     * @return 影响条数
     */
    public int insertData(String table,Map<String, Object> data) {
        Hashtable param = new Hashtable();
        param.put("data", data);
        param.put("table", table);
        return mapper.insertData(param);
    }

    /**
     * 通用多条数据插入
     * @param table 表名
     * @param data 数据
     * @return 影响条数
     */
    public int insertData(String table,List<Map<String, Object>> data) {
        Hashtable param = new Hashtable();
        Map<String, Object> firstItem = data.get(0);
        param.put("data", data);
        param.put("firstItem", firstItem);
        param.put("table", table);
        return mapper.insertDatas(param);
    }

    /**
     * 通用字符更新,数据带''
     * @param table 表名
     * @param idfield 主键字段
     * @param id 主键
     * @param data 数据
     * @return 影响条数
     */
    public int updateStringData(String table,String idfield,String id,Map<String, Object> data) {
        Hashtable param = new Hashtable();
        param.put("idfield", idfield);
        param.put("id", id);
        param.put("data", data);
        param.put("table", table);
        return mapper.updateStringData(param);
    }

    /**
     * 通用更新
     * @param table 表明
     * @param idfield 主键字段
     * @param id 主键
     * @param data 数据
     * @return 影响条数
     */
    public int updateData(String table,String idfield,String id,Map<String, Object> data) {
        Hashtable param = new Hashtable();
        param.put("idfield", idfield);
        param.put("id", id);
        param.put("data", data);
        param.put("table", table);
        return mapper.updateData(param);
    }

}
