# 🖨️ Sistema de Controle de Impressora Elgin
### *(ImpressoraTech Elgin)*
---

## 📘 Sobre o Projeto

Este projeto foi desenvolvido por alunos para se comunicar com uma impressora térmica **Elgin i9** usando a biblioteca `E1_Impressora01.dll`. O sistema tem login e um menu com 10 operações de impressão (texto, QR Code, código de barras, cupom fiscal e cancelamento via XML, gaveta e sinal sonoro), com corte automático do papel depois de cada impressão. Faz parte da disciplina de **Programação Orientada a Objetos**.

---

## 🧰 Tecnologias Utilizadas

- **Java 21**
- **IntelliJ IDEA**
- **JNA 5.15.0** (comunicação com a DLL)
- **DLL E1_Impressora01** (SDK da Elgin)
- **Impressora Elgin i9**

---

## 📁 Estrutura do Projeto

```
/src
   Main.java          → Cria os objetos, faz o login e mostra o menu
   Usuario.java        → Guarda login/senha e confere se estão certos
   Impressora.java      → Guarda os dados da conexão e tem um método para cada operação,
                          incluindo o corte automático do papel depois de imprimir
   ImpressoraDLL.java    → Ligação do Java com a DLL (fornecido pelo professor, não alterado)
/Bibliotecas E1 Impressora → DLLs da Elgin (x64 e x86)
/libs Java                → jna-5.15.0.jar
```

---

## ⚙️ Instalação e Execução

### 1. Baixar o projeto
1. Acesse o repositório no GitHub.
2. Clique em **Code → Download ZIP**.
3. Extraia o arquivo.

### 2. Abrir no IntelliJ IDEA
1. **File → Open** e escolha a pasta extraída.
2. Em **File → Project Structure → Libraries**, confirme que o `jna-5.15.0.jar` está adicionado.
3. Conecte e ligue a impressora.
4. Em **Run → Edit Configurations**, confira se o *Working directory* é a pasta principal do projeto (onde está a pasta `Bibliotecas E1 Impressora`).
5. Abra `Main.java` e clique em ▶️ **Run**.

---

## 🔐 Login

- **Usuário:** `aluno`
- **Senha:** `1234`
- Máximo de **3 tentativas**. A cada erro, o programa mostra quantas tentativas restam. Na 3ª tentativa errada, o programa é encerrado.

---

## 📋 Funcionalidades do Menu

| Opção | O que faz |
|---|---|
| 1 | Configura a conexão com a impressora. Já usa direto os valores padrão (USB, modelo i9), sem perguntar nada |
| 2 | Abre a conexão com a impressora |
| 3 | Fecha a conexão com a impressora |
| 4 | Pede um texto e imprime (centralizado, invertido, tamanho normal) |
| 5 | Pede o conteúdo e imprime um QR Code |
| 6 | Pede os dados e imprime um código de barras (padrão CODE 128) |
| 7 | Pede o caminho de um arquivo XML e imprime o cupom de venda (SAT) |
| 8 | Pede o caminho de um arquivo XML e imprime o cupom de cancelamento (SAT) |
| 9 | Abre a gaveta de dinheiro, direto com valores padrão, sem perguntar nada |
| 10 | Pede a quantidade e emite o sinal sonoro (bip) |
| 0 | Sai do programa |

Depois de cada impressão (opções 4 a 8), o papel é cortado automaticamente — não é uma opção separada do menu, acontece sozinho logo após a impressão dar certo, como em uma máquina de recibo de verdade.

Se a conexão não estiver aberta, as opções 4 a 10 mostram o aviso "Conexão fechada. Abra a conexão antes." e voltam ao menu sem perguntar mais nada.

---

## 🧪 Testes Realizados

- Login com senha errada 3 vezes seguidas (o programa encerra corretamente).
- Login correto na 1ª, 2ª e 3ª tentativa.
- Entrada de texto onde era esperado um número no menu (o programa pede de novo, não trava).
- Tentar imprimir, abrir a gaveta ou bipar sem ter aberto a conexão (o programa bloqueia e avisa).
- Tentar abrir a conexão sem a impressora ligada (o programa avisa que não conseguiu carregar a DLL, sem travar).
- Configurar e abrir a conexão, imprimir texto, QR Code, código de barras, XML de venda (`XMLSAT.xml`) e XML de cancelamento (`CANC_SAT.xml`) — com o corte automático acontecendo depois de cada uma dessas 5 impressões.
- Abrir a gaveta e tocar o bip (confirmado que o papel *não* é cortado nessas duas opções, só nas de impressão).
- Fechar a conexão e tentar fechar de novo (avisa que já está fechada).
- *(Preencham aqui os testes feitos na impressora física de verdade em sala.)*

---

## 📚 Documentação Adicional

- [Documentação oficial dos comandos da impressora (Elgin Developer Community)](https://elgindevelopercommunity.github.io/group___m1.html)

---

## 👥 Autores
*(em ordem alfabética)*

- João Marcos
- Júlio César
- Laylla Ricci