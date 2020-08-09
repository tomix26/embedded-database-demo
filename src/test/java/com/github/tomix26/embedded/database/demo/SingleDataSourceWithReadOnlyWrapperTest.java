package com.github.tomix26.embedded.database.demo;

import com.github.tomix26.embedded.database.demo.domain.Person;
import com.github.tomix26.embedded.database.demo.domain.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Optional;
import java.util.logging.Logger;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase(beanName = "primaryDataSource")
@ActiveProfiles("test") // the test profile allows to overwrite the existing bean from ReadOnlyDatabaseConfiguration
public class SingleDataSourceWithReadOnlyWrapperTest {

	@TestConfiguration
	static class Config {

		@Bean("readOnlyDataSource")
		public DataSource readOnlyDataSource(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
			return new ReadOnlyDataSourceWrapper(primaryDataSource);
		}
	}

	@Autowired
	private PersonRepository personRepository;

	@Test
	public void testEmbeddedDatabase() {
		Optional<Person> personOptional = personRepository.findById(1L);

		assertThat(personOptional).hasValueSatisfying(person -> {
			assertThat(person.getId()).isNotNull();
			assertThat(person.getFirstName()).isEqualTo("Dave");
			assertThat(person.getLastName()).isEqualTo("Syer");
		});
	}

	// the wrapper should be used only in integration tests, it's NOT production ready
	private static class ReadOnlyDataSourceWrapper implements DataSource {

		private final DataSource delegate;

		public ReadOnlyDataSourceWrapper(DataSource delegate) {
			this.delegate = delegate;
		}

		@Override
		public Connection getConnection() throws SQLException {
			Connection connection = delegate.getConnection();
			connection.setReadOnly(true);
			return connection;
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			Connection connection = delegate.getConnection(username, password);
			connection.setReadOnly(true);
			return connection;
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException {
			return delegate.getLogWriter();
		}

		@Override
		public void setLogWriter(PrintWriter out) throws SQLException {
			delegate.setLogWriter(out);
		}

		@Override
		public void setLoginTimeout(int seconds) throws SQLException {
			delegate.setLoginTimeout(seconds);
		}

		@Override
		public int getLoginTimeout() throws SQLException {
			return delegate.getLoginTimeout();
		}

		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			return delegate.getParentLogger();
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return delegate.unwrap(iface);
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return delegate.isWrapperFor(iface);
		}
	}
}
