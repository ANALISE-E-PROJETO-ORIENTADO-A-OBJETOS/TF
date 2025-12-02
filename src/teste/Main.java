package teste;

import Cliente.Cliente;
import Locadora.Locadora;
import Midia.IMidia;
import Midia.Midia;
import Estado.Alugado;
import Estado.Disponivel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) {
        // 1. Obtendo a instância única (Singleton)
        Locadora locadora = Locadora.getInstance();

        System.out.println("#########################################################");
        System.out.println("🎬  TESTE INTEGRADO: LOCADORA JAVA");
        System.out.println("#########################################################\n");

        // ----------------------------------------------------------------------
        // CENÁRIO 1: PREPARAÇÃO (CADASTRO E DECORATOR)
        // ----------------------------------------------------------------------
        System.out.println(">>> [1] Preparação do Ambiente");

        // Criar Cliente
        String nomeCliente = "Lucas Testador";
        locadora.adicionarCliente(new Cliente(nomeCliente));
        System.out.println("✅ Cliente cadastrado: " + nomeCliente);

        // Criar Filme com VÁRIOS Gêneros (Decorator)
        // Estrutura: Terror(Drama(Filme))
        List<String> generos = new ArrayList<>();
        generos.add("Drama");
        generos.add("Terror");

        String nomeFilme = "O Iluminado";
        locadora.adicionarFilme(nomeFilme, 1980, 146, generos);

        // Validar Decorator
        IMidia midiaDecorada = locadora.procurarMidiaNoCatalogo(nomeFilme);
        if (midiaDecorada != null) {
            System.out.println("✅ Mídia criada com Decorators.");
            // Deve imprimir algo como: O Iluminado ... [Drama] [Terror]
            System.out.println("   Descrição completa: " + midiaDecorada.getData());
        } else {
            System.out.println("❌ Erro fatal: Mídia não encontrada.");
            return;
        }
        System.out.println();


        // ----------------------------------------------------------------------
        // CENÁRIO 2: PROCESSO DE ALUGUEL
        // ----------------------------------------------------------------------
        System.out.println(">>> [2] Realizando Locação");

        // Simulamos que o usuário digitou "5" dias no console
        Scanner scannerSimulado = new Scanner("5");
        int valorAluguel = 20;

        // Tenta locar
        boolean locou = locadora.locarMidia(nomeCliente, nomeFilme, valorAluguel, scannerSimulado);

        if (locou) {
            System.out.println("✅ Mídia adicionada ao carrinho.");
        } else {
            System.out.println("❌ Falha ao adicionar ao carrinho.");
        }

        // Validar estado antes do pagamento (Ainda deve ser Disponível no objeto real, pois só está no carrinho)
        Midia midiaReal = desempacotarParaTeste(midiaDecorada);
        System.out.println("   Estado atual (pré-pagamento): " + (midiaReal != null ? midiaReal.getStatus() : "Erro"));
        System.out.println();


        // ----------------------------------------------------------------------
        // CENÁRIO 3: PAGAMENTO E MUDANÇA DE ESTADO
        // ----------------------------------------------------------------------
        System.out.println(">>> [3] Finalizando Pagamento (Chain of Responsibility)");

        boolean pagou = locadora.finalizarPagamento(nomeCliente);

        if (pagou) {
            System.out.println("✅ Pagamento processado.");
            // AGORA o estado deve ter mudado para Alugado
            if (midiaReal.getEstado() instanceof Alugado) {
                 System.out.println("✅ SUCESSO: Estado da mídia alterou para: " + midiaReal.getStatus());
            } else {
                 System.out.println("❌ ERRO: Estado da mídia não mudou! Atual: " + midiaReal.getStatus());
            }
        } else {
            System.out.println("❌ Erro no pagamento.");
        }
        System.out.println();


        // ----------------------------------------------------------------------
        // CENÁRIO 4: SIMULAÇÃO DE ATRASO E BLOQUEIO (OBSERVER)
        // ----------------------------------------------------------------------
        System.out.println(">>> [4] Teste de Atraso e Bloqueio Automático");

        // Vamos forçar o atraso manualmente já que não queremos esperar dias reais
        if (midiaReal != null && midiaReal.getEstado() instanceof Alugado) {
            System.out.println("   ⚠️  Forçando estado 'Atrasado' via código...");

            // O cast é seguro aqui pois verificamos com instanceof acima
            ((Alugado) midiaReal.getEstado()).marcarAtraso(midiaReal);

            // O Observer deve ter sido notificado neste momento
        }

        // Verificar se a Locadora bloqueou o cliente
        boolean clienteEstaLivre = locadora.checarCliente(nomeCliente);

        if (!clienteEstaLivre) {
            System.out.println("✅ SUCESSO: O cliente '" + nomeCliente + "' foi BLOQUEADO pelo sistema.");
        } else {
            System.out.println("❌ FALHA: O cliente continua livre mesmo com atraso.");
        }

        // Tentar alugar outra coisa (Deve falhar)
        System.out.println("   Tentando alugar outra mídia enquanto bloqueado...");
        locadora.adicionarSerie("Breaking Bad", 2008, 5, 60, List.of("Drama"));
        boolean tentativaBloqueada = locadora.locarMidia(nomeCliente, "Breaking Bad", 10, new Scanner("1"));

        if (!tentativaBloqueada) {
            System.out.println("✅ Sistema impediu nova locação corretamente.");
        } else {
            System.out.println("❌ ERRO CRÍTICO: Cliente bloqueado conseguiu alugar!");
        }
        System.out.println();


        // ----------------------------------------------------------------------
        // CENÁRIO 5: DEVOLUÇÃO E DESBLOQUEIO
        // ----------------------------------------------------------------------
        System.out.println(">>> [5] Devolução e Desbloqueio");

        locadora.devolverMidia(nomeFilme, nomeCliente);
        System.out.println("✅ Mídia devolvida.");

        // Validar Estado da Mídia (Deve ser Disponível)
        if (midiaReal.getEstado() instanceof Disponivel) {
             System.out.println("✅ Estado da mídia voltou para: " + midiaReal.getStatus());
        } else {
             System.out.println("❌ Estado da mídia incorreto após devolução: " + midiaReal.getStatus());
        }

        // Validar Desbloqueio do Cliente
        if (locadora.checarCliente(nomeCliente)) {
            System.out.println("✅ SUCESSO: Cliente desbloqueado automaticamente.");
        } else {
            System.out.println("❌ FALHA: Cliente continua bloqueado após devolver tudo.");
        }

        System.out.println("\n#########################################################");
        System.out.println("🏁  FIM DO TESTE");
        System.out.println("#########################################################");

        scannerSimulado.close();
    }

    /**
     * Helper Mágico para Testes:
     * Entra dentro dos objetos Decorator (Drama, Terror, etc.) recursivamente
     * até encontrar o objeto 'Midia' (Filme/Serie) real lá no fundo.
     * Isso permite checar o Estado (Alugado/Disponivel) sem quebrar o encapsulamento do Decorator.
     */
    private static Midia desempacotarParaTeste(IMidia midia) {
        IMidia atual = midia;

        // Enquanto o objeto atual NÃO for a classe concreta Midia (Filme ou Serie)
        while (atual != null && !(atual instanceof Midia)) {
            IMidia proximo = null;
            Class<?> clazz = atual.getClass();

            // Loop para procurar a variável 'wrappee' (ou similar) nas classes pai (Generos)
            while (clazz != null && proximo == null) {
                for (Field f : clazz.getDeclaredFields()) {
                    f.setAccessible(true); // Permite ler variáveis private/protected
                    try {
                        Object val = f.get(atual);
                        // Se achou um campo que é IMidia, é o recheio do Decorator
                        if (val instanceof IMidia && val != null) {
                            proximo = (IMidia) val;
                            break;
                        }
                    } catch (Exception e) {}
                }
                clazz = clazz.getSuperclass(); // Sobe para a classe pai (ex: de Drama para Generos)
            }

            if (proximo != null) {
                atual = proximo; // Desce um nível
            } else {
                return null; // Não achou nada, desiste
            }
        }
        return (Midia) atual;
    }
}