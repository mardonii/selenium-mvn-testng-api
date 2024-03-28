Feature: Create New User

  Background: User preparing data

  Scenario: [POSITIVE] Membuat User baru dengan Data yang Valid
    And User Hit Endpoint
    Then Response is okay

  Scenario: [Negative] Membuat User baru dengan Memasukkan email null
    When Email dikosongkan
    Then Response email cant be blank


  Scenario: [Negative] Membuat User dengan Email yang sudah terdaftarkan
    When Status diisi dengan selain active & inactive
    Then Response email sudah ada