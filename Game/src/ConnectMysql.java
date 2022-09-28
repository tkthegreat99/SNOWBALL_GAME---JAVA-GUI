import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectMysql {
		Connection conn;
		java.sql.Statement stmt;
		java.sql.ResultSet rs;
	void connect(){
		try
		{
			this.conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/nunu_rank?serverTimezone=UTC","root","mijung1208!");
			this.stmt=this.conn.createStatement();
			System.out.println("µ¥ÀÌÅÍº£ÀÌ½º ¿¬°á ¼º°ø");
	
		}
		catch(Exception e)
		{
			System.out.println("connection error:+"+e);
		}
	}
	void update(String dbCommand) {
		try {
			this.stmt.executeUpdate(dbCommand);
		}
		catch(Exception e) {
			System.out.println("update error:"+e);
		}
	}
	void select(String dbSelect) {
		try {
			this.rs=this.stmt.executeQuery(dbSelect);
		}
		catch(Exception e) {
			System.out.println("select error:"+e);
		}
	}
	void print(String dbSelect) {
		
		try {
			this.rs=this.stmt.executeQuery(dbSelect);
		
			while(rs.next()){
				System.out.println(rs.getString("ID"));
			}
		}
	
		catch(Exception e) {
			System.out.println("getString error:"+e);
		}
	}
	void close() {
		try {
			conn.close();
		}
		catch(Exception e) {
			System.out.println("close error:"+e);
		}
	}
}