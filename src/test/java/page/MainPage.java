package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class MainPage {
    private SelenideElement initiateBuy = $$("button").find(exactText("Купить"));
    private SelenideElement initiateBuyWithCredit = $$("button").find(exactText("Купить в кредит"));

    public PaymentPage buyCard() {
        initiateBuy.click();
        return new PaymentPage();
    }

    public PaymentPage buyWithCredit() {
        initiateBuyWithCredit.click();
        return new PaymentPage();
    }
}
