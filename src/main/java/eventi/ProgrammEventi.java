package eventi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgrammEventi {
    // ATTRIBUTI
    private String titolo;
    private List<Evento> eventi ;

    //  COSTRUTTORE

    public ProgrammEventi(String titolo) throws IllegalArgumentException{
        // controllo
        if (titolo.isEmpty() || titolo == null){
            throw new IllegalArgumentException("errore nell' inseriment del titolo");
        }
        //attributi
        this.titolo = titolo;
        this.eventi=new ArrayList<Evento>();
    }

    // GETTERS AND SETTERS

    public String getTitolo() {
        return titolo;
    }

    // METODI
    // 1.  aggiunge alla lista un Evento, passato come parametro
    public void aggiungiEvento(Evento event){
        eventi.add(event);
    }
    // 2.  restituisce una lista con tutti gli eventi presenti in una certa data
    public List<Evento> eventiDataSpecifica(LocalDate date){
        List<Evento> eventiInData = new ArrayList<>();
        for (Evento evento : eventi) {
            if (evento.getDate().equals(date)) {
                eventiInData.add(evento);
            }
        }
        return eventiInData;
    }
    // 3.  restituisce quanti eventi sono presenti nel programma
    public int totaleEventi(){
        return eventi.size();
    }
    // 4.  svuota la lista di eventi

    public void svuotaEventi(){
        eventi.clear();
    }
    // 5.  restituisce una stringa che mostra il titolo del programma e tutti gli eventi
    public void stampaInformazioniEventi(){
        System.out.println("Programma : "+ getTitolo());
        for (Evento e : eventi){
            System.out.println(e.toString());
        }
    }
}
