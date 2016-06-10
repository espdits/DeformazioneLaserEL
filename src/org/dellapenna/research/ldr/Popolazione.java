/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dellapenna.research.ldr;

import Servizi.gestioneSalvataggio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author marco
 */
public class Popolazione {
    
    //Variabile che conta le mosse da effettuare per creare la prima popolazione
    final int contatoreMosse=5;
    
    //Variabile che imposta la dimensione della popolazione
    final int dimPopolazione=50;

    Random rq = new Random(); //per le prove
    Random rm = new Random(); //per le prove

    
    
    /**
     * Metodo che crea la prima popolazione, dove ogni individuo ha subito un
     * numero di mosse prestabilito, salvandola in un file esterno.
     */
    public void creaPopolazione( ArrayList<LineaDeformabile> primaPopolazione) throws IOException {
        // ArrayList contenente gli individui generati
        primaPopolazione = new ArrayList<>();
        //fino a che non creo 50 individui 

        for (int i = 0; i < dimPopolazione; i++) {
            //Creo nuova linea deformabile ( individuo )
            LineaDeformabile individuo = new LineaDeformabile();

            //applico 5 mosse randomiche ammissibili
            for (int j = 0; j < contatoreMosse; j++) {
                // posizione quadrato da deformare
                int quadrato_da_deformare;
                Mossa mossa_da_applicare;

                //sè è deformabile il quadrato selezionato deformo
                do { //Seleziono un quadrato da modificare
                    quadrato_da_deformare = rq.nextInt(individuo.lunghezza_linea);
                    // System.err.println("numero estratto"+quadrato_da_deformare);
                    // Seleziono un tipo di modifica da effettuare
                    switch (rm.nextInt(4)) {
                        default:

                        case 0:
                            mossa_da_applicare = Mossa.a_d;
                            break;
                        case 1:
                            mossa_da_applicare = Mossa.a_s;
                            break;
                        case 2:
                            mossa_da_applicare = Mossa.b_d;
                            break;
                        case 3:
                            mossa_da_applicare = Mossa.b_s;
                            break;
                    }

                    // System.err.println("isDeformabile"+individuo.isDeformabile(mossa_da_applicare,quadrato_da_deformare));
                } while (!(individuo.isDeformabile(mossa_da_applicare, quadrato_da_deformare)));
                // Deformo un quadrato della linea deformabile
                individuo.deforma(mossa_da_applicare, quadrato_da_deformare);

                //System.err.println("quadrato defotmato" + quadrato_da_deformare + "numero trasformazioni"+j + "DIM_POPOLAZIONE"+i);
            }
            // Aggiungo un individuo ogni 5 trasformazioni
            primaPopolazione.add(individuo);
            //jscjidsojfodihfahifchjaiohcaish
        }
        //Stampe di controllo della popolazione
        //    System.out.println("individuo nella poplazione " +it);
        //   });

        System.out.println("Popolazione" + primaPopolazione.size());

        //Salvo su file
        gestioneSalvataggio.salvaDATA(primaPopolazione, contatoreMosse);
    }

    
    public int valFitness(Linea linea, LineaDeformabile lineaDeformabile){
        return 0;
    }
    
}
