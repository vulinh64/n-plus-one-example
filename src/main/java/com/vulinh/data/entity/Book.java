package com.vulinh.data.entity;

import module java.base;

import com.vulinh.data.base.AbstractEntity;
import com.vulinh.data.base.JpaIdentifiable.DynamicJpaIdentifiable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Book extends AbstractEntity<Long> implements DynamicJpaIdentifiable<Long> {

  @Serial private static final long serialVersionUID = 6912792636480220955L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
  @SequenceGenerator(name = "book_id_generator", sequenceName = "book_id_seq", allocationSize = 1)
  private Long id;

  private String title;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "author_id", nullable = false)
  @ToString.Exclude
  private Author author;
}
