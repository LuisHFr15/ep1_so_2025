package controllers;

import models.processos.SegmentoTexto;
import views.LogStrategy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Stream;

public class FilesIO {
    private static String logFile = "log";
    private static LinkedList<String> logs = new LinkedList<String>();

    public static void setQuantum(String quantum) {
        logFile += quantum + ".txt";
    }

    private static SegmentoTexto leArquivo(Path filePath) throws IOException {
        SegmentoTexto codigoProcesso = new SegmentoTexto();
        try(BufferedReader leitor = Files.newBufferedReader(filePath)) {
            leitor.lines().forEach(line -> {
                codigoProcesso.adicionaInstrucao(line);
            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            return codigoProcesso;
        }
    }

    public static HashMap<String, SegmentoTexto> carregaDadosDosProcessos() throws IOException {
        Path path = Paths.get("programas");
        HashMap<String, SegmentoTexto> processos = new HashMap<String, SegmentoTexto>();
        try(Stream<Path> paths = Files.list(path)) {
            paths.forEach(filePath -> {
                try {
                    SegmentoTexto processo = leArquivo(filePath);
                    processos.put(filePath.getFileName().toString(), processo);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            return processos;
        }
    }

    public static void adicionaLog(LogStrategy strategy) throws IOException{
        logs.add(strategy.log());
    }

    public static void escreveLog() throws IOException {
        System.out.println("Escrevendo log");
        BufferedWriter escritor = new BufferedWriter(new FileWriter(logFile));
        for(String log : logs) {
            escritor.write(log);
            escritor.newLine();
        }
        escritor.close();
    }
}
