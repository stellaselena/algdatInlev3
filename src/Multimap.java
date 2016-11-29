/**
 * Symboltabell som tillater at det assosieres flere verdier 
 * til hver enkelt søkenøkkel.
 *
 * Legg merke til at `get`-metoden skal returnere et `Iterable`-objekt
 * som er fritt for duplikater. 
 */
import java.security.Key;
import java.security.KeyStore;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Multimap<Key,Value> {
    /**
     * Knytter verdien `v` sammen nøkkelen `k`.
     *
     *
     * returnerer `false` dersom verdien `v` allerede
     * var tilknyttet `k`. Ellers returneres `false`.
     * ????skrivefeil?????
     *
     * Tabellen modifiseres ikke dersom `v` allerede
     * var tilknyttet `k`.
     *
     *  Husk på to de to mulige tilfellene: (1) når det kommer en ny nøkkel (key).
     *  Da må man lage en ny beholder for verdier tilhørende denne nøkkelen,
     *  og deretter legge den nye
     *  verdien inn i denne nye beholderen. (2) Når nøkkelen finnes fra før.
     *  Da må man hente fram den beholderen som hører til nøkkelen, og legge
     *  verdien inn der.
     */

    private HashMap<Key,HashSet<Value>>map;

    public Multimap(){
        map = new HashMap<Key, HashSet<Value>>();

    }

    public String toString(){

        Iterator itr = map.keySet().iterator();
        while (itr.hasNext()){
            HashMap.Entry pair = (HashMap.Entry)itr.next();
            System.out.println(pair.getKey().toString());
            HashSet<Value> values = (HashSet<Value>)pair.getValue();
            Iterator itr2 = values.iterator();
            while (itr2.hasNext()){
                System.out.println(itr2.next().toString());
            }

        }


        return "LOL";
    }


    public boolean put(Key k, Value v){

        if(!map.containsKey(k)){
            HashSet<Value> hset =new HashSet<Value>();
            hset.add(v);
            map.put(k, hset);
            return true;
        }else{
             map.get(k).add(v);
            return false;

        }

    }
    
    /**
     * Gir oss tilgang til alle verdiene som er knyttet
     * til nøkkelen k, i form av et objekt av typen
     * Iterable. Mengden som returneres skal være duplikatfri.
     */ 
    public Iterable<Value> get(Key k){

        return  map.get(k);
    }
    
    /**
     * Gir tilgang til alle nøklene i tabellen.
     */
    public Iterable<Key> keys(){

        return map.keySet();
    }

    /**
     * Returnerer antall nøkler tabellen.
     */
    public int size(){
        return map.size();
    }
}
