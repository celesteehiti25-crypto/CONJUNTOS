import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/** Caso académico: solicitudes de títulos con un ejemplar por título. */
public class BibliotecaConjuntos {
    static final class Libro {
        final int codigo;
        final String titulo;

        Libro(int codigo, String titulo) {
            if (codigo <= 0 || titulo == null || titulo.isBlank()) {
                throw new IllegalArgumentException("Código o título inválido");
            }
            this.codigo = codigo;
            this.titulo = titulo;
        }

        @Override
        public String toString() {
            return codigo + " - " + titulo;
        }
    }

    static final class Biblioteca {
        private final Map<Integer, Libro> catalogo = new HashMap<>();

        void registrar(Libro libro) {
            Objects.requireNonNull(libro, "El libro es obligatorio");
            if (catalogo.putIfAbsent(libro.codigo, libro) != null) {
                throw new IllegalArgumentException("Código duplicado: " + libro.codigo);
            }
        }

        Optional<Libro> buscarPorCodigo(int codigo) {
            return Optional.ofNullable(catalogo.get(codigo));
        }

        Set<Integer> codigos() {
            return new HashSet<>(catalogo.keySet());
        }

        Set<Integer> conjuntoValidado(Integer... codigos) {
            Objects.requireNonNull(codigos, "La entrada es obligatoria");
            Set<Integer> resultado = new HashSet<>();
            for (Integer codigo : codigos) {
                if (codigo == null || !catalogo.containsKey(codigo)) {
                    throw new IllegalArgumentException("Código no registrado: " + codigo);
                }
                resultado.add(codigo);
            }
            return resultado;
        }
    }

    // Cada operación trabaja con una copia para conservar las entradas.
    static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> resultado = new HashSet<>(a);
        resultado.addAll(b);
        return resultado;
    }

    static <T> Set<T> interseccion(Set<T> a, Set<T> b) {
        Set<T> resultado = new HashSet<>(a);
        resultado.retainAll(b);
        return resultado;
    }

    static <T> Set<T> diferencia(Set<T> a, Set<T> b) {
        Set<T> resultado = new HashSet<>(a);
        resultado.removeAll(b);
        return resultado;
    }

    static Biblioteca crearBiblioteca() {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.registrar(new Libro(101, "Programación en Java"));
        biblioteca.registrar(new Libro(102, "Estructuras de datos"));
        biblioteca.registrar(new Libro(103, "Bases de datos"));
        biblioteca.registrar(new Libro(104, "Matemáticas discretas"));
        biblioteca.registrar(new Libro(105, "Ingeniería de software"));
        biblioteca.registrar(new Libro(106, "Redes de computadoras"));
        biblioteca.registrar(new Libro(107, "Sistemas operativos"));
        biblioteca.registrar(new Libro(108, "Cálculo integral"));
        return biblioteca;
    }

    static String presentar(Object valor) {
        if (valor instanceof Set<?>) {
            // En esta demostración, las salidas de conjuntos contienen enteros.
            TreeSet<Integer> ordenados = new TreeSet<>();
            for (Object elemento : (Set<?>) valor) {
                ordenados.add((Integer) elemento);
            }
            return ordenados.toString();
        }
        return String.valueOf(valor);
    }

    static void mostrar(String etiqueta, Set<Integer> codigos) {
        System.out.println(etiqueta + ": " + presentar(codigos));
    }

    static void demostracion() {
        Biblioteca biblioteca = crearBiblioteca();
        Set<Integer> a = biblioteca.conjuntoValidado(101, 102, 103, 105);
        Set<Integer> b = biblioteca.conjuntoValidado(102, 103, 104, 106);
        Set<Integer> prestados = biblioteca.conjuntoValidado(102, 105, 107);
        Set<Integer> solicitudes = union(a, b);
        Set<Integer> disponibles = diferencia(biblioteca.codigos(), prestados);

        System.out.println("BIBLIOTECA ESCOLAR - OPERACIONES DE CONJUNTOS");
        mostrar("Grupo A", a);
        mostrar("Grupo B", b);
        mostrar("Unión A U B", solicitudes);
        mostrar("Intersección A y B", interseccion(a, b));
        mostrar("Diferencia A - B", diferencia(a, b));
        mostrar("Diferencia B - A", diferencia(b, a));
        mostrar("Disponibles I - P", disponibles);
        mostrar("Solicitudes que pueden atenderse", interseccion(solicitudes, disponibles));
        mostrar("Solicitudes sin disponibilidad", diferencia(solicitudes, disponibles));
        System.out.println("Búsqueda 103: " + biblioteca.buscarPorCodigo(103)
                .map(Libro::toString).orElse("No encontrado"));
        System.out.println("Búsqueda 999: " + biblioteca.buscarPorCodigo(999)
                .map(Libro::toString).orElse("No encontrado"));
    }

    private static int superadas = 0;

    static void comprobar(String id, String caso, Object esperado, Object obtenido) {
        if (!Objects.equals(esperado, obtenido)) {
            throw new AssertionError(id + " " + caso + ": esperado="
                    + presentar(esperado) + ", obtenido=" + presentar(obtenido));
        }
        superadas++;
        System.out.println(id + " | " + caso + " | " + presentar(esperado)
                + " | " + presentar(obtenido) + " | OK");
    }

    static String capturarError(Runnable accion) {
        try {
            accion.run();
            return "Sin error";
        } catch (IllegalArgumentException error) {
            return "IllegalArgumentException";
        }
    }

    static void pruebas() {
        superadas = 0;
        Biblioteca biblioteca = crearBiblioteca();
        Set<Integer> a = biblioteca.conjuntoValidado(101, 102, 103, 105);
        Set<Integer> b = biblioteca.conjuntoValidado(102, 103, 104, 106);
        Set<Integer> vacio = new HashSet<>();
        System.out.println("ID | Caso | Esperado | Obtenido | Estado");
        comprobar("P01", "Unión", Set.of(101, 102, 103, 104, 105, 106), union(a, b));
        comprobar("P02", "Intersección", Set.of(102, 103), interseccion(a, b));
        comprobar("P03", "Diferencia A - B", Set.of(101, 105), diferencia(a, b));
        comprobar("P04", "Diferencia B - A", Set.of(104, 106), diferencia(b, a));
        comprobar("P05", "Unión con vacío", a, union(a, vacio));
        comprobar("P06", "Intersección con vacío", vacio, interseccion(a, vacio));
        comprobar("P07", "A menos vacío", a, diferencia(a, vacio));
        comprobar("P08", "Vacío menos A", vacio, diferencia(vacio, a));
        comprobar("P09", "Intersección consigo mismo", a, interseccion(a, a));
        comprobar("P10", "Diferencia consigo mismo", vacio, diferencia(a, a));
        comprobar("P11", "Conjuntos disjuntos", vacio,
                interseccion(Set.of(101, 102), Set.of(107, 108)));
        comprobar("P12", "Eliminación de repetidos", Set.of(101, 102),
                biblioteca.conjuntoValidado(101, 101, 102));
        Set<Integer> copiaA = new HashSet<>(a);
        Set<Integer> copiaB = new HashSet<>(b);
        union(a, b);
        interseccion(a, b);
        diferencia(a, b);
        comprobar("P13", "Entradas conservadas", true, a.equals(copiaA) && b.equals(copiaB));
        comprobar("P14", "Unión conmutativa", union(a, b), union(b, a));
        comprobar("P15", "Intersección conmutativa", interseccion(a, b), interseccion(b, a));
        comprobar("P16", "Diferencia no conmutativa", false,
                diferencia(a, b).equals(diferencia(b, a)));
        Set<Integer> disponibles = diferencia(biblioteca.codigos(),
                biblioteca.conjuntoValidado(102, 105, 107));
        comprobar("P17", "Disponibilidad", Set.of(101, 103, 104, 106, 108), disponibles);
        comprobar("P18", "Demanda sin disponibilidad", Set.of(102, 105),
                diferencia(union(a, b), disponibles));
        comprobar("P19", "Búsqueda existente", "Bases de datos",
                biblioteca.buscarPorCodigo(103).orElseThrow().titulo);
        comprobar("P20", "Búsqueda inexistente", false, biblioteca.buscarPorCodigo(999).isPresent());
        comprobar("P21", "Registro duplicado", "IllegalArgumentException",
                capturarError(() -> biblioteca.registrar(new Libro(101, "Otro título"))));
        comprobar("P22", "Código no registrado", "IllegalArgumentException",
                capturarError(() -> biblioteca.conjuntoValidado(999)));
        comprobar("P23", "Salida ordenada", "[101, 102, 103, 105]", presentar(a));
        Set<Integer> resultado = union(a, b);
        resultado.clear();
        comprobar("P24", "Resultado independiente", true, a.equals(copiaA) && b.equals(copiaB));
        System.out.println("RESUMEN: " + superadas + "/24 pruebas superadas.");
    }

    public static void main(String[] args) {
        if (args.length == 1 && "--pruebas".equals(args[0])) {
            pruebas();
        } else if (args.length == 0) {
            demostracion();
        } else {
            System.err.println("Uso: java BibliotecaConjuntos.java [--pruebas]");
            System.exit(2);
        }
    }
}
