import java.sql.*;
public class insertDemo {
    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demojdbc","root","root1234");
        PreparedStatement pstmt = conn.prepareStatement("insert into employees values ('Subrata Roy',1001,'Developer')");
        int res = pstmt.executeUpdate();
        if(res>0){
            System.out.println("Success");
        }
        else {
            System.out.println("Failure");
        }
        conn.close();
    }
}
