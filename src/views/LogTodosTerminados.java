package views;

import controllers.TabelaDeProcessos;
import models.BCP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LogTodosTerminados implements LogStrategy {
    private TabelaDeProcessos tabela;
    private int quantum;
    public LogTodosTerminados(TabelaDeProcessos tabela, int quantum) {
        this.tabela = tabela;
        this.quantum = quantum;
    }

    private int getQuantidadeDeTrocas(ArrayList<TabelaDeProcessos.TrocaDeProcessos> trocas, BCP processo) {
        int quantidade = 0;
        for(TabelaDeProcessos.TrocaDeProcessos troca: trocas) {
            if(troca.origem == processo) {
                quantidade++;
            }
        }
        return quantidade;
    }

    private double calculaMedia(HashMap<BCP, Integer> quantidadeTrocas) {
        double media = 0;
        for(BCP processo: quantidadeTrocas.keySet()) {
            media += quantidadeTrocas.get(processo);
        }
        return media / quantidadeTrocas.size();
    }

    private HashSet<BCP> pegaProcessosTerminados(ArrayList<TabelaDeProcessos.TrocaDeProcessos> trocas) {
        HashSet<BCP> processos = new HashSet<>();
        for(TabelaDeProcessos.TrocaDeProcessos troca: trocas) {
            processos.add(troca.origem);
            processos.add(troca.destino);
        }

        return processos;
    }

    private double pegaMediaDeTrocas() {
        HashMap<BCP, Integer> quantidadeTrocas = new HashMap<BCP, Integer>();
        ArrayList<TabelaDeProcessos.TrocaDeProcessos> trocasDeProcessos = this.tabela.getTrocasDeProcessos();
        HashSet<BCP> processos = this.pegaProcessosTerminados(trocasDeProcessos);

        for(BCP processo: processos) {
            int trocas = this.getQuantidadeDeTrocas(trocasDeProcessos, processo);
            quantidadeTrocas.put(processo, trocas);
        }
        return this.calculaMedia(quantidadeTrocas);
    }

    private double pegaMediaDeIntrucoes() {
        double media = 0;
        ArrayList<Integer> instrucoes = this.tabela.getInstrucoesExecutadas();
        for(int instrucao: instrucoes) {
            media += instrucao;
        }
        return media / instrucoes.size();
    }

    public String log() {
        double mediaDeTrocas = this.pegaMediaDeTrocas();
        double mediaDeIntrucoes = this.pegaMediaDeIntrucoes();
        int quantum = this.quantum;
        String logString = "MEDIA DE TROCAS " + String.format("%.2f", mediaDeTrocas) + "\n";
        logString += "MEDIA DE INTRUCOES " + String.format("%.2f", mediaDeIntrucoes) + "\n";
        logString += "QUANTUM " + quantum;

        return logString;
    }
}
