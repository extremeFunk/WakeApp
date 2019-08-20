package io.rainrobot.wake.fx.doa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class  RMEntity extends SingleValueEntity<Boolean>{
    public RMEntity() {}
    public RMEntity(Boolean val, int id) {
        super(val, id);
    }
}
