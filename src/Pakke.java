import java.util.Iterator;
import java.util.Stack;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;


/**
 * A `Pakke`-object represents a directory in the file system.
 */
public class Pakke implements Comparable<Pakke> {
    public static final String ROOT_PATH = "STDLIB";
    public static final Pakke ROOT = new Pakke("java");
    protected static final int ROOT_PATH_LENGTH = ROOT.file.getAbsolutePath().toString().length()-5;

    private java.io.File[] files = null;
    private java.io.File file;
    
    public Pakke(String packageName){
        this(new java.io.File(ROOT_PATH+java.io.File.separator+packageName.replace('.',java.io.File.separatorChar)));
    }

    protected Pakke(java.io.File theFile){
        file = theFile;
        if (!isPakke()) throw new RuntimeException("Invalid directory given in initialization of Pakkesystem.Pakke");
    }



    // Offentlig grensesnitt


    /**
     * Gir alle klasser i en pakke.
     */
    public Iterable<Klasse> klasser(){
        ensureFileArrayInit();
        return Arrays.stream(files).filter(f -> f.isFile()).map(f -> new Klasse(f))::iterator; // NOTE: Stream of files
    }

    /**
     * Gir alle direkte underpakker i en pakke.
     */
    public Iterable<Pakke> pakker(){
        ensureFileArrayInit();
        return Arrays.stream(files).filter(f -> f.isDirectory()).map(f -> new Pakke(f))::iterator;
    }

    /**
     *  Gir alle underpakker, samt underpakkenes underpakker o.s.v.
     */
    public Iterable<Pakke> undertre(){return () -> new Itr(this);}
    



    // Antageligvis ikke nyttige
    
    public boolean hasParent(){return this.file.getAbsoluteFile().getParent() != null;}

    public Pakke getParent(){
        if(!this.hasParent())
            return ROOT;
        return new Pakke(this.file.getAbsoluteFile().getParentFile());
    }

    
    // PRIVATE METODER
    
    private boolean isPakke(){return this.file.isDirectory();}
    
    private void ensureFileArrayInit(){
        files = file.listFiles();
        if (files == null) files = new java.io.File[0];
    }

    private static class Itr implements Iterator<Pakke> {
        private Stack<Pakke> todo = new Stack<>();
        Itr(Pakke start){pushAll(start);}
        public boolean hasNext(){return !todo.isEmpty();}
        public Pakke next(){
            Pakke out = todo.pop();
            pushAll(out);
            return out;
        }
        private void pushAll(Pakke pakke){
            for(Pakke d: pakke.pakker()) todo.push(d);
        }
    }
    
    // Metoder fra Object
    
    /*
     * Legg merke til at metodene
     * compareTo, equals og hashCode
     * avhenger av de tilsvarende metodene i
     * klassen java.io.File.
     *
     * Legg merke til at vi for sikkerhets skyld
     * legger ansvaret over på én felles underliggende
     * datatype. Jfr. java.lang.Double, som bruker 
     * én felles long-representasjon.
     */
    public int compareTo(Pakke other){return this.file.compareTo(other.file);}
    public boolean equals(Object other){return this == other || ((other instanceof Pakke) && this.file.equals(((Pakke)other).file));}
    public int hashCode(){return this.file.hashCode();}
    public String toString(){return file.getAbsolutePath().toString().substring(ROOT_PATH_LENGTH+1).replace(java.io.File.separatorChar,'.');}


    // Test code

    public static void main(String[] args){

        Pakke pakke = Pakke.ROOT;

        int importCount = 0;
        int starImportCount = 0;
        for(Pakke p: pakke.undertre()){
            TreeSet<Pakke> pakker = new TreeSet<>();
            for (Klasse f: p.klasser()){
                for(Klasse x: f.imports()){
                    pakker.add(x.pakke());
                }
            }
            System.out.printf("%n%nPakken %s importerer pakkene%n\t%s",p,pakker.stream().map((x) -> x.toString() ).collect(Collectors.joining("\n\t")));
        }
    }
}
