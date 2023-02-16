package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class MessageSenderTest {

    GeoService geoService = Mockito.mock(GeoService.class);

    LocalizationService localizationService = Mockito.mock(LocalizationService.class);

    Location location = Mockito.mock(Location.class);

    @ParameterizedTest
    @MethodSource("sourceIP")
    void send(String ip) {
        //arrange
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();

        //act
        String exMessage = ip.startsWith("172.") ? "Добро пожаловать" : "Welcome";

        Mockito.when(localizationService.locale(Mockito.any())).thenReturn(exMessage);
        Mockito.when(geoService.byIp(ip)).thenReturn(location);

        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String message = messageSender.send(headers);

        //assert
        Assertions.assertEquals(exMessage, message);
        Mockito.verify(geoService, Mockito.only()).byIp(ip);
        Mockito.verify(localizationService, Mockito.times(2)).locale(Mockito.any());
        Mockito.verify(location, Mockito.times(2)).getCountry();
    }

    private static Stream<Arguments> sourceIP(){
        return Stream.of(
                Arguments.of("172.0.32.11"),
                Arguments.of("96.44.183.149"),
                Arguments.of("172.12.100.50"),
                Arguments.of("96.12.190.2")
        );
    }
}