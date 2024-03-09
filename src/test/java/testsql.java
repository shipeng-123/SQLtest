import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class testsql {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "123456";
        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 建立连接
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                System.out.println("成功连接到数据库！");

                // 创建一个Statement对象来执行SQL语句
                try (Statement statement = connection.createStatement()) {
                    // 执行一个SQL查询语句
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM user_db"); // 替换your_table为你的表名
                    // 处理查询结果
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        System.out.println("ID: " + id + ", Name: " + name);
                    }

                    // 执行一个SQL更新语句
                    String sqlUpdate = "INSERT INTO user_db (id, name) VALUES (?,?)";
                    // 使用PreparedStatement防止SQL注入
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
                        preparedStatement.setInt(1, 1); // 这里替换为你想插入的id值
                        preparedStatement.setString(2, "John Doe"); // 这里替换为你想插入的name值
                        int affectedRows = preparedStatement.executeUpdate();
                        System.out.println("插入了 " + affectedRows + " 行。");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
}
