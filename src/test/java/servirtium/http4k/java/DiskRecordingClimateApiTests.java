package servirtium.http4k.java;

import org.http4k.client.ApacheClient;
import org.http4k.core.Uri;
import org.http4k.server.SunHttp;
import org.http4k.servirtium.ServirtiumServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import servirtium.http4k.ClimateApi;

import java.io.File;

import static org.http4k.servirtium.InteractionStorage.Disk;
import static servirtium.http4k.java.JUnitUtil.getMarkdownNameFrom;

public class DiskRecordingClimateApiTests implements ClimateApiTests {

    @Override
    public Uri uri() {
        return Uri.of("http://localhost:" + servirtium.port());
    }

    private ServirtiumServer servirtium;

    @BeforeEach
    public void start(TestInfo info) {
        servirtium = ServirtiumServer.Recording(
                getMarkdownNameFrom(info),
                ClimateApi.DEFAULT_CLIMATE_API_SITE,
                Disk(new File("src/test/resources")),
                new ClimateInteractionOptions(), 0, SunHttp::new,
                ApacheClient.create()
        );
        servirtium.start();
    }

    @Override
    public void failed(Exception e) {
        Interaction interaction = servirtium.getLastInteraction();
        System.err.println("In case this aids debugging.....");
        System.err.println("Last Interaction - Request Headers:" + interaction.getRequestHeadersAsString());
        System.err.println("Last Interaction - Request Body:" + interaction.getRequestBodyAsString());
        System.err.println("Last Interaction - Response Headers:" + interaction.getResponseHeadersAsString());
        System.err.println("Last Interaction - Response Body:" + interaction.getResponseBodyAsString());
        System.err.println("Exception - " + e.getMessage());
    }

    @AfterEach
    public void stop(TestInfo info) {
        System.out.println("foo=" + info.);
        servirtium.stop();
    }
}
