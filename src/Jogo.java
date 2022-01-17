import java.time.LocalDateTime;

public class Jogo {
  private String nomeTimeA;
  private String nomeTimeB;
  private LocalDateTime data;
  private double valorVitoriaA;
  private double valorVitoriaB;
  private double valorEmpate;
  private int apostasA;
  private int apostasB;
  private double totalApostado;

  public Jogo(  String nomeTimeA, String nomeTimeB, LocalDateTime data, double valorVitoriaA, double valorVitoriaB,
      double valorEmpate, int apostasA, int apostasB, double totalApostado) {
    this.nomeTimeA = nomeTimeA;
    this.nomeTimeB = nomeTimeB;
    this.data = data;
    this.valorVitoriaA = valorVitoriaA;
    this.valorVitoriaB = valorVitoriaB;
    this.valorEmpate = valorEmpate;
    this.apostasA = apostasA;
    this.apostasB = apostasB;
    this.totalApostado = totalApostado;
  }

  public String getNomeTimeA() {
    return nomeTimeA;
  }

  public void setNomeTimeA(String nomeTimeA) {
    this.nomeTimeA = nomeTimeA;
  }

  public String getNomeTimeB() {
    return nomeTimeB;
  }

  public void setNomeTimeB(String nomeTimeB) {
    this.nomeTimeB = nomeTimeB;
  }

  public LocalDateTime getData() {
    return data;
  }

  public void setData(LocalDateTime data) {
    this.data = data;
  }

  public double getValorVitoriaA() {
    return valorVitoriaA;
  }

  public void setValorVitoriaA(double valorVitoriaA) {
    this.valorVitoriaA = valorVitoriaA;
  }

  public double getValorVitoriaB() {
    return valorVitoriaB;
  }

  public void setValorVitoriaB(double valorVitoriaB) {
    this.valorVitoriaB = valorVitoriaB;
  }

  public double getValorEmpate() {
    return valorEmpate;
  }

  public void setValorEmpate(double valorEmpate) {
    this.valorEmpate = valorEmpate;
  }

  public int getApostasA() {
    return apostasA;
  }

  public void setApostasA(int apostasA) {
    this.apostasA = apostasA;
  }

  public int getApostasB() {
    return apostasB;
  }

  public void setApostasB(int apostasB) {
    this.apostasB = apostasB;
  }

  public double getTotalApostado() {
    return totalApostado;
  }

  public void setTotalApostado(double totalApostado) {
    this.totalApostado = totalApostado;
  }

}