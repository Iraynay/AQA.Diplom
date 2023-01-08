package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import data.DBHelper;
import data.DataHelper;
import page.MainPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPaymentTest {
    MainPage mainPage = new MainPage();

    @BeforeEach
    void shouldOpenApp() {
        DBHelper.clean();
        open("http://localhost:8080", MainPage.class);
        mainPage.buyWithCredit();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldSuccessCreditPayIfValidApprovedCard() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectApprovalFromBank();
        var expected = DataHelper.getValidCardStatus();
        var actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }

    @Test
    void shouldDeclinedCreditPayIfDeclinedCard() {
        var cardNumber = DataHelper.getInvalidCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectRejectionFromBank();
        var expected = DataHelper.getInvalidCardStatus();
        var actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }

    @Test
    void shouldErrorMessageIfEmptyForm() {
        var cardNumber = DataHelper.getEmptyCardNumber();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getEmptyCardHolder();
        var cvs = DataHelper.getEmptyCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldErrorMessageIfInvalidCard() {
        var cardNumber = DataHelper.getRandomCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectRejectionFromBank();
        var expected = DataHelper.getInvalidCardStatus();
        var actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }

    @Test
    void shouldErrorMessageIfEmptyCardNumber() {
        var cardNumber = DataHelper.getEmptyCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldErrorMessageIfInvalidMonth() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getInValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldErrorMessageIfEmptyMonth() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldErrorMessageIfInvalidYear() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getInvalidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldErrorMessageIfEmptyYear() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldErrorMessageIfInvalidCardHolder() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getRandomCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldErrorMessageIfEmptyCardHolder() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getEmptyCardHolder();
        var cvs = DataHelper.getValidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldErrorMessageIfInvalidCVC() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getInvalidCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldErrorMessageIfEmptyCVC() {
        var cardNumber = DataHelper.getValidCardNumber();
        var month = DataHelper.getValidMonth();
        var year = DataHelper.getValidYear();
        var owner = DataHelper.getCardHolder();
        var cvs = DataHelper.getEmptyCVC();
        var paymentPage = new MainPage().buyWithCredit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }


}
