package com.hadoop.hive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 *
 */
public class App {
	private static final Logger logger = LogManager.getLogger(App.class);
	public static void main(String[] args){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DbConnectionPoolManage.getInstance().getConnection();
			pstmt = conn.prepareStatement("select a.* from article a");
			rs = pstmt.executeQuery();
			logger.debug("-----查询记录------");
			while(rs.next()){
				logger.debug("-----------");
				logger.debug(rs.getInt(1));
				logger.debug(rs.getInt(2));
				logger.debug(rs.getString(3));
				logger.debug(rs.getString(4));
			}
		} catch (Exception | NoClassDefFoundError e) {
			logger.error("error", e);
		}finally{
			DbConnectionPoolManage.closeConnection(conn, pstmt, rs);
		}
		
	}
}
