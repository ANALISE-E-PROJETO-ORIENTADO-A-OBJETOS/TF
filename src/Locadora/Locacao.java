package Locadora; 

import java.time.LocalDate;
import Cliente.Cliente;
import Midia.IMidia; 

public class Locacao {
    private IMidia midia; 
    private Cliente cliente;
    private LocalDate dataDevolucaoPrevista;
    private boolean concluida;

    public Locacao(IMidia midia, Cliente cliente, LocalDate dataDevolucaoPrevista) {
        this.midia = midia;
        this.cliente = cliente;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.concluida = false;
    }

    public IMidia getMidia() {
        return midia;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public boolean isConcluida() {
        return concluida;
    }
    
    public void marcarConcluida() {
        this.concluida = true;
    }

    public boolean isAtrasada() {
        return !concluida && LocalDate.now().isAfter(dataDevolucaoPrevista);
    }
}