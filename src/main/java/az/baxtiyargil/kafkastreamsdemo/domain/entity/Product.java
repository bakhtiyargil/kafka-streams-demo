package az.baxtiyargil.kafkastreamsdemo.domain.entity;

import az.baxtiyargil.kafkastreamsdemo.domain.converter.JsonNodeBlobConverter;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.Hibernate;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import static az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.SERIAL_VERSION_UID;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = SERIAL_VERSION_UID;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq_gen")
    @SequenceGenerator(name = "product_id_seq_gen", sequenceName = "ISEQ$$_72308", allocationSize = 1)
    @Column(name = "product_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @NotEmpty
    @Column(name = "product_name", nullable = false)
    private String name;

    @DecimalMin(value = "0.00")
    @Digits(integer = 8, fraction = 2)
    @NotNull
    @Column(
            name = "unit_price",
            precision = 10,
            scale = 2,
            nullable = false
    )
    private BigDecimal unitPrice;

    @Lob
    @Column(name = "product_details", columnDefinition = "BLOB")
    @Convert(converter = JsonNodeBlobConverter.class)
    private JsonNode details;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "product_image")
    private byte[] image;

    @Column(name = "image_mime_type", length = 512)
    private String imageMimeType;

    @Column(name = "image_filename", length = 512)
    private String imageFilename;

    @Column(name = "image_charset", length = 512)
    private String imageCharset;

    @Column(name = "image_last_updated")
    private LocalDateTime imageLastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
