package com.vulinh.data.entity;

import module java.base;

import com.vulinh.data.base.AbstractEntity;
import com.vulinh.data.base.JpaIdentifiable.DynamicJpaIdentifiable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Category extends AbstractEntity<Long> implements DynamicJpaIdentifiable<Long> {

  @Serial private static final long serialVersionUID = 8637466660631821214L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_generator")
  @SequenceGenerator(name = "category_id_generator", sequenceName = "category_id_seq", allocationSize = 1)
  private Long id;

  private String name;
}
