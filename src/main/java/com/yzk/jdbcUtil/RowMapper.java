package com.yzk.jdbcUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
    /**
     *
     * @param rs 传入结果集对象，在接口的实现类中就可以用结果集对象取出数据库中的数据并将结果封装到JavaBean中
     *
     * @return
     */
    public Object rowMap(ResultSet rs)throws SQLException;
}
