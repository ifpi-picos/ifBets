public class App {
    public static void main(String[] args) throws Exception {
        Database.carregarClientes();
        Database.carregarJogos();
        Database.carregarApostas();
        //Testes.run();
        Interface interfaceUsuario = new Interface();
        Administrador.run(interfaceUsuario);
    }
}
