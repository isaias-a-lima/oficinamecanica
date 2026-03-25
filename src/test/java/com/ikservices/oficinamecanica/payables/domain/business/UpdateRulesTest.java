package com.ikservices.oficinamecanica.payables.domain.business;

import com.ikservices.oficinamecanica.categories.domain.Category;
import com.ikservices.oficinamecanica.categories.domain.CategoryId;
import com.ikservices.oficinamecanica.commons.exception.IKException;
import com.ikservices.oficinamecanica.payables.domain.Payable;
import com.ikservices.oficinamecanica.payables.domain.PayableId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateRulesTest {

    private UpdateRules subject;
    private Payable payable;
    private Category category;

    @BeforeEach
    public void setup() {
        subject = new UpdateRules();

        payable = new Payable();
        payable.setId(new PayableId(1, 1L));

        category = new Category();
        category.setId(new CategoryId(1, 1L));
    }

    @Test
    public void testUpdateRulesValidation() {

        Assertions.assertEquals(
                "O campo Data de criação deve ser preenchido",
                Assertions.assertThrows(IKException.class, () -> subject.execute(payable)).getMessage()
        );

        payable.setCreationDate(LocalDate.now());

        Assertions.assertEquals(
                "O campo Descrição deve ser preenchido",
                Assertions.assertThrows(IKException.class, () -> subject.execute(payable)).getMessage()
        );

        payable.setDescription("Test");

        Assertions.assertEquals(
                "O campo Data de Vencimento deve ser preenchido",
                Assertions.assertThrows(IKException.class, () -> subject.execute(payable)).getMessage()
        );

        payable.setDueDate(LocalDate.now().plusDays(5));

        Assertions.assertEquals(
                "O campo Valor deve ser preenchido",
                Assertions.assertThrows(IKException.class, () -> subject.execute(payable)).getMessage()
        );

        payable.setPayValue(BigDecimal.TEN);

        Assertions.assertEquals(
                "O campo Categoria deve ser preenchido",
                Assertions.assertThrows(IKException.class, () -> subject.execute(payable)).getMessage()
        );

        category.getId().setCategoryId(3); // 3 = Supplier type
        payable.setCategory(category);

        Assertions.assertEquals(
                "O campo Fornecedor deve ser preenchido",
                Assertions.assertThrows(IKException.class, () -> subject.execute(payable)).getMessage()
        );

    }

    @Test
    public void testWhenCategoryIsNotSupplier() {
        payable.setCreationDate(LocalDate.now());
        payable.setDescription("Test");
        payable.setDueDate(LocalDate.now().plusDays(5));
        payable.setPayValue(BigDecimal.TEN);
        payable.setSupplierId(1);
        category.getId().setCategoryId(1); // 1 = consumption
        payable.setCategory(category);

        subject.execute(payable);

        Assertions.assertNull(payable.getSupplierId());
    }

    @Test
    public void testWhenIsPaidCannotBeChanged() {
        payable.setCreationDate(LocalDate.now());
        payable.setDescription("Test");
        payable.setDueDate(LocalDate.now().plusDays(5));
        payable.setPayValue(BigDecimal.TEN);
        category.getId().setCategoryId(1); // 1 = consumption
        payable.setCategory(category);
        payable.setPayDate(LocalDate.now().minusDays(1));

        Assertions.assertEquals(
                "Esta conta não pode ser alterada porque já foi paga",
                Assertions.assertThrows(IKException.class, () -> subject.execute(payable)).getMessage()
        );
    }
}


