package PaisesDB2;

import java.util.Scanner;



public class Main {

	public static void main(String[] args) {
		
        SQLite dbManager = new SQLite();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {

            System.out.println("\n=== Menu Nagusia ===");
            System.out.println("1. Erakutsi erabiltze hari garen DatuBaseak");
            System.out.println("2. Sqlserver-eko erregistro bat aldatu");
            System.out.println("3. Sinkronizatu datuak  SQLite-tik  Access-era");
            System.out.println("5. Sinkronizatu datuak  SQLserver-etik a Access-era");
            System.out.println("6. Gehitu Erregistro bat Acces-era");
            System.out.println("7. Gehitu Aldaketak Sqlitera- Acces-etik");
            System.out.println("4. Itxi Konexioa");
            System.out.println("5. Irten");
            System.out.print("Aukeratu: ");

            int option = 0;
            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Aukera desegokia mesedez sartu zenbaki bat.");
                scanner.nextLine(); 
                continue; 
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
                	dbManager.addRecordToAccessWithScanner();
                	break;
                case 7:
                	dbManager.addNewRecordsToSQLite();
                	break;
                	

                case 8:
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