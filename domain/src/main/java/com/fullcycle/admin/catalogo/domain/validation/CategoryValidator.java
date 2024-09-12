package com.fullcycle.admin.catalogo.domain.validation;

import com.fullcycle.admin.catalogo.domain.category.Category;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category category, final ValidationHandler validationHandler) {
        super(validationHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (this.category.getName() == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
        }
    }
}
