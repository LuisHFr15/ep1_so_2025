package models.processos;

public class Registrador {
    public char reg;
    private int valor;

    public Registrador(char reg) {
        this.reg = reg;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return reg + "=" + valor;
    }
}
