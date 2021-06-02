package task.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "T_PRODUCT")
public class Product {

    @Id
    @Column(name = "P_PRODUCT_ID")
    @GeneratedValue(generator = "uuid-generator")
    @GenericGenerator(name = "uuid-generator", strategy = "uuid")
    private String productId;

    @Column(name = "P_CATEGORY")
    private String category;

    @Column(name = "P_MANUFACTURE")
    private String manufacture;

    @Column(name = "P_MODEL")
    private String model;

    @Column(name = "P_FROM_PRICE")
    private Double fromPrice;

    @Column(name = "P_TO_PRICE")
    private Double toPrice;

    @Column(name = "P_URL")
    private String url;
}
