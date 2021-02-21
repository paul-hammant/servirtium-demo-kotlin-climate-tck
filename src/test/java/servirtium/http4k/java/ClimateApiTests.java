package servirtium.http4k.java;

import org.http4k.core.Uri;
import org.junit.jupiter.api.Test;
import servirtium.http4k.ClimateApi;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface ClimateApiTests {

    Uri uri();

    @Test
    default void averageRainfallForGreatBritainFrom1980To1999Exists() {
        try {
            assertEquals(988.8454972331015, new ClimateApi(uri()).getAveAnnualRainfall(1980, 1999, "gbr"));
        } catch (Exception e) {
            failed(e);
            throw e;
        }
    }

    public default void failed(Exception e) {
    }

    @Test
    default void averageRainfallForFranceFrom1980To1999Exists() {
        try {
            assertEquals(913.7986955122727, new ClimateApi(uri()).getAveAnnualRainfall(1980, 1999, "fra"));
        } catch (Exception e) {
            failed(e);
            throw e;
        }
    }

    @Test
    default void averageRainfallForEgyptFrom1980To1999Exists() {
        try {
            assertEquals(54.58587712129825, new ClimateApi(uri()).getAveAnnualRainfall(1980, 1999, "egy"));
        } catch (Exception e) {
            failed(e);
            throw e;
        }
    }

    @Test
    default void averageRainfallForGreatBritainFrom1985To1995DoesNotExist() {
        try {
            try {
                new ClimateApi(uri()).getAveAnnualRainfall(1985, 1995, "gbr");
            } catch (ClimateApi.BadDateRange e) {
                assertEquals("date range 1985-1995 not supported", e.getLocalizedMessage());
            }
        } catch (Exception e) {
            failed(e);
            throw e;
        }
    }

    @Test
    default void averageRainfallForMiddleEarthFrom1985To1995DoesNotExist() {
        try {
            try {
                new ClimateApi(uri()).getAveAnnualRainfall(1980, 1999, "mde");
            } catch (ClimateApi.CountryISOwrong e) {
                        assertEquals("mde not recognized by climateweb", e.getLocalizedMessage());
            }
        } catch (Exception e) {
            failed(e);
            throw e;
        }
    }

    @Test
    default void averageRainfallForGreatBritainAndFranceFrom1980To1999CanBeCalculatedFromTwoRequests() {
        try {
            assertEquals(951.3220963726872, new ClimateApi(uri()).getAveAnnualRainfall(1980, 1999, "gbr", "fra"));
        } catch (Exception e) {
            failed(e);
            throw e;
        }
    }
}
