package com.fullcycle.admin.catalogo.application.category.update;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;
    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCagtegory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);
        final var expectedName = "films";
        final var expectedDescription = "a categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        useCase.execute(aCommand).get();

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.nonNull(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("Film", null, true);

        final String expectedName = null;
        final var expectedDescription = "a categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));
        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateCagtegory_shouldReturnInactiveCategoryId() {
        final var aCategory = Category.newCategory("Film", null, true);

        final var expectedName = "films";
        final var expectedDescription = "a categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.nonNull(aUpdatedCategory.getUpdatedAt())
                                && Objects.nonNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var aCategory = Category.newCategory("Film", null, true);
        final var expectedName = "films";
        final var expectedDescription = "a categoria mais assistida";
        final var expectedId = aCategory.getId();
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(Category.with(aCategory)));
        when(categoryGateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(1)).update(argThat(
                aUpdatedCategory ->
                        Objects.equals(expectedName, aUpdatedCategory.getName())
                                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                && Objects.nonNull(aUpdatedCategory.getUpdatedAt())
                                && Objects.isNull(aUpdatedCategory.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenCallsUpdateCagtegory_shouldReturnNotFoundException() {

        final var expectedName = "films";
        final var expectedDescription = "a categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID 123 was not found";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(eq(CategoryID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));
        Mockito.verify(categoryGateway, times(0)).update(any());
    }
}
