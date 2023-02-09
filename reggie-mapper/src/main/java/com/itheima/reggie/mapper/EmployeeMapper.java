package com.itheima.reggie.mapper;
import com.itheima.reggie.domain.Employee;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
//数据库交互层
@Mapper
public interface EmployeeMapper {

    //查询所有员工
    List<Employee> employee();

    //根据用户名查询
    Employee getByUsername(String username);

    //根据用户名手机号查询
    Employee getByphone(String phone);

    //根据用户名查询身份证号
    Employee getByIdNumber(String IdNumber);

    //添加员工信息
    void insert(Employee employee);

    //员工信息模糊查询
    List<Employee> page(String name);
}
