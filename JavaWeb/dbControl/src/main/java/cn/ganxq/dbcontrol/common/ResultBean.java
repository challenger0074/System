package cn.ganxq.dbcontrol.common;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {
    private int code=0;
    private String message="成功";
    private T data=null;

    public ResultBean(){
    }
    public ResultBean(int code,String message){
        this.code  =code;
        this.message = message;
    }

    public ResultBean(T data){
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void setError(int code,String message){
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
