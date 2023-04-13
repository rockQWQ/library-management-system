package com.yzk.Service;

import com.yzk.Bean.Borrowing;

import java.sql.SQLException;
import java.util.List;

public interface BorrowingService {
    List<Borrowing> queryList() throws SQLException;

    int insert(Borrowing borrowing) throws SQLException;
}
