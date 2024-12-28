package cn.ganxq.dbcontrol.model;

import lombok.Data;

@Data
public class QueryInfo {
    private String query;
    private int pageNum;//当前页
    private int pageSize;//每页显示条数
    public QueryInfo(){}
    public QueryInfo(String query, int pageNum, int pageSize) {
        this.query = query;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    public String toString(){
        return "QueryInfo{"+"query='"+query+'\''+",pageNum="+pageNum+",pageSize="+pageSize+'}';
    }
}
