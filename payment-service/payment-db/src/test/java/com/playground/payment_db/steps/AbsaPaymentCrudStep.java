package com.playground.payment_db.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.playground.payment.core.domain.enums.PaymentStatus;
import com.playground.payment.core.exceptions.BusinessException;
import com.playground.payment_db.infrastructure.persistence.entities.AbsaPaymentEntity;
import com.playground.payment_db.port.outbound.AbsaPaymentRepository;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AbsaPaymentCrudStep {

    private AbsaPaymentEntity payment;

    private BusinessException thrownException;

    private final AbsaPaymentRepository repository;

    private List<AbsaPaymentEntity> paymentList;
    private boolean deleteResult;
    private AbsaPaymentEntity updateData;

    @Given("Initialise payment request and put default value if needed :")
    public void testSuccessfullySaveInit(List<AbsaPaymentEntity> payments){
        payment = payments.get(0);
        payment.setStatus(PaymentStatus.PENDING.getStatus());
        payment.setCreatedAt(LocalDateTime.now());
    }

    @When("I save the payment AbsaPaymentEntity")
    public void testSuccessfullySave(){
        payment = repository.save(payment);
    }

    @Then("The payment should be registered with:")
    public void testSuccessfullySaveVerdict(DataTable dataTable){
        assertThat(payment).isNotNull();
        assertThat(payment.getStatus()).isEqualTo(dataTable.cell(1, 0));
        assertThat(payment.getAmount()).isEqualTo(dataTable.cell(1, 1));
        assertThat(payment.getBenificairyName()).isEqualTo(dataTable.cell(1, 2));
        assertThat(payment.getBenificairyAccountNumber()).isEqualTo(dataTable.cell(1, 3));
        assertThat(payment.getBeneficiaryBankName()).isEqualTo(dataTable.cell(1, 4));
        assertThat(payment.getCreatedAt()).isNotNull();
    }

    @When("I get the payment by id {long}")
    public void testGetPaymentEntityById(Long id){
        payment = repository.findById(id);
    }

    @Then("The payment should be find with:")
    public void testSuccessfullyFetch(DataTable dataTable){
        assertThat(payment.getId()).isEqualTo(Long.valueOf(dataTable.cell(1, 0)));
        assertThat(payment.getOrderId()).isEqualTo(dataTable.cell(1, 1));
        assertThat(payment.getAmount()).isEqualTo(dataTable.cell(1, 2));
        assertThat(payment.getStatus()).isEqualTo(dataTable.cell(1, 3));
    }


    @When("I try to get the payment by id {long}")
    public void iTryToGetThePaymentById(Long id) {
        try {
            payment = repository.findById(id);
        } catch (BusinessException e) {
            thrownException = e;
        }
    }

    @When("I get all payments")
    public void iGetAllPayments() {
        paymentList = repository.findAll();
    }

    @Then("I should get a list with {int} payment")
    public void iShouldGetAListWithPayments(int expectedCount) {
        assertThat(paymentList).isNotNull();
        assertThat(paymentList).hasSize(expectedCount);
    }

    @And("The first payment should have orderId {string}")
    public void theFirstPaymentShouldHaveOrderId(String expectedOrderId) {
        assertThat(paymentList).isNotEmpty();
        assertThat(paymentList.get(0).getOrderId()).isEqualTo(expectedOrderId);
    }

    @When("I try to get all payments")
    public void iTryToGetAllPayments() {
        try {
            paymentList = repository.findAll();
        } catch (BusinessException e) {
            thrownException = e;
        }
    }
    
    @When("I update the payment with id {long} with new amount {string}")
    public void iUpdateThePaymentWithIdWithNewAmount(Long id, String newAmount) {
        updateData = repository.findById(id);
        updateData.setAmount(new BigDecimal(newAmount));
        payment = repository.update(id, updateData);
    }

    @Given("I prepare update data with amount {string}")
    public void iPrepareUpdateDataWithAmount(String amount) {
        updateData = new AbsaPaymentEntity();
        updateData.setAmount(new BigDecimal(amount));
        thrownException = null;
    }

    @When("I try to update payment with id {long}")
    public void iTryToUpdatePaymentWithId(Long id) {
        try {
            payment = repository.update(id, updateData);
        } catch (BusinessException e) {
            thrownException = e;
        }
    }

    @Then("The payment should be updated with amount {string}")
    public void thePaymentShouldBeUpdatedWithAmount(String expectedAmount) {
        assertThat(payment).isNotNull();
        assertThat(payment.getAmount()).isEqualTo(expectedAmount);
    }

    @When("I delete the payment with id {long}")
    public void iDeleteThePaymentWithId(Long id) {
        deleteResult = repository.deleteById(id);
    }

    @When("I try to delete payment with id {long}")
    public void iTryToDeletePaymentWithId(Long id) {
        try {
            deleteResult = repository.deleteById(id);
        } catch (BusinessException e) {
            thrownException = e;
        }
    }

    @Then("The payment should be deleted successfully")
    public void thePaymentShouldBeDeletedSuccessfully() {
        assertThat(deleteResult).isTrue();
    }

    @Then("I should get an exception with message {string}")
    public void iShouldGetAnExceptionWithMessage(String expectedMessage) {
        assertThat(thrownException).isNotNull();
        assertThat(thrownException.getMessage()).isEqualTo(expectedMessage);
    }

    @And("The exception should be of type {string} and have status code {int}")
    public void theExceptionShouldBeOfTypeAndHaveStatusCode(String expectedType, int expectedStatusCode) {
        assertThat(thrownException.getClass().getSimpleName()).isEqualTo(expectedType);
        assertThat(thrownException.getStatusCode()).isEqualTo(expectedStatusCode);
    }

}
