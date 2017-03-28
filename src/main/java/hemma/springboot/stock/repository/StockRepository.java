package hemma.springboot.stock.repository;

import hemma.springboot.stock.model.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface StockRepository extends CrudRepository<Stock, Integer> {
//    Collection<Stock> getStocks() throws Exception;
//
//    Stock getStock(Integer id) throws Exception;
}
