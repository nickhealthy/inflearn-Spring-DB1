package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 * 서비스 계층을 단순화하고 커네션을 추상화 및 동기화를 진행하기 위해 트랜잭션 매니저 사용(PlatformTransactionManager 객체를 사용)
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager; // 트랜잭션 매니저(인터페이스)
    private final MemberRepositoryV3 memberRepository;

    // 계좌이체 메서드
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        // 트랜잭션 시작, status는 현재 트랜잭션의 상태 정보가 포함되어 있다. 이후 트랜잭션을 커밋, 롤백할 때 필요하다.
        // new DefaultTransactionDefinition - 트랜잭션과 관련된 옵션을 설정할 수 있다. 자세한 내용은 뒤에서 진행..
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 비즈니스 로직
            bizLogic(fromId, toId, money);
            transactionManager.commit(status); // 성공시 커밋
        } catch (Exception e) {
            transactionManager.rollback(status); // 실패시 롤백
            throw new IllegalStateException(e);
        } // 트랜잭션 매니저를 사용하게 되면 트랜잭션이 종료된 것이므로 commit, rollback에 내부적으로 트랜잭션을 종료하는 로직이 들어가 있다.

    }


    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }


    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
