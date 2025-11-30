package Locadora; 

import Cliente.Cliente;
import Midia.Midia;
import java.time.LocalDate; 

public class Locacao {
    
    private Midia midia;
    private Cliente cliente;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucaoPrevista;
    private boolean concluida; 

    public Locacao(Midia midia, Cliente cliente, LocalDate dataDevolucaoPrevista) {
        this.midia = midia;
        this.cliente = cliente;
        this.dataLocacao = LocalDate.now(); 
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.concluida = false;
    }

    public Midia getMidia() {
        return midia;
    }

    public Cliente getCliente() {
        return cliente;
    }
    
    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void marcarConcluida() {
        this.concluida = true;
    }
    
    public boolean isAtrasada() {
        return this.midia.getEstado().getClass().getSimpleName().equals("Atrasado");
    }
}