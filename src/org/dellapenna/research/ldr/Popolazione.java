/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dellapenna.research.ldr;

import Servizi.gestioneSalvataggio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author marco
 */
public class Popolazione {

    //Variabile che conta le mosse da effettuare per creare la prima popolazione
    final int contatoreMosse = 5;

    //Variabile che imposta la dimensione della popolazione
    final int dimPopolazione = 50;

    Random rq = new Random(); //per le prove
    Random rm = new Random(); //per le prove

    /**
     * Metodo che crea la prima popolazione, dove ogni individuo ha subito un
     * numero di mosse prestabilito, salvandola in un file esterno.
     */
    public void creaPopolazione(ArrayList<LineaDeformabile> primaPopolazione) throws IOException {
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

        }
        //Stampe di controllo della popolazione
        //    System.out.println("individuo nella poplazione " +it);
        //   });

        System.out.println("Popolazione" + primaPopolazione.size());

        //Salvo su file
        gestioneSalvataggio.salvaDATA(primaPopolazione, contatoreMosse);
    }

    /**
     * Metodo che valuta la fitness di un individuo rispetto la linea data
     *
     * @param linea foram che si vuole ottenere
     * @param lineaDeformabile individuo in esame
     * @return val_fitness valore della funzione di fitness
     */
    public int valFitness(Linea linea, LineaDeformabile lineaDeformabile) {

        int val_fitness = 0;
        //iteratore della linea.
        Iterator it_linea = linea.getQuadratiDeformati().entrySet().iterator();
        //iteratore della linea deformabile
        Iterator it_lineaDef = lineaDeformabile.getQuadratiDeformati().entrySet().iterator();

        //per ogni quadrato della linea deformabile
        // Verifica con il metodo hasNext() che nella hashmap
        // ci siano altri elementi su cui ciclare
        while (it_lineaDef.hasNext()) {

            Map.Entry entry_lineaDef = (Map.Entry) it_lineaDef.next();
            //Quadrato LINEA deformabile
            Quadrato quadrato_lineaDef;
            //quadrato in esame della Linea Deformabile
            quadrato_lineaDef = (Quadrato) entry_lineaDef.getValue();

            // prendo posizione del quadrato in esame
            int posQLD;
            posQLD = (int) entry_lineaDef.getKey();
            //se ci sono quadrati limitrofi nella linea a quello selezionato della linea deformabile
            // se il quadrato è nella medesima posizione 
            if (linea.getQuadratiDeformati().containsKey(posQLD)) {
                //Quadrato della linea da confutare
                Quadrato quadrato_linea;
                quadrato_linea = linea.getQuadratiDeformati().get(posQLD);
                switch (quadrato_linea.nome_def) {
                    case "UL":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }
                        break;
                    case "LL":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;
                    case "UR":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;
                    case "LR":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;

                }
            } // se è nella posizione -1 
            else if (linea.getQuadratiDeformati().containsKey(posQLD - 1)) {
                //Quadrato della linea da confutare
                Quadrato quadrato_linea;
                quadrato_linea = linea.getQuadratiDeformati().get(posQLD);
                switch (quadrato_linea.nome_def) {
                    case "UL":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }
                        break;
                    case "LL":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;
                    case "UR":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;
                    case "LR":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;

                }

            } //se è nella posizione +1
            else if (linea.getQuadratiDeformati().containsKey(posQLD + 1)) {
                //Quadrato della linea da confutare
                Quadrato quadrato_linea;
                quadrato_linea = linea.getQuadratiDeformati().get(posQLD);
                switch (quadrato_linea.nome_def) {
                    case "UL":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }
                        break;
                    case "LL":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;
                    case "UR":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;
                    case "LR":
                        switch (quadrato_lineaDef.nome_def) {
                            case "UL":
                                //aggiorno fitness
                                break;

                            case "LL":
                                //aggiorno fitness
                                break;

                            case "UR":
                                //aggiorno fitness
                                break;

                            case "LR":
                                //aggiorno fitness
                                break;
                        }

                        break;

                }

            } //non c'è nessun quadrato nelle posizioni limitrofi al quadrato della linea deformabile selezionatas
            else {

            }
        }

        return val_fitness;
    }

}
