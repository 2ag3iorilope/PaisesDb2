package PaisesDB2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
	 public void editRecordWithUserChoiceSQLServer() {
		    if (connection == null) {
		        System.err.println("SQL Server konexioa ez da ireki. Mesedez, ireki konexioa lehenik.");
		        return;
		    }

		    Scanner scanner = new Scanner(System.in);  // Mover el scanner aquí

		    try {
		        // Mostrar los registros actuales
		        System.out.println("Erregistroak SQL Server datu-basean:");
		        String selectQuery = "SELECT * FROM estatuak";
		        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
		        ResultSet resultSet = selectStatement.executeQuery();

		        // Imprimir los registros disponibles
		        while (resultSet.next()) {
		            System.out.printf("Pais: %s, Capital: %s, Moneda: %s, Superficie: %d, Poblacion: %d, Bizi_Esperantza: %d, Kontinenteak_Izena: %s%n",
		                resultSet.getString("Pais"),
		                resultSet.getString("Capital"),
		                resultSet.getString("Moneda"),
		                resultSet.getInt("Superficie"),
		                resultSet.getInt("Poblacion"),
		                resultSet.getInt("Bizi_Esperantza"),
		                resultSet.getString("Kontinenteak_Izena")
		            );
		        }

		        // Preguntar al usuario qué registro quiere modificar
		        System.out.println("\nSartu aldatu nahi duzun herrialdearen izena:");
		        String pais = scanner.nextLine();

		        // Comprobar si el registro existe
		        String checkQuery = "SELECT COUNT(*) AS count FROM estatuak WHERE Pais = ?";
		        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
		        checkStatement.setString(1, pais);
		        ResultSet checkResult = checkStatement.executeQuery();

		        if (checkResult.next() && checkResult.getInt("count") == 0) {
		            System.out.println("Ez da aurkitu erregistro bat izen honekin: " + pais);
		            return;
		        }

		        // Ofrecer opciones al usuario para elegir qué cambiar
		        System.out.println("\nAukeratu aldatu nahi duzun eremua:");
		        System.out.println("1. Capital");
		        System.out.println("2. Moneda");
		        System.out.println("3. Superficie");
		        System.out.println("4. Poblacion");
		        System.out.println("5. Bizi_Esperantza");
		        System.out.println("6. Kontinenteak_Izena");
		        System.out.println("7. Amaitu aldaketak");

		        boolean seguirEditando = true;
		        while (seguirEditando) {
		            System.out.print("\nAukeratu eremuaren zenbakia (1-7): ");
		            String input = scanner.nextLine();  // Usar nextLine() para asegurarse de que lea correctamente
		            int opcion;

		            try {
		                opcion = Integer.parseInt(input);  // Intentar convertir a número
		            } catch (NumberFormatException e) {
		                System.out.println("Sartutako balioa ez da zenbakia. Mesedez, sartu zenbaki bat.");
		                continue;  // Volver al ciclo si no es un número válido
		            }

		            String updateQuery = "";
		            PreparedStatement updateStatement;

		            switch (opcion) {
		                case 1: // Capital
		                    System.out.print("Sartu kapital berria: ");
		                    String nuevaCapital = scanner.nextLine();
		                    updateQuery = "UPDATE estatuak SET Capital = ? WHERE Pais = ?";
		                    updateStatement = connection.prepareStatement(updateQuery);
		                    updateStatement.setString(1, nuevaCapital);
		                    updateStatement.setString(2, pais);
		                    updateStatement.executeUpdate();
		                    System.out.println("Capital eguneratu da.");
		                    break;

		                case 2: // Moneda
		                    System.out.print("Sartu moneda berria: ");
		                    String nuevaMoneda = scanner.nextLine();
		                    updateQuery = "UPDATE estatuak SET Moneda = ? WHERE Pais = ?";
		                    updateStatement = connection.prepareStatement(updateQuery);
		                    updateStatement.setString(1, nuevaMoneda);
		                    updateStatement.setString(2, pais);
		                    updateStatement.executeUpdate();
		                    System.out.println("Moneda eguneratu da.");
		                    break;

		                case 3: // Superficie
		                    System.out.print("Sartu gainazal berria: ");
		                    int nuevaSuperficie = Integer.parseInt(scanner.nextLine());
		                    updateQuery = "UPDATE estatuak SET Superficie = ? WHERE Pais = ?";
		                    updateStatement = connection.prepareStatement(updateQuery);
		                    updateStatement.setInt(1, nuevaSuperficie);
		                    updateStatement.setString(2, pais);
		                    updateStatement.executeUpdate();
		                    System.out.println("Superficie eguneratu da.");
		                    break;

		                case 4: // Poblacion
		                    System.out.print("Sartu populazio berria: ");
		                    int nuevaPoblacion = Integer.parseInt(scanner.nextLine());
		                    updateQuery = "UPDATE estatuak SET Poblacion = ? WHERE Pais = ?";
		                    updateStatement = connection.prepareStatement(updateQuery);
		                    updateStatement.setInt(1, nuevaPoblacion);
		                    updateStatement.setString(2, pais);
		                    updateStatement.executeUpdate();
		                    System.out.println("Poblacion eguneratu da.");
		                    break;

		                case 5: // Bizi_Esperantza
		                    System.out.print("Sartu bizi esperantza berria: ");
		                    int nuevaBiziEsperantza = Integer.parseInt(scanner.nextLine());
		                    updateQuery = "UPDATE estatuak SET Bizi_Esperantza = ? WHERE Pais = ?";
		                    updateStatement = connection.prepareStatement(updateQuery);
		                    updateStatement.setInt(1, nuevaBiziEsperantza);
		                    updateStatement.setString(2, pais);
		                    updateStatement.executeUpdate();
		                    System.out.println("Bizi esperantza eguneratu da.");
		                    break;

		                case 6: // Kontinenteak_Izena
		                    System.out.print("Sartu kontinente berria: ");
		                    String nuevoKontinenteakIzena = scanner.nextLine();
		                    updateQuery = "UPDATE estatuak SET Kontinenteak_Izena = ? WHERE Pais = ?";
		                    updateStatement = connection.prepareStatement(updateQuery);
		                    updateStatement.setString(1, nuevoKontinenteakIzena);
		                    updateStatement.setString(2, pais);
		                    updateStatement.executeUpdate();
		                    System.out.println("Kontinente eguneratu da.");
		                    break;

		                case 7: // Finalizar
		                    seguirEditando = false;
		                    System.out.println("Aldaketak amaitu dira.");
		                    break;

		                default:
		                    System.out.println("Aukeratu eremu baliozko bat (1-7).");
		            }
		        }

		    } catch (SQLException e) {
		        System.err.println("Errorea SQL Server datu-basearekin: " + e.getMessage());
		    } finally {
		        // scanner.close();  // No cerrar aquí para evitar problemas de lectura posterior
		    }
		}
	 public boolean isConnectionOpen() {
	        return connection != null;
	    }
}
