package Estado;

import Midia.Midia;

public class Alugado implements IEstado {
    @Override
    public void alugar(Midia midia) {
        System.out.println("Mídia " + midia.getNome() + " já está alugada!");
    }
    
    @Override
    public void devolver(Midia midia) {
        System.out.println("Mídia " + midia.getNome() + " devolvida com sucesso!");
        midia.setEstado(new Disponivel());
    }
    
    @Override
    public void renovar(Midia midia) {
        System.out.println("Locação da mídia " + midia.getNome() + " renovada!");
        // Aq da pra adicionar sla, dias de locacao na midia, e ai
        // somar mais dias na locacao ao renovar
    }
    
    @Override
    public String getStatus(Midia midia) {
        return "Alugada";
    }
    
    // método para forçar atraso (só para testes)
    public void marcarAtraso(Midia midia) {
        midia.setEstado(new Atrasado()); 
    }
}