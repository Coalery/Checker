import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class E_SqliteDBManager {
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {e.printStackTrace(System.err);}
	}
	
	public static void createNewDBFile() {
		File f = new File("log.db");
		
		if(f.exists())
			return;
		try { f.createNewFile(); } catch (IOException e) { e.printStackTrace(System.err); }
		
		String url = "jdbc:sqlite:" + f.getAbsolutePath();
		String sql = "CREATE TABLE \"log\" (\n" + 
				" number	INTEGER,\n" + 
				" name TEXT,\n" + 
				" type TEXT,\n" + 
				" writeDate TEXT,\n" + 
				" writeTime TEXT,\n" +
				" teacher TEXT,\n" +
				" etc TEXT\n" + 
				");";
		
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			if(stmt != null)
				try { stmt.close(); } catch(SQLException e) { e.printStackTrace(System.err); }
			if(conn != null)
				try { conn.close(); } catch(SQLException e) { e.printStackTrace(System.err); }
		}
	}
	
	public static void insertData(int number, String name, String type, String etc) {
		createNewDBFile();
		Calendar c = Calendar.getInstance();
		String writeDate = String.format("%04d%02d%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
		String writeTime = String.format("%02d%02d%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String url = "jdbc:sqlite:" + new File("log.db").getAbsolutePath();
		String sql = "INSERT INTO log(number,name,type,writeDate,writeTime,teacher,etc) VALUES(?,?,?,?,?,?,?)";
		
		try {
			
			conn = DriverManager.getConnection(url);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, number);
			pstmt.setString(2, name);
			pstmt.setString(3, type);
			pstmt.setString(4, writeDate);
			pstmt.setString(5, writeTime);
			pstmt.setString(6, Util.getConfig("teacher"));
			pstmt.setString(7, etc);
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace(System.err);
		} finally {
			if(pstmt != null)
				try { pstmt.close(); } catch(SQLException e) { e.printStackTrace(System.err); }
			if(conn != null)
				try { conn.close(); } catch(SQLException e) { e.printStackTrace(System.err); }
		}
	}
	
}
