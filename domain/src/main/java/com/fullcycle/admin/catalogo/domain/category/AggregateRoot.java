package com.fullcycle.admin.catalogo.domain.category;

public class AggregateRoot<ID extends Identifier> extends Entity<ID> {

    protected AggregateRoot(ID id) {
        super(id);
    }
}
