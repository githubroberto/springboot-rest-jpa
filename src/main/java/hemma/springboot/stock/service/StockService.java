package hemma.springboot.stock.service;

import hemma.springboot.stock.model.Stock;

import java.util.Collection;

public interface StockService {

    Collection<Stock> getStocks();

    Stock getStock(Integer stockId);

    Stock save(Stock stock);
}
