Feature: Test our crud AbsaPaymentEntity operations

    TEST Create, Read, Update and Delete scenario

    Scenario: Save a payment successfuly
        Given Initialise payment request and put default value if needed :
            | orderId | amount | status | createdAt | benificairyName | benificairyAccountNumber | beneficiaryBankName |
            | ABC123 | 1000 ||| Arnold | 2334 4112 2000 | Absa bank |
        When I save the payment AbsaPaymentEntity
        Then The payment should be registered with:
            | status      | amount | benificiaryName | benificairyAccountNumber | beneficiaryBankName |
            | Pending     | 1000   | Arnold          | 2334 4112 2000 | Absa bank |

    Scenario: Find a payment by id successfuly
        Given Initialise payment request and put default value if needed :
            | orderId | amount | status | createdAt | benificairyName | benificairyAccountNumber | beneficiaryBankName |
            | ABC123 | 1000 ||| Arnold | 2334 4112 2000 | Absa bank |
        And I save the payment AbsaPaymentEntity
        When I get the payment by id 1
        Then The payment should be find with:
            | id | orderId | amount | status |
            | 1  | ABC123 | 1000.00 | Pending |

    Scenario: Find a payment by non-existing id should fail
        When I try to get the payment by id 999
        Then I should get an exception with message "ABSA payment not found with id : 999"
        And The exception should be of type "BusinessException" and have status code 404

    Scenario: Find all payments successfully
        Given Initialise payment request and put default value if needed :
            | orderId | amount | status | createdAt | benificairyName | benificairyAccountNumber | beneficiaryBankName |
            | ABC123 | 1000 ||| Arnold | 2334 4112 2000 | Absa bank |
        And I save the payment AbsaPaymentEntity
        When I get all payments
        Then I should get a list with 1 payment
        And The first payment should have orderId "ABC123"

    Scenario: Find all payments when none exist should fail
        When I try to get all payments
        Then I should get an exception with message "No ABSA payments found"
        And The exception should be of type "BusinessException" and have status code 404

    Scenario: Update a payment successfully
        Given Initialise payment request and put default value if needed :
            | orderId | amount | status | createdAt | benificairyName | benificairyAccountNumber | beneficiaryBankName |
            | ABC123 | 1000 ||| Arnold | 2334 4112 2000 | Absa bank |
        And I save the payment AbsaPaymentEntity
        When I update the payment with id 1 with new amount "2000"
        Then The payment should be updated with amount "2000"

    Scenario: Update a non-existing payment should fail
        Given I prepare update data with amount "2000"
        When I try to update payment with id 999
        Then I should get an exception with message "ABSA payment not found with id : 999"
        And The exception should be of type "BusinessException" and have status code 404

    Scenario: Delete a payment successfully
        Given Initialise payment request and put default value if needed :
            | orderId | amount | status | createdAt | benificairyName | benificairyAccountNumber | beneficiaryBankName |
            | ABC123 | 1000 ||| Arnold | 2334 4112 2000 | Absa bank |
        And I save the payment AbsaPaymentEntity
        When I delete the payment with id 1
        Then The payment should be deleted successfully

    Scenario: Delete a non-existing payment should fail
        When I try to delete payment with id 999
        Then I should get an exception with message "ABSA payment not found with id : 999"
        And The exception should be of type "BusinessException" and have status code 404