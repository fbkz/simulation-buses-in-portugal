/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projv1;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import static projv1.Autocarro.num;

/**
 *
 * @author User
 */
public class Expresso extends Autocarro{
    
    //O Expresso foi necessario o "Override" dos métodos quase todos, porque
    //era necessário que o autocarro nao parasse em 2 estaçoes, mudando completamente
    //o funcionamento dos métodos, passageiros que fossem para as cidades em que
    //nao para nao poderiam entrar no autocarro.
    int tempo;
    String cidades[] = {"Lisboa", "Porto", "Braga"};
    
    public Expresso(){
        super();
        this.capacidade = 69;
        this.localizado = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        while (localizado == 3 || localizado == 1){
            this.localizado = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        }
        this.destino = localizado + 2;
        if (localizado == 4){
            this.destino = 2;
            this.sentido = -1;
        }
        this.tipo = "Expresso"; 
    }
    
    @Override
    void Percorrer(){
        try {
            
                Thread.sleep(tempo);
             } catch (Exception e) {
                System.out.println(e);
             }
    }
    
    @Override
    public synchronized void Passageiro(){
        lock.lock();
        if (destino == 0){
            tempo = this.TempoentreCidades[0] + this.TempoentreCidades[1];
        } else if (destino == 2){
            if (sentido == -1){
                tempo = this.TempoentreCidades[2] + this.TempoentreCidades[3];
            } else { tempo = this.TempoentreCidades[0] + this.TempoentreCidades[1];}
        } else {
            tempo = this.TempoentreCidades[2] + this.TempoentreCidades[3];
        }

        try{
        String z = "\nQuem entrou no "+this.tipo+" nr "+this.numero+": ";
        synchronized (Projv1.Pessoas) {
            for(Iterator<Pessoa> iterator = Projv1.Pessoas.iterator(); iterator.hasNext();){
                Pessoa a = iterator.next();
                if ((a.ReturnOrigem() == this.localizado) && (lotado<capacidade)
                        && (a.ReturnDestino() == 0||a.ReturnDestino() == 2||a.ReturnDestino() == 4)
                        && (((a.ReturnDestino() - a.ReturnOrigem())* this.sentido))>0){
                    this.Passageiros.add(a);
                    iterator.remove();
                    lotado++;
                    z+= (a.ReturnNome()+" | ");
                    }
                }
            System.out.println(z);
            System.out.println("O "+ this.tipo+" nr "+this.numero+" partiu de " + 
                   Projv1.cidades[localizado]+" com "+this.lotado+" passageiros"+
                    ". Irá demorar " + this.tempo/1000 + " segundos");
        }}finally{lock.unlock();}
    }

    @Override
    public void run(){
        while(Pessoa.quantidade != 0){
            this.Passageiro();            
            this.Percorrer();

            System.out.println("O "+ this.tipo+" nr "+this.numero + 
                    " chegou a "+Projv1.cidades[destino]);
            if (sentido == -1){
                localizado--;
                localizado--;
                destino--;
                destino--;
                if (localizado == 0){
                    sentido = 1;
                    destino = 2;
                }
            }
            else{ 
                localizado++;
                localizado++;
                destino++;
                destino++;
                if (localizado == 4){
                    sentido = -1;
                    destino = 2;
                    }
                }
            
            try {
                Thread.sleep(3000);
             } catch (Exception e) {
                System.out.println(e);
             }
            
            String zz = "\nQuem saiu do "+this.tipo+" nr "+this.numero+": ";
            for(Iterator<Pessoa> iterator = Passageiros.iterator(); iterator.hasNext();){
                Pessoa a = iterator.next();
                if (a.ReturnDestino() == this.localizado){
                    iterator.remove();
                    Pessoa.quantidade--;
                    zz+= (a.ReturnNome()+" | ");
                    //System.out.println(a.ReturnNome() + " chegou ao destino "+ Projv1.cidades[a.ReturnDestino()] +" e saiu do autocarro");
                    lotado--;
                    }
                }
            System.out.println(zz);
            try {
                Thread.sleep(3000);
             } catch (Exception e) {
                System.out.println(e);
             }
            if (parar == 1){
                int a = ThreadLocalRandom.current().nextInt(20000, 35000);
                System.out.println("Funcionario madou parar " + this + ". "
                        + "Tempo até o arranque: "+ a/1000);
                try {
                Thread.sleep(a);
             } catch (Exception e) {
                System.out.println(e);
             }
                this.parar = 0;
            }
        }
    }
}
