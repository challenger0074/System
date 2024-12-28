package cn.ganxq.dbcontrol.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

public class Result extends JSONObject {
    private static final transient  String RESULT_OK_CODE="0";
    private static final transient  String RESULT_OK_INFO="成功";
    private static final transient  String RESULT_ERROR_CODE="-1";
    private static final transient  String RESULT_ERROR_INFO="系统错误";
    private static final transient  String X_RESULT_CODE="X_RESULTCODE";
    private static final transient  String X_RESULT_INFO="X_RESULTINFO";
    private static final transient  String X_RESULT_NUM="X_RESULTNUM";


    public static Result SUCCESS(){
        return RESULT(RESULT_OK_CODE,RESULT_OK_INFO);
    }
    public static Result SUCCESS(String message){
        return RESULT(RESULT_OK_CODE,message);
    }
    public static Result SUCCESS_WITH_DATA(String key,Object data){
        Result result = SUCCESS();
        result.put(key,data);
        return result;
    }

    public static Result ERROR(){
        return RESULT(RESULT_ERROR_CODE,RESULT_ERROR_INFO);
    }

    public static Result ERROR(String message){
        if (message==null || "null".equalsIgnoreCase(message)){
            message = RESULT_ERROR_INFO;
        }
        return RESULT(RESULT_ERROR_CODE,message);
    }

    public static Result ERROR(JSONObject data){
        Result result =  ERROR();
        result.putAll(data);
        return result;
    }

    public static  Result RESULT(String code,String message){
        Result result = new Result();
        result.put(X_RESULT_CODE,code);
        result.put(X_RESULT_INFO,message);
        return result;
    }
    public static  Result RESULT(int code,String message){
        Result result = new Result();
        result.put(X_RESULT_CODE,String.valueOf(code));
        result.put(X_RESULT_INFO,message);
        return result;
    }

    public static Result RESULT(List<?> list){
        Result result = SUCCESS();
        result.put("list", list);
        return result;
    }

    public static Result RESULT(Map<String,Object> data){
        Result result = SUCCESS();
        result.putAll(data);
        return result;
    }

    public static boolean IS_ERROR(JSONObject result){
        return  !RESULT_OK_CODE.equals(result.get(X_RESULT_CODE));
    }



    public static boolean IS_ERROR(JSONArray result){
        if(CollectionUtils.isEmpty(result)){
            return true;
        }
        JSONObject info = result.getJSONObject(0);
        if(RESULT_OK_CODE.equals(info.getString(X_RESULT_CODE))){
            return false;
        }else{
            return true;
        }
    }

    public static boolean IS_ERROR(Object resultObj) throws Exception{
        String value = GET_X_RESULT_CODE(resultObj);
        return RESULT_OK_CODE.equals(value)?false:true;
    }

    public static boolean IS_ERROR(ResultBean resultBean) throws Exception{
        return (resultBean==null || resultBean.getCode()!=0)?true:false;
    }

    public static String GET_X_RESULT_CODE(Object resultObj){
        try {
            Object value=null;
            Object object;
            if (resultObj == null) {
                return "-1";
            }
            if (resultObj instanceof List && ((List<?>) resultObj).size() > 0) {
                object = ((List<?>) resultObj).get(0);
            } else {
                object = resultObj;
            }
            if (object == null) {
                return "-1";
            }
            if (object instanceof Map) {
                value = ((Map<?, ?>) object).get(X_RESULT_CODE);
            } else {
                PropertyDescriptor[] list = PropertyUtils.getPropertyDescriptors(object);
                if (list==null){
                    return "-1";
                }
                for (PropertyDescriptor prop : list){
                    if (X_RESULT_CODE.equalsIgnoreCase(prop.getName())){
                        value = PropertyUtils.getSimpleProperty(object,prop.getName());
                        break;
                    }
                }
            }
            if (value == null) {
                return "-1";
            }
            return value.toString();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String GET_X_RESULT_CODE(JSONObject result){
        if(result==null){
            return "-1";
        }
        String v = result.getString(X_RESULT_CODE);
        if (v==null){
            v = result.getString("x_RESULTCODE");
        }
        return (v!=null)?v:"-1";
    }
    public static int GET_X_RESULT_CODE_INT(JSONObject result){
        return Integer.valueOf(GET_X_RESULT_CODE(result));
    }

    public static String GET_X_RESULT_INFO(JSONObject result){
        if(result==null){
            return "";
        }
        String v= result.getString(X_RESULT_INFO);
        if (v!=null && StringUtils.isNotEmpty(v)) {
            if (v.matches(".*(csf|ORA|Exception).*")){
                v = RESULT_ERROR_INFO;
            }
            v = v.replaceAll("\\[规则库\\]", "").replaceAll("\\[原规则\\]", "");
            if (v != null && (v.endsWith("，") || v.endsWith(","))) {
                v = v.substring(0, v.length() - 1);
            }
        }
        return (v!=null)?v:"";
    }

    public static String GET_X_RESULT_CODE(JSONArray result){
        if(CollectionUtils.isEmpty(result)){
            return "-1";
        }
        return GET_X_RESULT_CODE(result.getJSONObject(0));
    }
    public static int GET_X_RESULT_CODE_INT(JSONArray result){
        return Integer.valueOf(GET_X_RESULT_CODE(result));
    }

    public static String GET_X_RESULT_INFO(JSONArray result){
        if(CollectionUtils.isEmpty(result)){
            return "";
        }
        return GET_X_RESULT_INFO( result.getJSONObject(0));
    }

    public static int GET_X_RESULT_NUM(JSONArray result){
        if(CollectionUtils.isEmpty(result)){
            return 0;
        }
        return result.getJSONObject(0).getIntValue(X_RESULT_NUM);
    }

    public Result(){
        super();
        put(X_RESULT_CODE, RESULT_OK_CODE);
        put(X_RESULT_INFO, RESULT_OK_INFO);
    }
    public Result(String code,String message){
        super();
        put(X_RESULT_CODE, code);
        put(X_RESULT_INFO, message);
    }
    public final boolean isOk(){
        return RESULT_OK_CODE.equals(get(X_RESULT_CODE));
    }
    public final boolean IS_OK(){
        return RESULT_OK_CODE.equals(get(X_RESULT_CODE));
    }
    public final boolean IS_ERROR(){
        return !RESULT_OK_CODE.equals(get(X_RESULT_CODE));
    }
}
