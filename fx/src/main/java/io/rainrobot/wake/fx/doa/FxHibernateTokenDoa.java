package io.rainrobot.wake.fx.doa;

import io.rainrobot.wake.app.ITokenDoa;
import io.rainrobot.wake.fx.doa.entity.TokenEntity;
@Deprecated
public class FxHibernateTokenDoa extends FxSingleValueDoa<TokenEntity, String> implements ITokenDoa {

    public FxHibernateTokenDoa(HibernateExecuter executer) {
        super(executer);
    }

    @Override
    public String get() {
        return super.getValue();
    }

    @Override
    public void save(String token) {
        super.setValue(token);
    }

    @Override
    protected TokenEntity newEntityInstance(String val, int id) {
        return new TokenEntity(val, id);
    }


    @Override
    protected Class<TokenEntity> getEntityClass() {
        return TokenEntity.class;
    }

}

