package models.processos.comandos;

public class Comando {
    public TipoComando tipo;
    public int valor;

    public Comando(String comando) {
        if(comando.contains("=")) {
            String reg = comando.split("=")[0];
            if(reg.equals("X")) {
                this.tipo = TipoComando.X;
            } else if(reg.equals("Y")) {
                this.tipo = TipoComando.Y;
            } else {
                throw new IllegalArgumentException("Comando inválido: " + comando);
            }
            this.valor = Integer.parseInt(comando.split("=")[1]);
        }
        else if(comando.equals("E/S")) {
            this.tipo = TipoComando.E_S;
            this.valor = 0;
        }
        else if(comando.equals("COM")) {
            this.tipo = TipoComando.COMPUTA;
            this.valor = 0;
        }
        else if (comando.equals("SAIDA")) {
            this.tipo = TipoComando.SAIDA;
            this.valor = 0;
        }
        else {
            throw new IllegalArgumentException("Comando inválido: " + comando);
        }
    }
}
