package io.rainrobot.wake.fx.doa.entity;


import javax.persistence.Entity;

@Entity
public class  TokenEntity extends SingleValueEntity<String>{
    public TokenEntity() {}
    public TokenEntity(String val, int id) {
        super(val, id);
    }
}