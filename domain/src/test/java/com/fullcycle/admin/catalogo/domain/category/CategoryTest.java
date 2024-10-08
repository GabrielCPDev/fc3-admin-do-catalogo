package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CategoryTest {

    private String validName;
    private String validDescription;
    private boolean validIsActive;

    @BeforeEach
    public void setUp() {
        validName = "Filmes";
        validDescription = "A categoria mais assistida";
        validIsActive = true;
    }

    @Test
    public void givenValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var actualCategory = Category.newCategory(validName, validDescription, validIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(validName, actualCategory.getName());
        Assertions.assertEquals(validDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String invalidName = null;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var actualCategory = Category.newCategory(invalidName, validDescription, validIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String invalidName = "   ";
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var actualCategory = Category.newCategory(invalidName, validDescription, validIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String invalidName = "Fi ";
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var actualCategory = Category.newCategory(invalidName, validDescription, validIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String invalidName = """
                Um nome extremamente longo que ultrapassa os 255 caracteres e que deveria causar um erro de validação. 
                Este texto é apenas um exemplo para simular um caso de teste onde o nome de uma categoria não deve ser permitido, 
                uma vez que excede o limite estabelecido pelas regras de negócio. """;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var actualCategory = Category.newCategory(invalidName, validDescription, validIsActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldPass() {
        final String emptyDescription = "   ";

        final var actualCategory = Category.newCategory(validName, emptyDescription, validIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(validName, actualCategory.getName());
        Assertions.assertEquals(emptyDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenValidFalseIsActive_whenCallNewCategory_thenReturnInactiveCategory() {
        final var actualCategory = Category.newCategory(validName, validDescription, false);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(validName, actualCategory.getName());
        Assertions.assertEquals(validDescription, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }
}
