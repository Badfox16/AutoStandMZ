package badfox16.autostandmz.dto;

import java.sql.Date;

public class AluguerDTO {
    int codigo, id_carro, id_cliente;
    double taxa;
    Date data_alugar, data_devolver;
    
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public int getId_carro() {
        return id_carro;
    }
    public void setId_carro(int id_carro) {
        this.id_carro = id_carro;
    }
    public int getId_cliente() {
        return id_cliente;
    }
    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }
    public double getTaxa() {
        return taxa;
    }
    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }
    public Date getData_alugar() {
        return data_alugar;
    }
    public void setData_alugar(Date data_alugar) {
        this.data_alugar = data_alugar;
    }
    public Date getData_devolver() {
        return data_devolver;
    }
    public void setData_devolver(Date data_devolver) {
        this.data_devolver = data_devolver;
    }
}
