package models.processos;

import models.processos.comandos.Comando;
import java.util.ArrayList;
import java.util.List;

public class SegmentoTexto {
    // Primeira posicao = nome do teste, então o tamanho máximo do array será 22 para suportar o nome do processo + comandos
    public static final int TAMANHO_MAXIMO = 22;

    private final List<String> instrucoes;

    public SegmentoTexto() {
        this.instrucoes = new ArrayList<>(TAMANHO_MAXIMO);
    }

    public void adicionaInstrucao(String instrucao) {
        if (this.instrucoes.size() >= TAMANHO_MAXIMO) {
            throw new IllegalStateException("Segmento de texto cheio (max " + TAMANHO_MAXIMO + ")");
        }
        this.instrucoes.add(instrucao);
    }

    public String pegaInstrucaoAuxiliar() {
        String nomeProcesso = this.instrucoes.get(0);
        this.instrucoes.remove(0);
        return nomeProcesso;
    }

    public ContadorDoPrograma criaContadorDoPrograma() {
        ContadorDoPrograma contador = new ContadorDoPrograma();
        for (String instrucao : this.instrucoes) {
            if (instrucao == null) continue;
            try {
                contador.adicionaComando(instrucao);
            } catch (Exception e) {
                throw new IllegalArgumentException("Erro ao transformar instrução em Comando: " + instrucao, e);
            }
        }
        return contador;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SegmentoTexto:\n");
        for (int i = 0; i < TAMANHO_MAXIMO; i++) {
            String instr = (i < instrucoes.size()) ? instrucoes.get(i) : null;
            sb.append(String.format("[%02d] %s\n", i, instr == null ? "(vazio)" : instr));
        }
        return sb.toString();
    }
}
