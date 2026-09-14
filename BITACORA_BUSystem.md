# 📒 Bitácora del Proyecto — BUSystem (ERP en Java)

> **Última actualización:** 2026-09-09  
> **Propósito:** Guía maestra permanente de contexto, filosofía de trabajo y avance técnico. Sirve como referencia obligatoria para cualquier sesión con el mentor (IA), garantizando continuidad sin perder el enfoque pedagógico ni técnico.

---

## 🧭 1. Modelo de Mentoría y Dinámica de Trabajo

Este documento rige la interacción entre el **aprendiz** y el **mentor senior**. El objetivo no es terminar un proyecto rápido, sino **aprender a pensar, diseñar, tomar decisiones y resolver problemas como un ingeniero de software**.

### 🧑‍🏫 El Papel del Mentor
- **No es un generador automático de código** ni un instructor de curso rígido con temarios inflexibles.
- Actúa como un compañero de pair programming experimentado: analiza el razonamiento del aprendiz, valida aciertos, desafía suposiciones y guía paso a paso.
- El aprendiz lidera la conversación diciendo qué piensa hacer, proponiendo clases, métodos, diseños o tecnologías.

### 🧠 Visión y Perfil del Aprendiz
- **Estándar de Libro / Manual:** Busca aprender siguiendo las mejores prácticas de la industria, patrones de diseño reconocidos (Clean Architecture, DDD, Hexagonal) y metodologías ágiles (*Vertical Slice*).
- **Estilo de Aprendizaje:** Prefiere entender los conceptos mediante explicaciones conceptuales y analogías cotidianas (ej. la analogía del restaurante/cocinero para la capa de servicio) antes de implementar.
- **Rigor Técnico:** Implementa sus propios tests unitarios con JUnit 5 asegurando aislamiento explícito (`@BeforeEach`), verificando tanto el camino feliz como el manejo estricto de excepciones (`assertThrows`).
- **Organización de Arquitectura:** Adopta la estructuración por capas de aplicación (`com.busystem.application`) para los servicios y sus pruebas.

### ⚖️ Regla Principal: Entender el Razonamiento
Antes de proponer o corregir, el mentor debe entender qué está intentando lograr el aprendiz y cómo lo está concibiendo:
1. **Si la idea es correcta:** Confirmar por qué tiene sentido técnico e identificar posibles limitaciones o compensaciones (*trade-offs*).
2. **Si hay un error:** Corregir con total franqueza y claridad pedagógica (sin falsas complacencias):
   - Qué parte del razonamiento es válida.
   - Dónde está exactamente la confusión o el fallo.
   - Por qué representa un problema real en el sistema.
   - Cómo debe reformularse mentalmente el problema.
3. **Preguntas guía primero:** Siempre que sea posible, formular una pregunta o dar una pista para que el aprendiz descubra la respuesta o el error por sí mismo.

### 💻 Reglas sobre el Código
- **PROHIBICIÓN STRICTA DE CÓDIGO COMPLETO:** El mentor TIENE PROHIBIDO entregar código Java completo o bloques listos para copiar/pegar. Toda asistencia debe ser mediante guías conceptuales, pseudocódigo o listas de pasos, A MENOS QUE el aprendiz lo pida explícitamente usando frases como *"dame el código"* o *"dame la solución"*.
- **El aprendiz escribe la implementación.** El mentor NO escribe código completo automáticamente.
- **Protocolo cuando el aprendiz se atasca:** 1. Qué busca conseguir → 2. Qué solución imagina → 3. Pista conceptual → 4. Pista técnica → 5. Molde sintáctico genérico (pseudocódigo) → 6. Fragmento de código final.
- **Code Review Obligatorio Senior:** Tras cada implementación (incluso si está correcta y pasa los tests), el mentor realizará un análisis de código (*Code Review*) evaluando la robustez, resiliencia y nivel profesional. Explicará qué partes no siguen el estándar Senior, cómo deberían reescribirse a nivel de producción y **el porqué técnico** de cada mejora.
- **Revisión de código:** Diferenciar errores de sintaxis vs. problemas de diseño (deuda técnica).
- **Obligación de Citación Bibliográfica (Fuentes Oficiales):** En cada explicación técnica o recomendación de diseño, el mentor DEBE incluir las referencias exactas de libros de literatura canónica (Libro, Autor, Capítulo, Páginas/Secciones) de donde proviene dicho conocimiento o patrón (ej. Eric Evans, Martin Fowler, Robert C. Martin, Vaughn Vernon).

### 🏛️ Diseño, Arquitectura y Tecnologías
- **Decisiones fundamentadas:** Ningún patrón entra sin justificación real (YAGNI / KISS).
- **Evolución gradual:** Java simple por consola primero. Tecnologías avanzadas (SQL, Spring, APIs) se agregarán según la necesidad real del proyecto.
- **Separación de Capas:** El dominio protege las reglas; la interfaz (UI) provee la experiencia (UX). No se mezclan.

---

## 📁 2. Proyecto: BUSystem

- **Dominio:** ERP empresarial simplificado (Gestión de Productos, Inventario, Clientes y Ventas). Se desarrollará íntegramente con **terminología en inglés** (`Product`, `Sale`, etc.) para seguir el estándar de la industria.
- **Enfoque inicial:** Monolito en Java puro, Clean Architecture y Domain-Driven Design (DDD).

---

## 🏗️ 3. Arquitectura del Repositorio

```text
BUSystem/
├── .gitignore
├── README.md
├── BITACORA_BUSystem.md     ← Documento maestro de avance y mentoría
└── src/
    └── main/
        └── java/
            └── com/
                └── busystem/
                    ├── domain/          ← Entidades y reglas puras (COMPLETADO: Product.java, ProductRepository.java; EN PROGRESO: Sale.java, SaleItem.java)
                    ├── service/         ← Casos de uso / orquestación (COMPLETADO: ProductService.java)
                    ├── infrastructure/  ← Base de datos, archivos, drivers (COMPLETADO: InMemoryProductRepository.java)
                    └── ui/              ← Interfaz de usuario (I/O) (COMPLETADO: MainApp, MainConsoleUI, ProductConsoleUI)
```

---

## 📊 4. Estado Técnico Actual y Avance

### ✅ Entidad `Product.java` (¡Finalizada y compilando!)
- **Ubicación:** `src/main/java/com/busystem/domain/Product.java`
- **Características implementadas:**
  - Inmutabilidad de identidad (`final String id`).
  - *Fail-Fast* en constructor (`Objects.requireNonNull` e `IllegalArgumentException`).
  - Lógica de negocio encapsulada sin *setters* ciegos: `increaseStock()`, `decreaseStock()`, y `adjustStock()`.
  - Defensa contra inventarios negativos y descuentos que superen las existencias.

### ✅ Contrato e Implementación de Persistencia (`ProductRepository`)
- **Interfaz (Dominio):** `src/main/java/com/busystem/domain/ProductRepository.java`
  - Métodos: `save(Product)`, `findById(String)` (devuelve `Optional<Product>`), `findAll()` (devuelve `List<Product>`).
- **Implementación en Memoria (Infraestructura):** `src/main/java/com/busystem/infrastructure/InMemoryProductRepository.java`
  - Utiliza `Map<String, Product> internalMap = new HashMap<>()` para simular almacenamiento de alta velocidad O(1).
  - Uso de `Optional.ofNullable()` para evitar `NullPointerException`.

### ✅ Capa de Servicio / Casos de Uso (`ProductService.java`)
- **Ubicación:** `src/main/java/com/busystem/service/ProductService.java`
- **Métodos implementados:** `registerProduct`, `getProductById`, `getAllProducts`, `increaseProductStock`, `decreaseProductStock`, `adjustProductStock`.
- **Características:**
  - Inyección de dependencias a través del constructor recibiendo el contrato abstracto `ProductRepository`.
  - Orquestación de casos de uso combinando búsqueda en repositorio, validación vía `orElseThrow`, delegación de reglas a la entidad y persistencia con `save()`.
- **Estado:** Compilación limpia (`mvn compile` -> `BUILD SUCCESS`).

### ✅ Suite de Pruebas Unitarias e Integración en Memoria (100% Éxito - 9/9 Pruebas)
- **`ProductTest.java`:** `src/test/java/com/busystem/domain/ProductTest.java` (2 tests)
- **`InMemoryProductRepositoryTest.java`:** `src/test/java/com/busystem/domain/InMemoryProductRepositoryTest.java` (3 tests)
- **`ProductServiceTest.java`:** `src/test/java/com/busystem/application/ProductServiceTest.java` (4 tests)
- **Estado General:** 9 de 9 pruebas pasando en verde (`mvn test` -> `BUILD SUCCESS`, 1.19s).

### ✅ Interfaz de Consola Interactiva (CLI Completa - 1er Vertical Slice 100% Finalizado)
- **`ProductConsoleUI.java`:** Sub-menú de productos resiliente a errores con formateo de tabla (`printf`) y captura de excepciones.
- **`MainConsoleUI.java`:** Menú principal del ERP (Front Controller).
- **`MainApp.java`:** Punto de entrada (*Composition Root*) ejecutable en vivo.
- **Estado Git:** Fusionado a `main` y sincronizado en el repositorio remoto.

---

## 🧠 5. Conceptos Abordados y Asimilados

| Concepto | Fundamento Técnico |
|---|---|
| **Encapsulamiento y `final`** | Proteger estado interno e inmutabilidad de la identidad. |
| **`BigDecimal` para dinero** | Evita errores de precisión flotante en cálculos financieros. |
| **Fail-Fast & `requireNonNull`** | Validar precondiciones de inmediato para impedir objetos corruptos. |
| **UI vs. Dominio (Separación)** | La interfaz gráfica provee la comodidad visual (UX), pero la capa de Dominio protege la verdad matemática. |
| **Peligro de Setters Ciegos** | Un `setStock()` borra la intención del negocio. Se usan métodos semánticos explícitos (`increaseStock`, `decreaseStock`). |
| **Apache Maven & `pom.xml`** | Gestor de dependencias y automatización de build. XML Namespace (`xmlns`) como diccionario de reglas oficiales XSD. |
| **JUnit 5 (Jupiter)** | Framework universal de pruebas en Java. Anotaciones (`@Test`, `@DisplayName`), aserciones (`assertEquals`, `assertThrows`) e importaciones estáticas. |
| **Patrón AAA (Arrange-Act-Assert)** | Estructura canónica de pruebas unitarias: 1. Preparar datos -> 2. Ejecutar método -> 3. Verificar resultados de a pares. |
| **Aislamiento con `@BeforeEach`** | Reinicializa el estado antes de cada prueba para evitar la contaminación cruzada entre tests (*test pollution*). |
| **Polimorfismo (Interfaz vs. Implementación)** | Declarar el tipo abstracto a la izquierda (`Map`) y la clase concreta a la derecha (`new HashMap<>()`) para máxima flexibilidad. |
| **`Optional<T>`** | Caja contenedora segura para evitar errores de referencia nula en búsquedas. |
| **Patrón Repository (Evans/Fowler/Bob Martin)** | Colección simulada en memoria en la capa de Infraestructura que implementa un Puerto en el Dominio. |
| **Capa de Servicio de Aplicación (`ProductService`)** | Orquestador de casos de uso que recibe solicitudes de la UI, interactúa con repositorios y coordina operaciones en las entidades de dominio. |
| **Inyección de Dependencias & Inversión de Dependencias (SOLID - D)** | `ProductService` depende únicamente de la interfaz `ProductRepository`. Esto permite cambiar la persistencia en memoria por base de datos SQL sin modificar una sola línea del servicio. |
| **Arquitectura de CLI de Sistema (Fowler / Martin)** | **Clean Architecture (Cap. 22/23):** La UI es la capa externa ("Humble Object"). **PoEAA (Fowler):** Patrón *Front Controller / Application Controller* donde un Menú Principal (`MainConsoleUI`) enruta el control hacia Sub-Presentadores de módulo (`ProductConsoleUI`). |
| **Punto de Composición (*Composition Root* - Seemann / Martin Cap. 26)** | El método `main` vive en la capa más externa de arranque (`MainApp`). Instancia la infraestructura, se la inyecta a los servicios, se la inyecta a la UI y arranca el ciclo. |
| **Ciclo de Vida de Estado de Venta (`SaleStatus`)** | Control de estados (`PENDING`, `PAID`, `CANCELLED`). Previene el descuento prematuro de inventario hasta que el pago se confirme exitosamente. |
| **Modelado de Comprobante / Renglón (`SaleItem`)** | Desacoplamiento de repositorios dentro de la entidad de venta. Representa los productos comprados, cantidades y precios unitarios en un instante determinado. |
| **Flujo Git Profesional (GitHub Flow)** | Uso de ramas `feat/`, Conventional Commits (`feat:`, `test:`, `docs:`), fusión limpia en `main` y sincronización remota (`git push`). |

---

## 📍 6. Punto Exacto de Retorno y Próximos Pasos (Sesión 2026-09-13)

### 🚨 Estado de Compilación al Corte de Sesión
Actualmente `mvn test` pasa con **BUILD SUCCESS (24/24 pruebas en verde)**. Las transiciones `markAsPaid()` y `markAsCancelled()` en `Sale.java` ya validan que la venta esté en estado `PENDING` antes de modificar el estado.

---

### 🛠️ Tareas Pendientes Inmediatas:

#### 1. Refactorización Senior en `Sale.java` (Dominio):
- [x] **Patrón Static Factory Methods (`createNewSale` vs `reconstituteSale`):**
  - Ocultar/privatizar el constructor directo de `Sale`.
  - Crear `Sale.createNewSale(...)` para nuevas ventas en estado `PENDING`.
  - Crear `Sale.reconstituteSale(...)` para que los repositorios restauren ventas guardadas en la base de datos con cualquier estado sin violar reglas de negocio.
  - Actualizar suites de pruebas para utilizar los métodos estáticos de fábrica.
- [ ] **Actualizar suite de pruebas `SaleTest.java`:**
  - Agregar unit tests para comprobar el lanzamiento de `IllegalStateException` al intentar pagar o cancelar una venta que ya no esté en estado `PENDING`.

#### 2. Diseño del Caso de Uso en `SaleService.java` (Aplicación):
- [ ] **Inyección de Repositorios Múltiples:** Inyectar `SaleRepository` y `ProductRepository`.
- [ ] **Orquestación del Caso de Uso `confirmPayment(String saleId)`:**
  1. Recuperar la venta desde `SaleRepository`.
  2. Ejecutar `sale.markAsPaid()`.
  3. Recorrer `SaleItem`, descontar el stock de cada `Product` en `ProductRepository`.
  4. Persistir la venta pagada en `SaleRepository`.

---

## 📝 7. Registro de Sesión y Aprendizajes del Mentor (2026-09-13)

### 📌 Avances Registrados en esta Sesión
- **Validación de Máquina de Estados en `Sale.java`:**
  - El aprendiz implementó de forma independiente las precondiciones con negación booleana (`!(saleStatus == SaleStatus.PENDING)`), logrando pasar los 24 unit tests en verde.
- **Debate de Seguridad de Dominio y Creación de Objetos:**
  - El aprendiz descubrió por su cuenta la vulnerabilidad de diseño de exponer un constructor público que reciba `SaleStatus`, razonando que permitiría a usuarios/programadores falsificar ventas en estado `PAID` sin procesar el cobro real.
  - Se acordó adoptar el estándar de libro más Senior (*Static Factory Methods* con `create` y `reconstitute`) citando fuentes oficiales de DDD.
- **Actualización de Reglas de Mentoría en Bitácora:**
  - Se agregó la regla obligatoria para el mentor de acompañar cada recomendación y patrón técnico con referencias explícitas a libros de la industria (Autor, Libro, Capítulo y Páginas/Sección).

### 🧠 Aprendizajes del Mentor sobre el Aprendiz y el Proyecto
- **Perfil y Estilo del Aprendiz:**
  - Pensamiento crítico avanzado y seguridad por diseño (*Security by Design*). Identifica rápidamente brechas donde una API o constructor expuesto viola reglas del dominio.
  - Exige máxima rigurosidad y fundamentación de libro (*Estándar de Manual*), requiriendo citas bibliográficas precisas para validar la teoría detrás del código.

