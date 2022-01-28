import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Jogo {
  private String nomeTimeA;
  private String nomeTimeB;
  private LocalDateTime data;
  private double valorVitoriaA;
  private double valorVitoriaB;
  private double valorEmpate;
  private int apostasA;
  private int apostasB;
  private int apostasEmpate;
  private double totalApostadoA;
  private double totalApostadoB;

  public Jogo(String nomeTimeA, String nomeTimeB, LocalDateTime data, double valorVitoriaA,
      double valorVitoriaB, double valorEmpate, int apostasA, int apostasB, int apostasEmpate, double totalApostadoA, double totalApostadoB) {
    this.nomeTimeA = nomeTimeA;
    this.nomeTimeB = nomeTimeB;
    this.data = data;
    this.valorVitoriaA = valorVitoriaA;
    this.valorVitoriaB = valorVitoriaB;
    this.valorEmpate = valorEmpate;
    this.apostasA = apostasA;
    this.apostasB = apostasB;
    this.apostasEmpate = apostasEmpate;
    this.totalApostadoA = totalApostadoA;
    this.totalApostadoB = totalApostadoB;
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

  public int getApostasEmpate() {
    return apostasEmpate;
  }

  public void setApostasEmpate(int apostasEmpate) {
    this.apostasEmpate = apostasEmpate;
  }

  public double getTotalApostadoA() {
    return totalApostadoA;
  }

  public void setTotalApostadoA(double totalApostadoA) {
    this.totalApostadoA = totalApostadoA;
  }

  public double getTotalApostadoB() {
    return totalApostadoB;
  }

  public void setTotalApostadoB(double totalApostadoB) {
    this.totalApostadoB = totalApostadoB;
  }

  @Override
  public String toString() {
    return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
        nomeTimeA, nomeTimeB, data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
        valorVitoriaA, valorVitoriaB, valorEmpate, apostasA, apostasB, apostasEmpate, totalApostadoA, totalApostadoB);
  }

}