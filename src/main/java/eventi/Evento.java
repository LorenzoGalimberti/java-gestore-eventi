/*
* La consegna è di creare una classe Evento che abbia le seguenti proprietà:
● titolo
● data
● numero di posti in totale
● numero di posti prenotati
Quando si istanzia un nuovo evento questi attributi devono essere tutti valorizzati nel costruttore , tranne posti prenotati che va inizializzato a 0.
Inserire il controllo che la data non sia già passata e che il numero di posti totali sia positivo. In caso contrario sollevare opportune eccezioni.
Aggiungere metodi getter e setter in modo che:
● titolo sia in lettura e in scrittura
● data sia in lettura e scrittura
● numero di posti totale sia solo in lettura
● numero di posti prenotati sia solo in lettura
Vanno inoltre implementati dei metodi public che svolgono le seguenti funzioni:
1. prenota : aggiunge un certo numero di posti prenotati. Se l’evento è già passato o non ha posti disponibili deve sollevare un’eccezione.
2. disdici : riduce di un certo numero i posti prenotati. Se l’evento è già passato o non ci sono prenotazioni deve sollevare un’eccezione.
3. l’override del metodo toString() in modo che venga restituita una stringa contenente: data formattata - titolo
Aggiungete eventuali metodi (public e private) che vi aiutino a svolgere le funzioni richieste.*/

package eventi;

import java.time.LocalDate;

public class Evento {
    // ATTRIBUTI
    private String title;
    private LocalDate date;
    private  int totalPlaces;
    private int bookedPlaces;

    // COSTRUTTORE

    public Evento(String title, LocalDate date, int totalPlaces) {
        this.title = title;
        this.date = date;
        this.totalPlaces = totalPlaces;
        this.bookedPlaces=0;
    }

    // GETTER & SETTER

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public int getBookedPlaces() {
        return bookedPlaces;
    }

    // METODI

    // prenota
    public void prenota(int numeroPosti) throws IllegalArgumentException{
        //prenota : aggiunge un certo numero di posti prenotati. Se l’evento è già passato o non ha posti disponibili deve sollevare un’eccezione.
        // verifica data
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("L'evento è già passato.");
        }

        // verifica posti disponibili
        if (numeroPosti > totalPlaces) {
            throw new IllegalArgumentException("Non ci sono abbastanza posti disponibili.");
        }

        // aggiorna i posti prenotati
        bookedPlaces += numeroPosti;

        // riduci il numero di posti disponibili
        totalPlaces -= numeroPosti;
    }
    // disdici
    public void disdici(int numeroPosti) throws IllegalArgumentException {
        // verifica data evento
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("L'evento è già passato.");
        }

        // Vverifica se si puo disdire o meno
        if (numeroPosti > bookedPlaces) {
            throw new IllegalArgumentException("Non ci sono prenotazioni da disdire.");
        }

        // Riduci il numero di posti prenotati
        bookedPlaces -= numeroPosti;

        // Aggiorna il numero di posti disponibili
        totalPlaces += numeroPosti;
    }
    // tostring

    @Override
    public String toString() {
        return "Evento{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", totalPlaces=" + totalPlaces +
                ", bookedPlaces=" + bookedPlaces +
                '}';
    }
}
