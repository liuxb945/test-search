package com.rmd.search.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcUtil {
	
    private static String driveClassName = null;  
    private static String url = null;  
    private static String username = null;  
    private static String password = null;  
    static{  
        try {  
          InputStream in = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");  
           Properties prop = new Properties();  
            prop.load(in);  
            driveClassName = prop.getProperty("driverClass");  
            url = prop.getProperty("jdbcUrl");  
            username = prop.getProperty("user");  
            password = prop.getProperty("password");  
            Class.forName(driveClassName);  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }  
    //
    


	public static Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return conn;
	}


	public static ResultSet query(String sql, Connection conn) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


	@SuppressWarnings("unchecked")
	public static List<HashMap> queryList(String sql, Connection conn,
			boolean closeConnection) {
		return rsToList(query(sql, conn), conn, closeConnection);
	}


	@SuppressWarnings("unchecked")
	public static HashMap find(String sql, Connection conn,
			boolean closeConnection) {
		return rsToALine(query(sql, conn), conn, closeConnection);
	}


	@SuppressWarnings("unchecked")
	public static HashMap find(ResultSet rs, Connection conn,
			boolean closeConnection) {
		return rsToALine(rs, conn, closeConnection);
	}


	public static int execSQL(String sql, Connection conn,
			boolean closeConnection) {
		int affectNum = 0;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			affectNum = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (closeConnection) {
				closeAll(null, ps, conn);
			}
		}
		return affectNum;
	}
	

	public static int execSQL(String sql, Connection conn,Object[] val
			) {
		int affectNum = 0;
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0;i<val.length;i++){
				ps.setString((i+1), val[i].toString());
			}
			
			affectNum = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return affectNum;
	}


	@SuppressWarnings("unchecked")
	public static List<HashMap> rsToList(ResultSet rs, Connection conn,
			boolean closeConnection) {
		ArrayList ret = new ArrayList();
		ArrayList rsColNames = new ArrayList();
		if (rs != null) {
			try {
				while (rs.next()) {
					rsColNames = getRsColumns(rs, conn);
					// System.out.println(rs.getInt("replyId"));
					Map line = new HashMap();
					for (int i = 0; i < rsColNames.size(); i++) {
						line.put(rsColNames.get(i).toString(),
								rs.getObject(rsColNames.get(i).toString()));
					}
					ret.add(line);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (closeConnection) {
					closeAll(rs, null, conn);
				}
			}
			// System.out.println(ret);
			return ret;
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public static HashMap rsToALine(ResultSet rs, Connection conn,
			boolean closeConnection) {
		ArrayList rsColNames = new ArrayList();
		if (rs != null) {
			try {
				while (rs.next()) {
					rsColNames = getRsColumns(rs, conn);
					HashMap line = new HashMap();
					for (int i = 0; i < rsColNames.size(); i++) {
						line.put(rsColNames.get(i).toString(),
								rs.getObject(rsColNames.get(i).toString()));
					}
					closeAll(rs, null, null);
					return line;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (closeConnection) {
					closeAll(rs, null, conn);
				}
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public static ArrayList getRsColumns(ResultSet rs, Connection conn) {
		ArrayList ret = new ArrayList();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int k;
			k = rsmd.getColumnCount();
			for (int i = 1; i <= k; i++) {
				ret.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}


	public static void closeAll(ResultSet rs, PreparedStatement ps,
			Connection conn) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (!conn.isClosed()) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}