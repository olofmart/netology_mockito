import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.olmart.entity.Country;
import ru.olmart.entity.Location;
import ru.olmart.geo.GeoService;
import ru.olmart.geo.GeoServiceImpl;
import ru.olmart.i18n.LocalizationService;
import ru.olmart.sender.MessageSenderImpl;
import org.junit.jupiter.params.ParameterizedTest;
import java.util.Map;
import java.util.stream.Stream;
import static ru.olmart.entity.Country.RUSSIA;
import static ru.olmart.entity.Country.USA;


public class MessageSenderImplTest {

    @BeforeEach
    public void initTest() {
        System.out.println("Test start");
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("\nTest complete");
    }

    @ParameterizedTest
    @MethodSource("sourceSendTest")
    void sendTest(String ipLocal, Location location, Country country, String str,String ipHeader, String ip, String expected) {
        GeoService geoService = Mockito.spy(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ipLocal)).thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(country)).thenReturn(str);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = Map.of(ipHeader, ip);
        String result = messageSender.send(headers);

        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> sourceSendTest() {
        return Stream.of(Arguments.of(
                "172.123.12.19",new Location("Moscow", Country.RUSSIA, "Lenina", 15),
                RUSSIA, "Добро пожаловать", "x-real-ip", "172.123.12.19", "Добро пожаловать"),
                Arguments.of("96.44.183.149",new Location("New York", Country.USA, " 10th Avenue", 32),
                USA, "Welcome", "x-real-ip", "96.44.183.149", "Welcome"));
    }
}
