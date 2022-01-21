import java.util.ArrayList;
import java.util.List;

public class Resultados {
  private List<Aposta> apostas = new ArrayList<>();

  public Resultados(Aposta aposta) {
    this.apostas.add(aposta);
  }
}
