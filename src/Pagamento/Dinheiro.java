package Pagamento;

public abstract class Dinheiro implements Pagamento{

	
	protected Pagamento next;
    protected int valorNota; 
    
    public Dinheiro(int valorNota) {
        this.valorNota = valorNota;
    }
    
    
    @Override
    public void setNext(Pagamento next) {
        this.next = next;
    }
    

    @Override
    public void handle(int request) {
    	if (request >= valorNota) {
            int restante = request % valorNota;
            
            
            if (restante > 0 && next != null) {
                next.handle(restante);
            } else if (restante == 0) {
                System.out.println("Pagamento concluído.");
            } else if (restante > 0 && next == null) {
                System.out.println("ERRO: Não foi possível completar o pagamento. Faltam R$" + restante + ",00.");
            }
        } else {
            if (next != null) {
                next.handle(request);
            } else {
                System.out.println("ERRO: Não foi possível processar a requisição de R$" + request + ",00. Fim da cadeia.");
            }
        }
    }
}
