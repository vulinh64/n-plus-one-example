package com.vulinh.data.entity;

import module java.base;

import com.vulinh.data.base.AbstractEntity;
import com.vulinh.data.base.JpaIdentifiable.DynamicJpaIdentifiable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Customer extends AbstractEntity<Long> implements DynamicJpaIdentifiable<Long> {

  @Serial private static final long serialVersionUID = -4610991767898726642L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_generator")
  @SequenceGenerator(
      name = "customer_id_generator",
      sequenceName = "customer_id_seq",
      allocationSize = 1)
  private Long id;

  private String name;

  private String email;

  @OneToMany(mappedBy = "customer")
  @BatchSize(size = 5)
  @ToString.Exclude
  @Builder.Default
  private List<Order> orders = new ArrayList<>();
}
