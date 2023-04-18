package badfox16.autostandmz.dto;

public class ClientesDTO {
    private int id_cliente;
    private String nome, BI, telefone;
    
    public int getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getBI() {
        return BI;
    }
    public void setBI(String bI) {
        BI = bI;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    

}
