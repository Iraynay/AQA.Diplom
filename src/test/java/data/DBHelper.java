package data;
import lombok.SneakyThrows;
import lombok.val;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class DBHelper {
    private static String url = System.getProperty("url");
    private static String user = System.getProperty("user");
    private static String password = System.getProperty("password");

    private DBHelper () {
    }
    @SneakyThrows
    public static String getStatusBuy() {
        var statusSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (
                var connection = getConnection(url, user, password);
                var statusStmt = connection.createStatement();
        ) {
            try (var rs = statusStmt.executeQuery(statusSql)) {
                if (rs.next()) {
                    var status = rs.getString(1);

                    return status;
                }
                return null;
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }


    }



    @SneakyThrows

    public static String getStatusCreditBuy() {
        var statusSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (
                var connection = getConnection(url, user, password);
                var statusStmt = connection.createStatement();
        ) {
            try (var rs = statusStmt.executeQuery(statusSql)) {
                if (rs.next()) {
                    var status = rs.getString(1);

                    return status;
                }
                return null;
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void clean() {
        var credit = "DELETE FROM credit_request_entity";
        var order = "DELETE FROM order_entity";
        var payment = "DELETE FROM payment_entity";
        try (
                var connection = getConnection(url, user, password);
                var prepareStatPay = connection.createStatement();
                var prepareStatCredit = connection.createStatement();
                var prepareStatOrder = connection.createStatement();

        ) {
            prepareStatPay.executeUpdate(payment);
            prepareStatCredit.executeUpdate(credit);
            prepareStatOrder.executeUpdate(order);

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
