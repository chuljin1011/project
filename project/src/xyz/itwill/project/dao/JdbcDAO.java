package xyz.itwill.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

//JDBC 기능을 사용한 모든 DAO 클래스가 상속받기 위한 클래스
// => Connection 객체가 저장된 DBCP 객체를 생성해 저장하고 DBCP 객체로부터 Connection 객체를 
//제공받아 반환하는 메소드와 매개변수로 JDBC 관련 객체를 전달받아 제거하는 메소드 작성
// => 객체 생성이 목적이 아닌 상속을 목적으로 작성된 클래스이므로 추상메소드로 선언하는 것을 권장
public abstract class JdbcDAO {
	//PoolDataSource 객체(DBCP 객체)를 저장하기 위한 필드
	private static PoolDataSource pds;
	
	static {
		//PoolDataSource 객체를 반환받아 필드에 저장
		pds=PoolDataSourceFactory.getPoolDataSource();
		
		try {
			pds.setConnectionFactoryClassName("oracle.jdbc.driver.OracleDriver");
			pds.setURL("jdbc:oracle:thin:@www.itwill.xyz:1521:xe");
			pds.setUser("team22");
			pds.setPassword("pass123");
			pds.setInitialPoolSize(10);
			pds.setMaxPoolSize(15);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection con=null;
		try {
			con=pds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public void close(Connection con) {
		try {
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection con, PreparedStatement pstmt) {
		try {
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
