package task.service.town;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.dao.TownDao;
import task.entity.TownsAttractions;

import java.util.List;

@Service
public class TownAttractionService {

    @Autowired
    TownDao townDao;

    @Transactional
    public String getTownAttraction(String town){
        final List<TownsAttractions> attractions = townDao.findByTown(town);
        if(attractions.size()==0){
            return "К сожелению, мы не нашли данный город!";
        }
        return attractions.get(0).getAttractions();
    }

    @Transactional
    public void createTown(TownsAttractions townsAttractions){
        townDao.save(townsAttractions);
    }

    @Transactional
    public void deleteTown(String name) {
        townDao.deleteById(name);
    }

    @Transactional
    public TownsAttractions findTown(String name){
        System.out.println(name);
        final List<TownsAttractions> town = townDao.findByTown(name);
        if(town.size()==0){
            return null;
        }
        System.out.println(town);
        return town.get(0);
    }

    @Transactional
    public void updateTown(TownsAttractions town) {
        townDao.saveAndFlush(town);
    }
}
