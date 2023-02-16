package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

class LocalizationServiceTest {

    @ParameterizedTest
    @MethodSource("sourceCountry")
    void locale(Country country, String ex) {
        //arrange
        LocalizationService localizationService = new LocalizationServiceImpl();
        //act
        String locale = localizationService.locale(country);

        //assert
        Assertions.assertEquals(locale, ex);
    }

    private static Stream<Arguments> sourceCountry(){
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome")
        );
    }
}