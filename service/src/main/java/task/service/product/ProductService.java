package task.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.dao.ProductDao;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    @Transactional
    public String getProductUrl(String category, String manufacture, String model, Double price){
        final List<String> products = productDao.findUrl(category, manufacture, model, price);
        if(products.size()==0){
            return "К сожелению, мы не нашли подходящий товар!\nПопробуйте ещё раз!";
        }
        return products.get(0);
    }

}
