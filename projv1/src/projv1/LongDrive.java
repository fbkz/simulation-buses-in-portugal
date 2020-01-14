/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projv1;

/**
 *
 * @author User
 */
public class LongDrive extends Autocarro{
    
    public LongDrive(){
        super();
        this.tipo = "LongDrive";
        capacidade = 59;
        for(int i = 0; i < this.TempoentreCidades.length ; i++){
            TempoentreCidades[i] *= 2;
        }
    }
    
}
