package PaisesDB2;

import java.util.Scanner;



public class Main {

	 public static void main(String[] args) {
	        SQLite dbManager = new SQLite();
	        Scanner scanner = new Scanner(System.in);
	        boolean exit = false;

	        while (!exit) {
	            // Mostrar menú
	            System.out.println("\n=== MENÚ PRINCIPAL ===");
	            System.out.println("1. Mostrar bases de datos disponibles");
	            System.out.println("2. Conectar a una base de datos");
	            System.out.println("3. Sincronizar datos de SQLite a Access");
	            System.out.println("4. Cerrar conexión");
	            System.out.println("5. Salir");
	            System.out.print("Selecciona una opción: ");

	            // Leer opción del usuario
	            int option = scanner.nextInt();
	            scanner.nextLine(); // Limpiar el buffer del scanner

	            switch (option) {
	                case 1:
	                    dbManager.showAvailableDatabases();
	                    break;

	                case 2:
	                    System.out.print("Introduce el tipo de base de datos (SQLite, Access o SQL Server): ");
	                    String databaseType = scanner.nextLine();
	                    dbManager.openConnection(databaseType);
	                    break;

	                case 3:
	                    System.out.println("Sincronizando datos de SQLite a Access...");
	                    dbManager.syncNewDataToAccess();
	                    break;

	                case 4:
	                    System.out.println("Cerrando conexión...");
	                    dbManager.closeConnection();
	                    break;

	                case 5:
	                    System.out.println("Saliendo del programa. ¡Hasta pronto!");
	                    exit = true;
	                    break;

	                default:
	                    System.out.println("Opción no válida. Por favor, selecciona una opción del menú.");
	            }
	        }

	        scanner.close();
	    }
	}