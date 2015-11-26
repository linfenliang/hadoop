package com.hadoop.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DbConnectionPoolManage {
	private static final Logger logger = LogManager.getLogger(DbConnectionPoolManage.class);
	
	private static DbConnectionPoolManage instance;
	private static final String jdbcDriver = "org.apache.hive.jdbc.HiveDriver";
	private static final String jdbcUrl = "jdbc:hive2://master:10000/default";
	private static final String username = "hadoop";
	private static final String password = "";
	private DbConnectionPoolManage() {
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			logger.error("jdbcDriver failed",e);
		}
	}

	public final static DbConnectionPoolManage getInstance() {
		if (instance == null) {
			synchronized (DbConnectionPoolManage.class) {
				if (instance == null) {
					instance = new DbConnectionPoolManage();
				}
			}
		}
		return instance;
	}

	public final Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(jdbcUrl, username, password);
		} catch (Exception e) {
			logger.error("数据库连接失败" , e);
		}

		return con;
	}

	public static final void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(stmt);
		closeConnection(conn);
	}

	private static final void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("close DataBase Connection failed , do not care");
			} finally {
				conn = null;
			}
		}
	}

	private static final void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("close DataBase Statement failed , do not care");
			} finally {
				stmt = null;
			}
		}
	}

	private static final void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("close DataBase ResultSet failed , do not care");
			} finally {
				rs = null;
			}
		}
	}
}