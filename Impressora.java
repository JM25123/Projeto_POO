import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Impressora {


    //dados que serão armazenados
    public int tipo;
    public String modelo;
    public String conexao;
    public int parametro;

    //boolean = true or false, mudado depois
    public boolean conexaoAberta = false;

    public static final String ASSINATURA_QRCODE_CANCELAMENTO =
            "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

    public boolean isConexaoAberta() {
        return conexaoAberta;
    }


    //OPÇÃO 1 - ARMAZENA OS DADOS
    public void configurarConexao(int tipo, String modelo, String conexao, int parametro) {
        this.tipo = tipo;
        this.modelo = modelo;
        this.conexao = conexao;
        this.parametro = parametro;
        System.out.println("Conexão configurada. Use a opção 2 para abrir. ");
    }


    //OPÇÃO 2 - ABRE A CONEXAO
    public void abrirConexao() {
        if (modelo == null || conexao == null) {
            System.out.println("Configure a conexão primeiro (opção 1).");
            return;
        }
        try {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexão. Código: " + retorno);
            }
        } catch (Throwable e) {


            // Throwable pega tanto o erro de "DLL não encontrada" na primeira tentativa quanto
            // o erro que o Java dá nas tentativas seguintes, depois que a DLL já falhou uma vez.


            System.out.println("Não foi possível carregar a DLL da impressora: " + e.getMessage());
        }
    }

    //OPÇÃO 3 - FECHA CONEXAO
    public void fecharConexao() {
        if (!conexaoAberta) {
            System.out.println("A conexão já está fechada.");
            return;
        }
        try {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada com sucesso!");
            } else {
                System.out.println("Erro ao fechar conexão. Código: " + retorno);
            }
        } catch (Throwable e) {
            System.out.println("Erro ao tentar fechar a conexão: " + e.getMessage());
        }
    }

    //OPÇÃO 4 - IMPRIME TEXTO
    public void imprimirTexto(String dados, int posicao, int estilo, int tamanho) {
        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(dados, posicao, estilo, tamanho);
            // Para esse comando o retorno de sucesso é a quantidade de dados
            // enviados; só é erro se vier um número negativo.
            if (retorno >= 0) {
                System.out.println("Texto enviado para impressão! (" + retorno + " bytes)");
            } else {
                System.out.println("Erro ao imprimir texto. Código: " + retorno);
            }
        } catch (Throwable e) {
            System.out.println("Erro ao imprimir texto: " + e.getMessage());
        }
    }

    // OPÇÃO 5 - IMPRIME QR CODE
    public void imprimirQRCode(String dados, int tamanho, int nivelCorrecao) {
        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, tamanho, nivelCorrecao);
            if (retorno == 0) {
                System.out.println("QR Code impresso com sucesso!");
            } else {
                System.out.println("Erro ao imprimir QR Code. Código: " + retorno);
            }
        } catch (Throwable e) {
            System.out.println("Erro ao imprimir QR Code: " + e.getMessage());
        }
    }

    //OPÇÃO 6 - IMPRIME CÓDIGO DE BARRAS
    public void imprimirCodigoBarras(int tipo, String dados, int altura, int largura, int HRI) {
        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(tipo, dados, altura, largura, HRI);
            if (retorno == 0) {
                System.out.println("Código de barras impresso com sucesso!");
            } else {
                System.out.println("Erro ao imprimir código de barras. Código: " + retorno);
            }
        } catch (Throwable e) {
            System.out.println("Erro ao imprimir código de barras: " + e.getMessage());
        }
    }

    // OPÇÃO 7 - IMPRIME XML DE VENDA (SAT)
    public void imprimirXMLSAT(String caminhoArquivo) {
        String conteudo;
        try {
            conteudo = Files.readString(Paths.get(caminhoArquivo));
        } catch (IOException e) {
            System.out.println("Arquivo XML não encontrado: " + caminhoArquivo);
            return;
        }
        try {
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(conteudo, 0);
            if (retorno == 0) {
                System.out.println("Cupom SAT impresso com sucesso!");
            } else {
                System.out.println("Erro ao imprimir cupom SAT. Código: " + retorno);
            }
        } catch (Throwable e) {
            System.out.println("Erro ao imprimir cupom SAT: " + e.getMessage());
        }
    }

    //OPÇÃO 8 - IMPRIME XML DE CANCELAMENTO (SAT)
    public void imprimirXMLCancelamentoSAT(String caminhoArquivo) {
        String conteudo;
        try {
            conteudo = Files.readString(Paths.get(caminhoArquivo));
        } catch (IOException e) {
            System.out.println("Arquivo XML não encontrado: " + caminhoArquivo);
            return;
        }
        try {
            int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(
                    conteudo, ASSINATURA_QRCODE_CANCELAMENTO, 0);
            if (retorno == 0) {
                System.out.println("Cupom de cancelamento impresso com sucesso!");
            } else {
                System.out.println("Erro ao imprimir cancelamento. Código: " + retorno);
            }
        } catch (Throwable e) {
            System.out.println("Erro ao imprimir cancelamento: " + e.getMessage());
        }
    }

    // OPÇÃO 9 - ABRE A GAVETA
    public void abrirGaveta(int pino, int ti, int tf) {
        try {
            int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(pino, ti, tf);
            if (retorno == 0) {
                System.out.println("Gaveta aberta!");
            } else {
                System.out.println("Erro ao abrir gaveta. Código: " + retorno);
            }
        } catch (Throwable e) {
            System.out.println("Erro ao abrir gaveta: " + e.getMessage());
        }
    }

    // OPÇÃO 10 - bipzão brabissimo
    public void sinalSonoro(int qtd, int tempoInicio, int tempoFim) {
        try {
            int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(qtd, tempoInicio, tempoFim);
            if (retorno == 0) {
                System.out.println("Bip!");
            } else {
                System.out.println("Erro no sinal sonoro. Código: " + retorno);
            }
        } catch (Throwable e) {
            System.out.println("Erro no sinal sonoro: " + e.getMessage());
        }
    }
}
