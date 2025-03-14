import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class CardDeliveryTest {

    public String generateDate (int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

//    @BeforeEach
//    void setUp() {
//        Configuration.baseUrl = "http://localhost:9999";
//    }

    @Test
    void shouldSentForm(){
        String deliveryDate = generateDate(4,"dd.MM.yyyy");

        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] .input__control").setValue("Уфа");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] .input__control").setValue(deliveryDate);
        $("[data-test-id='name'] .input__control").setValue("Петрова Мая");
        $("[data-test-id='phone'] .input__control").setValue("+79001112233");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + deliveryDate));
    }
}
