import java.util.Collections;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * A `Klasse`-object represents a normal file in the file system.
 */
public class Klasse {

    private static final String ROOT_PATH = Pakke.ROOT_PATH;
    private static final int ROOT_PATH_LENGTH = Pakke.ROOT_PATH_LENGTH;

    private java.io.File file;

    public Klasse(String className){this(new java.io.File(ROOT_PATH+java.io.File.separator+className.replace('.',java.io.File.separatorChar)+".java"));}

    protected Klasse(java.io.File theFile){
        file = theFile;
        if (!isKlasse()) throw new RuntimeException("Invalid file given in initialization of Pakkesystem.Klasse");
    }

    // Offentlig grensesnitt

    /**
     * Gir alle klasser som blir importert i klassen
     */
    public Iterable<Klasse> imports(){
        ArrayList<Klasse> imports = new ArrayList<>();
        for(Klasse k: allImports()){
            if(!k.toString().endsWith("*"))
                imports.add(k);
        }
        return imports;
    }


    /**
     * Gir pakken som klassen hører til i
     */
    public Pakke pakke(){
        java.io.File current = this.file;

        while (!current.isDirectory())
           if ((current = current.getParentFile()) == null) return Pakke.ROOT;
        
        return new Pakke(current);
    }



    // PRIVATE METODER
    

    private Iterable<String> lines(){
        return new Iterable<String>(){
            public Iterator<String> iterator(){
                try {
                    Scanner sc = new Scanner(file);
                    sc.useDelimiter("\\R"); 
                    return sc;
                } catch (FileNotFoundException e) {
                    return Collections.emptyIterator();
                }
            }
        };
    }

    private Iterable<Klasse> allImports(){
        ArrayList<Klasse> imports = new ArrayList<>();
        for(String line: lines()){
            if(line.startsWith("import") && line.contains("java.")){
                String name = line.substring(line.indexOf('.')-4,line.lastIndexOf(';'));
                imports.add(new Klasse(name));
            }
        }
        return imports;
    }

    private boolean isKlasse(){return !this.file.isDirectory();}

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
    public int compareTo(Klasse other){return this.file.compareTo(other.file);}
    public boolean equals(Object other){return (other instanceof Klasse) && this.file.equals(((Klasse)other).file);}
    public int hashCode(){return this.file.hashCode();}
    
    public String toString(){
        String str = file.getAbsolutePath().toString().substring(ROOT_PATH_LENGTH+1).replace(java.io.File.separatorChar,'.');
        return str.substring(0,str.length()-5);
    }

    // TEST CODE
    public static void main(String[] args){Pakke.main(args);}
}
