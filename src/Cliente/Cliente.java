package Cliente;

import Observer.IObserver;

public class Cliente implements IObserver {
    private String nome;
    private Carrinho carrinho;

    public Cliente(String nome){
        this.nome = nome;
        this.carrinho = new Carrinho();
    }
    
    public String getNome() {
    	return nome;
    }

    public Carrinho getCarrinho() { 
        return carrinho;
    }
    
    @Override
    public void update(Object context) {
        System.out.println("Cliente " + nome + " foi notificado que: " + context);
    }
    
}
