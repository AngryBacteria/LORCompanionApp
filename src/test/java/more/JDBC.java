package more;

import javafx.scene.layout.CornerRadii;
import model.card.Card;
import model.card.CardService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JDBC {


    public static final String DBMS_URL = "localhost";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    public static final String DB_NAME = "postgres";
    private static final String USER_NAME ="postgres";
    private static final String USER_PASS = "aAkZLaxPK4kt93";
    private static final CardService cardService = new CardService(true);

    public static void main(String[] args) {

        try {
            Class.forName(DB_DRIVER);
            Connection connection = DriverManager.getConnection(getJdbcUrl(), USER_NAME, USER_PASS);
            //normalStatement(connection);
            System.out.println("---------------------------------------");
            //preparedStatement(connection);
            System.out.println("---------------------------------------");
            insertCard(connection);
            System.out.println("---------------------------------------");
            //deleteEverything(connection, "card");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Something went wrong when trying to connect to the db");
            e.printStackTrace();
        }


    }

    private static void normalStatement(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from card");

        while (resultSet.next()){
            System.out.println(resultSet.getString(3));
        }
    }

    private static void preparedStatement(Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("select * from card where cost = ?");
        preparedStatement.setInt(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.println(resultSet.getString(3));
        }
    }

    private static void insertCard(Connection connection) throws SQLException {

        List<Card> cardArrayList = cardService.getCardArray().stream().filter(Card::isChampion).collect(Collectors.toList());
        PreparedStatement preparedStatement = connection.prepareStatement("Insert Into card values (default, ?, ?, ?, ?, ?, ?)");
        for (Card card : cardArrayList){

            preparedStatement.setString(1, card.getCardCode());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setInt(3, card.getAttack());
            preparedStatement.setInt(4, card.getCost());
            preparedStatement.setInt(5, card.getHealth());
            preparedStatement.setString(6, "Champion");
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
    }

    private static void deleteEverything(Connection connection, String name) throws SQLException {

        Statement dropConstraint = connection.createStatement();
        dropConstraint.executeUpdate("DELETE FROM card");

    }

    private static void dropConstraint(Connection connection, String tableName, String constraintName) throws SQLException {

        Statement dropConstraint = connection.createStatement();
        dropConstraint.executeUpdate("ALTER TABLE " + tableName + " DROP CONSTRAINT " + constraintName);

    }


    private static String getJdbcUrl() {
        return "jdbc:postgresql://" + DBMS_URL + "/" + DB_NAME;
    }

}
