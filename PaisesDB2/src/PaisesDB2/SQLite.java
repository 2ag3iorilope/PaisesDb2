package PaisesDB2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLite {
	
	/** Konexioa. */
	private Connection connection;

	/** Datubasearen URL. */
	private String databaseUrl;
	
	/**
	 * Konexioa Ireki.
	 *
	 * @param databaseType , gure datubase mota
	 */
	public void openConnection(String databaseType) {
		try {
			switch (databaseType.toLowerCase()) {
			case "sqlite":
				databaseUrl = "jdbc:sqlite:./DatuBaseak/paises.db";
				connection = DriverManager.getConnection(databaseUrl);
				System.out.println("SQLite konexioa ondo egin da.");
				break;
			case "access":
				databaseUrl = "jdbc:ucanaccess://./DatuBaseak/paises.accdb";
				connection = DriverManager.getConnection(databaseUrl);
				System.out.println("Access konexioa ondo egin da.");
				break;
			case "sqlserver":
				databaseUrl = "jdbc:mysql://localhost:3306/paises?useSSL=false&serverTimezone=UTC";
				String username = "root";
				String password = "mysql";
				connection = DriverManager.getConnection(databaseUrl, username, password);
				System.out.println("SQL Server konexioa ondo egin da.");
				break;
			default:
				System.out.println("Aukeratu ezarritako datu-baseetako bat: SQLite, Access edo SQL Server.");
				break;
			}
		} catch (SQLException e) {
			System.err.println("Errorea konexioa ezartzean: " + e.getMessage());
		}
	}

	/**
	 * Konexioa itxi.
	 */
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Konexioa itxi da.");
			}
		} catch (SQLException e) {
			System.err.println("Errorea konexioa ixten: " + e.getMessage());
		}
	}
	public void showAvailableDatabases() {
        System.out.println("Erabiltzen diren datu-baseak:");
        System.out.println("1. SQLite");
        System.out.println("2. Access");
        System.out.println("3. SQL Server");
    }
	
	 public void syncNewDataToAccess() {
	        Connection sqliteConnection = null;
	        Connection accessConnection = null;

	        try {
	            // SQLite konexioa ireki
	            sqliteConnection = DriverManager.getConnection("jdbc:sqlite:./DatuBaseak/paises.db");

	            // Access konexioa ireki
	            accessConnection = DriverManager.getConnection("jdbc:ucanaccess://./DatuBaseak/paises.accdb");

	            // SQLite-tik datuak lortu
	            String selectQuery = "SELECT * FROM estatuak";
	            PreparedStatement sqliteStatement = sqliteConnection.prepareStatement(selectQuery);
	            ResultSet sqliteResultSet = sqliteStatement.executeQuery();

	            while (sqliteResultSet.next()) {
	                String pais = sqliteResultSet.getString("Pais");
	                String capital = sqliteResultSet.getString("Capital");
	                String moneda = sqliteResultSet.getString("Moneda");
	                int superficie = sqliteResultSet.getInt("Superficie");
	                int poblacion = sqliteResultSet.getInt("Poblacion");
	                int biziEsperantza = sqliteResultSet.getInt("Bizi_Esperantza");
	                String kontinenteakIzena = sqliteResultSet.getString("Kontinenteak_Izena");

	                // Check if the record already exists in Access
	                String checkQuery = "SELECT COUNT(*) AS count FROM estatuak WHERE Pais = ?";
	                PreparedStatement accessCheckStatement = accessConnection.prepareStatement(checkQuery);
	                accessCheckStatement.setString(1, pais);
	                ResultSet accessCheckResult = accessCheckStatement.executeQuery();

	                if (accessCheckResult.next() && accessCheckResult.getInt("count") == 0) {
	                    // Insert the new record into Access
	                    String insertQuery = "INSERT INTO estatuak (Pais, Capital, Moneda, Superficie, Poblacion, Bizi_Esperantza, Kontinenteak_Izena) VALUES (?, ?, ?, ?, ?, ?, ?)";
	                    PreparedStatement accessInsertStatement = accessConnection.prepareStatement(insertQuery);
	                    accessInsertStatement.setString(1, pais);
	                    accessInsertStatement.setString(2, capital);
	                    accessInsertStatement.setString(3, moneda);
	                    accessInsertStatement.setInt(4, superficie);
	                    accessInsertStatement.setInt(5, poblacion);
	                    accessInsertStatement.setInt(6, biziEsperantza);
	                    accessInsertStatement.setString(7, kontinenteakIzena);
	                    accessInsertStatement.executeUpdate();

	                    System.out.println("Datu berria gehituta Access-en: " + pais);
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Errorea datuak sinkronizatzen: " + e.getMessage());
	        } finally {
	            try {
	                if (sqliteConnection != null && !sqliteConnection.isClosed()) {
	                    sqliteConnection.close();
	                }
	                if (accessConnection != null && !accessConnection.isClosed()) {
	                    accessConnection.close();
	                }
	            } catch (SQLException e) {
	                System.err.println("Errorea konexioa ixten: " + e.getMessage());
	            }
	        }
	    }

}
