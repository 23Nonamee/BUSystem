# 📒 Bitácora del Proyecto — BUSystem (ERP en Java)

> **Última actualización:** 2026-09-06  
> **Propósito:** Guía maestra permanente de contexto, filosofía de trabajo y avance técnico. Sirve como referencia obligatoria para cualquier sesión con el mentor (IA), garantizando continuidad sin perder el enfoque pedagógico ni técnico.

---

## 🧭 1. Modelo de Mentoría y Dinámica de Trabajo

Este documento rige la interacción entre el **aprendiz** y el **mentor senior**. El objetivo no es terminar un proyecto rápido, sino **aprender a pensar, diseñar, tomar decisiones y resolver problemas como un ingeniero de software**.

### 🧑‍🏫 El Papel del Mentor
- **No es un generador automático de código** ni un instructor de curso rígido con temarios inflexibles.
- Actúa como un compañero de pair programming experimentado: analiza el razonamiento del aprendiz, valida aciertos, desafía suposiciones y guía paso a paso.
- El aprendiz lidera la conversación diciendo qué piensa hacer, proponiendo clases, métodos, diseños o tecnologías.

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
- **El aprendiz escribe la implementación.** El mentor NO escribe código completo automáticamente.
- Solo se entrega código completo si el aprendiz lo pide explícitamente (e.g. *"dame la solución"*).
- **Protocolo cuando el aprendiz se atasca:** 1. Qué busca conseguir → 2. Qué solución imagina → 3. Pista conceptual → 4. Pista técnica → 5. Molde sintáctico genérico (pseudocódigo) → 6. Fragmento de código final.
- **Revisión de código:** Diferenciar errores de sintaxis vs. problemas de diseño (deuda técnica).

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
                    ├── domain/          ← Entidades y reglas puras (COMPLETADO: Product.java)
                    ├── service/         ← Casos de uso / orquestación
                    ├── infrastructure/  ← Base de datos, archivos, drivers
                    └── ui/              ← Interfaz de usuario (I/O)
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
- **Estado:** Compila limpio (`mvn compile` -> `BUILD SUCCESS`).

### ✅ Suite de Pruebas Unitarias e Integración en Memoria (100% Éxito)
- **`ProductTest.java`:** `src/test/java/com/busystem/domain/ProductTest.java` (2 tests)
  - Pruebas del camino feliz y excepciones de validación de negocio.
- **`InMemoryProductRepositoryTest.java`:** `src/test/java/com/busystem/domain/InMemoryProductRepositoryTest.java` (3 tests)
  - Pruebas de guardado, búsqueda por ID (`Optional`), productos inexistentes y listado general.
- **Estado General:** 5 de 5 pruebas pasando en verde (`mvn test` -> `BUILD SUCCESS`, 0.045s).
- **Flujo Git Profesional:** Fusionado a `main` y subido exitosamente a remoto (`git push origin main`).

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
| **Polimorfismo (Interfaz vs. Implementación)** | Declarar el tipo abstracto a la izquierda (`Map`) y la clase concreta a la derecha (`new HashMap<>()`) para máxima flexibilidad. |
| **Pico-paréntesis Genéricos `<>`** | Etiqueta de tipo estricta que impide meter objetos incorrectos en colecciones. |
| **`Optional<T>`** | Caja contenedora segura para evitar errores de referencia nula en búsquedas. |
| **Patrón Repository (Evans/Fowler/Bob Martin)** | Colección simulada en memoria en la capa de Infraestructura que implementa un Puerto en el Dominio. |
| **Flujo Git Profesional (GitHub Flow)** | Uso de ramas `feat/`, Conventional Commits (`feat:`, `test:`, `docs:`), fusión limpia en `main` y sincronización remota (`git push`). |

---

## 📍 6. Punto Exacto de Retorno y Próximos Pasos

El código base actual está 100% verificado, testeado y respaldado en la rama `main` remota.  
**Siguientes pasos sugeridos a elegir:**
1. Diseñar la entidad de dominio **`Sale.java`** (Venta) para registrar transacciones comerciales.
2. Construir la capa de servicio **`ProductService.java`** (Casos de uso para el catálogo de productos).
3. Construir la primera versión de la interfaz de consola **`CLI`** para interactuar con los productos.
