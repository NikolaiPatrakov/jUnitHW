package guru.qa;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;


public class KudagoMainPageParametrizedTests extends TestBase{

    @BeforeEach
    void setUp() {
        open("");
    }

    @CsvSource(value = {
            "Москва , https://kudago.com/msk/",
            "Санкт-Петербург , https://kudago.com/spb/"
    })
    @ParameterizedTest(name = "После нажатия на город {0} открывается ссылка с адресом {1}")
    @Tag("REGRESS")
    void linksWithCitiesShouldOpenCorrectLinkUsingCsvSourceProvider(String mainCitiesNames, String expectedLink) {
        $(".city-select").click();
        $("#locations-dropdown").find(withText(mainCitiesNames)).click();
        webdriver().shouldHave(url(expectedLink));
    }

    @ValueSource(strings = {
            "Екатеринбург", "Казань", "Нижний Новгород"
    })
    @ParameterizedTest(name = "После нажатия на город {0} должна быть видна панель навигации")
    @Tag("SMOKE")
    void checkNavigationPanelIsVisibleAfterClickingOnCityLink(String citiesNames) {
        $(".city-select").click();
        $("#locations-dropdown").find(withText(citiesNames)).click();
        $(".site-nav").shouldBe(Condition.visible);
    }

    static Stream<Arguments> linksWithCitiesShouldOpenCorrectLinkUsingMethodSourceProvider() {
        return Stream.of(
                Arguments.of(
                        "Москва" , "https://kudago.com/msk/"
                ),
                Arguments.of(
                        "Санкт-Петербург" , "https://kudago.com/spb/"
                )
        );
    }

    @MethodSource
    @ParameterizedTest(name = "После нажатия на город {0} открывается ссылка с адресом {1}")
    @Tag("SMOKE")
    void linksWithCitiesShouldOpenCorrectLinkUsingMethodSourceProvider(String mainCitiesNames, String expectedLink) {
        $(".city-select").click();
        $("#locations-dropdown").find(withText(mainCitiesNames)).click();
        webdriver().shouldHave(url(expectedLink));
    }
}