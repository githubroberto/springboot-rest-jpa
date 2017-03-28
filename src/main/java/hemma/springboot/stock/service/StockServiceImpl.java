package hemma.springboot.stock.service;

import hemma.springboot.stock.model.Stock;
import hemma.springboot.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Collection<Stock> getStocks() {
        try {
            List<Stock> stockList = new ArrayList<>();
            Iterable<Stock> stockIterable = stockRepository.findAll();
            stockIterable.forEach(stockList::add);
            return stockList;
        } catch (Exception exception) {
            return Collections.emptyList();
        }
    }

    @Override
    public Stock getStock(Integer stockId) {
        try {
            return stockRepository.findOne(stockId);
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public Stock save(Stock stock) {
        try {
            return stockRepository.save(stock);
        } catch (Exception e) {
            return null;
        }
    }
}
