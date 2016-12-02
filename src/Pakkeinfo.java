import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.HashSet;

public class Pakkeinfo {

    /**
     * Returnerer en oversikt over hvilke klasser som hører
     * til i ulike pakker, slik at
     * <p>
     * `pakke2klasseMultimap().get(pakke)`
     * <p>
     * gir oss alle klassene som hører til i pakken `pakke`
     */
    public Multimap<Pakke, Klasse> pakke2klasseMultimap() {
        Multimap<Pakke, Klasse> map = new Multimap<Pakke, Klasse>();
        Pakke pakke = Pakke.ROOT;

        for (Pakke p : pakke.undertre()) {

            for (Klasse f : p.klasser()) {
                map.put(p, f);
            }
        }

        return map;
    }

    /**
     * Returnerer en oversikt over import mellom klasser, slik at
     * <p>
     * `klasseImportMultimap().get(klasse)`
     * <p>
     * gir alle klasser som blir importert i klassen `klasse`
     */
    public Multimap<Klasse, Klasse> klasseImportsMultimap() {

        Multimap<Klasse, Klasse> map = new Multimap<Klasse, Klasse>();
        Pakke pakke = Pakke.ROOT;

        for (Pakke p : pakke.undertre()) {
            for (Klasse f : p.klasser()) {
                for (Klasse x : f.imports()) {

                    map.put(f, x);
                }
            }
        }

        return map;
    }

    /**
     * Returnerer en oversikt over import mellom klasser, slik at
     *
     *   `inverseKlasseImportMultimap().get(klasse)`
     *
     * gir alle klasser som importerer klassen `klasse`
     */
    public Multimap<Klasse, Klasse> inverseKlasseImportsMultimap() {
        Multimap<Klasse, Klasse> map = new Multimap<Klasse, Klasse>();
        Pakke pakke = Pakke.ROOT;

        for (Pakke p : pakke.undertre()) {
            for (Klasse f : p.klasser()) {
                for (Klasse x : f.imports()) {
                    map.put(x, f);
                }
            }
        }

        return map;

    }


    /**
     * Returnerer et Iterable-objekt som gir tilgang
     * til alle klassen i en pakke.
     */
    public Iterable<Klasse> klasser(Pakke pakke) {

        return this.pakke2klasseMultimap().get(pakke);
    }

    /**
     * Returnerer et Iterable-objekt som lar oss iterere
     * over pakker som blir direkte importert fra 'pakke'
     *
     */
    public Iterable<Pakke> directImports(Pakke pakke) {
        HashSet<Pakke> pakker = new HashSet<>();

        for(Klasse kl : klasser(pakke)){

            for(Klasse kl2 : kl.imports()){
                pakker.add(kl2.pakke());

            }
        }
        return  pakker;
    }

    /**
     * Returnerer et Iterable objekt som lar oss iterere
     * over pakker som importerer pakken 'pakke' direkte.
     * (OBS: HER HAR JEG RETTET DOKUMENTASJONEN (LARS))
     */
    public Iterable<Pakke> directImporters(Pakke pakke) {
        HashSet<Pakke> pakker = new HashSet<>();
        Multimap<Klasse, Klasse> invers = this.inverseKlasseImportsMultimap();
        for(Klasse kl : klasser(pakke)){
            if(invers.get(kl) != null){
                for(Klasse kl2: invers.get(kl)){
                    pakker.add(kl2.pakke());

                }
            }

        }

        return pakker;
    }

    // All direkte importering over
    // Nå indirekte importering


    /**
     * Returnerer et Iterable-objekt som lar oss iterere over
     * pakker som brukes (importeres) av 'pakke' (direkte eller indirekte).
     */
    public Iterable<Pakke> allImports(Pakke pakke) {
        return Collections.emptyList();
    }

    /**
     * Returnerer et Iterable objekt som lar oss iterere
     * over pakker som bruker (importerer) pakken 'pakke' (direkte eller indirekte).
     */
    public Iterable<Pakke> allImporters(Pakke pakke) {
        return Collections.emptyList();
    }

    // NYTT KAPITTEL: Graf-algoritmer.

    /**
     * returnerer '-1' hvis klasse b ikke bruker klasse b,
     * returnerer '0' hvis klasse b er identisk med klasse a
     * returnerer '1' hvis klasse b bruker klasse a direkte, og
     * ellers returnerer den det minimale antallet
     * imports som forbinder klassen a med klassen b, d.v.s. avstanden
     * mellom de to klassene i den rettede grafen av klasser
     * som importerer hverandre.
     */
    public int distance(Klasse a, Klasse b) {
        return -1;
    }

    /**
     * Tilsv. som i metoden over, men det er en del av oppgaven å
     * definere en menigsfull definisjon av 'avstanden' mellom
     * to pakker.
     *
     * DEFINISJONEN SKRIVES INN HER:
     *
     * Det forutsettes at løsningen forholder seg til
     * den definisjonen som er valgt.
     */
    public int distance(Pakke a, Pakke b) {
        return -1;
    }

    /**
     * returnerer 'true' hvis det finnes sykliske importeringer
     * i javas standardbibliotek, d.v.s. om det finnes
     *  pakker som gjensidig importerer hverandre
     * (direkte eller indirekte).
     *
     * Hvis dette ikke er tilfellet returneres 'false'
     *
     * jfr læreboka s. 576
     */
    public boolean hasCycle() {
        return true;
    }

    /**
     * Gir iterasjon over én kjede av sykliske
     * importeringer dersom det finnes en slik.
     *
     * Dersom det ikke forekommer syklisk
     * importering returners en tom liste.
     *
     * jfr læreboka s. 576
     */
    public Iterable<Pakke> getCycle() {
        return Collections.emptyList();
    }

    public static void main(String[] args) {
        Client.main(args);

    }
}
