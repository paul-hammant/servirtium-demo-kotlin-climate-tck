package servirtium.http4k.servirtium.http4k

import org.http4k.core.Uri
import org.http4k.servirtium.InteractionOptions
import org.http4k.servirtium.InteractionStorage
import org.http4k.servirtium.ServirtiumServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import servirtium.http4k.ClimateApi.Companion.DEFAULT_CLIMATE_API_SITE
import servirtium.http4k.ClimateApiTests
import java.io.File

class RecordingClimateApiTest : ClimateApiTests {

    override val uri by lazy { Uri.of("http://localhost:${servirtium.port()}") }

    private lateinit var servirtium: ServirtiumServer

    @BeforeEach
    fun start(info: TestInfo) {
        servirtium = ServirtiumServer.Recording(
            info.displayName.removeSuffix("()"),
            DEFAULT_CLIMATE_API_SITE,
            InteractionStorage.Disk(File(".")),
            object : InteractionOptions {
            }
        )
        servirtium.start()
    }

    @AfterEach
    fun stop() {
        servirtium.stop()
    }
}

