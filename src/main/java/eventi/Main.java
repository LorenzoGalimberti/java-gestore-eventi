//MILESTONE 2
//1. Creare una classe Main di test, in cui si chiede all’utente di inserire un nuovo evento con tutti i parametri.
//2. Dopo che l’evento è stato istanziato, chiedere all’utente se e quante prenotazioni vuole fare e provare ad effettuarle, implementando opportuni controlli e gestendo eventuali eccezioni.
//3. Stampare a video il numero di posti prenotati e quelli disponibili
//4. Chiedere all’utente se e quanti posti vuole disdire
//5. Provare ad effettuare le disdette, implementando opportuni controlli e gestendo eventuali eccezioni
//6. Stampare a video il numero di posti prenotati e quelli disponibili

package eventi;

import java.util.Scanner;
import java.time.LocalDate;
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
// punto 1
        try {
            System.out.print("Inserisci il nome dell'evento: ");
            String title = scan.nextLine();

            System.out.print("Inserisci la data dell'evento (yyyy-MM-dd): ");
            String dataEventoString = scan.nextLine();
            LocalDate dataEvento = LocalDate.parse(dataEventoString);

            System.out.print("Inserisci il numero di posti totali dell'evento: ");
            int postiTotali = scan.nextInt();
            scan.nextLine();


            // punto 2
            Evento event = new Evento(title, dataEvento, postiTotali);
            // set posti prenotati per test
            System.out.print("Inserisci il numero di posti prenotati dell'evento: ");
            int postiTotaliPrenotati = scan.nextInt();
            scan.nextLine();
            event.prenota(postiTotaliPrenotati);
            // punto 3
            System.out.println("Posti prenotati ---> " + event.getBookedPlaces());
            System.out.println("Posti disponibili ---> " + event.getTotalPlaces());

            // punto 4
            System.out.print("Vuoi disdire dei posti ? (Y/n): ");
            String answer = scan.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                System.out.print("Bene! Quanti posti vuoi disdire? ");
                int postiDisdireInput = scan.nextInt();
                scan.nextLine();
                event.disdici(postiDisdireInput);
                System.out.println("Posti prenotati ---> " + event.getBookedPlaces());
                System.out.println("Posti disponibili ---> " + event.getTotalPlaces());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Errore durante la creazione dell'evento: " + e.getMessage());
        }




        // close scanner
        scan.close();
    }



}
