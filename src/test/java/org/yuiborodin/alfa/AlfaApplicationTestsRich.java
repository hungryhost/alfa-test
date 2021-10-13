package org.yuiborodin.alfa;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.yuiborodin.alfa.currencies.CurrencyService;
import org.yuiborodin.alfa.images.ImageService;
import org.yuiborodin.alfa.utils.TypeUtils;

import java.util.HashMap;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.yuiborodin.alfa.utils.DateUtils.getPreviousDate;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(properties = {
        "currency.base_currency=RUB",
        "currency.api_key=121",
        "currency.base_url=http://127.0.0.1:8081/",
        "images.base_url=http://127.0.0.1:8081/",
        "images.api_key=121" })
class AlfaApplicationTestsRich {


    @Autowired
    CurrencyService currencyService;
    @Autowired
    ImageService imageService;

    WireMockServer wireMockServer = new WireMockServer(8081);

    @BeforeClass
    public void startServer() {

        wireMockServer.stubFor(get(urlEqualTo("/api/latest.json"))
                .withQueryParam("app_id", equalTo("121"))
                .withQueryParam("base", equalTo("RUB"))
                .withQueryParam("symbols", equalTo("USD"))
                .willReturn(
                        aResponse().withBody(
                                " { 'disclaimer': 'Usage subject to terms: https://openexchangerates.org/terms', " +
                                        "'license': 'https://openexchangerates.org/license', " +
                                        "'timestamp': '1634090400', " +
                                        "'base': 'RUB', " +
                                        "'rates': { " +
                                        "'USD': '0.01456' }")));
        wireMockServer.stubFor(get(urlEqualTo(String.format("/api/historical/%s.json", getPreviousDate())))
                .withQueryParam("app_id", equalTo("121"))
                .withQueryParam("base", equalTo("RUB"))
                .withQueryParam("symbols", equalTo("USD"))
                .willReturn(
                        aResponse().withBody(
                                " { 'disclaimer': 'Usage subject to terms: https://openexchangerates.org/terms', " +
                                        "'license': 'https://openexchangerates.org/license', " +
                                        "'timestamp': '1634090400', " +
                                        "'base': 'RUB', " +
                                        "'rates': { " +
                                        "'USD': '0.01345' }"
                        )));
        wireMockServer.stubFor(get(urlEqualTo(("/v1/gifs/search/")))
                .withQueryParam("q", equalTo("rich"))
                .withQueryParam("limit", equalTo("1"))
                .withQueryParam("offset", equalTo("5"))
                .withQueryParam("rating", equalTo("g"))
                .withQueryParam("lang", equalTo("en"))
                .willReturn(
                        aResponse().withBody(
                                " { 'data': [ { 'images': { 'original': { 'url': 'testurlrich' } } } ] }"
                        )));
        wireMockServer.start();
    }

    @AfterClass
    public void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void contextLoads() {
    }
    @Test
    public void testUserOfService() {
        HashMap<String, Double> return_latest = new HashMap<>();
        return_latest.put("USD", 0.01345);
        HashMap<String, Double> return_previous = new HashMap<>();
        return_previous.put("USD", 0.01445);



        assertEquals(TypeUtils.ImageType.rich, currencyService.compareRates("USD"));
        assertEquals(0.01256, currencyService.getLatestRate());
        assertEquals(0.01345, currencyService.getPreviousRate());
        assertEquals(0.01345, currencyService.getPreviousRate());
        assertEquals("testurlrich", imageService.getImage(TypeUtils.ImageType.rich));
        //System.out.println();
        //System.out.println(currencyService.getPreviousRate());
    }

}