package data;
import lombok.val;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class DBHelper {
    private static String url = System.getProperty("url");
    private static String user = System.getProperty("user");
    private static String password = System.getProperty("password");

    private DBHelper () {
    }

    public static String getStatusBuy() {
        val statusSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        try (
                val connection = getConnection(url, user, password);
                val statusStmt = connection.createStatement();
        ) {
            try (val rs = statusStmt.executeQuery(statusSql)) {
                if (rs.next()) {
                    val status = rs.getString(1);

                    return status;
                }
                return null;
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }


    }

    public static String getStatusCreditBuy() {
        val statusSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        try (
                val connection = getConnection(url, user, password);
                val statusStmt = connection.createStatement();
        ) {
            try (val rs = statusStmt.executeQuery(statusSql)) {
                if (rs.next()) {
                    val status = rs.getString(1);

                    return status;
                }
                return null;
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void clean() {
        val credit = "DELETE FROM credit_request_entity";
        val order = "DELETE FROM order_entity";
        val payment = "DELETE FROM payment_entity";
        try (
                val connection = getConnection(url, user, password);
                val prepareStatPay = connection.createStatement();
                val prepareStatCredit = connection.createStatement();
                val prepareStatOrder = connection.createStatement();

        ) {
            prepareStatPay.executeUpdate(payment);
            prepareStatCredit.executeUpdate(credit);
            prepareStatOrder.executeUpdate(order);

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
