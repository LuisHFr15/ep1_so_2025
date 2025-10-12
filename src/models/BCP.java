package models;

import models.processos.ContadorDoPrograma;
import models.processos.Estado;
import models.processos.Registrador;
import models.processos.SegmentoTexto;
import models.processos.comandos.*;

import java.util.ArrayList;

public class BCP {
    public String nomeProcesso;
    private ContadorDoPrograma contador;
    public Estado estado;
    private ArrayList<Registrador> registradores;
    public SegmentoTexto codigo;

    public BCP(String nomeProcesso, SegmentoTexto codigo) {
        this.nomeProcesso = nomeProcesso;
        this.estado = Estado.PRONTO;
        this.codigo = codigo;
        this.contador = codigo.criaContadorDoPrograma();
        this.registradores = new ArrayList<Registrador>();
        this.registradores.add(new Registrador('X'));
        this.registradores.add(new Registrador('Y'));
    }

    public ContadorDoPrograma getContador() {
        return this.contador;
    }

    public SegmentoTexto getCodigo() {
        return this.codigo;
    }

    public boolean temComandosParaExecutar() {
        return this.contador.temComandosParaExecutar();
    }

    public int comandosRestantes() {
        return this.contador.comandosRestantes();
    }

    public void bloqueiaProcesso() {
        this.estado = Estado.BLOQUEADO;
    }

    public void desbloqueiaProcesso() {
        this.estado = Estado.PRONTO;
    }

    public void alteraParaExecutando() {
        this.estado = Estado.EXECUTANDO;
    }

    public Comando executaProcesso() {
        Comando com = this.contador.executaComando();
        if(com.tipo == TipoComando.X) {
            Registrador x = this.pegaRegistrador('X');
            x.setValor(com.valor);
        }
        else if(com.tipo == TipoComando.Y) {
            Registrador y = this.pegaRegistrador('Y');
            y.setValor(com.valor);
        }
        return com;
    }

    private Registrador pegaRegistrador(char reg) {
        for(Registrador r : this.registradores) {
            if(r.reg == reg) {
                return r;
            }
        }
        throw new IllegalArgumentException("Registrador não encontrado: " + reg);
    }

    @Override
    public String toString() {
        return "BCP{" +
                "nomeProcesso='" + nomeProcesso + '\'' +
                ", estado=" + estado +
                '}';
    }
}
