import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.olmart.entity.Country;
import ru.olmart.i18n.LocalizationService;
import ru.olmart.i18n.LocalizationServiceImpl;

public class LocalizationServiceImplTest {
    LocalizationService sut;

    @BeforeEach
    public void initTest() {
        System.out.println("Test start");
        sut = new LocalizationServiceImpl();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete");
    }

    @Test
    public void localeTest () {
        Country country = Country.RUSSIA;
        String expected = "Добро пожаловать";

        String result = sut.locale(country);

        Assertions.assertEquals(expected, result);
    }
}
