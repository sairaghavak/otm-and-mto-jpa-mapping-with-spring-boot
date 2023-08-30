package otm.mto;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author sairaghavak
 */
@Entity(name = "InvoiceLine")
@Table(name = "invoice_line")
@NoArgsConstructor
@Getter
public class InvoiceLine {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "invoice_line_id_pk")
  private Long id;

  @Column(name = "item")
  private String item;

  @Column(name = "unit_price")
  private Double unitPrice;

  @Column(name = "quantity")
  private Integer quantity;

  public InvoiceLine(String item, Double unitPrice, Integer quantity, Invoice invoice) {
    this.item = item;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
    this.invoice = invoice;
  }

  @ManyToOne
  @JoinColumn(name = "invoice_id_fk", referencedColumnName = "invoice_id_pk", nullable = false)
  private Invoice invoice;

  @Override
  public String toString() {
    return "{ item=" + item + ", unitPrice=" + unitPrice + ", quantity=" + quantity + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (!(o instanceof InvoiceLine)) return false;

    InvoiceLine that = (InvoiceLine) o;
    return this.getItem().equals(that.getItem())
        && this.getUnitPrice().equals(that.getUnitPrice())
        && this.getQuantity().equals(that.getQuantity());
  }

  @Override
  public int hashCode() {
    return Objects.hash(item, unitPrice, quantity);
  }
}
