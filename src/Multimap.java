/**
 * Symboltabell som tillater at det assosieres flere verdier 
 * til hver enkelt søkenøkkel.
 *
 * Legg merke til at `get`-metoden skal returnere et `Iterable`-objekt
 * som er fritt for duplikater. 
 */
import java.util.Collections;

public class Multimap<Key,Value> {
    /**
     * Knytter verdien `v` sammen nøkkelen `k`.
     *
     * returnerer `false` dersom verdien `v` allerede
     * var tilknyttet `k`. Ellers returneres `false`.
     * ????skrivefeil?????
     *
     * Tabellen modifiseres ikke dersom `v` allerede
     * var tilknyttet `k`.
     */
    public boolean put(Key k, Value v){ 
        // TODO: Fullfør denne
        return false;
    }
    
    /**
     * Gir oss tilgang til alle verdiene som er knyttet
     * til nøkkelen k, i form av et objekt av typen
     * Iterable. Mengden som returneres skal være duplikatfri.
     */ 
    public Iterable<Value> get(Key k){
        // TODO: Fullfør denne
        return Collections.emptyList();
    }
    
    /**
     * Gir tilgang til alle nøklene i tabellen.
     */
    public Iterable<Key> keys(){
        // TODO: Fullfør denne
        return Collections.emptyList();
    }

    /**
     * Returnerer antall nøkler tabellen.
     */
    public int size(){
        return 0;
    }
}
