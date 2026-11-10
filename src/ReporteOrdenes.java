import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Servicio de aplicación: arreglos + ArrayList de manera complementaria. */
public final class ReporteOrdenes {
    private final OrdenRepositorio repositorio;

    public ReporteOrdenes(OrdenRepositorio repositorio) {
        this.repositorio = Objects.requireNonNull(repositorio, "Repositorio obligatorio");
    }

    public ArrayList<OrdenServicio> obtenerOrdenes() throws SQLException {
        return new ArrayList<>(repositorio.listarOrdenes());
    }

    /** Arreglo de estados para las categorías; ArrayList para órdenes dinámicas. */
    public String resumenEstados() throws SQLException {
        EstadoOrden[] estados = EstadoOrden.values();
        int[] cantidades = new int[estados.length];
        ArrayList<OrdenServicio> ordenes = obtenerOrdenes();
        for (OrdenServicio orden : ordenes) {
            for (int i = 0; i < estados.length; i++) {
                if (orden.getEstado() == estados[i]) {
                    cantidades[i]++;
                    break;
                }
            }
        }
        StringBuilder salida = new StringBuilder("REPORTE DE ORDENES\n");
        for (int i = 0; i < estados.length; i++) {
            salida.append(estados[i]).append(": ").append(cantidades[i]).append('\n');
        }
        salida.append("TOTAL: ").append(ordenes.size());
        return salida.toString();
    }
}
