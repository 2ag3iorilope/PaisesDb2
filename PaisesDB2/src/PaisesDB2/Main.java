package PaisesDB2;

import java.util.Scanner;



public class Main {

	public static void main(String[] args) {
        SQLite dbManager = new SQLite();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Mostrar bases de datos disponibles");
            System.out.println("2. Sqlserver-eko erregistro bat aldatu");
            System.out.println("3. Sincronizar datos de SQLite a Access");
            System.out.println("5. Sincronizar datos de sqlserver a Access");
            System.out.println("4. Cerrar conexión");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            int option = 0;
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Limpiar el salto de línea pendiente después de nextInt()
            } catch (Exception e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer para evitar el bloqueo del Scanner
                continue; // Volver al menú sin proceder
            }

            switch (option) {
                case 1:
                    dbManager.showAvailableDatabases();
                    break;

                case 2:
                    String databaseType = "sqlserver";
                    if (!dbManager.isConnectionOpen()) {
                        dbManager.openConnection(databaseType);
                    }
                    dbManager.editRecordWithUserChoiceSQLServer();
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
                	dbManager.syncUpdatedDataToAccess();
                	break;

                case 6:
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