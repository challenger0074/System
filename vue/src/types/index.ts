//定义一个接口,限制person对象的具体属性
import {getCurrentInstance, inject} from "vue/dist/vue";

export interface PersonInter{
    id:string,
    name:string,
    age:number
};
export interface employee{
    employeeId: string,
    employeeName: string,
    department: string,
    gender: string,
    age: number,  // 初始化为 0 或其他默认数字
    entryDate: string,
    contact: string,
    address: string,
    notes: string,
    status: string
}

export interface toast{

    open:Object

}
