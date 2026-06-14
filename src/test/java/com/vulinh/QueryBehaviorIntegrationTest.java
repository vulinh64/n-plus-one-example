package com.vulinh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import org.hibernate.LazyInitializationException;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class QueryBehaviorIntegrationTest {

  @SuppressWarnings("resource")
  @Container
  static final PostgreSQLContainer<?> POSTGRES =
      new PostgreSQLContainer<>("postgres:18.3-alpine3.23")
          .withDatabaseName("example")
          .withUsername("postgres")
          .withPassword("123456");

  @Autowired private MockMvc mockMvc;

  @Autowired private EntityManagerFactory entityManagerFactory;

  private Statistics statistics;

  @DynamicPropertySource
  static void configurePostgres(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
  }

  @BeforeEach
  void resetStatistics() {
    statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
    statistics.clear();
  }

  @Test
  void noTransactionFailsWhenMapperAccessesDetachedLazyCollection() {
    var exception =
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/author/no-transaction")));
    assertEquals(LazyInitializationException.class, exception.getRootCause().getClass());

    assertPrepareStatementCount(1);
  }

  @Test
  void transactionalLazyLoadingProducesAuthorNPlusOneQueries() throws Exception {
    mockMvc
        .perform(get("/author/transactional"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(5))
        .andExpect(jsonPath("$.content[0].name").value("Jane Austen"))
        .andExpect(jsonPath("$.content[0].books.length()").value(3))
        .andExpect(jsonPath("$.page.totalElements").value(5));

    assertPrepareStatementCount(6);
  }

  @Test
  void authorEntityGraphLoadsAuthorsAndBooksWithOneQuery() throws Exception {
    mockMvc
        .perform(get("/author/entity-graph"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(5))
        .andExpect(jsonPath("$.content[?(@.name == 'J.R.R. Tolkien')].books.length()").value(4))
        .andExpect(jsonPath("$.page.totalElements").value(5));

    assertPrepareStatementCount(1);
  }

  @Test
  void authorJoinFetchLoadsAuthorsAndBooksWithOneQuery() throws Exception {
    mockMvc
        .perform(get("/author/join-fetch"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(5))
        .andExpect(jsonPath("$.content[?(@.name == 'George Orwell')].books.length()").value(2))
        .andExpect(jsonPath("$.page.totalElements").value(5));

    assertPrepareStatementCount(1);
  }

  @Test
  void batchFetchLoadsTenCustomersAndOrdersWithThreeQueries() throws Exception {
    mockMvc
        .perform(get("/customer/batch-fetch"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(10))
        .andExpect(jsonPath("$.content[0].name").value("Alice Johnson"))
        .andExpect(jsonPath("$.content[0].orders.length()").value(3))
        .andExpect(jsonPath("$.page.totalElements").value(10));

    assertPrepareStatementCount(3);
  }

  @Test
  void diyFetchLoadsTenCustomersAndOrdersWithTwoQueries() throws Exception {
    mockMvc
        .perform(get("/customer/diy"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(10))
        .andExpect(jsonPath("$.content[9].name").value("Jack Anderson"))
        .andExpect(jsonPath("$.content[9].orders.length()").value(3))
        .andExpect(jsonPath("$.page.totalElements").value(10));

    assertPrepareStatementCount(2);
  }

  @Test
  void multipleLazyCollectionsProduceOnePlusTwoNQueries() throws Exception {
    mockMvc
        .perform(get("/product/multiple-collection-n-plus-one"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(3))
        .andExpect(jsonPath("$.content[0].categories.length()").value(3))
        .andExpect(jsonPath("$.content[0].availableStores.length()").value(5))
        .andExpect(jsonPath("$.page.totalElements").value(3));

    assertPrepareStatementCount(7);
  }

  @Test
  void multipleCollectionEntityGraphLoadsEverythingWithOneQuery() throws Exception {
    mockMvc
        .perform(get("/product/multiple-collection-entity-graph"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.length()").value(3))
        .andExpect(jsonPath("$.content[0].categories.length()").value(3))
        .andExpect(jsonPath("$.content[0].availableStores.length()").value(5))
        .andExpect(jsonPath("$.page.totalElements").value(3));

    assertPrepareStatementCount(1);
  }

  private void assertPrepareStatementCount(long expected) {
    assertEquals(expected, statistics.getPrepareStatementCount());
  }
}
