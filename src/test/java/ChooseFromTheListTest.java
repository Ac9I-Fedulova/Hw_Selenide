import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

public class ChooseFromTheListTest {
    public String generateDate (int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSentForm(){
        String deliveryDate = generateDate(7,"dd.MM.yyyy");

        Selenide.open("http://localhost:9999");

        $("[data-test-id='city'] .input__control").setValue("Вл");
        $$(".popup__container .menu-item__control").findBy(Condition.text("Владивосток")).click();
        $("[data-test-id='date'] .input__control").sendKeys("SPACE");
        if (!generateDate(4,"MM").equals(generateDate(7, "MM"))) $(".calendar__arrow_direction_right[data-step='1']").click();
        $$(".calendar__layout .calendar__day").findBy(Condition.text(generateDate(7,"d"))).click();
        $("[data-test-id='name'] .input__control").setValue("Петрова Мая");
        $("[data-test-id='phone'] .input__control").setValue("+79001112233");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + deliveryDate));
    }
}

