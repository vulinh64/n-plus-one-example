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
public class Store extends AbstractEntity<Long> implements DynamicJpaIdentifiable<Long> {

  @Serial private static final long serialVersionUID = 5057160571821373194L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_id_generator")
  @SequenceGenerator(name = "store_id_generator", sequenceName = "store_id_seq", allocationSize = 1)
  private Long id;

  private String name;
}
