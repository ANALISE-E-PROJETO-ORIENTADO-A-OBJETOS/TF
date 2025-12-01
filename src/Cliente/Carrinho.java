package Cliente;

import java.util.ArrayList;
import java.util.List;

import Midia.Midia;
import Pagamento.Pagamento;

public class Carrinho {
	private List<Midia> midias;
	private int valorTotalAPagar;
	
	public Carrinho() {
        this.midias = new ArrayList<>();
        this.valorTotalAPagar = 0;
    }

	
	public void adicionarMidia(Midia midia, int valor) {
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


	public List<Midia> getMidias() {
		return midias;
	}
}