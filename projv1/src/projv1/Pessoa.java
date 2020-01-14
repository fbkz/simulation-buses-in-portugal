/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projv1;
import java.util.concurrent.ThreadLocalRandom;


/**o
 *
 * @author User
 */
public class Pessoa {
    static int quantidade = 0;
    String Nome;
    int nmrAutocarro = 0;
    int fila;
    int Origem= 0;
    int Destino=0;
    
    static int senhas[] = {1, 1, 1, 1, 1};
    
    static String Nomes[] = {"Manuel", "Rafael", "Luis", "Fabio", "Samuel", 
    "Vitor", "Joao", "Hugo", "Bruno", "Andre", "Tiago", "Artur", 
    "Ana", "Carolina", "Catarina", "Sofia", "Madalena", "Filipa", "Matilde", 
    "Sara", "Tatiana", "Mariana", "Alexandra", "Claudia", "Margarida", "Joana"};
    
    public Pessoa(){
        this.Nome = Nomes[ThreadLocalRandom.current().nextInt(0, 25 + 1)];
        Origem = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        this.Destino = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        while (this.Destino == this.Origem){
            this.Destino = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        }
        
        this.fila = senhas[Origem];
        senhas[Origem]++;
    }
    
    public int ReturnOrigem(){
        return this.Origem;
    }
    
    public int ReturnDestino(){
        return this.Destino;
    }
    
    public String ReturnNome(){
        return this.Nome;
    }
    
    
}
