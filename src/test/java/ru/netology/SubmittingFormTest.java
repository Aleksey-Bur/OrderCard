package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class SubmittingFormTest {

    @Test
    void shouldTestNotCorrectName() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Petr Ivanov");
        $("[data-test-id='phone'] input").setValue("+79005557777");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $(".input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestNotCorrectPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Петр Иванов");
        $("[data-test-id='phone'] input").setValue("90055577");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $$(".input__sub").last().shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestCorrectForm() {
        $("[data-test-id=name] input").setValue("Петр Иванов");
        $("[data-test-id='phone'] input").setValue("+79005557777");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
}