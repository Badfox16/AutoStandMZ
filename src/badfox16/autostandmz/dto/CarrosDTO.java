package badfox16.autostandmz.dto;

public class CarrosDTO {
    //atributos
    private int id_carro, preco;
    private String marca, modelo, matricula, status;

    public String getStatus() {
        return status;
    }
    public void setStatus(String Status) {
        this.status = Status;
    }
    //metodos acessores 
    public int getId_carro() {
        return id_carro;
    }
    public void setId_carro(int id_carro) {
        this.id_carro = id_carro;
    }
    public int getPreco() {
        return preco;
    }
    public void setPreco(int preco) {
        this.preco = preco;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
