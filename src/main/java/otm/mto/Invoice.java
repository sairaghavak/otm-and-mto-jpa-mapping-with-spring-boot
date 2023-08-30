package otm.mto;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author sairaghavak
 */
@Entity(name = "Invoice")
@Table(name = "invoice")
@NoArgsConstructor
@Getter
public class Invoice {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "invoice_id_pk")
  private Long id;

  @Column(name = "total_price")
  private Double totalPrice;

  @OneToMany(
      mappedBy = "invoice",
      cascade = CascadeType.ALL,
      orphanRemoval = true, // default is false
      fetch = FetchType.EAGER) // default is lazy
  /*- Note: With LAZY fetch it would result in this exception
  Exception in thread "main" org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: otm.mto.Invoice.invoiceLines: could not initialize proxy - no Session
  */
  private Set<InvoiceLine> invoiceLines;

  public Invoice(Double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public void setInvoiceLines(Set<InvoiceLine> invoiceLines) {
    this.invoiceLines = invoiceLines;
  }

  @Override
  public String toString() {
    return "{ invoice_id=" + id + ", totalPrice=" + totalPrice + "}";
  }
}
