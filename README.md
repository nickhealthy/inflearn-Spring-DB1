## 섹션 1 | JDBC 이해

애플리케이션을 개발할 때 중요한 데이터는 대부분 데이터베이스에 보관한다.

애플리케이션 서버는 다음 과정을 통해 데이터베이스를 사용한다.

![스크린샷 2024-03-16 오후 2 36 20](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/f723ccf9-1a43-4293-ad84-70feb7509387)

1. **커넥션 연결**: 주로 TCP/IP를 사용해서 커넥션을 연결한다.
2. **SQL 전달**: 애플리케이션 서버는 DB가 이해할 수 있는 SQL을 연결된 커넥션을 통해 DB에 전달한다.
3. **결과응답**: DB는 전달된 SQL을 수행하고, 그 결과를 응답하한다. 애플리케이션 서버는 응답 결과를 활용한다.



### JDBC 표준 인터페이스 등장 이유

다음과 같은 2가지 큰 문제점이 있다. 아래와 같은 문제를 해결하기 위해 JDBC가 등장하게 된다.

1. 데이터베이스를 다른 종류의 데이터베이스로 변경하면 애플리케이션 서버에 개발된 데이터베이스 사용 코드도 함께 변경해야 한다.
2. 개발자가 각각의 데이터베이스마다 커넥션 연결, SQL 전달, 그리고 그 결과를 응답 받는 방법을 새로 학습해야 한다.



**JDBC(Java Database Connectivity)는 자바에서 데이터베이스에 접속할 수 있도록 하는 자바 API이다.** JDBC는 데이터베이스에서 자료를 쿼리하는나 업데이트 하는 방법을 제공한다.

* 자바는 아래와 같이 같이 표준 인터페이스를 정의해두었는데, 개발자는 이 표준 인터페이스를 사용해서 개발하면 된다.
* 인터페이스만 있다고 개발이 가능한 것은 아니고, 각 DB 벤더에서 자신의 DB에 맞도록 구현해서 라이브러리로 제공하는데, 이것을 <u>JDBC 드라이버</u>라고 한다.



대표적으로 다음 3가지 기능을 표준 인터페이스로 정의해서 제공한다.

* `java.sql.Connection` - 연결
* `java.sql.Statement` - SQL을 담은 내용
* `java.sql.ResultSet` - SQL 요청 응답



![스크린샷 2024-03-16 오후 2 45 23](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/9f1846d5-9d02-4a66-bea1-3a69548c84af)



### 정리

JDBC의 등장으로 다음과 같은 문제가 해결되었다.

1. 데이터베이스를 다른 종류의 데이터베이스로 변경하더라도 애플리케이션 로직은 이제 JDBC 표준 인터페이스에만 의존하므로, 다른 종류의 데이터베이스로 변경하려면 JDBC 구현 라이브러리만 변경하면 된다.
2. 개발자가 각각의 데이터베이스마다 커넥션 연결, SQL 전달, 그리고 그 결과를 응답 받는 방법을 새로 학습하지 않고 JDBC 표준 인터페이스 사용법만 학습하면 된다.



### 표준화의 한계점

JDBC의 등장으로 많은 것이 편리해졌지만, <u>각각의 데이터베이스마다 SQL, 데이터타입 등의 일부 사용법이 다르다.</u>
ANSI SQL이라는 표준이 있긴 하지만 일반적인 부분만 공통했기 때문에 한계가 있다.
<u>결국 데이터베이스를 변경하면 JDBC 코드는 변경하지 않아도 되지만 SQL은 해당 데이터베이스에 맞도록 변경해야한다.</u>



## JDBC와 최신 데이터 접근 기술

JDBC로 인해 많은 것이 편해졌지만 여전히 사용하는 방법이 복잡하다. 그래서 최근에는 JDBC를 직접 사용하기 보단 JDBC를 편리하게 사용하는 다양한 기술이 존재하는데, 대표적으로 SQL Mapper와 ORM 기술로 나눌 수 있다.



![스크린샷 2024-03-16 오후 3 10 15](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/9fe31173-afae-49a8-b2b5-45af7aed5331)

* SQL Mapper
  * 장점: JDBC를 편리하게 사용하도록 도와준다.
    * SQL 응답 결과를 객체로 편리하게 반환해준다.
    * JDBC의 반복 코드를 제거해준다.
  * 단점: 개발자가 직접 SQL을 작성해야한다.
  * 대표 기술: 스프링 JdbcTemplate, MyBatis



![스크린샷 2024-03-16 오후 3 13 06](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/cfa38246-4d78-446a-8713-0478f617df26)

* ORM 기술 
  * <u>ORM은 객체를 관계형 데이터베이스 테이블과 매핑해주는 기술이다.</u> 이 기술 덕분에 개발자는 반복적인 SQL을 직접 작성하지 않고, ORM 기술이 개발자 대신에 SQL을 동적으로 만들어 실행해준다.
  * 대표 기술: JPA, 하이버네이트, 이클립스링크
  * <u>JPA는 자바 진영의 ORM 표준 인터페이스이고,</u> 이것을 구현한 것이 하이버네이트와 이클립스 링크 등의 구현 기술이 있다.



### SQL Mapper vs ORM 기술

* SQL Mapper는 SQL만 직접 작성하면 나머지 번거로운 일은 SQL Mapper가 대신 해결해준다.
  SQL만 작성할 줄 알면 금방 배워서 사용할 수 있다.
* ORM 기술은 SQL 자체를 작성하지 않아도 되어서 개발 생산성이 매우 높아진다. 편리한 반면 쉬운 기술은 아니므로 실무에서 사용하려면 깊이있게 학습해야 한다.
* **이런 기술들도 내부에서는 모두 JDBC를 사용하므로, JDBC가 어떻게 동작하는지 기본 원리를 알아두어야 한다.**



## 데이터베이스 연결

> H2 데이터베이스를 사용하였다.



[ConnectionConst] - 데이터베이스에 접속하는데 필요한 기본 정보를 상수로 정의

```java
package hello.jdbc.connection;

public abstract class ConnectionConst {
    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
```



[DBConnectionUtil] - JDBC를 사용해서 실제 데이터베이스에 연결하는 코드

* `DriverManager.getConnection()`: 데이터베이스에 연결하려면 JDBC가 제공하는 해당 메서드를 사용하면 된다.
  * 해당 메서드를 사용하면 데이터베이스 드라이버를 찾아서 <u>해당 드라이버가 제공하는 커넥션을 반환해준다.</u>
  * 여기서는 H2 데이터베이스 드라이버가 작동해서 실제 데이터베이스와 커넥션을 맺고 그 결과를 반환해준다.

```java
package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

/**
 * JDBC를 사용해서 실제 데이터베이스에 연결하는 코드
 */
@Slf4j
public class DBConnectionUtil {

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection = {}, class = {}", connection, connection.getClass());
            return connection;

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
```



[DBConnectionUtilTest] - 데이터베이스 연결 테스트

```java
package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DBConnectionUtilTest {

    @Test
    void connection() {
        Connection connection = DBConnectionUtil.getConnection();
        assertThat(connection).isNotNull();
    }
}
```



실행 결과

* `class=class org.h2.jdbc.JdbcConnection`: H2 데이터베이스 드라이버가 제공하는 H2 전용 커넥션이다.
  * 물론 이 커넥션은 JDBC 표준 커넥션 인터페이스인 `java.sql.Connection` 인터페이스를 구현하고 있음

```cmd
DBConnectionUtil - get connection=conn0: url=jdbc:h2:tcp://localhost/~/test
 user=SA, class=class org.h2.jdbc.JdbcConnection
```



### JDBC DriverManager 연결 이해

다형성처럼 각 벤더사의 JDBC 드라이버는 `java.sql.Connection` 표준 커넥션 인터페이스를 구현체를 제공하고 있다.

#### DriverManager 커넥션 요청 흐름

![스크린샷 2024-03-16 오후 3 26 44](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/a4b30017-0fef-424f-abf4-0800a9d2bd80)

**JDBC가 제공하는 `DriverManager`는 라이브러리에 등록된 DB 드라이버들을 관리하고, 커넥션을 획득하는 기능을 제공한다.**

1. 애플리케이션 로직에서 커넥션이 필요하면 `DriverManager.getConnection()`dmf ghcnf
2. DriverManager는 라이브러리에 등록된 드라이버 목록을 자동으로 인식한다. 이 드라이버들에게 순서대로 다음 정보를 넘겨 커넥션을 획득할 수 있는지 확인한다.
   * URL: 예) `jdbc:h2:tcp://localhost/~/test`
   * 이름, 비밀번호 등 접속에 필요한 추가 정보
   * 각각의 드라이버는 URL 정보를 체크해서 자신이 처리할 수 있는 요청인지 확인한다. 드라이버 자신이 처리할 수 있는 요청이면 실제 데이터베이스에 연결해서 커넥션을 획득하고 이 커넥션을 클라이언트에게 반환한다.
     * 예를 들어 `jdbc:h2`로 시작하면 h2 데이터베이스에 접근하기 위한 규칙이다.
3. 이렇게 찾은 커넥션을 클라이언트에게(애플리케이션) 반환한다.



## JDBC 개발 - 등록, 조회, 수정, 삭제

JDBC를 사용해서 회원(`Member`) 데이터를 데이터베이스에 관리하는 기능을 개발한다.

> 해당 실습을 진행하기 위해선 데이터베이스에 member 테이블이 먼저 생성되어 있어야 한다.



[Member] - member 테이블에 데이터를 저장하고 조회할 때 사용한다.

* 회원의 ID와 해당 회원이 소지한 금액을 표현하는 클래스

```java
package hello.jdbc.domain;

import lombok.Data;

@Data
public class Member {

    private String memberId;
    private int money;

    public Member() {
    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }
}
```



[MemberRepositoryV0] - 회원 등록(JDBC를 사용하여 데이터베이스에 저장)

* `getConnection()`: 이전에 만들어 둔 `DBConnectionUtil`를 통해 데이터베이스 커넥션을 획득한다.
* `con.prepareStatement(sql);`: 데이터베이스에 전달한 SQL과 파라미터로 전달할 데이터들을 준비한다.
* `pstmt.executeUpdate();`: `Statement`를 통해 준비된 SQL을 커넥션을 통해 실제 데이터베이스에 전달한다.
* `pstmt.executeQuery();`: 데이터를 조회할 땐 해당 메서드를 사용하며, 반환 결과로 `ResultSet`에 담아 반환한다.
* `ResultSet`은 내부에 있는 커서를 이동해서 다음 데이터를 조회할 수 있다.
  * `rs.next()`: 최초의 커서는 데이터를 가리키고 있지 않기 때문에 `rs.next`를 최초 한번은 호출해야 데이터를 조회 가능하다.

```java
package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0 {

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member (member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId = {}" + memberId);
            }

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);

        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

    }
}

```



#### 리소스 정리

* 리소스 정리를 하지 않게 되면, 리소스 누수가 발생하게 되는데 결과적으로 커넥션 부족으로 장애가 발생할 수 있다.
* 따라서 리소스는 항상 수행해야하므로 `finally` 키워드에 작성해야하며, 리소스를 정리해야 할 땐 생성된 순서의 역순으로 자원을 해제해야 한다. 
  * 위의 예제에서는 `Connection`을 통해 `PreparedStatement`을 만들었기 때문에 리소스를 반환할 땐 `PreparedStatement -> Connection` 순으로 리소스를 해제해야한다.



#### 참고

`PreparedStatement`는 `Statement`의 자식 타입인데, `?`를 통한 파라미터 바인딩을 가능하게 해준다.
<u>SQL Injection 공격을 예방하려면 `PreparedStatement`를 통한 파라미터 바인딩 방식을 사용해야한다.</u>



[MemberRepositoryV0Test] - 회원 등록, 조회, 수정, 삭제

```java
package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

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
```



#### 참고

<u>테스트는 반복해서 실행 가능한 것이 중요하다.</u>
테스트 중간에 오류가 발생해서 삭제 로직을 수행할 수 없다면 테스트를 반복해서 실행할 수 없다.
따라서 해당 코드는 좋은 코드라 볼 수 없고, 트랜잭션을 활용하면 문제를 깔끔히 해결할 수 있다고 한다.