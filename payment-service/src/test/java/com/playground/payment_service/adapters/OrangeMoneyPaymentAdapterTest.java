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

import com.playground.payment_service.adpaters.outbound.OrangeMoneyPaymentAdapter;
import com.playground.payment_service.domain.models.OrangeMoneyEntity;
import com.playground.payment_service.infrastructure.exceptions.BusinessException;
import com.playground.payment_service.infrastructure.persistence.JpaOrangeMoneyPaymentRepository;

@DataJpaTest
@Import(OrangeMoneyPaymentAdapter.class)
class OrangeMoneyPaymentAdapterTest {

    @Autowired
    private OrangeMoneyPaymentAdapter adapter;

    @Autowired
    private JpaOrangeMoneyPaymentRepository repository;

    private OrangeMoneyEntity buildEntity() {
        OrangeMoneyEntity e = new OrangeMoneyEntity();
        e.setOrderId("ORD-OM-1");
        e.setAmount(new BigDecimal("25.00"));
        e.setStatus("PENDING");
        e.setCreatedAt(LocalDateTime.now());
        e.setBenificairyName("Alice");
        e.setPhoneNumber("+237600000000");
        e.setEmailAddress("alice@example.com");
        return e;
    }

    @Test
    @DisplayName("save() persiste")
    void save_ok() {
        OrangeMoneyEntity saved = adapter.save(buildEntity());
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @DisplayName("findById() ok")
    void findById_ok() {
        OrangeMoneyEntity saved = repository.save(buildEntity());
        OrangeMoneyEntity found = adapter.findById(saved.getId());
        assertThat(found.getOrderId()).isEqualTo("ORD-OM-1");
    }

    @Test
    @DisplayName("findById() not found")
    void findById_notFound() {
        assertThatThrownBy(() -> adapter.findById(321L))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("321");
    }

    @Test
    @DisplayName("findAll() non vide")
    void findAll_ok() {
        repository.save(buildEntity());
        List<OrangeMoneyEntity> list = adapter.findAll();
        assertThat(list).hasSize(1);
    }

    @Test
    @DisplayName("findAll() vide -> exception")
    void findAll_empty() {
        assertThatThrownBy(() -> adapter.findAll())
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("No Orange Money payments");
    }

    @Test
    @DisplayName("update() modifie")
    void update_ok() {
        OrangeMoneyEntity saved = repository.save(buildEntity());
        OrangeMoneyEntity patch = buildEntity();
        patch.setOrderId("ORD-OM-2");
        OrangeMoneyEntity updated = adapter.update(saved.getId(), patch);
        assertThat(updated.getOrderId()).isEqualTo("ORD-OM-2");
    }

    @Test
    @DisplayName("deleteById() supprime")
    void delete_ok() {
        OrangeMoneyEntity saved = repository.save(buildEntity());
        boolean result = adapter.deleteById(saved.getId());
        assertThat(result).isTrue();
        assertThat(repository.count()).isZero();
    }
}
