package com.fullcycle.admin.catalogo.domain.category;

import java.time.Instant;
import java.util.UUID;

public class Category {
    private String id;
    private String name;
    private String description;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final String id,
            final String expectedName,
            final String expectedDescription,
            final Boolean expectedIsActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {
        this.id = id;
        this.name = expectedName;
        this.description = expectedDescription;
        this.isActive = expectedIsActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public String getId() {
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
        final var id = UUID.randomUUID().toString();
        final var now = Instant.now();
        return new Category(id,
                aName,
                aDescription,
                isActive,
                now, now, null

        );
    }
}
