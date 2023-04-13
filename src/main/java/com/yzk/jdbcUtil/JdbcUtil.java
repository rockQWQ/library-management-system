package com.yzk.jdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库操作的工具类
 */
public class JdbcUtil {

    private  static final String url = "jdbc:mysql://47.98.247.62:3306/mall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=PRC";
    private  static final String username="root";
    private  static final  String password = "123456";

    static {//在静态代码块中加载驱动，因为驱动只需加载一次即可

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得数据库连接的方法
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {

        Connection connection =  DriverManager.getConnection(url,username,password);
        return  connection;
    }


    /**
     *
     * @param sql  传入sql语句
     * @param values  传入sql语句？号占位的值
     * @param cls   将查询结果封装到指定的JavaBean时，cls传入JavaBean的 Class实例,
     * @return
     * @throws SQLException
     *
     * //示例：
     *   sql = "select * from emp";
     *   values=null;
     *   EmpBean.class
     *   此方法完成将查询结果的每一行封装到EmpBean中，再将EmpBean放到方法返回值的List集合中
     *
     */
    public static List query(String sql,Object[] values,Class cls) throws SQLException {
        Connection connection = getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    Object val = values[i];//取出数组元素值
                    //判断数组元素值的类型是否为String,Integer,Double
                    if (val instanceof Integer) {
                        pstmt.setInt(i + 1, (Integer) val);
                    } else if (val instanceof String) {
                        pstmt.setString(i + 1, (String) val);
                    } else if (val instanceof Double) {
                        pstmt.setDouble(i + 1, (Double) val);
                    } else {
                        throw new RuntimeException("形参values数组中存储的元素类型应该为Integer或String或Double,请完善JDBCUtil类中的update方法");
                    }
                }
            }

            ResultSet rs = pstmt.executeQuery();//ResultSet 查询结果中的数据(表中数据)


            //ResultSetMetaData 查询结果的表的信息(通过此对象可以获得表的名称，查询结果的列数，查询结果的列名，列的数据类型，长度等一系列信息)
            ResultSetMetaData rsmd =  rs.getMetaData();


            int columnCount =   rsmd.getColumnCount();//获得查询结果的列的数量
            System.out.println("sql语句查询的结果列数为:"+columnCount);




            //创建List集合，存储查询结果
            List<Object> queyDataList = new ArrayList<>();

            Map<String,Object> rowDataMap = new HashMap<>();
            while (rs.next()) {//取出一行数据

                for(int i=1;i<=columnCount;i++){ //取出一行中每一列的值
                    String columnName = rsmd.getColumnLabel(i).toLowerCase();//获得查询结果的列名
                    // String  columnValue =  rs.getString(columnName);
                    String columnTypeName =  rsmd.getColumnTypeName(i);//获得列的数据类型


                    //根据列的数据类型调用getXX方法
                    Object columnValue = null;
                    if (columnTypeName.equals("VARCHAR")){
                        columnValue  =  rs.getString(columnName);
                    }else if (columnTypeName.equals("DECIMAL")){//根据列的数据类型调用getXX方法
                        //数值型小数位数大于0时，调用getDouble方法取数据
                        if (rsmd.getScale(i)>0) {
                            columnValue = rs.getDouble(columnName);
                        }else {
                            columnValue = rs.getInt(columnName);
                        }
                    }else {
                        columnValue = rs.getString(columnName);
                    }

                    rowDataMap.put(columnName,columnValue);//把列名为做map的key,列值做为map的value放到HashMap中


                    // System.out.println("列名为:"+columnName +"列值:"+columnValue );
                }


                //  System.out.println("rowDataMap存储的数据为:"+rowDataMap);
                //调用
                Object beanData =    ReflectUtil.setMapValueToBean(rowDataMap,cls);//此方法的第一个参数在哪里获得，第一个参数要求传入Map集合

                queyDataList.add(beanData);//把javaBean放到List集合中返回
            }
            return queyDataList;
        }finally {
            connection.close();
        }
    }

    /**
     *
     * @param sql  传入select语句
     * @param values  传入select语句?号占位符的值
     * @return
     */
    public static List query(String sql,Object[] values,RowMapper rowMapper) throws SQLException {
        Connection connection = getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    Object val = values[i];//取出数组元素值
                    //判断数组元素值的类型是否为String,Integer,Double
                    if (val instanceof Integer) {
                        pstmt.setInt(i + 1, (Integer) val);
                    } else if (val instanceof String) {
                        pstmt.setString(i + 1, (String) val);
                    } else if (val instanceof Double) {
                        pstmt.setDouble(i + 1, (Double) val);
                    } else {
                        throw new RuntimeException("形参values数组中存储的元素类型应该为Integer或String或Double,请完善JDBCUtil类中的update方法");
                    }
                }
            }

            ResultSet rs = pstmt.executeQuery();
            int i = 0;
            //创建List集合，存储查询结果
            List<Object> queyDataList = new ArrayList<>();
            while (rs.next()) {
                //问题：如何根据sql语句查询的表取出查询结果放到JavaBean中
                // System.out.println("循环取出第" + (i++) + "行的数据");
                Object rowData =   rowMapper.rowMap(rs);//每取出一行数据，就调用接口的rowMap方法
                //  System.out.println("取出来的数据为:"+rowData);

                queyDataList.add(rowData);//把取出来的结果放到List集合中
            }
            return queyDataList;
        }finally {
            connection.close();
        }



    }

    public static PageInfo query(String sql,Object[] values,Class cls,int pageNum,int pageSize) throws SQLException {
        String contsql = "select count(*) coun from("+sql+") t";
        Connection connection = getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(contsql);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    Object val = values[i];//取出数组元素值
                    //判断数组元素值的类型是否为String,Integer,Double
                    if (val instanceof Integer) {
                        pstmt.setInt(i + 1, (Integer) val);
                    } else if (val instanceof String) {
                        pstmt.setString(i + 1, (String) val);
                    } else if (val instanceof Double) {
                        pstmt.setDouble(i + 1, (Double) val);
                    }else if (val == null) {
                        pstmt.setObject(i + 1, val);
                    } else {
                        throw new RuntimeException("形参values数组中存储的元素类型应该为Integer或String或Double,请完善JDBCUtil类中的update方法");
                    }
                }
            }

            ResultSet rs = pstmt.executeQuery();
            int totalRecord = 0;
            while (rs.next()) {
                totalRecord = rs.getInt("coun");
            }
            sql = sql+" limit ?,?";

            System.out.println("sql语句"+sql);

            if(values==null){
                values = new Object[2];
            }else{
                Object[] newValues = new Object[values.length+2];
                for (int i = 0; i < values.length; i++) {
                    newValues[i] = values[i];
                }
                values = newValues;
            }
            values[values.length-1] = pageSize;
            values[values.length-2] = (pageNum-1)*pageSize;

            List queryDate = query(sql, values, cls);

            PageInfo pageInfo = new PageInfo(queryDate, totalRecord, pageNum, pageSize);
            return pageInfo;

            //根据形参

        }finally {
            connection.close();
        }


    }

    /**
     *
     * @param sql  传入insert,update,delete SQL语句 (不能传入select语句)
     * @param values 传入第一个参数SQL语句？号占位符的值列表(数组)
     *               此方法只能执行单条的insert,或update,或delete语句
     *               没用使用JDBC的事务，如需使用事务管理 ，请自行编写JDBC操作代码
     *
     * @return
     * @throws SQLException
     *
     * 如：传入 第一个参数 sql =  insert into dept(deptno,dname,loc) values(?,?,?)
    第二个参数values=   new Object[]{44,“研发部”,"望城"}
    此方法返回执行结果

    传入 第一个参数 sql =  update accounts set money = money-? where cardid=?
    第二个参数    values=   new Object[]{100.0,"62220219xxxxxx"}
    此方法返回执行结果
     */
    public static int update(String sql ,Object[] values) throws SQLException {
        //找出SQL语句中有几个?号占位值，正则表达式
        // String sql = "《xx》"
        Pattern pattern = Pattern.compile("[?]");//表示匹配一个?号字符
        Matcher m =  pattern.matcher(sql);
        int count = 0;
        while (m.find()) {   //找下一个匹配的子串,返回boolean类型
            m.group();//取出子串
            count++;
        }
        if(count!=values.length){
            throw new RuntimeException("sql语句?号占位符数量("+count+")  与value数组的中元素个数("+values.length+") 不匹配");
        }

        Connection connection = getConnection();
        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            //用循环遍历 values
            if (values!=null) {
                for (int i = 0; i < values.length; i++) {
                    Object val = values[i];//取出数组元素值

                    System.out.println("jdbc报错val类型\t"+val);

                    //判断数组元素值的类型是否为String,Integer,Double
                    if (val instanceof  Integer){
                        pstmt.setInt(i+1, (Integer)val);
                    }else if (val instanceof  String){
                        pstmt.setString(i+1, (String)val);
                    }else if (val instanceof  Double){
                        pstmt.setDouble(i+1, (Double) val);
                    }else{
                        throw  new RuntimeException("形参values数组中存储的元素类型应该为Integer或String或Double,请完善JDBCUtil类中的update方法");
                    }
                }
            }
            int res = pstmt.executeUpdate();
            return  res;

        }finally {
            connection.close();
        }


    }
}
