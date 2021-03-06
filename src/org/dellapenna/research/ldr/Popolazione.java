/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dellapenna.research.ldr;

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
    private final int contatoreMosse = 5;

    //Variabile che imposta la dimensione della popolazione
    private final int dimPopolazione = 50;

    //ArrayList salvataggio prima popolazione ?!!??!?!
    private ArrayList<LineaDeformabile> primaPOP = new ArrayList<>();

    Random rq = new Random(); //per le prove
    Random rm = new Random(); //per le prove

    /**
     * Metodo che crea la prima popolazione, dove ogni individuo ha subito un
     * numero di mosse prestabilito, salvandola in un file esterno.
     *
     * @param primaPopolazione vettore passato da fuori il quale viene riempito
     * con individuo raffiguranti la prima poplazione
     * @throws java.io.IOException I/O eccezzione attualmente non gestita
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
        primaPOP = primaPopolazione;

        //Salvo su file
        // gestioneSalvataggio.salvaDATA(primaPopolazione, contatoreMosse);
    }

    /**
     * Metodo che valuta la fitness di un individuo rispetto la linea data
     *
     * @param linea foram che si vuole ottenere
     * @param lineaDeformabile individuo in esame
     * @return val_fitness valore della funzione di fitness
     */
    public double valFitness(Linea linea, LineaDeformabile lineaDeformabile) {

        double val_fitness = 0;
        //per ogni quadrato deformato della linea deformabile
        for (Map.Entry entry_lineaDef : lineaDeformabile.getQuadratiDeformati().entrySet()) {
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
                double auxFit;
                auxFit = val_fitness;
                // riduzione di peso non effettuata ( posizione giusta ) 
                val_fitness = val_fitness + checkAndValutation(quadrato_linea, quadrato_lineaDef);
                
            } // se è nella posizione -1 
            else if (linea.getQuadratiDeformati().containsKey(posQLD - 1)) {
                //Quadrato della linea da confutare
                Quadrato quadrato_linea;
                quadrato_linea = linea.getQuadratiDeformati().get(posQLD - 1);
                double auxFit;
                auxFit = val_fitness;

                //0.7 valore messo per far perdere peso al valore di fitness generato a causa della differenza di posizione
                val_fitness = val_fitness + ((checkAndValutation(quadrato_linea, quadrato_lineaDef)) * 0.7);

            } //se è nella posizione +1
            else if (linea.getQuadratiDeformati().containsKey(posQLD + 1)) {
                //Quadrato della linea da confutare
                Quadrato quadrato_linea;
                quadrato_linea = linea.getQuadratiDeformati().get(posQLD + 1);
                double auxFit;
                auxFit = val_fitness;
                //0.7 valore messo per far perdere peso al valore di fitness generato a causa della differenza di posizione
                val_fitness = val_fitness + ((checkAndValutation(quadrato_linea, quadrato_lineaDef)) * 0.7);

            } //non c'è nessun quadrato nelle posizioni limitrofi al quadrato della linea deformabile selezionatas
            else {

            }
        }

        return val_fitness;
    }

    /**
     * vedi il tipo di trasformazione dei due quadrati di entrambe le linee e
     * assegna un valore di fitness
     *
     * @param quadrato_linea quadrato selezionato dalla linea
     * @param quadrato_lineaDef quadrato selezionato dalla linea deformabile
     * @return double val_fitness provvisorio senza riduzione di posizione.
     */
    private double checkAndValutation(Quadrato quadrato_linea, Quadrato quadrato_lineaDef) {
        double val_fitness;
        switch (quadrato_linea.nome_def) {
            case "UL":
                switch (quadrato_lineaDef.nome_def) {
                    case "UL":
                        //aggiorno fitness
                        return val_fitness = 0.8;
                    // break;

                    case "UR":
                        //aggiorno fitness
                        return val_fitness = 0.15;
                    //   break;

                    case "LL":
                        //aggiorno fitness
                        return val_fitness = 0.025;
                    //  break;

                    case "LR":
                        //aggiorno fitness
                        return val_fitness = 0.025;
                    //break;
                }
            //     break;
            case "LL":
                switch (quadrato_lineaDef.nome_def) {
                    case "LL":
                        //aggiorno fitness
                        return val_fitness = 0.8;
                    //break;

                    case "LR":
                        //aggiorno fitness
                        return val_fitness = 0.15;
                    //break;

                    case "UL":
                        //aggiorno fitness
                        return val_fitness = 0.025;
                    //break;

                    case "UR":
                        //aggiorno fitness
                        return val_fitness = 0.025;
                    //break;
                }

            //   break;
            case "UR":
                switch (quadrato_lineaDef.nome_def) {
                    case "UR":
                        //aggiorno fitness
                        return val_fitness = 0.8;
                    //break;

                    case "UL":
                        //aggiorno fitness
                        return val_fitness = 0.15;
                    //break;

                    case "LL":
                        //aggiorno fitness
                        return val_fitness = 0.025;
                    //break;

                    case "LR":
                        //aggiorno fitness
                        return val_fitness = 0.025;
                    //break;
                }

            //       break;
            case "LR":
                switch (quadrato_lineaDef.nome_def) {
                    case "LR":
                        //aggiorno fitness
                        return val_fitness = 0.8;
                    //break;

                    case "LL":
                        //aggiorno fitness
                        return val_fitness = 0.15;
                    //break;

                    case "UR":
                        //aggiorno fitness
                        return val_fitness = 0.025;
                    //break;

                    case "UL":
                        //aggiorno fitness
                        return val_fitness = 0.025;
                    //break;
                }

            //     break;
        }
        return 0; // se non è in nessun caso!??!??!? default
    }

    /**
     * Ritorna la prima popolazione
     *
     * @return primaPOP arraylist contenente la prima popolazione
     */
    public ArrayList getPrimaPOP() {
        return primaPOP;
    }

    /**
     * Preleva il numero di mosse da effettuare su ogni linea deformabile
     *
     * @return numero di mosse da effettuare su ogni linea deformabile
     */
    public int getContatoreMosse() {
        return contatoreMosse;
    }

    /**
     * Preleva la dimensione della popolazione impostata manualmente
     *
     * @return dimensione della popolazione
     */
    public int getDimPosizione() {
        return dimPopolazione;
    }

}
