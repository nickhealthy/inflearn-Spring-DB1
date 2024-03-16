package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        /**
         * 기본 DriverManager - 항상 새로운 커넥션 획득
         * - 아래 OUTPUT을 보면 매번 새로운 커넥션을 획득하는 것을 확인할 수 있다.
         * OUTPUT:
         * 22:09:53.442 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
         * 22:09:53.510 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = conn4: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
         * 22:09:53.511 [Test worker] DEBUG o.s.j.d.DriverManagerDataSource --
         *                 Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
         * 22:09:53.512 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = conn5: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
         */
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        /**
         * 커넥션 풀링: HikariProxyConnection -> JdbcConnection
         * - DriverManagerDataSource와는 달리 커넥션을 사용하고 다시 커넥션 풀에 적재 후 재사용하기 때문에 쿼리를 처리하고 거의 같은 커넥션을 사용하는 것을 확인할 수 있다.
         * OUTPUT:
         * 22:19:11.225 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = HikariProxyConnection@882471736 wrapping conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
         *
         * 22:19:11.238 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = HikariProxyConnection@1594039997 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
         * 22:19:11.256 [Test worker] INFO  h.j.r.MemberRepositoryV1Test --
         *                 findMember = Member(memberId=memberV3, money=10000)
         *
         * 22:19:11.276 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = HikariProxyConnection@659059448 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
         * 22:19:11.279 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 resultSize = 1
         *
         * 22:19:11.279 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = HikariProxyConnection@124494140 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
         * 22:19:11.281 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = HikariProxyConnection@635288507 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
         * 22:19:11.282 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
         *                 get connection = HikariProxyConnection@593447952 wrapping conn2: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
         */
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);

    }

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV3", 10000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember = {}", findMember);
        assertThat(findMember).isEqualTo(member);

        // update: money: 10000 -> 20000
        repository.update(member.getMemberId(), 20000);
        Member updateMember = repository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId())).isInstanceOf(NoSuchElementException.class);
    }
}