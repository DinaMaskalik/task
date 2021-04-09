package task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.entity.TownsAttractions;

import java.util.List;

@Repository
public interface TownDao extends JpaRepository<TownsAttractions, String> {

    List<TownsAttractions> findByTown(String town);

}
