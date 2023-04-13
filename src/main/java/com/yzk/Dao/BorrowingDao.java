package com.yzk.Dao;

import com.yzk.Bean.Borrowing;

import java.sql.SQLException;
import java.util.List;

public interface BorrowingDao {
    /**
     * 查询所有借阅记录
     * @return
     * @throws SQLException
     */
    List<Borrowing> queryList() throws SQLException;

    /**
     * 添加借阅记录
     * @return
     * @throws SQLException
     */
    int insert(Borrowing borrowing) throws SQLException;
}
