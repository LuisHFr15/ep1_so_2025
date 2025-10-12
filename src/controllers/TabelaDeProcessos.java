package controllers;

import models.*;
import models.processos.*;
import models.processos.comandos.*;

import java.util.ArrayList;
import java.util.Map;

public class TabelaDeProcessos {
    private static class TrocaDeProcessos {
        public BCP origem;
        public BCP destino;
        public int razao;

        public TrocaDeProcessos(BCP origem, BCP destino, int razao) {
            this.origem = origem;
            this.destino = destino;
            this.razao = razao;
        }
    }

    private ArrayList<BCP> listaProcessos;
    private ProcessosProntos listaProntos;
    private ProcessosBloqueados listaBloqueados;
    // origem: (destino: motivo)
    // motivo = 0 -> quantum acabou
    // motivo = 1 -> bloqueio por E/S
    // motivo = 2 -> finalizado o processo
    private ArrayList<TrocaDeProcessos> trocasDeProcessos;
    private ArrayList<Integer> instrucoesExecutadas;

    public TabelaDeProcessos() {
        this.listaProcessos = new ArrayList<BCP>();
        this.listaProntos = new ProcessosProntos();
        this.listaBloqueados = new ProcessosBloqueados();
        this.trocasDeProcessos = new ArrayList<TrocaDeProcessos>();
        this.instrucoesExecutadas = new ArrayList<Integer>();
    }

    public ArrayList<BCP> getListaProcessos() {
        return this.listaProcessos;
    }

    public ProcessosBloqueados getListaBloqueados() {
        return listaBloqueados;
    }

    public ProcessosProntos getListaProntos() {
        return listaProntos;
    }

    public void adicionaProcesso(BCP processo) {
        this.listaProcessos.add(processo);
        this.listaProntos.adicionaProcesso(processo);
    }

    public BCP pegaProximoProcessoDaFila() {
        return this.listaProntos.verProximoProcesso();
    }

    public void bloqueiaProcesso(BCP processo) {
        this.listaProntos.removeProcesso();
        BCP proximoProcesso = this.pegaProximoProcessoDaFila();

        processo.bloqueiaProcesso();
        this.listaBloqueados.adicionaProcesso(processo);
        this.trocasDeProcessos.add(new TrocaDeProcessos(processo, proximoProcesso, 1));
    }

    private void reativaProcesso(BCP processo) {
        processo.desbloqueiaProcesso();
        this.listaProntos.adicionaProcesso(processo);
        this.listaBloqueados.liberaProcesso(processo);
    }

    private ArrayList<BCP> capturaProcessosParaDesbloquear() {
        ArrayList<BCP> processosParaDesbloquear = new ArrayList<BCP>();
        Map<BCP, Integer> bloqueados = this.listaBloqueados.getListaProcessos();
        for (BCP processo : bloqueados.keySet()) {
            int tempoEspera = bloqueados.get(processo);
            System.out.println(processo + " " + tempoEspera);
            if(tempoEspera <= 0) {
                processosParaDesbloquear.add(processo);
            }
        }
        return processosParaDesbloquear;
    }

    public boolean desbloqueiaProcessos() {
        if(this.listaBloqueados.tamanhoFila() == 0) {
            return false;
        }
        this.listaBloqueados.reduzirTempoEspera();
        ArrayList<BCP> processosParaDesbloquear = this.capturaProcessosParaDesbloquear();
        for (BCP processo : processosParaDesbloquear) {
            this.reativaProcesso(processo);
        }
        return true;
    }

    public boolean temProcessosParaExecutar() {
        return this.listaProntos.tamanhoFila() > 0;
    }

    public void alteraProcessoParaExecutando(BCP processo) {
        processo.alteraParaExecutando();
    }

    private void adicionaQuantidadeExecutada(int instrucoes) {
        this.instrucoesExecutadas.add(instrucoes);
    }

    public void trocaProcessos(int instrucoes, Comando com) {
        BCP processoAtual = this.listaProntos.removeProcesso();
        BCP proximoProcesso = this.listaProntos.verProximoProcesso();
        int razao = 0;
        if(com.tipo == TipoComando.SAIDA) {
            razao = 2;
        }
        this.trocasDeProcessos.add(new TrocaDeProcessos(processoAtual, proximoProcesso, razao));
        this.adicionaQuantidadeExecutada(instrucoes);
    }

    private void completaProcesso(BCP processo) {
        this.listaProcessos.remove(processo);
    }

    public void printaTrocasDeProcessos() {
        int index = 0;
        for(TrocaDeProcessos troca: this.trocasDeProcessos) {
            String razao;
            if(troca.razao == 2) {
                razao = "Finalizado";
            }
            else if(troca.razao == 1) {
                razao = "Bloqueado";
            }
            else {
                razao = "Quantum";
            }
            System.out.println("Troca " + index + " " + troca.origem + " -> " + troca.destino + ": " + troca.razao);
            index++;
        }
    }
}
