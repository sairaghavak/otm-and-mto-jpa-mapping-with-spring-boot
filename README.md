# 1-Many and Many-1 JPA Mapping With Spring Boot

- Here is the list of important components in this SpringBoot Application.

  - **Entities**
    - `Invoice`
    - `InvoiceLine`

  - **Repository Interfaces**
    - `InvoiceRepository`
    - `InvoiceLineRepository`

  - **Tables**
    - `invoice`
    - `invoice_line`

- This sample application demonstrates the bi-directional assocaition between `Invoice` and `InvoiceLine` entities using JPA annitations `@OneToMany` and `@ManyToOne`.
- Also it throws some light on usage of `CascadeType.ALL`, `orhpanRemoval=true`, and `FetchType.EAGER`
