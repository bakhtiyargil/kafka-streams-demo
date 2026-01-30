package az.baxtiyargil.kafkastreamsdemo.domain.entity;

import az.baxtiyargil.kafkastreamsdemo.messaging.event.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.Hibernate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "EVENT_OUTBOX")
public class OutboxEvent {

    @Id
    private UUID id;

    @Column(name = "aggregate_id", nullable = false)
    private String aggregateId;

    @Column(name = "aggregate_type", nullable = false)
    private String aggregateType;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(columnDefinition = "json")
    private String payload;

    @Column(name = "trace_id", nullable = false)
    private String traceId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        OutboxEvent outboxEvent = (OutboxEvent) o;
        return getId() != null && Objects.equals(getId(), outboxEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static OutboxEvent from(Event<?> event, String jsonPayload) {
        OutboxEvent o = new OutboxEvent();
        o.id = event.getEventId();
        o.aggregateId = event.getAggregateId();
        o.aggregateType = event.getClass().getSimpleName();
        o.eventType = event.getType();
        o.payload = jsonPayload;
        o.traceId = event.getTraceId();
        o.status = "PENDING";
        o.createdAt = LocalDateTime.now();
        return o;
    }
}
