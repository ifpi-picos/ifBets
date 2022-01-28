import java.time.LocalDate;

public class Cliente {
    private String cpf;
    private String nome;
    private String email;
    private LocalDate dataDeNascimento;
    private Endereco endereco;

    public Cliente(String cpf, String nome, String email, LocalDate dataDeNascimento, Endereco endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.dataDeNascimento = dataDeNascimento;
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s", cpf, nome, email, dataDeNascimento, endereco.toString());
    }
}
