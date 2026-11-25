# Biblioteca escolar con operaciones de conjuntos

Proyecto académico de consola en Java. Compara solicitudes de dos grupos,
consulta el catálogo por código y calcula la disponibilidad de libros.
Se utilizan HashSet, HashMap y TreeSet de la biblioteca estándar.

## Requisitos

JDK 17 o superior. Las ejecuciones incluidas se comprobaron con OpenJDK 21.0.13.
No requiere Maven, Gradle, una base de datos ni bibliotecas externas.

## Ejecutar en Windows con Visual Studio Code

1. Descomprime el ZIP y abre la carpeta Proyecto_Biblioteca_Conjuntos.
2. Abre la terminal de VS Code y comprueba Java con `java --version`.
3. Entra a la carpeta del código: `cd src`.
4. Ejecuta la demostración:

```text
java -Dfile.encoding=UTF-8 BibliotecaConjuntos.java
```

5. Ejecuta las pruebas:

```text
java -Dfile.encoding=UTF-8 BibliotecaConjuntos.java --pruebas
```

El comando ejecuta directamente el archivo fuente. Si prefieres compilar:

```text
javac -encoding UTF-8 BibliotecaConjuntos.java
java -Dfile.encoding=UTF-8 BibliotecaConjuntos
java -Dfile.encoding=UTF-8 BibliotecaConjuntos --pruebas
```

También puedes usar ejecutar_demo.bat y ejecutar_pruebas.bat desde el Explorador.
Si Windows no reconoce `java`, instala un JDK y agrega su carpeta bin al PATH.
Si los acentos se ven incorrectos en CMD, ejecuta `chcp 65001` antes del programa.

## Datos del caso

- Catálogo I = {101, 102, 103, 104, 105, 106, 107, 108}.
- Solicitudes A = {101, 102, 103, 105}.
- Solicitudes B = {102, 103, 104, 106}.
- Prestados P = {102, 105, 107}.

Cada código identifica un título con un único ejemplar en este escenario.
Una solicitud repetida cuenta una sola vez: se analizan títulos distintos,
no cantidades de solicitudes. Los resultados se ordenan por código para mostrarlos.

## Resultados principales

Unión: {101, 102, 103, 104, 105, 106}.
Intersección: {102, 103}.
A menos B: {101, 105}. B menos A: {104, 106}.
Disponibles: {101, 103, 104, 106, 108}.
Solicitudes sin disponibilidad: {102, 105}.

## Organización

- src/BibliotecaConjuntos.java: implementación completa, datos y pruebas.
- salida_demostracion.txt: salida real de la demostración.
- resultados_pruebas.txt: resultados reales de las 24 pruebas.
- ejecutar_demo.bat: inicia la demostración en Windows.
- ejecutar_pruebas.bat: inicia las pruebas en Windows.

Las pruebas comparan valores esperados con valores obtenidos. Un fallo lanza
AssertionError y termina con un código de salida distinto de cero; no es necesario
activar las aserciones de Java con -ea. Se espera `24/24 pruebas superadas`.

## Alcance

Los datos se cargan en memoria al iniciar. El programa no registra préstamos,
devoluciones, fechas ni usuarios y no conserva cambios después de cerrarse.
Las operaciones generan nuevos conjuntos y no modifican los originales.
Para varios ejemplares por título se necesitarían conteos o identificadores de
ejemplar. Las clases de colección se reutilizan; no se programó su estructura
interna desde cero. Las pruebas son funcionales y no miden rendimiento.
