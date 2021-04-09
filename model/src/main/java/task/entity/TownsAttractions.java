package task.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "T_TOWN_ATTRACTION")
public class TownsAttractions {

    @Id
    @Column(name = "TA_TOWN")
    private String town;

    @Column(name = "TA_ATTRACTIONS")
    private String attractions;

}
