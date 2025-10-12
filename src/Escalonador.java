import controllers.InputLoader;
import controllers.TabelaDeProcessos;
import models.BCP;
import models.ProcessosBloqueados;
import models.ProcessosProntos;
import models.processos.ContadorDoPrograma;
import models.processos.SegmentoTexto;

import java.io.IOException;
import java.util.ArrayList;

public class Escalonador {
    public static void main(String[] args) throws IOException {
        InputLoader loader = new InputLoader();
        TabelaDeProcessos tabela = loader.constroiTabelaDeProcessos();
        ProcessosProntos listaProntos = tabela.getListaProntos();
        ProcessosBloqueados bloqueados = tabela.getListaBloqueados();

        System.out.println("Prontos: " + listaProntos);
        System.out.println("Bloqueados: " + bloqueados);

        BCP processoAtual = listaProntos.verProximoProcesso();
        System.out.println(processoAtual);
        tabela.bloqueiaProcesso(processoAtual);
        processoAtual = listaProntos.verProximoProcesso();
        System.out.println(processoAtual);
        tabela.bloqueiaProcesso(processoAtual);

        tabela.printaTrocasDeProcessos();
        System.out.println("Prontos: " + listaProntos);
        System.out.println("Bloqueados: " + bloqueados);

        while(bloqueados.tamanhoFila() > 0) {
            tabela.desbloqueiaProcessos();
            System.out.println("Bloqueados: " + bloqueados);
        }

        System.out.println("Prontos: " + listaProntos);
    }
}