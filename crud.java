import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Peca {
    private String nome;
    private String fabricante;
    private int ano;

    public Peca(String nome, String fabricante, int ano) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.ano = ano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Fabricante: " + fabricante + ", Ano: " + ano;
    }
}

public class Principal {
    private static final String NOME_ARQUIVO = "pecas.txt";
    private static List<Peca> pecas = new ArrayList<>();

    public static void main(String[] args) {
        carregarPecas();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Listar Peças");
            System.out.println("2. Adicionar Peça");
            System.out.println("3. Atualizar Peça");
            System.out.println("4. Deletar Peça");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Consumir o caractere de nova linha

            switch (escolha) {
                case 1:
                    listarPecas();
                    break;
                case 2:
                    adicionarPeca(scanner);
                    break;
                case 3:
                    atualizarPeca(scanner);
                    break;
                case 4:
                    deletarPeca(scanner);
                    break;
                case 5:
                    salvarPecas();
                    System.out.println("Encerrando o programa.");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void carregarPecas() {
        try (BufferedReader leitor = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(", ");
                pecas.add(new Peca(partes[0], partes[1], Integer.parseInt(partes[2])));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar as peças: " + e.getMessage());
        }
    }

    private static void salvarPecas() {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Peca peca : pecas) {
                escritor.println(peca.getNome() + ", " + peca.getFabricante() + ", " + peca.getAno());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar as peças: " + e.getMessage());
        }
    }

    private static void listarPecas() {
        if (pecas.isEmpty()) {
            System.out.println("Nenhuma peça disponível.");
        } else {
            pecas.forEach(System.out::println);
        }
    }

    private static void adicionarPeca(Scanner scanner) {
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o fabricante: ");
        String fabricante = scanner.nextLine();
        System.out.print("Digite o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Consumir o caractere de nova linha
        pecas.add(new Peca(nome, fabricante, ano));
        System.out.println("Peça adicionada com sucesso.");
    }

    private static void atualizarPeca(Scanner scanner) {
        System.out.print("Digite o nome da peça a ser atualizada: ");
        String nome = scanner.nextLine();
        for (Peca peca : pecas) {
            if (peca.getNome().equalsIgnoreCase(nome)) {
                System.out.print("Digite o novo nome (deixe em branco para manter o atual): ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isEmpty()) {
                    peca.setNome(novoNome);
                }
                System.out.print("Digite o novo fabricante (deixe em branco para manter o atual): ");
                String novoFabricante = scanner.nextLine();
                if (!novoFabricante.isEmpty()) {
                    peca.setFabricante(novoFabricante);
                }
                System.out.print("Digite o novo ano (deixe em branco para manter o atual): ");
                String novoAnoStr = scanner.nextLine();
                if (!novoAnoStr.isEmpty()) {
                    try {
                        int novoAno = Integer.parseInt(novoAnoStr);
                        peca.setAno(novoAno);
                    } catch (NumberFormatException e) {
                        System.out.println("Formato de ano inválido. Ano não atualizado.");
                    }
                }
                System.out.println("Peça atualizada com sucesso.");
                return;
            }
        }
        System.out.println("Peça não encontrada.");
    }

    private static void deletarPeca(Scanner scanner) {
        System.out.print("Digite o nome da peça a ser deletada: ");
        String nome = scanner.nextLine();
        pecas.removeIf(peca -> peca.getNome().equalsIgnoreCase(nome));
        System.out.println("Peça deletada com sucesso.");
    }
}
