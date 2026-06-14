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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Author extends AbstractEntity<Long> implements DynamicJpaIdentifiable<Long> {

  @Serial private static final long serialVersionUID = -3605874675614348758L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
  @SequenceGenerator(
      name = "author_id_generator",
      sequenceName = "author_id_seq",
      allocationSize = 1)
  private Long id;

  private String name;

  @OneToMany(mappedBy = "author")
  @ToString.Exclude
  @Builder.Default
  private List<Book> books = new ArrayList<>();
}
