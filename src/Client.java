import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.security.Key;

public class Client {

    private static final Pakke PAKKE_A = new Pakke("java.security");
    private static final Pakke PAKKE_B = new Pakke("java.awt");



    
    public static void main(String[] args){
        System.out.println("Innlevering 3: Test");

        Pakkeinfo pInfo = new Pakkeinfo();

        pInfo.pakke2klasseMultimap();
        pInfo.klasseImportsMultimap();
        linje();


        // OPPGAVE 1, Punkt b.
/*
        System.out.printf("Pakken %s importerer følgende pakker direkte:%n",PAKKE_A);
        for(Pakke p: pInfo.directImports(PAKKE_A))
           System.out.println(p); 

        linje();
  */
        System.out.printf("Pakken %s blir direkte  importert i følgende pakker:%n",PAKKE_A);
        for(Pakke p: pInfo.directImporters(PAKKE_A))
           System.out.println(p); 

        linje();

        


        // Oppgave 2, punkt a
       
        System.out.printf("Pakken %s importerer følgende pakker direkte og indirekte:%n",PAKKE_A);
        for(Pakke p: pInfo.allImports(PAKKE_A))
           System.out.println(p); 

        linje();
        
        System.out.printf("Pakken %s blir direkte eller indirekte importert i følgende pakker:%n",PAKKE_A);
        for(Pakke p: pInfo.allImporters(PAKKE_A))
           System.out.println(p); 

        linje();
        

        // LEGG INN FLERE TESTER HER!
        

        int dist = pInfo.distance(PAKKE_A,PAKKE_B);

        System.out.printf("Avstanden mellom disse klassene er lik %d%n",dist);
        
        // Oppgave 2, punkt b
        
        if (pInfo.hasCycle()){
            System.out.println("Det forekommer syklisk importering i javas standardbibliotek. Et eksempel på en slik importering er:");
            for(Pakke p: pInfo.getCycle())
                System.out.println(p);
        }

        linje();
    }





    public static void linje(){System.out.println("\n===================================================================\n");}



}

