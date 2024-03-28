Feature: Update User

  Background: User Menyiapkan data

  Scenario: [POSITIVE] Melakukan Update dengan Data yang Validas
    When User melakukan Update
    Then Berhasil Melakukan Update

  Scenario: [Negative] Melakukan Update dengan Memasukkan email kosong

    When Pada Email dikosongkan
    Then Tidak berhasil melakukan Update


  Scenario: [Negative] Melakukan Update dengan email yang sudah terdaftarkan
    When Update dengan email yang sudah digunakan
    Then Gagal melakukan Update