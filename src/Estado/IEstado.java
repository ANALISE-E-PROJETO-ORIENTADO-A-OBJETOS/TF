package Estado;

import Midia.Midia;

public interface IEstado {
    void alugar(Midia midia);
    void devolver(Midia midia);
    void renovar(Midia midia);
    String getStatus(Midia midia);
}
