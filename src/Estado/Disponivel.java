package Estado;

import Midia.Midia;

public class Disponivel implements IEstado {
    @Override
    public void alugar(Midia midia) {
        System.out.println("Mídia " + midia.getNome() + " alugada com sucesso!");
        midia.setEstado(new Alugado());
    }

    @Override
    public void devolver(Midia midia) {
        System.out.println("Mídia " + midia.getNome() + " já está disponível.");
    }

    @Override
    public void renovar(Midia midia) {
        System.out.println("Mídia já disponível, não dá pra renovar.");
    }

    @Override
    public String getStatus(Midia midia) {
        return "Disponível";
    }
}