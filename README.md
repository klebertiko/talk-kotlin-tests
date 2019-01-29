# Talk Kotlin Tests

## Build tool
### Gradle
- `$ gradle init --type=kotlin-application`

## REST
### Javalin

```kotlin
import io.javalin.Javalin

fun main(args: Array<String>) {
    val app = Javalin.create().start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }
}
```

## HTTP Client
### Fuel
#### Instalation
```groovy
implementation 'com.github.kittinunf.fuel:fuel:<latest-version>'
```

#### Use
```kotlin
"https://httpbin.org/get"
  .httpGet()
  .responseString { request, response, result ->
    when (result) {
      is Result.Failure -> {
        val ex = result.getException()
      }
      is Result.Success -> {
        val data = result.get()
      }
    }
  }
```

## Test
- Use per test class lifecycle to avoid the need for static members
- No static access. It impedes proper object-oriented design, because static access harms testability (can’t exchange objects easily) and obfuscates     dependencies and side-effects. Kotlin strongly encourages us to avoid static access by simply not providing an easy way to create static members.
- Immutability. We should use immutable references with val instead of var
- Non-Nullability. We should favor non-nullable types (String) over nullable types (String?)
- Use Data Classes
```kotlin
val actual = getFoo()
val expected = Foo(id = 2, foo = foo)
assertThat(actual).isEqualTo(expected)
```
- Result
```
Expected :Foo(id=2, foo=foo)
Actual   :Foo(id=1, foo=foo)
```

- Use backticks for methods names
- Kotlin everything is final and some mock libraries do not play well, in this cases use open class or use Mockk its supports mock final classes by default
- Create mocks once

```kotlin
private val dao: DesignDAO = mockk()
private val mapper: DesignMapper = mockk()
private val controller = DesignController(dao, mapper)

@BeforeEach
fun init() {
    clearMocks(dao, mapper)
}

@RepeatedTest(300)
fun test() {
    controller.doSomething()
}
```

### JUnit4
- Not so good with Kotlin
- A new instance of the test class is created for every test method
- Need to use static members

### JUnit5
- S2 Kotlin

```kotlin
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest { }
```

### Spek2
- To write specification and gherkin style tests

#### Specification
```kotlin
object CalculatorSpec: Spek({
    describe("A calculator") {
        val calculator by memoized { Calculator() }

        describe("addition") {
            it("returns the sum of its arguments") {
                assertThat(3, calculator.add(1, 2))
            }
        }
    }
})
```

#### Gherkin
```kotlin
object SetFeature: Spek({
    Feature("Set") {
        val set by memoized { mutableSetOf<String>() }

        Scenario("adding items") {
            When("adding foo") {
                set.add("foo")
            }

            Then("it should have a size of 1") {
                assertEquals(1, set.size)
            }

            Then("it should contain foo") {
                assertTrue(set.contains("foo"))
            }
        }

        Scenario("empty") {
            Then("should have a size of 0") {
                assertEquals(0, set.size)
            }

            Then("should throw when first is invoked") {
                assertFailsWith(NoSuchElementException::class) {
                    set.first()
                }
            }
        }

        Scenario("getting the first item") {
            val item = "foo"
            Given("a non-empty set")  {
                set.add(item)
            }

            lateinit var result: String

            When("getting the first item") {
                result = set.first()
            }

            Then("it should return the first item") {
                assertEquals(item, result)
            }
        }
    }
})
```

## Static analysis and linter
### Detekt
#### Features

- Code smell analysis for your Kotlin projects
- Complexity report based on logical lines of code, McCabe complexity and amount of code smells
- Highly configurable (rule set or rule level)
- Suppress findings with Kotlin’s @Suppress and Java’s @SuppressWarnings annotations
- Specify code smell thresholds to break your build or print a warning
- Code Smell baseline and ignore lists for legacy projects
- Gradle plugin for code analysis via Gradle builds
- Gradle tasks to use local IntelliJ distribution for formatting and inspecting Kotlin code
- Optionally configure detekt for each sub module by using profiles (gradle-plugin)
- SonarQube integration
- Extensible by own rule sets and FileProcessListener's
- IntelliJ integration
- Unofficial Maven plugin by Ozsie https://github.com/Ozsie

#### Basic configuration

```groovy
plugins {
    id("io.gitlab.arturbosch.detekt").version("[version]")
}

detekt {
    toolVersion = "[version]"
    input = files("src/main/kotlin")
    filters = ".*/resources/.*,.*/build/.*"
    config = files("path/to/config.yml")
}
```

#### Enable reports

```groovy
detekt {
    toolVersion = "[version]"
    input = files("src/main/kotlin")
    filters = ".*/resources/.*,.*/build/.*"
    config = files("path/to/config.yml")
    reports {
        xml {
            enabled = true
            destination = file("path/to/destination.xml")
        }
        html {
            enabled = true
            destination = file("path/to/destination.html")
        }
    }
}
```

#### Wrapper over KtLint for formatting

```groovy
dependencies {
    detektPlugins "io.gitlab.arturbosch.detekt:detekt-formatting:[version]"
}
```

## Running
* `$ ./gradlew run`
* Navigate to http://localhost:7000