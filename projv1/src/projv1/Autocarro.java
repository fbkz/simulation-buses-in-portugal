/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projv1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;
import static projv1.Pessoa.Nomes;
/**
 *
 * @author User
 */
public class Autocarro implements Runnable{
    
    private int furo = -1; private int furotempo; //-1 não tem furo   1 tem furo
    int parar = 0;                              //funcionario pode mandar parar
    int tempo;                                  //Tempo entre cidades
    String Motorista;                           //Motorista do Autocarro
    int TempoentreCidades[] = {10000, 12000, 15000, 5000}; //Distancias
    static int num = 1;                         //numero para cada autocarro
    int numero;                                 //numero do autocarro
    int capacidade;                             //capacidade do autocarro
    int lotado = 0;                             //pessoas dentro do autocarro
    int localizado;                             //Onde se encontra
    int destino;                                //Onde se dirige
    String tipo;                                //Qual o tipo do Autocarro
    List<Pessoa> Passageiros = Collections.synchronizedList(new ArrayList<Pessoa>());//Passageiros no Autocarro
    int sentido = 1;                            //sentido do Autocarro
    
    
    ReentrantLock lock = new ReentrantLock();
    
    
    public Autocarro(){ //Aleatorio o Motorista, a localizaçao e o sentido
        this.numero = num;
        num++;
        this.Motorista = Nomes[ThreadLocalRandom.current().nextInt(0, 25 + 1)];
        this.localizado = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        this.destino = localizado + 1;
        if (localizado == 4){
            this.destino = 3;
            this.sentido = -1;
        }
    }
    
    public String ReturnMotorista(){
        return this.Motorista;
    }
    
    public void AlterarMotorista(String a){
        this.Motorista = a;
    }
    
    void Percorrer(){ //Thread para entre as cidades, e verifica se existe um furo a meio
        try {
                Thread.sleep(tempo/2);
             } catch (Exception e) {
                System.out.println(e);
             }
        if (this.furo == 1){
            System.out.println("Furo no " + this.tipo + " nr "+this.numero
                +" no caminho "+Projv1.cidades[this.localizado] + "---" + Projv1.cidades[this.destino]
                + "  Demorará "+ this.furotempo/1000+" segundos para o arranjo");
             try {
                Thread.sleep(furotempo);
             } catch (Exception e) {
                System.out.println(e);
             }
             this.furo *= -1;
             System.out.println("Furo arranjado!!!");
        }
        
        try {
                Thread.sleep(tempo/2);
             } catch (Exception e) {
                System.out.println(e);
             }
        }
    
    
    public void MudaFuro(int a){ //Caso exista furo, muda a variavel(causada pela Thread Avaria
        this.furo = 1;
        this.furotempo = a;
    }
    
    
    //Método da entrada de passageiros, um passageiro que queira ir num sentido,
    //nao irá entrar num autocarro em sentido contrario. Coloca-o na lista Passageiros
    public synchronized void Passageiro(){ 
        lock.lock();
        if (destino == 0){
            tempo = this.TempoentreCidades[0];
        } else{ if (sentido == 1){
            tempo = TempoentreCidades[this.destino - sentido];
        } else {tempo = TempoentreCidades[this.destino + sentido];
        }}
        try{
        String z = "\nQuem entrou no "+this.tipo+" nr "+this.numero+": ";
        synchronized (Projv1.Pessoas) {
            for(Iterator<Pessoa> iterator = Projv1.Pessoas.iterator(); iterator.hasNext();){
                Pessoa a = iterator.next();
                if ((a.ReturnOrigem() == this.localizado) && (lotado<capacidade)
                        && (((a.ReturnDestino() - a.ReturnOrigem())* this.sentido))>0){
                    Passageiros.add(a);
                    iterator.remove();
                    lotado++;
                    z+= (a.ReturnNome()+" | ");
                    }
                }
            System.out.println(z);
            System.out.println("O "+ this.tipo+" nr "+this.numero+" partiu de " + 
                   Projv1.cidades[localizado]+" com "+this.lotado+" passageiros"+
                    ". Irá demorar "+ tempo/1000+ " segundos");
        }}finally{lock.unlock();}
    }

    
    //Funcionamento da Thread dos Autocarros, Com varias pausas nas cidades e
    //no caminho entre elas
    public void run(){
        while(Pessoa.quantidade != 0){
            Passageiro();            
            Percorrer();

            System.out.println("O "+ this.tipo+" nr "+this.numero + 
                    " chegou a "+Projv1.cidades[destino]);
            
            if (sentido == -1){
                localizado--;
                destino--;
                if (localizado == 0){
                    sentido = 1;
                    destino = 1;
                }
            }
            else{ 
                localizado++;
                destino++;
                if (localizado == 4){
                    sentido = -1;
                    destino = 3;
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
                System.out.println("Funcionario madou parar " + this.tipo 
                        + " nr " +  this.numero + ". "
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
    
    public String ToString(){
        return this.tipo + " nr " +  this.numero;
    }
    
    
}
