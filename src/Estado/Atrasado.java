package Estado;

import Midia.Midia;

public class Atrasado implements IEstado {
    @Override
    public void alugar(Midia midia) {
        System.out.println("Mídia " + midia.getNome() + " está atrasada! Não pode ser alugada.");
    }
    
    @Override
    public void devolver(Midia midia) {
        System.out.println("Mídia " + midia.getNome() + " devolvida com atraso!");
        midia.setEstado(new Disponivel());
    }
    
    @Override
    public void renovar(Midia midia) {
        System.out.println("Não é possível renovar mídia atrasada. Devolva primeiro.");
    }
    
    @Override
    public String getStatus(Midia midia) {
        return "Atrasado";
    }
}
