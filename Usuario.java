public class Usuario {

    public String login;
    public String senha;

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public boolean validarLogin(String loginDigitado, String senhaDigitada) {
        return login.equals(loginDigitado) && senha.equals(senhaDigitada);

        //Comparou o login e senha digitados com o login e senha originais
    }
}

//login também

