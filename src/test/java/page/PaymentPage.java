package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement fieldCardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement fieldMonth = $("[placeholder='08']");
    private SelenideElement fieldYear = $("[placeholder='22']");
    private SelenideElement fieldOwner = $$("[class='input__control']").get(3);
    private SelenideElement fieldCvc = $("[placeholder='999']");
    private SelenideElement buttonContinue = $$("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").get(1);
    private SelenideElement bankApproved = $(withText("Операция одобрена Банком."));
    private SelenideElement errorBankRefusal = $(withText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement errorFormat = $(withText("Неверный формат"));
    private SelenideElement invalidDurationCard = $(withText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private SelenideElement requiredField = $(withText("Поле обязательно для заполнения"));

    public void fillOutFields(String cardNumber, String month, String year, String owner, String cvs) {
        fieldCardNumber.setValue(cardNumber);
        fieldMonth.setValue(month);
        fieldYear.setValue(year);
        fieldOwner.setValue(owner);
        fieldCvc.setValue(cvs);
        buttonContinue.click();
    }

    public void expectApprovalFromBank() {
        bankApproved.should(Condition.visible, Duration.ofSeconds(15));
    }

    public void expectRejectionFromBank() {
        errorBankRefusal.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void waitInvalidFormat() {
        errorFormat.shouldBe(Condition.visible);
    }

    public void waitSureFillOutField() {
        requiredField.shouldBe(Condition.visible);
    }

    public void waitInvalidDuration() {
        invalidDurationCard.shouldBe(Condition.visible);
    }

    public void waitInvalidYear() {
        cardExpired.shouldBe(Condition.visible);
    }

}
