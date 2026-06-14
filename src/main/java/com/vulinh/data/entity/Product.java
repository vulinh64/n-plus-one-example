package com.vulinh.data.entity;

import module java.base;

import com.vulinh.data.base.AbstractEntity;
import com.vulinh.data.base.JpaIdentifiable.DynamicJpaIdentifiable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Product extends AbstractEntity<Long> implements DynamicJpaIdentifiable<Long> {

  @Serial private static final long serialVersionUID = -2744736107567813186L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
  @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq", allocationSize = 1)
  private Long id;

  private String name;

  @ManyToMany
  @JoinTable(
      name = "product_category",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id"))
  @ToString.Exclude
  @Builder.Default
  private Set<Category> categories = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "product_available_store",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "store_id"))
  @ToString.Exclude
  @Builder.Default
  private Set<Store> availableStores = new HashSet<>();
}
