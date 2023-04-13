package com.yzk.Dao.impl;

import com.yzk.Bean.Borrowing;
import com.yzk.Dao.BorrowingDao;
import com.yzk.jdbcUtil.JdbcUtil;

import java.sql.SQLException;
import java.util.List;

public class BorrowingDaoImpl implements BorrowingDao {
    @Override
    public List<Borrowing> queryList() throws SQLException {
        String sql = "select * from borrowing";

        List<Borrowing> query = JdbcUtil.query(sql, null,Borrowing.class);
        return query;
    }

    @Override
    public int insert(Borrowing borrowing) throws SQLException {
        String sql = "insert into borrowing(startTime,endTime,status,username,bookid) values(?,?,?,?,?)";
        return JdbcUtil.update(sql,new Object[]{borrowing.getStartTime(),borrowing.getEndTime(),borrowing.getStatus(),borrowing.getUsername(),borrowing.getBookid()});
    }
}