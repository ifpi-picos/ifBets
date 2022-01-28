import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Aposta {
  private LocalDateTime data;
  private Jogo jogo;
  private Cliente cliente;
  private String timeEscolhido;
  private Double valorApostado;

  public Aposta(LocalDateTime data, Jogo jogo, Cliente cliente, String timeEscolhido, Double valorApostado) {
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

  public String getTimeEscolhido() {
    return timeEscolhido;
  }

  public void setTimeEscolhido(String timeEscolhido) {
    this.timeEscolhido = timeEscolhido;
  }

  public Double getValorApostado() {
    return valorApostado;
  }

  public void setValorApostado(Double valorApostado) {
    this.valorApostado = valorApostado;
  }

  @Override
  public String toString() {
    return String.format("%s, %s, %s, %s, %s", data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), jogo.getNomeTimeA()+":"+jogo.getNomeTimeB(), cliente.getCpf(), timeEscolhido, valorApostado);
  }
}
