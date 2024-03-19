package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - 트랜잭션 매니저(PlatformTransactionManager)
 * 결과적으로 서비스 계층을 단순화하고 하나의 기술에 종속되는 것을 막기 위해
 * 트랜잭션 매니저인 PlatformTransactionManager(인터페이스)를 사용하여 추상화와 동기화까지 처리할 수 있게 되었다.
 * - 기존에는 하나의 트랜잭션을 유지하기 위해 Connection을 파라미터로 받아서 처리를 진행하였지만, 불필요한 파라미터가 없어졌다.
 * - 서비스 계층은 최대한 유지보수가 단순해야하며 코드가 변경되지 않도록 하는 것이 중요한데 트랜잭션 매니저를 사용함으로서 추상화을 이룰 수 있게 되었고, 서비스 계층이 DB 기술에 의존도를 낮추게 되었다.
 */
class MemberServiceV3_1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_1 memberService;

    @BeforeEach
    void beforeEach() {
        // 커넥션을 생성
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        // 서비스 계층을 단순화하고, 커넥션을 추상화 및 동기화를 진행하기 위해 트랜잭션 매니저 사용(PlatformTransactionManager 객체를 사용)
        // 또한 트랜잭션 매니저는 데이터소스를 통해 커넥션을 생성하므로 DataSource 인자가 필요하다.
        // new DataSourceTransactionManager - 지금은 JDBC 기술을 사용하기 때문에 트랜잭션 매니저(인터페이스)의 JBBC 구현체인 객체를 사용함
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberRepository = new MemberRepositoryV3(dataSource);
        memberService = new MemberServiceV3_1(transactionManager, memberRepository);
    }

    @AfterEach
    void afterEach() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        // given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        // when
        memberService.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);

        // then
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberB.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(12000);
    }

    @Test
    @DisplayName("이체중 예외 발생")
    void accountTransferEx() throws SQLException {
        // given: 다음 데이터를 저장해서 테스트를 준비한다.
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        // when: 계좌이체 로직을 실행한다.
        // memberEx 회원의 ID는 ex 이므로 중간에 예외가 발생한다.
        assertThatThrownBy(() ->
                memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);

        // then: memberA의 돈이 롤백 되어야함
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberEx.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(10000);
        assertThat(findMemberB.getMoney()).isEqualTo(10000);
    }
}