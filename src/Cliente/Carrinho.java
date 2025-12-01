package Cliente;

import java.util.ArrayList;
import java.util.List;
import Midia.IMidia; 
import Pagamento.Pagamento;

public class Carrinho {
    private List<IMidia> midias; 
    private int valorTotalAPagar;
    
    public Carrinho() {
        this.midias = new ArrayList<>();
        this.valorTotalAPagar = 0;
    }

    public void adicionarMidia(IMidia midia, int valor) { 
        this.midias.add(midia);
        this.valorTotalAPagar += valor;
    }

    public void limparCarrinho() {
        this.midias.clear();
        this.valorTotalAPagar = 0;
    }
    
    public void processarPagamento(int valorTotal, Pagamento chainHead) {
        chainHead.handle(valorTotal); 
    }
 
    public int getValorTotalAPagar() {
        return valorTotalAPagar;
    }

    public List<IMidia> getMidias() { 
        return midias;
    }
}