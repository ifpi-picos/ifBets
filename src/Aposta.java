import java.time.LocalDateTime;

public class Aposta {
  private LocalDateTime data;
  private Jogo jogo;
  private Cliente cliente;
  private String timeEscolhido;
  private Number valorApostado;

  public Aposta(LocalDateTime data, Jogo jogo, Cliente cliente, String timeEscolhido, Number valorApostado) {
    this.data = data;
    this.jogo = jogo;
    this.cliente = cliente;
    this.timeEscolhido = timeEscolhido;
    this.valorApostado = valorApostado;
  }

  public LocalDateTime getData() {
    return data;
  }

  public void setData(LocalDateTime data) {
    this.data = data;
  }

  public Jogo getJogo() {
    return jogo;
  }

  public void setJogo(Jogo jogo) {
    this.jogo = jogo;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public String getEscolha() {
    return escolha;
  }

  public void setEscolha(String escolha) {
    this.escolha = escolha;
  }

}
