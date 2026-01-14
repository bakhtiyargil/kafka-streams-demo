package az.baxtiyargil.kafkastreamsdemo.domain.entity;

import az.baxtiyargil.kafkastreamsdemo.error.exception.ApplicationErrorCodes;
import az.baxtiyargil.kafkastreamsdemo.error.exception.ApplicationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.Hibernate;
import java.util.Objects;

@Data
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_seq_gen")
    @SequenceGenerator(name = "inventory_id_seq_gen", sequenceName = "ISEQ$$_72319", allocationSize = 1)
    @Column(name = "inventory_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "store_id", nullable = false)
    private Long storeId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @NotNull
    @Column(name = "product_inventory", nullable = false)
    private Long productInventory;

    public void updateIfAvailableInventory(Integer quantity) {
        if (quantity > productInventory) {
            throw new ApplicationException(ApplicationErrorCodes.INSUFFICIENT_INVENTORY, this.productId, this.storeId);
        }
        this.productInventory = this.productInventory - quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Inventory inventory = (Inventory) o;
        return getId() != null && Objects.equals(getId(), inventory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
