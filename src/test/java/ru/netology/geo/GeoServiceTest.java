package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

class GeoServiceTest {

    @ParameterizedTest
    @MethodSource("sourceIP")
    void byIp(String ip, Country ex) {
        //arrange
        GeoService geoService = new GeoServiceImpl();

        //act
        Country country = geoService.byIp(ip).getCountry();

        //assert
        Assertions.assertEquals(ex, country);
    }

    private static Stream<Arguments> sourceIP(){
        return Stream.of(
                Arguments.of("127.0.0.1", null),
                Arguments.of("172.0.32.11", Country.RUSSIA),
                Arguments.of("96.44.183.149", Country.USA),
                Arguments.of("172.12.100.50", Country.RUSSIA),
                Arguments.of("96.12.190.2", Country.USA)
        );
    }
}