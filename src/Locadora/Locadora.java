package Locadora;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Cliente.Cliente;
import Creators.CreatorFilme;
import Creators.CreatorSerie;
import Midia.Filme;
import Midia.IMidia;
import Midia.Midia;
import Midia.Serie;
import Observer.IObserver;

import java.lang.reflect.Field;
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
import Generos.Comedia;
import Generos.Drama;
import Generos.Fantasia;
import Generos.Romance;
import Generos.Terror;


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
    
    
    public void adicionarFilme(String titulo, int ano, int duracao, List<String> generos) {
        IMidia novoFilme = this.creatorFilme.createMidia(titulo, ano, duracao);
        
        novoFilme = aplicarDecoradores(novoFilme, generos);
        
        this.filmes.add(novoFilme);
        novoFilme.subscribe(this);
    }

    public void adicionarSerie(String titulo, int ano, int temporadas, int episodios, List<String> generos) {
        
        IMidia novaSerie = this.creatorSerie.createMidia(titulo, ano, temporadas, episodios);
        
        novaSerie = aplicarDecoradores(novaSerie, generos);
        
        this.series.add(novaSerie);
        novaSerie.subscribe(this);
    }

   

    private IMidia aplicarDecoradores(IMidia midia, List<String> generos) {
        IMidia midiaDecorada = midia;

        for (String genero : generos) {
            switch (genero.toLowerCase()) {
                case "comedia":
                    midiaDecorada = new Comedia(midiaDecorada);
                    break;
                case "drama":
                    midiaDecorada = new Drama(midiaDecorada);
                    break;
                case "fantasia":
                    midiaDecorada = new Fantasia(midiaDecorada);
                    break;
                case "romance":
                    midiaDecorada = new Romance(midiaDecorada);
                    break;
                case "terror":
                    midiaDecorada = new Terror(midiaDecorada);
                    break;
                default:
                   
            }
        }
        return midiaDecorada;
    }
    
    
    private Midia desempacotarMidia(IMidia midiaPotencialmenteDecorada) {
        IMidia atual = midiaPotencialmenteDecorada;

        while (atual != null && !(atual instanceof Midia)) {
            
            IMidia proximoNivel = null;
            Class<?> classeAtual = atual.getClass();


            while (classeAtual != null && proximoNivel == null) {
                Field[] campos = classeAtual.getDeclaredFields();
                
                for (Field campo : campos) {
                    campo.setAccessible(true); 
                    try {
                        Object valor = campo.get(atual);

                        if (valor instanceof IMidia && valor != null) {
                            proximoNivel = (IMidia) valor;
                            break; 
                        }
                    } catch (Exception e) {
                        // Ignora campos protegidos/inacessíveis que falharem
                    }
                }

                classeAtual = classeAtual.getSuperclass();
            }

            if (proximoNivel != null) {
                atual = proximoNivel;
            } else {

                return null; 
            }
        }

        return (Midia) atual;
    }
    
public boolean locarMidia(String nomeCliente, String tituloMidia, int precoMidia, Scanner scanner, int dias) {
        
        IMidia midiaDecorada = procurarMidiaNoCatalogo(tituloMidia);
        
        if (midiaDecorada == null) { 
            System.out.println("Mídia não encontrada."); return false; 
        }

        Cliente cliente = procurarCliente(nomeCliente);
        if (cliente == null || !checarCliente(nomeCliente)) { return false; }
        
        System.out.print("Dias de aluguel: ");
        int diasAluguel = 1;
        if(scanner.hasNextInt()) diasAluguel = scanner.nextInt(); else scanner.next();
        
        cliente.getCarrinho().adicionarMidia(midiaDecorada, precoMidia);
        
        LocalDate dataPrevista = LocalDate.now().plusDays(dias);
        
        Locacao novaLocacao = new Locacao(midiaDecorada, cliente, dataPrevista);
        this.locacoesAtivas.add(novaLocacao);
        
        System.out.println("Adicionado ao carrinho: " + midiaDecorada.getData());
        return true;
    }
    
    public boolean finalizarPagamento(String nomeCliente) {
        Cliente cliente = procurarCliente(nomeCliente);
        if (cliente == null) return false;

        int valorTotal = cliente.getCarrinho().getValorTotalAPagar();
        if (valorTotal <= 0) return false;

        cliente.getCarrinho().processarPagamento(valorTotal, this.pagamentoChain); 
        
        List<IMidia> midiasNoCarrinho = cliente.getCarrinho().getMidias();

        for (IMidia midiaDecorada : midiasNoCarrinho) {
            
            Optional<Locacao> locacao = this.locacoesAtivas.stream()
                .filter(l -> l.getMidia() == midiaDecorada && l.getCliente() == cliente && !l.isConcluida())
                .findFirst();

            if (locacao.isPresent()) {
                Midia midiaReal = desempacotarMidia(midiaDecorada);
                
                if (midiaReal != null) {
                    midiaReal.subscribe(cliente); 
                    midiaReal.alugar(); 
                }
            } 
        }
        cliente.getCarrinho().limparCarrinho(); 
        return true;
    }
    
    public void devolverMidia(String tituloMidia, String nomeCliente) {        
        IMidia midiaDecorada = procurarMidiaNoCatalogo(tituloMidia);
        Cliente cliente = procurarCliente(nomeCliente);
        
        if (midiaDecorada != null && cliente != null) {
            
            Midia midiaReal = desempacotarMidia(midiaDecorada);
            if (midiaReal != null) midiaReal.devolver();
            
            Optional<Locacao> locacao = this.locacoesAtivas.stream()
                    .filter(l -> l.getMidia() == midiaDecorada && l.getCliente() == cliente)
                    .findFirst();
                
            locacao.ifPresent(l -> {
                l.marcarConcluida(); 
                this.locacoesAtivas.remove(l);
            });
            
             boolean temOutrasPendencias = this.locacoesAtivas.stream().filter(l -> l.getCliente() == cliente).anyMatch(Locacao::isAtrasada); 
            if (!temOutrasPendencias) this.clientesBloqueados.remove(cliente); 
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
    
    public IMidia procurarMidiaNoCatalogo(String titulo) {  
        Optional<IMidia> midiaFilme = filmes.stream()
            .filter(m -> m.getData().toLowerCase().contains(titulo.toLowerCase())).findFirst();
        if (midiaFilme.isPresent()) return midiaFilme.get();
        
        Optional<IMidia> midiaSerie = series.stream()
            .filter(m -> m.getData().toLowerCase().contains(titulo.toLowerCase())).findFirst();
        if (midiaSerie.isPresent()) return midiaSerie.get();
        
        return null;
    }
    
    
    public Cliente procurarCliente(String nomeCliente) {
        return this.clientes.stream()
            .filter(c -> c.getNome().equalsIgnoreCase(nomeCliente))
            .findFirst()
            .orElse(null);
    }
}