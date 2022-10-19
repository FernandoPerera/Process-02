package utils;

import java.io.IOException;
import java.util.ArrayList;
public class Metodos {
    public void lanzarProceso(String cmd, String c, String navegador, int numeroProcesos) {

        ArrayList<Process> listaProcesos = new ArrayList<Process>();
        ArrayList<String> listaInicioFin = new ArrayList<String>();
        ArrayList<Long> listaMs = new ArrayList<Long>();

        int contador = 0;

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(cmd, c, navegador);

        try {

            while (contador < numeroProcesos) {
                long inicio = System.currentTimeMillis();
                Process p = processBuilder.start();

                listaMs.add(inicio);
                listaProcesos.add(p);
                
                String output = "\t------------------------------------------------\n";
                output += "\tInicio del proceso " + (contador + 1) + " : " + inicio;

                long limite = (long) (listaMs.get(contador) + 0.5);

                while (!listaProcesos.isEmpty()) {

                    if ( listaMs.get(contador) == limite ) {

                        for (int i = 0; i < listaProcesos.size(); i++) {

                            if (listaProcesos.get(i).isAlive()) {
                                listaProcesos.remove(i).destroy();
                            }
                            long fin = System.currentTimeMillis();

                            output += "\n\tFinal del proceso " + (contador + 1) + " : " + fin;
                            output += "\n\t------------------------------------------------\n";
                            listaInicioFin.add(output);

                            System.out.println(listaInicioFin.get(contador));
                        }
                    }
                }
                contador++;
                }
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

