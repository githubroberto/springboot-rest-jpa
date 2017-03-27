package hemma.springboot.stock.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;
    private String companyName;
    private String symbol;
    private BigDecimal price;
}
