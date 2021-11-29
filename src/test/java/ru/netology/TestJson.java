package ru.netology;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.DataGenerator.Registration.getUser;
import static ru.netology.DataGenerator.getRandomLogin;
import static ru.netology.DataGenerator.getRandomPassword;

public class TestJson {

        @BeforeEach
        void setup() {
            open("http://localhost:9999");
        }

        @Test
        @DisplayName("Should successfully login with active registered user")
        void shouldSuccessfulLoginIfRegisteredActiveUser() {
            var registeredUser = getRegisteredUser("active");
            $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
            $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
            $(byText("Продолжить")).click();
            $(byText("Личный кабинет")).shouldBe(visible);
            // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
            //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
            //  пользователя registeredUser
        }

        @Test
        @DisplayName("Should get error message if login with not registered user")
        void shouldGetErrorIfNotRegisteredUser() {
            var notRegisteredUser = getUser("active");
            $("[data-test-id='login'] .input__control").setValue(notRegisteredUser.getLogin());
            $("[data-test-id='password'] .input__control").setValue(notRegisteredUser.getPassword());
            $(byText("Продолжить")).click();
            $(withText("Неверно указан логин")).shouldBe(visible);
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
            //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        }

        @Test
        @DisplayName("Should get error message if login with blocked registered user")
        void shouldGetErrorIfBlockedUser() {
            var blockedUser = getRegisteredUser("blocked");
            $("[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
            $("[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
            $(byText("Продолжить")).click();
            $(withText("Пользователь заблокирован")).shouldBe(visible);
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
            //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        }

        @Test
        @DisplayName("Should get error message if login with wrong login")
        void shouldGetErrorIfWrongLogin() {
            var registeredUser = getRegisteredUser("active");
            var wrongLogin = getRandomLogin();
            $("[data-test-id='login'] .input__control").setValue(wrongLogin);
            $("[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
            $(byText("Продолжить")).click();
            $(withText("Неверно указан логин")).shouldBe(visible);
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
            //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
            //  "Пароль" - пользователя registeredUser
        }

        @Test
        @DisplayName("Should get error message if login with wrong password")
        void shouldGetErrorIfWrongPassword() {
            var registeredUser = getRegisteredUser("active");
            var wrongPassword = getRandomPassword();
            $("[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
            $("[data-test-id='password'] .input__control").setValue(wrongPassword);
            $(byText("Продолжить")).click();
            $(withText("Неверно указан логин")).shouldBe(visible);
            // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
            //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
            //  "Пароль" - переменную wrongPassword
        }
    }

