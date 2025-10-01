Feature: Payment Polymorphic Deserialization
  As a payment API
  I want to deserialize different payment request types based on provider
  So that I can handle multiple payment providers with the same endpoint

  Scenario: Deserialize ABSA payment request
    Given I have an ABSA payment request JSON:
      """
      {
        "provider": "absa",
        "orderId": "ORD-123456",
        "amount": 100.50,
        "benificairyName": "John Doe",
        "benificairyAccountNumber": "1234567890",
        "beneficiaryBankName": "ABSA Bank"
      }
      """
    When I send the payment request
    Then the request should be deserialized as AbsaPaymentRequest
    And the payment request should have the following values:
      | provider | orderId     | amount | benificairyAccountNumber | beneficiaryBankName |
      | absa     | ORD-123456  | 100.50 | 1234567890               | ABSA Bank           |

  Scenario: Deserialize Orange Money payment request
    Given I have an Orange Money payment request JSON:
      """
      {
        "provider": "om",
        "orderId": "OM-789012",
        "amount": 250.75,
        "benificairyName": "Jane Smith",
        "phoneNumber": "+237123456789",
        "emailAddress": "jane.smith@example.com"
      }
      """
    When I send the payment request
    Then the request should be deserialized as OrangeMoneyPaymentRequest
    And the payment request should have the following values:
      | provider | orderId    | amount | phoneNumber     | emailAddress           |
      | om       | OM-789012  | 250.75 | +237123456789   | jane.smith@example.com |

  Scenario: Invalid provider should fail deserialization
    Given I have an invalid payment request JSON:
      """
      {
        "provider": "unknown_provider",
        "orderId": "INV-123",
        "amount": 50.00,
        "benificairyName": "Test User"
      }
      """
    When I send the payment request
    Then the request should fail with deserialization error