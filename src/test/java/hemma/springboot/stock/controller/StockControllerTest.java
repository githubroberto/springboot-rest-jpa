package hemma.springboot.stock.controller;

import hemma.springboot.stock.model.Stock;
import hemma.springboot.stock.response.ResponseMessage;
import hemma.springboot.stock.service.StockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class StockControllerTest {

    @Mock
    private StockService stockService;

    private MockMvc mvc;

    @Before
    public void setup() {
        StockController stockController = new StockController(stockService);
        mvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    public void should_return_three_stocks() throws Exception {
        List<Stock> stocks = Arrays.asList(
                new Stock(1, "A", "SymbolA", new BigDecimal(11.00)),
                new Stock(2, "B", "SymbolB", new BigDecimal(21.00)),
                new Stock(3, "C", "SymbolC", new BigDecimal(31.00)));
        when(stockService.stocks()).thenReturn(stocks);
        mvc.perform(get("/stocks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.responseMessage", is(ResponseMessage.successful.getValue())))
                .andExpect(jsonPath("$.responseBody", is(notNullValue())))
                .andExpect(jsonPath("$.responseBody.stocks", is(notNullValue())))
                .andExpect(jsonPath("$.responseBody.stocks.length()", is(3)));
    }


    @Test
    public void should_return_one_stock_by_id() throws Exception {
        BigDecimal price = new BigDecimal(56.05).setScale(2, BigDecimal.ROUND_CEILING);
        when(stockService.stock(1)).thenReturn(new Stock(1, "VMWare", "VMW", price));
        mvc.perform(get("/stocks/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.responseMessage", is(ResponseMessage.successful.getValue())))
                .andExpect(jsonPath("$.responseBody", is(notNullValue())))
                .andExpect(jsonPath("$.responseBody.stock", is(notNullValue())))
                .andExpect(jsonPath("$.responseBody.stock.id", is(1)))
                .andExpect(jsonPath("$.responseBody.stock.companyName", is("VMWare")))
                .andExpect(jsonPath("$.responseBody.stock.symbol", is("VMW")))
                .andExpect(jsonPath("$.responseBody.stock.price", is(56.05)));
    }

    @Test
    public void should_add_stock() throws Exception {
        Integer id = 1;
        String companyName = "SavedStock";
        String symbol = "SS";
        BigDecimal price = new BigDecimal(97.05).setScale(2, BigDecimal.ROUND_CEILING);
        Stock stock = new Stock(id, companyName, symbol, price);
        when(stockService.save(stock)).thenReturn(true);
        mvc.perform(get("/stocks/add?id=" + id + "&companyName=" + companyName + "&symbol=" + symbol + "&price=" + price)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseCode", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.responseMessage", is(ResponseMessage.successful.getValue())))
                .andExpect(jsonPath("$.responseBody", is(notNullValue())))
                .andExpect(jsonPath("$.responseBody.stock", is(notNullValue())))
                .andExpect(jsonPath("$.responseBody.stock.id", is(1)))
                .andExpect(jsonPath("$.responseBody.stock.companyName", is("SavedStock")))
                .andExpect(jsonPath("$.responseBody.stock.symbol", is("SS")))
                .andExpect(jsonPath("$.responseBody.stock.price", is(97.05)));
    }
}