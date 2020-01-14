/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projv1;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Miracle
 */
public class Avaria implements Runnable{
    
    int i = 1;
    //Esta Thread corre a cada 5 segundos, com uma probabilidade de 10% irá furar
    //um autocarro aleatorio entre 30 a 50 segundos.
    public void run(){
        while(Pessoa.quantidade != 0){
            int random = ThreadLocalRandom.current().nextInt(1, 10);
            if (i == random){
                Autocarro a;
                a = Projv1.Autocarross.get(ThreadLocalRandom.current().nextInt(0, Projv1.Autocarross.size()));
                a.MudaFuro(ThreadLocalRandom.current().nextInt(30000, 50000));
            }
            try {
                Thread.sleep(5000);
             } catch (Exception e) {
                System.out.println(e);
             }
            
        }
    }
}
