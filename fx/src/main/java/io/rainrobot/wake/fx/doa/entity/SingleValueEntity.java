package io.rainrobot.wake.fx.doa.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Deprecated
public abstract class SingleValueEntity<T> {
	@Id
	private int id;
	private T val;

	public SingleValueEntity() {}

	public SingleValueEntity(T Val, int id) {
		this.id = id;
		this.val = Val;
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public T getVal() {return val;}
	public void setVal(T Val) {this.val = val;}
}
