package Locadora;


import java.util.List;
import java.util.Optional;

import Cliente.Cliente;
import Creators.CreatorFilme;
import Creators.CreatorSerie;
import Midia.Filme;
import Midia.IMidia;
import Midia.Midia;
import Midia.Serie;
import Observer.IObserver;

import java.time.LocalDate;
import java.util.ArrayList;

import Pagamento.CemReais;
import Pagamento.CincoReais;
import Pagamento.CinquentaReais;
import Pagamento.DezReais;
import Pagamento.DoisReais;
import Pagamento.DuzentosReais;
import Pagamento.Pagamento;
import Pagamento.UmReal;
import Pagamento.VinteReais;

public class Locadora implements IObserver{
    
    
    private static Locadora instance;

   
    private Pagamento pagamentoChain;     
    private List<Cliente> clientes;
    private List<IMidia> series;
    private List<IMidia> filmes;
    private List<Cliente> clientesBloqueados;
    private CreatorFilme creatorFilme;
    private CreatorSerie creatorSerie;
    private List<Locacao> locacoesAtivas;
    
  
    private Locadora() {
        
    	this.pagamentoChain = construirCadeiaDePagamento();
        this.series = new ArrayList<>();
        this.filmes = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.clientesBloqueados = new ArrayList<>();
        this.creatorFilme = new CreatorFilme();
        this.creatorSerie = new CreatorSerie();
        this.locacoesAtivas = new ArrayList<>();
       
    }

    public static Locadora getInstance() {
        if (instance == null) {
            instance = new Locadora();
        }
        return instance;
    }
    
 
    private Pagamento construirCadeiaDePagamento() {
    	
        Pagamento duzentos = new DuzentosReais();
        Pagamento cem = new CemReais();
        Pagamento cinquenta = new CinquentaReais();
        Pagamento vinte = new VinteReais();
        Pagamento dez = new DezReais();
        Pagamento cinco = new CincoReais();
        Pagamento dois = new DoisReais();
        Pagamento um = new UmReal(); 
        
        duzentos.setNext(cem);
        cem.setNext(cinquenta);
        cinquenta.setNext(vinte);
        vinte.setNext(dez);
        dez.setNext(cinco);
        cinco.setNext(dois);
        dois.setNext(um);
        
        return duzentos;
    }
    
    
    public void adicionarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }
    
    
    public void adicionarFilme(String titulo, int ano, int duracao) {
        IMidia novoFilme = this.creatorFilme.createMidia(titulo, ano, duracao);
        this.filmes.add(novoFilme);
        
        novoFilme.subscribe(this);
    }

    public void adicionarSerie(String titulo, int ano, int temporadas, int episodios, int duracaoAluguel) {        
        IMidia novaSerie = this.creatorSerie.createMidia(titulo, ano, temporadas, episodios, duracaoAluguel);
        this.series.add(novaSerie);
        
        novaSerie.subscribe(this);
    }

   


    public boolean locarMidia(String nomeCliente, String tituloMidia, int valorTotal) {
        
        Midia midia = procurarMidiaNoCatalogo(tituloMidia);
        if (midia == null) { 
        	return false; 
        }

        Cliente cliente = procurarCliente(nomeCliente);
        if (cliente == null || !checarCliente(nomeCliente)) {
        	System.out.println("Cliente " + cliente.getNome() + " não existe ou está bloqueado");
        	return false; 
        }
        
       
        LocalDate dataPrevista = LocalDate.now().plusDays(5); 
        Locacao novaLocacao = new Locacao(midia, cliente, dataPrevista);
        this.locacoesAtivas.add(novaLocacao);
        
   
        midia.subscribe(cliente); 

        cliente.getCarrinho().processarPagamento(valorTotal, this.pagamentoChain); 

        midia.alugar(); 
        
        return true;
    }
   
    public void devolverMidia(String tituloMidia, String nomeCliente) {        
        
        Midia midia = procurarMidiaNoCatalogo(tituloMidia);
        Cliente cliente = procurarCliente(nomeCliente);
        
        if (midia != null && cliente != null) {
            midia.devolver(); 
            
            Optional<Locacao> locacao = this.locacoesAtivas.stream().filter(l -> l.getMidia() == midia && l.getCliente() == cliente).findFirst();
                
            locacao.ifPresent(l -> {
                l.marcarConcluida(); 
                this.locacoesAtivas.remove(l);
            });
            
            boolean temOutrasPendencias = this.locacoesAtivas.stream().filter(l -> l.getCliente() == cliente).anyMatch(Locacao::isAtrasada); 
                
            if (!temOutrasPendencias) {
                this.clientesBloqueados.remove(cliente); 
            } 
            
        } else {
            System.err.println("DEVOLVER MÍDIA: Mídia ou Cliente não encontrado.");
        }
    }
    
   
    public boolean checarCliente(String nomeCliente) {
       
    	for (Cliente clienteBloqueado : this.clientesBloqueados) {
            if (clienteBloqueado.getNome() != null && clienteBloqueado.getNome().equalsIgnoreCase(nomeCliente)) {
                return false; 
            }
        }
        return true; 
    }
    
    
    @Override
    public void update(Object context) {
        String mensagem = (String) context;

        if (mensagem.contains("Atrasado")) {            

        	Optional<Locacao> locacaoAtrasada = this.locacoesAtivas.stream().filter(Locacao::isAtrasada).findFirst();

            if (locacaoAtrasada.isPresent()) {
                Cliente clienteParaBloquear = locacaoAtrasada.get().getCliente();
                
                if (!this.clientesBloqueados.contains(clienteParaBloquear)) {
                    this.clientesBloqueados.add(clienteParaBloquear); 
                }
            } else {
                System.out.println("-> Falha ao encontrar locação ativa associada ao item atrasado.");
            }
        }
    }
    
    
    public Midia procurarMidiaNoCatalogo(String titulo) {  
        Optional<IMidia> filme = filmes.stream()
            .filter(m -> m instanceof Midia)
            .filter(m -> ((Midia) m).getNome().equalsIgnoreCase(titulo))
            .findFirst();
            
        if (filme.isPresent()) {
            return (Midia) filme.get();
        }
        
        Optional<IMidia> serie = series.stream().filter(m -> m instanceof Midia).filter(m -> ((Midia) m).getNome().equalsIgnoreCase(titulo)).findFirst();

        if (serie.isPresent()) {
            return (Midia) serie.get();
        }
        
        return null;
    }
    
    
    private Cliente procurarCliente(String nomeCliente) {
        return this.clientes.stream()
            .filter(c -> c.getNome().equalsIgnoreCase(nomeCliente))
            .findFirst()
            .orElse(null);
    }
}