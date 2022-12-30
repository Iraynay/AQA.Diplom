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
    PaymentPage paymentPage = new PaymentPage();

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
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectApprovalFromBank();
        val expected = DataHelper.getValidCardStatus();
        val actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }
    @Test
    void shouldDeclinedCreditPayIfDeclinedCard() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectRejectionFromBank();
        val expected = DataHelper.getInvalidCardStatus();
        val actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }
    @Test
    void shouldErrorMessageIfEmptyForm() {
        val cardNumber = DataHelper.getEmptyCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getEmptyYear();
        val owner = DataHelper.getEmptyCardHolder();
        val cvs = DataHelper.getEmptyCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
        paymentPage.waitSureFillOutField();
    }
    @Test
    void shouldErrorMessageIfInvalidCard() {
        val cardNumber = DataHelper.getRandomCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.expectRejectionFromBank();
        val expected = DataHelper.getInvalidCardStatus();
        val actual = DBHelper.getStatusCreditBuy();
        assertEquals(expected, actual);
    }

    @Test
    void shouldErrorMessageIfEmptyCardNumber() {
        val cardNumber = DataHelper.getEmptyCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }
    @Test
    void shouldErrorMessageIfInvalidMonth() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getInValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidDuration();
    }
    @Test
    void shouldErrorMessageIfEmptyMonth() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getEmptyMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }
    @Test
    void shouldErrorMessageIfInvalidYear() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldErrorMessageIfEmptyYear() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldErrorMessageIfInvalidCardHolder() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getRandomCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }
    @Test
    void shouldErrorMessageIfEmptyCardHolder() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyCardHolder();
        val cvs = DataHelper.getValidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitSureFillOutField();
    }

    @Test
    void shouldErrorMessageIfInvalidCVC() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getInvalidCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }
    @Test
    void shouldErrorMessageIfEmptyCVC() {
        val cardNumber = DataHelper.getValidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getCardHolder();
        val cvs = DataHelper.getEmptyCVC();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvs);
        paymentPage.waitInvalidFormat();
    }



}
