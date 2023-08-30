package otm.mto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author sairaghavak
 */
@SpringBootApplication
public class SpringBootMainClass {

  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext =
        SpringApplication.run(SpringBootMainClass.class, args);

    InvoiceRepository invoiceRepository = applicationContext.getBean(InvoiceRepository.class);

    Invoice invoice = new Invoice(5.5);
    InvoiceLine invoiceLine1 = new InvoiceLine("potato", 2.5, 1, invoice);
    InvoiceLine invoiceLine2 = new InvoiceLine("tomato", 3.0, 1, invoice);
    InvoiceLine duplicate = new InvoiceLine("tomato", 3.0, 1, invoice);
    Set<InvoiceLine> invoiceLines =
        new HashSet<>(Arrays.asList(invoiceLine1, invoiceLine2, duplicate));
    invoice.setInvoiceLines(invoiceLines);

    invoiceRepository.save(invoice);
    /**
     * This should also save/persist the invoiceLine as cascade=ALL is equivalent to
     * cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}
     */
    /*- If we delete/remove invoice like invoiceRepository.delete(invoice); it will delete all invoiceLines associated with this invoice because of CascadeType.REMOVE */

    /*-
     * usage of orphanRemoval = true with @OneToMany annotation in Invoice Entity
     * To remove an invoiceLine from an existing invoice. Fetch the required invoice, remove an invoiceLine and just save the invoice, it will delete the invoiceLine from invoice_line table
     *
     * Note: Without orphanRemoval=true it wouldn't delete the removed invoice_line say `potato`
     * from the invoice unlike the below example, it fails safely without triggering any delete
     * query on invoice_line table
     */

    Optional<Invoice> firstInvoice = invoiceRepository.findById(1l);
    if (firstInvoice.isPresent()) {
      Invoice invoiceFromDb = firstInvoice.get();
      Set<InvoiceLine> lines = invoiceFromDb.getInvoiceLines();
      lines.removeIf(invoiceLine -> invoiceLine.getItem().equals("potato"));
      invoiceRepository.save(invoiceFromDb);
    }

    // BiDirectional Relationship Check
    // Get all the invoice_line from invoice
    Optional<Invoice> invoiceById = invoiceRepository.findById(1l);
    if (invoiceById.isEmpty()) {
      throw new IllegalStateException("Invoice Not Found");
    }
    Set<InvoiceLine> invoiceLinesByInvoiceId = invoiceById.get().getInvoiceLines();
    System.out.println(
        String.format("Invoice %s has invoice_lines %s", invoice, invoiceLinesByInvoiceId));

    System.out.println("==================================================================");
    // Get the invoice associated with an invoice_line
    InvoiceLineRepository invoiceLineRepository =
        applicationContext.getBean(InvoiceLineRepository.class);
    Optional<InvoiceLine> invoiceLine = invoiceLineRepository.findById(2L); // potato is deleted
    if (invoiceLine.isEmpty()) {
      throw new IllegalStateException("InvoiceLine Not Found");
    }
    InvoiceLine invoiceLineById = invoiceLine.get();
    Invoice invoiceFromInvoiceLineById = invoiceLineById.getInvoice();
    System.out.println(
        String.format(
            "InvoiceLine %s belongs to invoice %s", invoiceLineById, invoiceFromInvoiceLineById));
  }
}
