package com.playground.payment_service.adapters;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.playground.payment_service.adpaters.outbound.AbsaPaymentAdapter;
import com.playground.payment_service.domain.models.AbsaPaymentEntity;
import com.playground.payment_service.infrastructure.exceptions.BusinessException;
import com.playground.payment_service.infrastructure.persistence.JpaAbsaPaymentRepository;

@DataJpaTest
@Import(AbsaPaymentAdapter.class)
class AbsaPaymentAdapterTest {

    @Autowired
    private AbsaPaymentAdapter adapter;

    @Autowired
    private JpaAbsaPaymentRepository repository;

    private AbsaPaymentEntity buildEntity() {
        AbsaPaymentEntity e = new AbsaPaymentEntity();
        e.setOrderId("ORD-1");
        e.setAmount(new BigDecimal("10.00"));
        e.setStatus("PENDING");
        e.setCreatedAt(LocalDateTime.now());
        e.setBenificairyName("John");
        e.setBenificairyAccountNumber("123456");
        e.setBeneficiaryBankName("ABSA");
        return e;
    }

    @Test
    @DisplayName("save() doit persister une entité")
    void save_ok() {
        AbsaPaymentEntity saved = adapter.save(buildEntity());
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("findById() retourne l'entité existante")
    void findById_ok() {
        AbsaPaymentEntity saved = repository.save(buildEntity());
        AbsaPaymentEntity found = adapter.findById(saved.getId());
        assertThat(found.getOrderId()).isEqualTo("ORD-1");
    }

    @Test
    @DisplayName("findById() lance exception si absent")
    void findById_notFound() {
        assertThatThrownBy(() -> adapter.findById(999L))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("999");
    }

    @Test
    @DisplayName("findAll() retourne liste non vide")
    void findAll_ok() {
        repository.save(buildEntity());
        List<AbsaPaymentEntity> list = adapter.findAll();
        assertThat(list).hasSize(1);
    }

    @Test
    @DisplayName("findAll() -> exception si vide")
    void findAll_empty() {
        assertThatThrownBy(() -> adapter.findAll())
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("No ABSA payments");
    }

    @Test
    @DisplayName("update() met à jour les champs")
    void update_ok() {
        AbsaPaymentEntity saved = repository.save(buildEntity());
        AbsaPaymentEntity patch = buildEntity();
        patch.setOrderId("ORD-2");
        AbsaPaymentEntity updated = adapter.update(saved.getId(), patch);
        assertThat(updated.getOrderId()).isEqualTo("ORD-2");
    }

    @Test
    @DisplayName("deleteById() supprime")
    void delete_ok() {
        AbsaPaymentEntity saved = repository.save(buildEntity());
        boolean result = adapter.deleteById(saved.getId());
        assertThat(result).isTrue();
        assertThat(repository.count()).isZero();
    }
}
