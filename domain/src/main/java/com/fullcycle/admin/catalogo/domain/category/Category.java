package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.AggregateRoot;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final Boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.isActive = isActive;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdateDate;
        this.deletedAt = aDeleteDate;
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public static Category newCategory(
            final String aName,
            final String aDescription,
            final boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        return new Category(
                id,
                aName,
                aDescription,
                isActive,
                now, now, null

        );
    }
}
