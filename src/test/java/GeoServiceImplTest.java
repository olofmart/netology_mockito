import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.olmart.entity.Country;
import ru.olmart.entity.Location;
import ru.olmart.geo.GeoService;
import ru.olmart.geo.GeoServiceImpl;

public class GeoServiceImplTest {
    GeoService sut;

    @BeforeEach
    public void initTest() {
        System.out.println("Test start");
        sut = new GeoServiceImpl();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete");
    }

    @Test
    public void byIpTest () {
        String ip = "96.44.183.149";
        Location expected = new Location("New York", Country.USA, " 10th Avenue", 32);

        Location result = sut.byIp(ip);

        Assertions.assertInstanceOf(Location.class, result);
        Assertions.assertEquals(expected.getCity(), result.getCity());
        Assertions.assertEquals(expected.getCountry(), result.getCountry());
        Assertions.assertEquals(expected.getStreet(), result.getStreet());
        Assertions.assertEquals(expected.getBuiling(), result.getBuiling());
    }

    @Test
    public void byCoordinatesExceptionTest () {

        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> sut.byCoordinates(45.12, 53.25));

        Assertions.assertEquals("Not implemented", thrown.getMessage());
    }

}
