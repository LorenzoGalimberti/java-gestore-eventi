package eventi;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgramEventiDB {
    // parametri di connessione al db
    private final static String DB_URL = "jdbc:mysql://localhost:3306/db_eventi";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "root";
    // query da esguire
    private final static String SQL_EVENTS = "select * from evento;";
    private final static String SQL_EVENT = "select titolo,data,posti_totali, posti_prenotati from evento where id = ? ;";


    public static void main(String[] args) {
        // istanzio lo scanner
        Scanner scanner = new Scanner(System.in);

        // db connection
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

            boolean flag = true;
            do {
                // menu interattivo
                System.out.println("MENU'");
                System.out.println(" Iserisci 1 per visualizzare gli eventi");
                System.out.println(" Iserisci 2 per prenotare un evento");
                System.out.println(" Iserisci 3 per disdire un evento");
                System.out.println(" Iserisci 4 per aggiungere un evento");
                System.out.println(" Iserisci 5 uscire dal menù ");

                System.out.print("inserisci il valore : ");
                String answer = scanner.nextLine();

                switch (answer) {
                    case "1":
                        // visualizza
                        System.out.println("");
                        System.out.println("LISTA EVENTI");
                        visualizzaEventi(connection, SQL_EVENTS);
                        System.out.println("");
                        break;
                    case "2":
                        System.out.print("Inserisci l' ID del' evento a cui vuoi partecipare : ");
                        int idEvent = scanner.nextInt();
                        scanner.nextLine();
                        prenotaEvento(scanner, connection, SQL_EVENT, idEvent);
                        break;
                    case "3":
                        System.out.print("Inserisci l' ID del' evento a cui vuoi disdire dei biglietti : ");
                        idEvent = scanner.nextInt();
                        scanner.nextLine();
                        disdiciEvento(scanner, connection, SQL_EVENT, idEvent);
                        break;
                    case "4":
                        aggiungiEvento(scanner,connection);
                        break;
                    case "5":
                        System.out.println("BYE BYE !!!!");
                        flag = false;
                        break;
                }
            } while (flag);


        } catch (SQLException exception) {
            System.out.println("An error occurred");
        }
        scanner.close();
    }

    // ALTRI METODI

    // Metodo per visualizzare gli eventi
    private static void visualizzaEventi(Connection connection, String query) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("titolo");
                LocalDate date = rs.getDate("data").toLocalDate();
                int totalPlaces = rs.getInt("posti_totali");
                int bookedPlaces = rs.getInt("posti_prenotati");

                System.out.println(id + ") " + title + " ---> Data " + date + " Posti totali: " + totalPlaces + " Posti prenotati: " + bookedPlaces);
            }
        }
    }

    // metodo per prenotare un evento
    private static void prenotaEvento(Scanner scan, Connection connection, String query, int choice) throws SQLException {

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, choice);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    int postiTotali = rs.getInt("posti_totali");
                    int postiPrenotati = rs.getInt("posti_prenotati");

                    // richiesta numero prenotazioni
                    System.out.print("Quanti posti vuoi prenotare ?  : ");
                    int postiDaPrenotare = scan.nextInt();
                    scan.nextLine();

                    // condizioni : maggiore di 0 e minore dei posti totali attuali
                    if (postiDaPrenotare > 0 && postiDaPrenotare <= postiTotali) {
                        // possiamo prenotare
                        // aumento i posti prenotati e riduco i posti totali
                        postiPrenotati += postiDaPrenotare;
                        postiTotali -= postiDaPrenotare;
                        // Aggiorna il numero di posti prenotati
                        String updateQuery = "update evento set posti_totali=? , posti_prenotati = ? where id = ?";
                        try (PreparedStatement updatePs = connection.prepareStatement(updateQuery)) {
                            updatePs.setInt(1, postiTotali);
                            updatePs.setInt(2, postiPrenotati);
                            updatePs.setInt(3, choice);
                            updatePs.executeUpdate();

                        }
                    } else {
                        System.out.println(" non ci sono abbastanza biglietti !! \n ne sono rimasti " + postiTotali);
                    }
                    // aggiorno delle variabili
                } else {
                    System.out.println("Evento non trovato.");
                }
            }
        } catch (SQLException exception) {
            System.out.println("Errore durante la prenotazione dell'evento.");
        }


    }

    // metodo per prenotare un evento
    private static void disdiciEvento(Scanner scan, Connection connection, String query, int choice) throws SQLException {

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, choice);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    int postiTotali = rs.getInt("posti_totali");
                    int postiPrenotati = rs.getInt("posti_prenotati");

                    // richiesta numero prenotazioni
                    System.out.print("Quanti posti vuoi disdire ?  : ");
                    int postiDaDisdire = scan.nextInt();
                    scan.nextLine();

                    // condizioni : maggiore di 0 e minore dei posti totali attuali
                    if (postiDaDisdire > 0 && postiDaDisdire <= postiPrenotati) {
                        // possiamo prenotare
                        // aumento i posti prenotati e riduco i posti totali
                        postiPrenotati -= postiDaDisdire;
                        postiTotali += postiDaDisdire;
                        // Aggiorna il numero di posti prenotati
                        String updateQuery = "update evento set posti_totali=? , posti_prenotati = ? where id = ?";
                        try (PreparedStatement updatePs = connection.prepareStatement(updateQuery)) {
                            updatePs.setInt(1, postiTotali);
                            updatePs.setInt(2, postiPrenotati);
                            updatePs.setInt(3, choice);
                            updatePs.executeUpdate();

                        }
                    } else {
                        System.out.println(" non puoi disdire un numero di posti maggiore alle attuali prenotazioni!!");
                        System.out.println("prenotazioni attuali : "+postiPrenotati);
                    }
                    // aggiorno delle variabili
                } else {
                    System.out.println("Evento non trovato.");
                }
            }
        } catch (SQLException exception) {
            System.out.println("Errore durante la prenotazione dell'evento.");
        }


    }

    // aggiungi evento

    public  static void aggiungiEvento(Scanner scan , Connection connection  ) throws  SQLException{


        try {
            System.out.println("Create new event");
            System.out.print("Title: ");
            String title = scan.nextLine();
            System.out.print("Date (yyyy-MM-dd): ");
            String dateString = scan.nextLine();
            LocalDate date = LocalDate.parse(dateString);

            System.out.print("Total seats: ");
            int totalSeats = Integer.parseInt(scan.nextLine());

            Evento event = new Evento(title, date, totalSeats); // solo se eseguo questa istruzione l'evento non è più null
            // INSERISCO NELLA TABELLA EVENTO L EVENTO STGESSO
            String query = "INSERT INTO evento (titolo, data, posti_totali, posti_prenotati) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, event.getTitle());
                ps.setDate(2, java.sql.Date.valueOf(date));
                ps.setInt(3, event.getTotalPlaces());
                ps.setInt(4, event.getBookedPlaces());

                int result = ps.executeUpdate();
                if (result == 1) {
                    System.out.println("Evento aggiunto con successo.");
                } else {
                    System.out.println("Errore durante l'aggiunta dell'evento.");
                }
            } catch (SQLException exception) {
                System.out.println("Errore durante l'aggiunta dell'evento.");
            }
        } catch (InvalidEventParametersException ex) {
            System.out.println("Unable to create event: " + ex.getMessage());
        } catch(DateTimeParseException de){
            System.out.println("Formato della data non valido ");
        } catch(NumberFormatException ne) {
            System.out.println("Numero non valido ");
        } catch(Exception exception){
            System.out.println("Generic error");
        }

    }
}