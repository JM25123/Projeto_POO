import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Usuario usuario = new Usuario("aluno", "1234");
        boolean autenticado = fazerLogin(sc, usuario);

        if (!autenticado) {
            return;
        }

        Impressora impressora = new Impressora();
        boolean continuar = true;

        while (continuar) {
            exibirMenu();
            int opcao = lerInteiro(sc, "Escolha uma opção: ");


            //Alternativa para leitura de dados utilizando o scanner dentro dos parenteses
            //e coloquei o IntelliJ para entender portugues, bem útil na verdade ;)


            switch (opcao) {
                case 1:
                    opcaoConfigurarConexao(impressora);
                    break;
                case 2:
                    impressora.abrirConexao();
                    break;
                case 3:
                    impressora.fecharConexao();
                    break;
                case 4:
                    if (precisaDeConexao(impressora)) {
                        opcaoImprimirTexto(sc, impressora);
                    }
                    break;
                case 5:
                    if (precisaDeConexao(impressora)) {
                        opcaoImprimirQRCode(sc, impressora);
                    }
                    break;
                case 6:
                    if (precisaDeConexao(impressora)) {
                        opcaoImprimirCodigoBarras(sc, impressora);
                    }
                    break;
                case 7:
                    if (precisaDeConexao(impressora)) {
                        opcaoImprimirXMLSAT(sc, impressora);
                    }
                    break;
                case 8:
                    if (precisaDeConexao(impressora)) {
                        opcaoImprimirXMLCancelamento(sc, impressora);
                    }
                    break;
                case 9:
                    if (precisaDeConexao(impressora)) {
                        opcaoAbrirGaveta(impressora);
                    }
                    break;
                case 10:
                    if (precisaDeConexao(impressora)) {
                        opcaoSinalSonoro(sc, impressora);
                    }
                    break;
                case 0:
                    continuar = false;
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Escolha um número do menu.");
            }
        }
    }


    //Parte do login
    public static boolean fazerLogin(Scanner sc, Usuario usuario) {
        int tentativas = 0;
        while (tentativas < 3) {
            System.out.print("Usuário: ");
            String login = sc.nextLine();
            System.out.print("Senha: ");
            String senha = sc.nextLine();

            if (usuario.validarLogin(login, senha)) {
                System.out.println("Login realizado com sucesso!\n");
                return true;
            }

            tentativas++;
            int restantes = 3 - tentativas;
            if (restantes > 0) {
                System.out.println("Usuário ou senha incorretos. Tentativas restantes: " + restantes);
            } else {
                System.out.println("Você errou o login 3 vezes. Encerrando o programa.");
            }
        }
        return false;
    }


    //Menu básico
    public static void exibirMenu() {
        System.out.println("\n<===== MENU =====>");
        System.out.println("1  - Configurar conexão");
        System.out.println("2  - Abrir conexão");
        System.out.println("3  - Fechar conexão");
        System.out.println("4  - Imprimir texto");
        System.out.println("5  - Imprimir QR Code");
        System.out.println("6  - Imprimir código de barras");
        System.out.println("7  - Imprimir XML de venda (SAT)");
        System.out.println("8  - Imprimir XML de cancelamento (SAT)");
        System.out.println("9  - Abrir gaveta");
        System.out.println("10 - Sinal sonoro (bip)");
        System.out.println("0  - Sair");
    }

    //lembra de abrir a conexao antes
    public static boolean precisaDeConexao(Impressora impressora) {
        if (!impressora.isConexaoAberta()) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return false;
        }
        return true;
    }


    // ============= OPÇÕES DO MENU =============

    // Padronizada: usa direto os valores de teste sugeridos no roteiro
    // (USB + i9), sem perguntar nada. Troquem aqui se o grupo for testar
    // com outro modelo ou tipo de conexão.
    public static void opcaoConfigurarConexao(Impressora impressora) {
        int tipo = 1;          // 1 = USB
        String modelo = "i9";   // ou "i8", conforme a impressora do grupo
        String conexao = "USB";
        int parametro = 0;      // 0 para USB

        impressora.configurarConexao(tipo, modelo, conexao, parametro);
    }


    //imprime texto
    public static void opcaoImprimirTexto(Scanner sc, Impressora impressora) {
        System.out.print("Texto a imprimir: ");
        String dados = sc.nextLine();

        System.out.println("Posição: 1-Centro");
        int posicao = 1;

        System.out.println("Estilo: 4-Invertido");
        int estilo = 4;

        System.out.println("Tamanho: 0-Normal");
        int tamanho = 0;

        System.out.println("Imprimindo uma copia...");
        int copias = 1;

        for (int i = 1; i <= copias; i++) {
            impressora.imprimirTexto(dados, posicao, estilo, tamanho);
        }
    }


    //Qr code
    public static void opcaoImprimirQRCode(Scanner sc, Impressora impressora) {
        System.out.print("Conteúdo do QR Code: ");
        String dados = sc.nextLine();

        System.out.println("Tamanho: 4, Nivel de Correcao: 4.");
        int tamanho = 4;
        int nivelCorrecao = 4;

        impressora.imprimirQRCode(dados, tamanho, nivelCorrecao);
    }


    //Codigo de barras
    public static void opcaoImprimirCodigoBarras(Scanner sc, Impressora impressora) {
        System.out.println("8-CODE128");
        int tipo = 8;

        System.out.print("Dados do código: ");
        String dados = sc.nextLine();

        System.out.println("Imprimindo configuracoes Padrao.");
        int altura = 100;
        int largura = 2;
        int hri = 3;

        impressora.imprimirCodigoBarras(tipo, dados, altura, largura, hri);
    }


    //imprime o XML SAT
    public static void opcaoImprimirXMLSAT(Scanner sc, Impressora impressora) {
        System.out.print("Caminho do arquivo XML de venda (ex: XMLSAT.xml): ");
        String caminho = sc.nextLine();
        impressora.imprimirXMLSAT(caminho);
    }


    //imprime o XML de cancelamento
    public static void opcaoImprimirXMLCancelamento(Scanner sc, Impressora impressora) {
        System.out.print("Caminho do arquivo XML de cancelamento (ex: CANC_SAT.xml): ");
        String caminho = sc.nextLine();
        impressora.imprimirXMLCancelamentoSAT(caminho);
    }


    // Padronizada: abre a gaveta direto com valores de exemplo do roteiro,
    // sem perguntar nada.
    public static void opcaoAbrirGaveta(Impressora impressora) {
        int pino = 0;
        int ti = 5;
        int tf = 10;
        impressora.abrirGaveta(pino, ti, tf);
    }


    //faz o bip
    public static void opcaoSinalSonoro(Scanner sc, Impressora impressora) {
        int qtd = lerInteiro(sc, "Quantidade de bips (1 a 63): ");
        int tempoInicio = 10;
        int tempoFim = 10;
        impressora.sinalSonoro(qtd, tempoInicio, tempoFim);
    }


    //checa o input errado no menu

    public static int lerInteiro(Scanner sc, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = sc.nextLine();
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
