public class App {
    public static void main(String[] args) throws Exception {
        //Testes.run();
        Database.carregarClientes();
        Database.carregarJogos();
        Interface.run();
    }
}
