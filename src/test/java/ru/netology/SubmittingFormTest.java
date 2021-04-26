package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class SubmittingFormTest {

    @Test
    void shouldTestCorrectForm() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Петр Иванов");
        $("[data-test-id='phone'] input").setValue("+79005557777");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

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
    void shouldTestNotAgreement() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Петр Иванов");
        $("[data-test-id='phone'] input").setValue("+79005557777");
        $("button").click();
        $("data-test-id=order-success").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй."));

    }

    @Test
    void shouldGetErrorIfOnlyAgreement() {
        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
