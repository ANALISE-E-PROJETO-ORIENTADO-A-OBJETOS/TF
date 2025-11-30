package Cliente;

import Pagamento.Pagamento;

public class Carrinho {
 
 public void processarPagamento(int valorTotal, Pagamento chainHead) {
     chainHead.handle(valorTotal); 
 }
}