package eventi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Concerto extends Evento{
    //Attributi
    private LocalTime ora;
    private BigDecimal prezzo;
    // COSTRUTTORE

    public Concerto(String title, LocalDate date, int totalPlaces, LocalTime ora, BigDecimal prezzo) throws IllegalArgumentException {
        super(title, date, totalPlaces);
        this.ora = ora;
        this.prezzo = prezzo;
    }

    // GETTER AND SETTER

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    // METODI
    // metodo formatta data
    @Override
    public String getDataFormattata() {
        // Aggiunge l'ora formattata alla data
        return super.getDataFormattata() + " " + formatOra();
    }


    public String formatOra() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return ora.format(formatter);
    }

    // metodo formattare il prezzo "##,##€"
    public String formatPrezzo() {
        return String.format("%,.2f€", prezzo);
    }
    // tostring

    @Override
    public String toString() {
        return "Concerto{"+"titolo"+ super.getTitle()+
                "ora=" + getDataFormattata() +
                ", prezzo=" + formatPrezzo() +
                '}';
    }
}
