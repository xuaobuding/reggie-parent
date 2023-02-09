package com.itheima.reggie.server;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.domain.Employee;
import java.util.List;


public interface EmployeeServer {
    //查询所有员工信息
    List<Employee> employee();
    //根据用户名查询员工
    Employee getByUsername(String username);

    Employee getByphone(String phone);
    //根据身份证号查询
    Employee getByIdNumber(String idNumber);
     //添加员工信息
    void add(Employee employee);
     //员工列表分页查询
    PageInfo<Employee> page(Integer page, Integer pageSize, String name);
}
