package com.itheima.reggie;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReggieTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void test(){
        //开启分页 pageNum: 当前第几页  pageSize：每页大小
        PageHelper.startPage(1,10);

        //查询逻辑
        List<Employee> list = employeeMapper.employee();

        //封装分页结果
        PageInfo<Employee> pageInfo = new PageInfo<>(list);

        //分页相关结果
        long total = pageInfo.getTotal();       //总条数
        int pageNum = pageInfo.getPageNum();    //当前页
        int pages = pageInfo.getPages();        //总页数
        List<Employee> pageInfoList = pageInfo.getList();   //当前页数据

        System.out.println("总条数:"+total+" 当前页:"+pageNum+" 总页数"+pages);
        System.out.println("当前页数据....");
        for (Employee emp : pageInfoList) {
            System.out.println(emp);
        }
    }
}