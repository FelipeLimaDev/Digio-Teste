package br.com.digio.androidtest.presentation.view

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.digio.androidtest.R
import br.com.digio.androidtest.utils.MockServerUtil
import br.com.digio.androidtest.utils.RecyclerViewItemCountIdlingResource
import br.com.digio.androidtest.utils.hasItemCountAtLeast
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val server = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        server.dispatcher = MockServerUtil.dispatcherSuccess
        server.start(MockServerUtil.PORT)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    /**
     * Verifica se a MainActivity exibe a string "hello_maria" ao iniciar.
     */
    @Test
    fun onDisplayTitleShouldHaveHello() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        val title = context.getString(R.string.hello_maria)

        // Verifica se o texto aparece na tela
        onView(withText(title)).check(matches(isDisplayed()))
    }

    /**
     * Teste que simula sucesso no MockWebServer e verifica se
     * a lista de Spotlight e products e carrega ao menos 2 e depois 3 itens respectivamente.
     */
    @Test
    fun onDisplayListsShouldHaveItems() {

        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/sandbox/products" -> MockServerUtil.successResponse
                    else -> MockServerUtil.errorNotFoundResponse
                }
            }
        }

        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // 1) Pega a RecyclerView e registra o IdlingResource para ao menos 1 item no spotlight
        var idlingResource: RecyclerViewItemCountIdlingResource? = null
        scenario.onActivity { activity ->
            val recySpotlight = activity.findViewById<RecyclerView>(R.id.recyMainSpotlight)
            idlingResource = RecyclerViewItemCountIdlingResource(recySpotlight, minItemCount = 1)
            IdlingRegistry.getInstance().register(idlingResource!!)
        }

        // 2) verifica a lista de spotlight e products
        onView(withId(R.id.recyMainSpotlight)).check(hasItemCountAtLeast(2))

        onView(withId(R.id.recyMainProducts)).check(hasItemCountAtLeast(3))

        idlingResource?.let { IdlingRegistry.getInstance().unregister(it) }
    }


    /**
     * Exemplo de teste de erro:
     * Verifica se, quando o servidor retorna erro, a tela exibe o container de loading
     */
    @Test
    fun onErrorShouldNotDisplayLists() {
        server.dispatcher = MockServerUtil.dispatcherError

        val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)

        // checar se o container de loading está visível
        onView(withId(R.id.loadDigioContainer))
            .check(matches(isDisplayed()))
    }
}
