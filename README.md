# 인프런 강의

해당 저장소의 `README.md`는 인프런 김영한님의 SpringBoot 강의 시리즈를 듣고 Spring 프레임워크의 방대한 기술들을 복기하고자 공부한 내용을 가볍게 정리한 것입니다.

문제가 될 시 삭제하겠습니다.



## 해당 프로젝트에서 배우는 내용

- 섹션 1 | JDBC 이해
- 섹션 2 | 커넥션풀과 데이터소스 이해
- 섹션 3 | 트랜잭션 이해
- 섹션 4 | 스프링과 문제 해결 - 트랜잭션
- 섹션 5 | 자바 예외 이해



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



# 섹션 2 | 커넥션풀과 데이터소스 이해

## 커넥션 풀 이해



![스크린샷 2024-03-16 오후 7 50 32](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/17caa93c-e914-4479-ab62-9a5d55907e63)

데이터베이스 커넥션을 획득하는 과정에서는 다음과 같은 복잡한 과정을 거친다.

1. 애플리케이션 로직은 DB 드라이버를 통해 커넥션을 조회한다.
2. DB 드라이버는 DB와 TCP/IP 커넥션을 연결한다. 물론 이 과정에서 3 way handshake 같은 TCP/IP 연결을 위한 네트워크 동작이 발생한다. 
3. DB 드라이버는 TCP/IP 커넥션이 연결되면 ID,PW와 기타 부가정보를 DB에 전달한다.
4. DB는 ID, PW를 통해 내부 인증을 완료하고, 내부에 DB 세션을 생성한다.
5. DB는 커넥션 생성이 완료되었다는 응답을 보낸다.
6. DB 드라이버는 커넥션 객체를 생성해서 클라이언트에 반환한다.



이러한 프로세스를 거치기때문에 결과적으로 애플리케이션 성능에 영향을 미치게되며, 사용자에게 좋지 않은 경험을 줄 수 있다.



이러한 문제를 해결하기 위한 것이 커넥션 풀이라는 방법이다.

1. **애플리케이션을 시작하는 시점에 커넥션 풀은 필요한 만큼 커넥션을 미리 확보해서 풀에 보관한다.**
2. 커넥션 풀에 들어 있는 커넥션은 TCP/IP로 DB와 커넥션이 연결되어 있는 상태이기 때문에 언제든지 즉시 SQL을 DB에 전달할 수 있다.
3. 애플리케이션은 이제 DB 드라이버를 통해 새로운 커넥션을 획득하는 것이 아닌, 생성되어 있는 커넥션을 객체 참조로 가져다 쓰면 된다.
4. 커넥션을 모두 사용하고 나면 이제는 커넥션을 종료하는 것이 아닌, 다음에 다시 사용할 수 있도록 해당 커넥션을 그대로 커넥션 풀에 반환하면 된다.



![스크린샷 2024-03-16 오후 7 55 06](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/c09dca8a-2594-4d8c-8091-ae27da286f40)



![스크린샷 2024-03-16 오후 7 55 14](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/ad13716a-7c9c-404e-bfc8-d4acc3f7fa25)



![스크린샷 2024-03-16 오후 7 58 27](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/3171298f-5f6b-449d-9f64-221b205a288e)



## DataSource 이해

커넥션을 얻는 방법은 JDBC `DriverManager`를 직접 사용하거나, 커넥션 풀을 사용하는 등 다양한 방법이 존재한다.

![스크린샷 2024-03-16 오후 8 07 59](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/e1a5058e-de00-439f-baaa-d0713191825c)





애플리케이션 로직에서 `DriverManager`를 사용하다가 `HikariCP` 같은 커넥션 풀을 사용하도록 변경하려면 애플리케이션 코드도 함께 변경해야 한다. 의존관계가 `DriverManager`에서 `HikariCP`로 변경되기 때문이다.

* 자바에서는 이러한 문제를 해결하기 위해 `DataSource`라는 제공하며, **커넥션을 획득하는 방법을 추상화** 하는 인터페이스이다.

![스크린샷 2024-03-16 오후 8 09 50](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/8bd61e92-69f9-4845-b56a-416e3d2dc3e1)



#### 정리

* 대부분의 커넥션 풀은 `DataSource` 인터페이스를 이미 구현했으므로, 애플리케이션은 `DataSource` 인터페이스에만 의존하도록 구현하면 된다.
  * 다른 커넥션 풀을 사용하고 싶다면 해당 구현체로 갈아끼우기만 하면 된다. 
* `DriverManager`는 `DataSource`를 인터페이스로 사용하지 않아 직접 고쳐야했는데, 스프링은 이런 문제를 해결하기 위해 `DriverManagerDataSource`라는 `DataSource`를 구현한 클래스를 제공한다.



## DataSource 예제1 - DriverManager

JDBC가 제공하던 `DriverManager`와 `DataSource`를 구현한 스프링이 제공하는 `DriverManagerDataSource`의 차이점을 알아보자



[ConnectionTest] - 각각 커넥션을 얻는 방법의 차이점

* `DriverManager`는 커넥션 객체를 얻을 때마다 설정 정보를 입력해야 하지만, 스프링이 제공하는 `DriverManagerDataSource`는 설정 정보를 한번만 입력해두고 `DataSource`(부모)객체를 통해서만 객체를 생성할 수 있다.
  * 설정과 사용의 분리로 인해 향후 변경에 더 유연하게 대처할 수 있다.

```java
package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    /**
     * DriverManager가 커넥션을 획득하는 방법
     * OUTPUT:
     * 20:17:30.836 [Test worker] INFO hello.jdbc.connection.ConnectionTest -- connection = conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
     * 20:17:30.838 [Test worker] INFO hello.jdbc.connection.ConnectionTest -- connection = conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
     */
    @Test
    void driverManager() throws SQLException {
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());

    }

    /**
     * 스프링이 제공하는 DataSource가 적용된 DriverManagerDataSource가 커넥션을 획득하는 방법
     * - 기존 코드와 비슷하지만 DataSource를 통해서 커넥션을 획득할 수 있으며, 설정과 사용의 분리가 명확하게 되어있다.
     * OUTPUT:
     * 20:20:36.537 [Test worker] INFO hello.jdbc.connection.ConnectionTest -- connection = conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
     * 20:20:36.540 [Test worker] INFO hello.jdbc.connection.ConnectionTest -- connection = conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
     */
    @Test
    void dataSourceDriverManager() throws SQLException {
        // DriverManagerDataSource - 항상 새로운 커넥션 획득
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());

    }
}
```





## DataSource 예제2 - 커넥션 풀

이번에는 `DataSource`를 통해 커넥션 풀을 사용하는 예제를 해보자



[ConnectionTest] - 데이터소스 커넥션 풀 추가

* 커넥션 풀에서 커넥션을 생성하는 작업은 애플리케이션 실행 속도에 영향을 주지 않기 위해 별도의 쓰레드에서 작동한다.
  * 만약 애플리케이션에서 커넥션 풀을 생성하기 위해 같은 쓰레드를 사용한다면, 커넥션 풀을 모두 생성하기 위해서 애플리케이션은 동작하지 않을 것이다.
* 커넥션 풀 최대 사이즈를 10으로 지정하고, 풀의 이름을 `MyPool`이라고 지정했다.

```java
package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    /**
     * HikariCP를 사용하여 커넥션 풀을 획득하는 방법
     * - 커넥션 풀에서 커넥션을 생성하는 작업은 애플리케이션 실행 속도에 영향을 주지 않기 위해 별도의 쓰레드에서 작동한다.
     * @throws SQLException
     * @throws InterruptedException
     */
    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        // 커넥션 풀링: HikariProxyConnection(Proxy) -> JdbcConnection(Target)
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        useDataSource(dataSource);
        Thread.sleep(1000); // 커넥션 풀에서 커넥션 생성 시간 대기

    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        log.info("connection = {}, class = {}", con1, con1.getClass());
        log.info("connection = {}, class = {}", con2, con2.getClass());

    }
}
```



#### 실행 결과

* [MyPool connection adder] - 커넥션 풀에 커넥션을 채우기 위한 별도의 쓰레드
* [Test worker] - Test를 수행하는 쓰레드
* 커넥션 획득에 로그를 보면 `com.zaxxer.hikari.pool.HikariProxyConnection` 클래스를 사용하고 있는데, 해당 클래스는 `HikariCP` 커넥션 풀에서 관리하는 실제 커넥션이며, wrapping 형태로 안에 JDBC 커넥션을 포함하고 있다.

```cmd
# 커넥션 풀 초기화 정보 출력
20:37:39.612 [Test worker] DEBUG com.zaxxer.hikari.HikariConfig --
                MyPool - configuration:
20:37:39.621 [Test worker] DEBUG com.zaxxer.hikari.HikariConfig --
                jdbcUrl.........................jdbc:h2:tcp://localhost/~/test
20:37:39.622 [Test worker] DEBUG com.zaxxer.hikari.HikariConfig --
                minimumIdle.....................10
20:37:39.622 [Test worker] DEBUG com.zaxxer.hikari.HikariConfig --
                password........................<masked>
20:37:39.622 [Test worker] DEBUG com.zaxxer.hikari.HikariConfig --
                poolName........................"MyPool"


# 커넥션 풀 전용 쓰레드가 커넥션 풀에 커넥션을 10개 채움
20:37:39.669 [Test worker] INFO  com.zaxxer.hikari.HikariDataSource --
                MyPool - Start completed.
20:37:39.675 [MyPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool --
                MyPool - Added connection conn1: url=jdbc:h2:tcp://localhost/~/test user=SA
20:37:39.692 [MyPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool --
                MyPool - Added connection conn2: url=jdbc:h2:tcp://localhost/~/test user=SA
20:37:39.774 [MyPool housekeeper] DEBUG com.zaxxer.hikari.pool.HikariPool --
                MyPool - Pool stats (total=8, active=2, idle=6, waiting=0)
20:37:39.789 [MyPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool --
                MyPool - Added connection conn8: url=jdbc:h2:tcp://localhost/~/test user=SA
20:37:39.807 [MyPool connection adder] DEBUG com.zaxxer.hikari.pool.HikariPool --
                MyPool - Added connection conn9: url=jdbc:h2:tcp://localhost/~/test user=SA

# 커넥션 풀에서 커넥션 획득 1
20:37:39.675 [Test worker] INFO  h.jdbc.connection.ConnectionTest --
                connection = HikariProxyConnection@379121284 wrapping conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
                
# 커넥션 풀에서 커넥션 획득 2
20:37:39.677 [Test worker] INFO  h.jdbc.connection.ConnectionTest --
                connection = HikariProxyConnection@2031377754 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
```



## DataSource 적용

이번에는 애플리케이션에 `DataSource` 를 적용해보자.



[MemberRepositoryV1] - `DataSource`를 통해 의존성 주입, `JdbcUtils` 사용으로 close 간소화

* 외부에서 `DataSource`를 주입 받아 사용하기 때문에 `DriverManagerDataSource, HikariDataSource`를 변경해서 사용하더라도 코드의 변경이 없다. (<u>DI + OCP</u>)
* 스프링에서 제공하는 `JdbcUtils` 사용으로 JDBC를 더 간편하게 사용할 수 있다.

```java
package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource 사용, JdbcUtils 사용
 * - 스프링은 JDBC를 편리하게 다룰 수 있는 JdbcUtils라는 편의 메서드를 제공한다.
 * - JdbcUtils을 사용하면 커넥션을 좀 더 편리하게 close 할 수 있다.
 */
@Slf4j
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    // 외부에서 DataSource를 주입 받아서 사용한다. 이를 통해 구현체가 달라지더라도 코드를 변경할 일이 없다.
    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection = {}, class = {}", con, con.getClass());
        return con;
    }

  	// save()
  	// findById()
  	// update()
  	// delete()

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);

    }
}
```



[MemberRepositoryV1Test] - `DataSource` 사용 테스트



```java
package hello.jdbc.repository;

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
        // 기본 DriverManager - 항상 새로운 커넥션 획득
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
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
```



#### 실행 결과 - DriverManagerDataSource

* 매번 새로운 커넥션을 획득하는 것을 확인할 수 있다.

```cmd
22:09:53.397 [Test worker] DEBUG o.s.j.d.DriverManagerDataSource --
                Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
22:09:53.442 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = conn0: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
22:09:53.461 [Test worker] DEBUG o.s.j.d.DriverManagerDataSource --
                Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
22:09:53.463 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
22:09:53.469 [Test worker] INFO  h.j.r.MemberRepositoryV1Test --
                findMember = Member(memberId=memberV3, money=10000)
22:09:53.491 [Test worker] DEBUG o.s.j.d.DriverManagerDataSource --
                Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
22:09:53.494 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = conn2: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
22:09:53.496 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                resultSize = 1
22:09:53.496 [Test worker] DEBUG o.s.j.d.DriverManagerDataSource --
                Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
22:09:53.500 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = conn3: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
22:09:53.507 [Test worker] DEBUG o.s.j.d.DriverManagerDataSource --
                Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
22:09:53.510 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = conn4: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection
22:09:53.511 [Test worker] DEBUG o.s.j.d.DriverManagerDataSource --
                Creating new JDBC DriverManager Connection to [jdbc:h2:tcp://localhost/~/test]
22:09:53.512 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = conn5: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class org.h2.jdbc.JdbcConnection

```



#### 실행 결과 - `HikariCP` 사용

* `DriverManagerDataSource`와는 달리 커넥션을 사용하고 다시 커넥션 풀에 적재 후 재사용하기 때문에 쿼리를 처리하고 거의 같은 커넥션을 사용하는 것을 확인할 수 있다.

```cmd
22:19:11.238 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = HikariProxyConnection@1594039997 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
22:19:11.256 [Test worker] INFO  h.j.r.MemberRepositoryV1Test --
                findMember = Member(memberId=memberV3, money=10000)
                
22:19:11.276 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = HikariProxyConnection@659059448 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
22:19:11.279 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                resultSize = 1
                
22:19:11.279 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = HikariProxyConnection@124494140 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection

22:19:11.281 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = HikariProxyConnection@635288507 wrapping conn1: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection
                
22:19:11.282 [Test worker] INFO  h.jdbc.repository.MemberRepositoryV1 --
                get connection = HikariProxyConnection@593447952 wrapping conn2: url=jdbc:h2:tcp://localhost/~/test user=SA, class = class com.zaxxer.hikari.pool.HikariProxyConnection

```



# 섹션 3 | 트랜잭션 이해

## 트랜잭션 - 개념 이해

데이터를 단순히 파일에 저장해도 되지만 데이터베이스에 저장하는 이유는 여러가지가 있지만,
가장 대표적인 이유는 바로 **데이터베이스는 트랜젹션이라는 개념을 지원하기 때문이다.**



### 트랜잭션이란

데이터베이스에서 트랜잭션은 하나의 거래를 안전하게 처리하도록 보장해주는 것을 의미한다.
<u>데이터베이스의 상태를 변환시키는 하나의 논리적 기능을 수행하기 위한 작업의 단위 또는 한꺼번에 모두 수행되어야 할 일련의 연산들을 의미한다.</u>

* 모든 작업이 성공해서 데이터베이스에 정상 반영하는 것을 커밋(Commit)이라 하고,
* 작업 중 하나라도 실패해서 커밋 이전 상태로 되돌리는 것을 롤백(Rollback)이라 한다.



#### 트랜잭션 ACID

트랜잭션은 ACID라 하는 원자성(Atomicity), 일관성(Consistency), 격리성(Isolation), 지속성(Durability)를 보장해야한다.

* 원자성: 트랜잭션 내에서 실행한 작업들은 마치 하나의 작업인 것처럼 모두 성공 하거나 모두 실패해야 한다.
* 일관성: 모든 트랜잭션은 일관성 있는 데이터베이스 상태를 유지해야 한다. 예를 들어 데이터베이스에서 정한 제약 조건을 항상 만족해야 한다.
* 격리성: 동시에 실행되는 트랜잭션들이 서로에게 영향을 미치지 않도록 격리한다. <u>예를 들어 동시에 같은 데이터를 수정하지 못하도록 해야한다.</u> 격리성은 동시성과 관련된 성능 이슈로 트랜잭션 격리 수준(Isolation level)을 선택할 수 있다.
* 지속성: 트랜잭션을 성공적으로 끝내면 그 결과가 항상 기록되어야 한다. 중간에 시스템에 문제가 발생하더라도 데이터베이스 로그 등을 사용해서 성공한 트랜잭션 내용을 복구해야 한다.



#### 트랜잭션 격리 수준 - Isolation level

아래 단계로 내려갈수록 격리 수준은 높아짐을 의미하며, 성능과도 큰 연관이 있기 때문에 주로 READ COMMITED(커밋된 읽기 수준으로 설정을 한다고 한다.)

* READ UNCOMMITED(커밋되지 않은 읽기)
* <u>READ COMMIITED(커밋된 읽기)</u>
* REPEATABLE READ(반복 가능한 읽기)
* SERIALIZABLE(직렬화 가능)



## 데이터베이스 연결 구조와 DB 세션

<img width="701" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/cb74939e-f685-4c83-a5aa-818d1f054280">

트랜잭션을 더 자세히 이해하기 위해 데이터베이스 서버 연결 구조와 DB 세션에 대해 알아보자

1. 사용자는 WAS 서버나 DB 접근 툴 같은 클라이언트를 통해 데이터베이스에 연결하게 되면 데이터베이스 서버에 연결을 요청하고 커넥션을 맺게 된다. 이때 DB는 서버 내에 세션이라는 것을 만든다.
2. 앞으로 해당 커넥션을 통한 모든 요청을 이 세션을 통해 실행하게 된다.
3. 세션은 트랜잭션을 시작하고, 커밋 또는 롤백을 통해 트랜잭션을 종료한다. 그리고 그 이후에 새로운 트랜잭션을 다시 시작할 수 있다. 
4. 사용자가 커넥션을 닫거나, 또는 DBA가 세션을 강제로 종료하면 세션은 종료된다.
5. <u>결국 데이터베이스 서버 내에 세션이라는 것을 통해 트랜잭션과 클라이언트의 요청 작업이 이루어진다고 보면 된다.</u>



## 트랜잭션 - DB 예제 1 - 개념이해

트랜잭션의 동작 개념을 큰 그림으로 이해해보자



#### 트랜잭션 사용법

* 데이터 변경 쿼리를 실행하고 데이터베이스에 그 결과를 반영하려면 커밋 명령어인 `commit`을 호출하고, 결과를 반영하고 싶지 않으면 롤백 명령어인 `rollback`을 호출하면 된다.
* **커밋을 호출하기 전까지는 임시로 데이터를 저장하는 것이다.** 따라서 트랜잭션을 시작한 세션(사용자)에게만 변경 데이터가 보이고 다른 세션에게는 변경 데이터가 보이지 않는다.
* 등록, 수정, 삭제 모두 같은 원리로 동작한다.



### 예시 1 - 커밋하지 않았을 경우

세션1, 세션2 사용자 모두 가운데 있는 테이블을 조회하게 되면 해당 데이터가 그대로 조회된다.

<img width="716" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/4b9eab7c-41ef-45ac-bb34-529e32c65d8a">



* 세션1 사용자가 트랜잭션을 시작하고, 신규 데이터를 DB에 추가하며 아직 커밋은 하지 않은 상태이다.
* 새로운 데이터는 임시 상태로 저장된다.
* 이때 세션1은 `select` 쿼리로 신규 데이터를 조회할 수 있지만, 세션2는 `select` 쿼리로 신규 데이터를 조회할 수 없다.

<img width="704" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/eacea11f-8f6a-496d-a52a-a1bf2ef54cf5">



#### 커밋하지 않은 데이터를 다른 곳에서 조회할 수 있으면 어떤 문제가 발생할까?

예를 들어 세션1이 신규 데이터를 커밋하지 않은 상태에서 세션2는 데이터를 조회하게 되었을 때 보이게 된다면, 세션2는 신규 데이터를 통해 어떤 로직을 수행할 수 있다. 하지만 세션1이 이때 신규 데이터를 롤백하게 된다면 신규 데이터는 다시 사라지게 되고 이때 <u>데이터 정합성에 큰 문제가 발생하게 된다.</u>



### 예시 1 - 커밋했을 경우/커밋하지 않고 롤백했을 경우

세션1이 신규 데이터를 추가한 이후 커밋을 했기 때문에 세션2에서도 동일하게 결과를 조회할 수 있다.

<img width="707" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/0f4eadc4-38c0-4b09-879f-8db6dc97393f">



* 세션1이 신규 데이터를 추가 후 커밋 대신 롤백을 수행하였다.(위 그림과 이어지는 내용이 아니고 별개의 내용임)
* 세션1, 세션2 모두 트랜잭션을 시작하기 직전의 상태로 복구된다.

<img width="699" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/f2203b98-7cf6-4493-85c3-999a46c4c88e">



## 트랜잭션 - DB 예제2 - 자동 커밋, 수동 커밋

이번에는 자동 커밋과 수동 커밋에 대해 알아보자



예제에 사용되는 스키마는 다음과 같다.

```mysql
 drop table member if exists;
 create table member (
     member_id varchar(10),
     money integer not null default 0,
     primary key (member_id)
);
```



### 자동 커밋

* 데이터베이스에 자동 커밋/수동 커밋이라는 개념이 존재하는데 <u>자동 커밋으로 설정하면 각각의 쿼리 실행 직후에 자동으로 커밋을 호출한다.</u>
* 이는 편리한 기능이기도 하나 쿼리를 하나하나 실행할 때마다 자동으로 커밋되어 버리기 때문에 우리가 원하는 트랜잭션 기능을 제대로 사용할 수 없다.
* **따라서 commit, rollback을 직접 호출하면서 트랜잭션 기능을 제대로 수행하려면 자동 커밋을 끄고 수동 커밋을 사용해야 한다.**
* <u>참고로 수동 커밋 모드나 자동 커밋 모드는 한번 설정하면 해당 세션(커넥션)에서는 계속 유지된다.</u> 중간에 변경하는 것은 가능하다.



#### 자동 커밋 설정

```mysql
set autocommit true; //자동 커밋 모드 설정
insert into member(member_id, money) values ('data1',10000); //자동 커밋
insert into member(member_id, money) values ('data2',10000); //자동 커밋
```



#### 수동 커밋 설정

```mysql
set autocommit false; //수동 커밋 모드 설정
 insert into member(member_id, money) values ('data3',10000);
 insert into member(member_id, money) values ('data4',10000);
commit; //수동 커밋
```



## DB 락 - 개념 이해

세션1과 세션2가 동시에 같은 데이터를 수정하고 싶은 경우가 있다. 이럴 때 어느 쪽에서 작업을 수행중인데 다른 세션에서 동일한 데이터를 변경하거나 롤백을 시도하는 경우 데이터 정합성에 문제가 발생할 수 있게 된다.

이런 문제를 해결하기 위해 데이터베이스는 락(lock)이라는 개념을 제공한다.



### 예제 - 락의 작동 방식

<img width="692" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/7b5ccb61-c8dc-49a3-a50a-13621b24f41f">

* 세션1이 트랜잭션을 시작한다. 먼저 들어온 요청이기 때문에 락을 선취한다.
* 세션1은 memberA의 money를 500으로 수정하려고 한다.



<img width="689" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/7bc8fe70-6fb0-4385-8352-022ad8ccaa30">

* 세션2가 트랜잭션을 시도한다. 세션2도 memberA의 money를  1000으로 수정하려고 한다.
* <u>이때 세션2는 락을 가질 수 없기 때문에 데이터 변경이 불가능하며, 락이 돌아올 때까지 대기하게 된다.</u>
* 락을 가질 때까지 무한정 대기하는 것은 아니며, 락 대기 시간이 넘어가게 되면 락 타임아웃 오류가 발생하게 된다.



<img width="693" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/a2cea4a2-3e2a-4e3b-a40b-5a7212b562b5">

* 세션1은 트랜잭션 작업을 마치고 커밋을 수행하게 된다.



<img width="695" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/40c4d7ba-2c1e-4e11-9a02-017e165e0388">

* 트랜잭션이 종료되었으므로 락도 함께 반납하게 된다.



<img width="699" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/7ef9cdfe-ccf6-47c1-8e38-dd4e2d16eff6">

* 트랜잭션을 수행하기 위해 대기중이던 세션2가 락을 획득하게 된다.



<img width="688" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/d76161f6-6098-4e78-a93f-a9a44aa283bf">

* 세션2는 트랜잭션을 시작해 memberA의 money 상태를 1000으로 변경한다.



<img width="686" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/ad3c3a89-2678-4278-bd19-2923103159be">

* 세션2는 커밋을 수행하고 트랜잭션이 종료되었으므로 락을 함께 반납하며 끝나게 된다.



## DB 락 - 조회

**일반적인 조회는 락을 사용하지 않는다.**

데이터베이스마다 다르지만, 보통 데이터를 조회할 땐 락을 획득하지 않고 바로 데이터를 조회할 수 있다.
예를 들어 세션1이 락을 획득하고 데이터를 변경하고 있더라도, 세션2에서는 데이터를 조회할 수 있다.



**하지만 조회 시점에 락이 필요한 경우도 있는데, 트랜잭션 종료 시점까지 해당 데이터를 다른 곳에서 변경하지 못하도록 강제로 막아야할 때 사용한다.**

* 예를 들어 애플리케이션 로직에서 memberA의 금액을 조회한 다음 이 금액 정보로 애플리케이션에서 어떤 계산을 수행해야 한다. 그런데 이 계산이 돈과 관련된 매우 중요한 계산이어서 계산을 완료할 때까지 memberA의 금액을 다른 곳에서 변경하면 안된다. 이럴 때 조회 시점에 락을 획득하면 된다.

* **즉, 조회된 데이터를 이용해서 어떤 계산을 하려고 할 때 정말 중요한 데이터라면 락을 걸어야 한다.**



### 조회와 락

* 데이터를 조회할 때도 락을 획득하고 싶다면 `select for update` 구문을 사용하면 된다.
* 이렇게 하면 세션1이 조회 시점에 락을 가져가버리기 때문에 다른 세션에서 해당 데이터를 변경할 수 없다.



### 예제 - 조회와 락

실습을 위해 데이터베이스에 기본 데이터 입력

```sql
set autocommit true;
delete from member;
insert into member(member_id, money) values ('memberA',10000);
```



세션1에서 데이터를 조회하면서 동시에 선택한 로우의 락을 획득한다.

* 세션1은 트랜잭션이 종료할 때까지 memberA의 로우의 락을 보유하게 된다.

```sql
set autocommit false;
select * from member where member_id='memberA' for update;
```



세션2에서 데이터를 변경하고 싶다. 데이터를 변경하려면 락이 필요하다.

* 세션1이 memberA의 로우의 락을 획득했기 떄문에 세션2는 락을 획득할 때까지 대기한다.
* 세션1이 트랜잭션을 종료하면 세션2가 락을 획득하고 데이터를 변경한다.

```sql
set autocommit false;
update member set money=500 where member_id = 'memberA';
```



세션1 커밋

* 세션2는 대기하고 있다가 세션1이 커밋됨과 동시에 트랜잭션을 수행하게 된다.[아래 사진 참고]

```sql
commit;
```



세션2 커밋

* 세션2도 커밋하여 데이터를 정상적으로 반영하게 된다.

```sql
commit;
```



실행결과

* 세션1은 `select..for update` 구문을 통해 조회와 동시에 락을 획득하게 된다.
* 오른쪽이 세션2인데 락을 획득하지 못하여 `update` 쿼리를 수행하지 못하고 대기상태에 있게 된다.

<img width="1316" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/37e4bc58-f8c4-47c5-a935-528ada4955a1">



* 세션1에서 커밋 이후 곧바로 세션2가 `update` 쿼리를 정상적으로 실행한 모습이다.
* 세션2는 이후 커밋을 통해 트랜잭션을 정상적으로 종료할 수 있다.

<img width="1357" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/366a863b-280c-4311-849d-09bb2162ade4">



## 트랜잭션 - 적용1

이제 애플리케이션에 트랜잭션을 적용해보자.
우선 트랜잭션을 사용하기 전 앞서 트랜잭션 없이 단순하게 계좌이체 비즈니스 로직만 구현할 것이다.



### 예제 - 트랜잭션 없이 단순 계좌이체 구현

[MemberServiceV1] - 계좌이체 서비스 구현

* fromId, toId 회원을 조회하여 fromID가 toId에게 계좌이체를 하는 시나리오이다.
* <u>예외 상황을 테스트하기 위해 toId가 "ex"인 경우 예외를 발생한다.</u>

```java
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    // 계좌이체 메서드
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
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
```



[MemberServiceV1Test] - 계좌이체 테스트 진행

* 정상 이체 테스트 - `accountTransfer()`
  * 예외가 발생하지 않았기 때문에 정상적으로 memberA는 2000원 감소, memberB는 2000원이 증가하였다.
* 비정상 이체 테스트 - `accountTransferEx()`
  * memberA가 memberEx로 계좌이체를 하는 부분에서 ex 회원은 예외가 발생하게 했으므로 예외가 발생하고, 다음 구분인 memberEx의 계좌에 2000원이 추가되지 않게 된다.
  * <u>하지만 memberA의 금액만 2000원 감소하였으므로 큰 문제가 발생하게 되었다.</u>

```java
package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */
class MemberServiceV1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV1 memberRepository;
    private MemberServiceV1 memberService;

    @BeforeEach
    void beforeEach() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);
        memberService = new MemberServiceV1(memberRepository);
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
        assertThatThrownBy(() ->
                memberService.accountTransfer(memberA.getMemberId(), memberEx.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);

        // then: 계좌이체는 실패한다. memberA의 돈만 2000원 줄어든다.
        Member findMemberA = memberRepository.findById(memberA.getMemberId());
        Member findMemberB = memberRepository.findById(memberEx.getMemberId());
        assertThat(findMemberA.getMoney()).isEqualTo(8000);
        assertThat(findMemberB.getMoney()).isEqualTo(10000);
    }
}
```



이러한 문제를 해결하기 위해 트랜잭션의 ACID 원칙을 적용하는 것이 필요하다.



## 트랜잭션 - 적용2

앞서 발생한 문제를 해결하기 위해 트랜잭션을 적용해보자.
**애플리케이션에서 트랜잭션은 비즈니스 로직이 있는 서비스 계층에서 시작해야한다.** 비즈니스 로직이 잘못되면 해당 비즈니스 로직으로 인해 문제가 되는 부분을 함께 롤백해야 하기 때문이다.

이때 아래와 같은 몇가지 고려사항이 있다.

* 트랜잭션을 하려면 결국 커넥션과 세션이 필요하다. <u>즉, 서비스 계층에서 커넥션을 만들고</u>, 트랜잭션 커밋 이후에 커넥션을 종료해야 한다.
* **애플리케이션에서 DB 트랜잭션을 사용하려면 트랜잭션을 사용하는 동안 같은 커넥션을 유지해야 한다.** 그래야 같은 세션을 사용하며 트랜잭션을 유지할 수 있다.
  * 하나의 세션이 하나의 트랜잭션을 관리하므로 하나의 작업을 트랜잭션으로 처리하려면 하나의 커넥션을 유지해야 한다는 뜻이다.

<img width="705" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/4051958e-4da6-4564-b242-63123c6175d8">



### 예제 - 트랜잭션 적용 | 같은 커넥션 유지를 위해 파라미터 사용

트랜잭션을 사용하기 위해 같은 커넥션을 유지하려면 어떻게 해야할까?
<u>가장 쉬운 방법은 커넥션을 파라미터로 전달해서 같은 커넥션이 사용되도록 유지하면 된다.</u>



[MemberRepositoryV2] - 같은 커넥션 유지를 위해 파라미터 추가(`Connection con`), 커넥션 새로 생성 코드 삭제(`getConnection()`), 커넥션 유지를 위한 커넥션 종료(`close()`) 삭제

* 앞서 테스트 코드에서 `findById(), update()` 메서드를 통해 계좌이체를 테스트하였다.
* <u>따라서 위의 메서드에 같은 커넥션이 사용되도록 메서드 시그니처를 바꾼다.(Connection con) 추가</u>
* 같은 커넥션을 사용해야 하므로 커넥션을 새로 생성하는 `getConnection()` 메서드는 사라져야한다. <u>즉, 서비스 계층에서 커넥션을 한번만 생성하고 계속 파라미터로 넘겨서 같은 커넥션을 유지해야 한다.</u> 
* 또한 서비스 계층에서 비즈니스 로직을 처리하기 위해 다양한 메서드를 사용하여야 하고, <u>커넥션을 계속 유지하고 사용되어야 하므로 커넥션을 종료하는 코드를 데이터를 처리하는 Repository 레이어에서 종료하면 안된다.</u>
  * 쉽게 말해, 서비스 계층에서 커넥션을 종료해야 하므로 Repository 레이어에서 커넥션을 종료하는 코드가 들어가선 안된다.

```java
package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - ConnectionParam
 */
@Slf4j
public class MemberRepositoryV2 {

    private final DataSource dataSource;

    // 외부에서 DataSource를 주입 받아서 사용한다. 이를 통해 구현체가 달라지더라도 코드를 변경할 일이 없다.
    public MemberRepositoryV2(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection = {}, class = {}", con, con.getClass());
        return con;
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

    public Member findById(Connection con, String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
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
            // connection은 여기서 닫지 않는다.
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(pstmt);
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

    public void update(Connection con, String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);

        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            // connection은 여기서 닫지 않는다.
            JdbcUtils.closeStatement(pstmt);
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
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);

    }
}
```



[MemberServiceV2] - 비즈니스 로직 & 트랜잭션 연동 로직을 작성

* 같은 커넥션을 사용하기 위해 커넥션을 생성하고, 그 커넥션을 필요한 곳에 파라미터로 넘긴다.(`getConnection()`)
* 트랜잭션을 시작하기 위해 자동커밋 모드를 비활성화 해야한다. (`con.setAutoCommit(false); // 트랜잭션 시작`)
* 성공시 커밋(`con.commit()`)
* 실패시 롤백(`con.rollback()`)
* 수동 커밋 모드를 자동 커밋 모드로 변경 후 커넥션 종료: `release()`
  * 커넥션 풀을 사용하게 되면 `con.close()`를 호풀하더라도 종료되는 것이 아닌 풀에 반납된다.

```java
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    // 계좌이체 메서드
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {

        // 트랜잭션을 시작하려면 커넥션이 필요하다.
        Connection con = dataSource.getConnection();
        try {
            con.setAutoCommit(false); // 트랜잭션 시작
            // 비즈니스 로직
            bizLogic(con, fromId, toId, money);
            con.commit(); // 성공시 커밋
        } catch (Exception e) {
            con.rollback(); // 실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            // 커넥션 사용 이후 안전하게 종료
            // 커넥션 풀을 사용하면 con.close를 호출해도 커넥션이 종료되는 것이 아니라 풀에 반납된다.
            release(con);
        }

    }

    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); // 커넥션 풀 고려
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }


    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
```



[MemberServiceV2Test] - 계좌이체 테스트(정상 이체, 비정상 이체)

* 정상 이체는 당연히 잘 수행된다.
* 비정상 이체
  * 중간에 로직을 수행하다 "ex" 유저가 들어와서 예외가 발생하게 되고, 아래 로직은 수행하지 않게 된다.
  * 서비스 계층에서 트랜잭션이 정상적이지 않으면 롤백을 통해 데이터를 원복 시킨다.
  * <u>트랜잭션 덕분에 모든 데이터를 정상적으로 초기화 할 수 있게 되었다. 결과적으로 계좌이체를 수행하기 직전으로 돌아가게 되었다.</u>

```java
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - 커넥션 파라미터 전달 방식 동기화
 */
class MemberServiceV2Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV2 memberRepository;
    private MemberServiceV2 memberService;

    @BeforeEach
    void beforeEach() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV2(dataSource);
        memberService = new MemberServiceV2(dataSource, memberRepository);
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
```



# 섹션 4 | 스프링과 문제 해결 - 트랜잭션

## 문제점들

### 애플리케이션의 구조

<img width="749" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/feb74a08-91b4-4c84-885f-d7debfac5b19">

#### 프레젠테이션 계층

* UI와 관련된 처리 담당
* 웹 요청과 응답
* 사용자 요청을 검증
* 주 사용 기술: 서블릿과 HTTP 같은 웹 기술, 스프링 MVC



#### 서비스 계층

* **비즈니스 로직을 담당**
* 주 사용 기술: 가급적 특정 기술에 의존하지 않고, 순수 자바 코드로 작성



#### 데이터 접근 계층

* **실제 데이터베이스에 접근하는 코드**
* 주 사용 기술: JDBC, JPA, File, Redis, Mongo...



이중에서 가장 중요한 곳은 **비즈니스 핵심 로직이 들어있는 서비스 계층이다.**

* 서비스 계층은 특정 기술에 종속적이지 않게 개발해야 한다.
  * 이렇게 계층을 나눈 이유도 서비스 계층을 최대한 순수하게 유지하기 위한 목적이 가장 크다.
  * 기술적인 부분은 프레젠테이션 계층, 데이터 접근 계층에서 가지고 간다.
* <u>서비스 계층은 가급적 비즈니스 로직만 구현하고 특정 구현 기술에 직접 의존해서는 안된다.</u>
  * 이렇게 구현하면 기술이 변경될 때 변경의 영향 범위를 최소화 할 수 있다.



### 예시 - 트랜잭션을 적용한 코드

[MemberServiceV2]

* 트랜잭션은 비즈니스 로직이 있는 서비스 계층에서 시작하는 것이 좋다.
* 문제는 트랜잭션을 사용하기 위해 다음과 같은 JDBC 기술에 의존하는 것이다.
  * `javax.sql.DataSource;`
  * `java.sql.Connection;`
  * `java.sql.SQLException;`
* 이렇게 JDBC 기술에 의존하면 JPA 같은 다른 기술로 바꾸어 사용하게 될 때 서비스 코드도 모두 변경해야한다.
* 따라서 핵심 비즈니스 로직을 유지보수 하기 어려워진다.

```java
package hello.jdbc.service;
 import javax.sql.DataSource;
 import java.sql.Connection;
 import java.sql.SQLException;
 @Slf4j
 @RequiredArgsConstructor
 public class MemberServiceV2 {
    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

		public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try {
		        con.setAutoCommit(false); //트랜잭션 시작 //비즈니스 로직
		        bizLogic(con, fromId, toId, money); con.commit(); //성공시 커밋
        } catch (Exception e) {
		        con.rollback(); //실패시 롤백
		        throw new IllegalStateException(e);
        } finally {
    	      release(con);
        } 
    }
    
    private void bizLogic(Connection con, String fromId, String toId, int money)
throws SQLException {
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);
        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }
}
```



### 문제점 정리

#### 트랜잭션 문제

가장 큰 문제는 트랜잭션을 적용하면서 생긴 다음과 같은 문제점들이 있다.

* JDBC 구현 기술이 서비스 계층에 누수되는 문제
  * 트랜잭션을 적용하기 위해 JDBC 구현 기술이 서비스 계층에 누수되었다.
* 트랜잭션 동기화 문제
  * 같은 트랜잭션을 유지하기 위해 커넥션을 파라미터로 넘기고 있다.
* 트랜잭션 적용 반복 문제
  * 트랜잭션 적용 코드를 보면 반복이 많다. `try, catch, finally` 등



#### 예외 누수

* 데이터 접근 계층의 JDBC 구현 기술 예외가 서비스 계층으로 전파된다.
* `SQLException`은 체크 예외이기 때문에 데이터 접근 계층을 호출한 서비스 계층에서 해당 예외를 잡아서 처리하거나 명시적으로 `throws`를 통해 다시 밖으로 던져야 한다.



#### JDBC 반복 문제

* 커넥션을 열고 `PreparedStatement`를 사용하고, 결과를 매핑하는 하고 리소스를 정리 하는 과정들의 반복이다.



## 트랜잭션 추상화

### 문제점

<u>현재 서비스 계층은 트랜잭션을 사용하기 위해 JDBC 기술에 의존하고 있다.</u>
구현 기술마다 트랜잭션을 사용하는 방법이 달라 서비스 계층을 유지보수 하기 힘든데, 이를 해결하기 위해 트랜잭션 추상화가 필요하다.



### 기술 의존 시 발생하는 문제

JDBC와 JPA가 사용하는 트랜잭션 코드는 다르다.
따라서 기술 변경 시 서비스 계층의 코드도 JPA 기술을 사용하도록 함께 수정해야 한다.



#### JDBC 트랜잭션 사용

<img width="745" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/989f8c8f-4159-4dd0-b796-c29a7d8b084a">



#### JDBC -> JPA 변경

<img width="774" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/81474301-f439-4105-aba5-c2d7dba64437">



### 스프링이 제공하는 트랜잭션 추상화

위와 같은 문제를 해결하기 위해 <u>트랜잭션 기능을 인터페이스를 만들어 추상화를 하면 되지만</u>,
스프링은 이미 트랜잭션 추상화 기술을 제공하고 있다.

* 서비스 계층은 특정 기술에 의존하는 것이 아니라 원하는 구현체를 DI를 통해 주입하면 된다.
* **클라이언트인 서비스 계층은 인터페이스에 의존하고, DI를 사용한 덕분에 OCP 원칙을 지킬 수 있다.**



<img width="751" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/db70b43f-3bf3-4abe-bec5-eb2ea208bf2c">





스프링 트랜잭션 추상화의 핵심은 `PlatformTransactionManager` 인터페이스다.

* `getTransaction()`: 트랜잭션을 시작
* `commit()`: 트랜잭션 커밋
* `rollback()`: 트랜잭션 롤백

```java
package org.springframework.transaction;

public interface PlatformTransactionManager extends TransactionManager {
   TransactionStatus getTransaction(@Nullable TransactionDefinition definition)
           throws TransactionException;
   void commit(TransactionStatus status) throws TransactionException;
   void rollback(TransactionStatus status) throws TransactionException;
}
```



## 트랜잭션 동기화

스프링이 제공하는 트랜잭션 매니저(`PlatformTransactionManager`)는 크게 2가지 역할을 한다.

* 트랜잭션 추상화
* 리소스 동기화



#### 리소스 동기화

<u>트랜잭션을 유지하려면 트랜잭션의 시작부터 끝까지 같은 데이터베이스 커넥션을(세션) 유지해야한다.</u>
이전에는 트랜잭션 동기화를 위해 파라미터로 같은 커넥션을 전달하는 방법을 사용했지만, 코드가 지저분해지는 것은 물론이고 코드의 중복 또한 발생하게 되는 문제점들이 있다.



### 스프링이 제공하는 트랜잭션 매니저

* 쓰레드 로컬(`ThreadLocal`)을 사용해서 커넥션을 동기화해준다.
  * 트랜잭션 매니저 내부에서 트랜잭션 동기화 매니저(`TransactionSynchronizationManager`)를 사용한다.
  * <u>트랜잭션 동기화 매니저에 쓰레드 로컬을 정의해서 사용한다.</u>
* 트랜잭션 동기화 매니저는 <u>쓰레드 로컬을 사용하기 때문에 멀티쓰레드 환경에서 안전하게 커넥션을 동기화 할 수 있다.</u>
  * 따라서 커넥션을 여기서 획득하면 되며, 파라미터로 커넥션을 연결하는 식으로 트랜잭션 동기화를 할 필요가 없다.

<img width="766" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/77134c81-0fae-4f32-83c7-14ffd95ea54d">



#### 트랜잭션 매니저의 동작 방식

1. 트랜잭션을 시작하려면 커넥션이 필요하다. 트랜잭션 매니저는 DataSource를 통해 커넥션을 만들고 트랜잭션을 시작한다.
2. 트랜잭션 매니저는 트랜잭션이 시작된 커넥션을 트랜잭션 매니저에 보관한다.
3. 리포지토리는 트랜잭션 동기화 매니저에 보관된 커넥션을 꺼내서 사용한다. 따라서 파라미터로 커넥션을 전달할 필요가 없다.
4. 트랜잭션이 종료되면 트랜잭션 매니저는 트랜잭션 동기화 매니저에 보관된 커넥션을 통해 트랜잭션 종료, 커넥션 연결도 닫게 된다.



> **참고**
>
> 쓰레드 로컬을 사용하면 각각의 쓰레드마다 별도의 저장소가 부여된다.
> 따라서 해당 쓰레드만 해당 데이터에 접근할 수 있다.



## 트랜잭션 문제 해결 - 트랜잭션 매니저1

트랜잭션 매니저를 애플리케이션 코드에 적용해보자

* <u>트랜잭션 매니저를 사용하게 되면 특정 기술의 트랜잭션에 의존하지 않고, 추상화와 동기화를 지원해준다고 했다.</u>



### 예시

[MemberRepositoryV3] - <u>커넥션을 파라미터로 전달하는 부분이 모두 제거되었다.</u>

* V2 버전에서는 `findById(), update()`에 파라미터로 커넥션을 넘겨줬었다.
* `DataSourceUtils.getConnection()`
  * **트랜잭션 동기화 매니저가 관리하는 커넥션이 있으면 해당 커넥션을 반환하고, 없는 경우 새로운 커넥션을 생성해서 반환한다.**
* `DataSourceUtils.releaseConnection()`
  * 해당 메서드를 사용하면 커넥션을 바로 닫는 것이 아니다.
  * **트랜잭션을 사용하기 위해 동기화된 커넥션은 커넥션을 닫지 않고 그대로 유지해준다.**
  * 트랜잭션 동기화 매니저가 관리하는 커넥션이 없는 경우 해당 커넥션을 닫는다.

```java
package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 * DataSourceUtils.getConnection()
 * DataSourceUtils.releaseConnection()
 * 트랜잭션 동기화 매니저를 사용하기 위해선 위의 메서드들을 사용해야 한다고 한다.
 */
@Slf4j
public class MemberRepositoryV3 {

    private final DataSource dataSource;

    // 외부에서 DataSource를 주입 받아서 사용한다. 이를 통해 구현체가 달라지더라도 코드를 변경할 일이 없다.
    public MemberRepositoryV3(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        // 주의! 트랜잭션 동기화를 사용하려면 DataSoureUtils를 사용해야 한다.
        // 트랜잭션 동기화 매니저가 관리하는 커넥션이 있으면 해당 커넥션을 반환한다.
        // 트랜잭션 동기화 매니저가 관리하는 커넥션이 없는 경우 새로운 커넥션을 생성해서 반환한다.
        Connection con = DataSourceUtils.getConnection(dataSource);
        log.info("get connection = {}, class = {}", con, con.getClass());
        return con;
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        // 주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        // 트랜잭션을 사용하기 위해 동기화된 커넥션은 커넥션을 닫지 않고, 그대로 유지해준다.(즉, 해당 메서드를 사용하더라도 커넥션을 바로 닫는 것이 아니다.)
        // 트랜잭션 동기화 매니저가 관리하는 커넥션이 없는 경우 해당 커넥션을 닫는다.
        DataSourceUtils.releaseConnection(con, dataSource);
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
}
```





[MemberServiceV3_1] - 트랜잭션 매니저를 사용하는 서비스 코드

* `private final PlatformTransactionManager transactionManager;`
  * 트랜잭션 매니저를 주입 받는다.
  * 지금은 JDBC 기술을 사용하기 때문에 테스트 코드에서 `DataSourceTransactionManager` 구현체를 주입 받아야 한다.
* `transactionManager.getTransaction()`
  * 트랜잭션을 시작한다.
  * `TransactionStatus`를 반환하게 되는데, 현재 트랜잭션의 상태 정보가 포함되어 있다. 이후 트랜잭션 커밋, 롤백할 때 필요하다.

```java
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
```





[MemberServiceV3_1Test] - 트랜잭션 매니저를 적용한 테스트

* `new DataSourceTransactionManager(dataSource);`
  * JDBC 기술을 사용하므로, JDBC용 트랜잭션 매니저를 구현체로 사용한다.
  * 트랜잭션 매니저는 데이터소스를 통해 커넥션을 생성하므로 DataSource가 필요하다.

```java
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
```



## 트랜잭션 문제 해결 - 트랜잭션 매니저 정리

트랜잭션 매니저의 전체 동작 흐름을 자세히 이해해보자



#### 트랜잭션 시작

클라이언트의 요청으로 서비스 로직을 실행한다.

1. 서비스 계층에서 `transactionManager.getTransaction()`을 호출해서 트랜잭션을 시작한다.
2. 트랜잭션을 시작하려면 먼저 데이터베이스 커넥션이 필요하다. <u>트랜잭션 매니저는 내부에서 데이터소스를 사용해서 커넥션을 생성한다.</u>
3. 커넥션을 수동 커밋 모드로 변경해서 실제 데이터베이스 트랜잭션을 시작한다.
4. 커넥션을 트랜잭션 동기화 매니저에 보관한다.
5. <u>트랜잭션 동기화 매니저는 쓰레드 로컬에 커넥션을 보관한다. 따라서 멀티쓰레드 환경에 안전하게 커넥션을 보관한다.</u>

<img width="782" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/731c3b57-64db-4aef-96cf-f40aad680a2a">





#### 로직 실행

6. 서비스는 비즈니스 로직을 실행하면서 리포지토리의 메서드들을 호출한다. 이때 이전처럼(V2) 커넥션을 파라미터로 전달하지 않는다.
7. 리포지토리 메서드들은 트랜잭션이 시작된 커넥션이 필요하다.
8. <u>리포지토리는 `DataSourceUtils.getConnection()`을 사용해서 트랜잭션 동기화 매니저에 보관된 커넥션을 꺼내서 사용한다. 이 과정을 통해 자연스럽게 같은 커넥션을 사용하고, 트랜잭션도 유지된다.</u>
9. 획득한 커넥션을 사용해서 SQL을 데이터베이스에 전달해서 실행한다.

<img width="779" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/b650ed9a-55a5-4a33-b170-8fb648f66a77">





#### 트랜잭션 종료

9. 비즈니스 로직이 끝나고 트랜잭션을 종료한다. 트랜잭션은 커밋하거나 롤백하면 종료된다.
10. <u>트랜잭션을 종료하려면 동기화 된 커넥션이 필요하다. 트랜잭션 동기화 매니저를 통해 동기화된 커넥션을 획득한다.</u>
11. 획득한 커넥션을 통해 트랜잭션 매니저는 데이터베이스에 트랜잭션을 커밋하거나 롤백한다.
12. 전체 리소스를 정리한다.
    * 트랜잭션 동기화 매니저를 정리한다. 쓰레드 로컬은 사용 후 꼭 정리해야 한다.
    * `con.setAutoCommit(true)`로 되돌린다.(커넥션 풀을 고려)
    * `con.close()`를 호출해 커넥션을 종료한다.

<img width="770" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/38f6935d-5a92-430f-a0a3-8f683bd44080">



### 정리

* 트랜잭션 매니저의 트랜잭션 추상화 덕분에 서비스 코드는 이제 JDBC 기술에 의존하지 않는다.
  * 기술 변경 시 의존 관계 주입만 해당 기술의 구현체로 변경하면 된다.
* 트랜잭션 동기화 매니저 덕분에 커넥션을 파라미터로 넘기지 않아도 트랜잭션을 유지할 수 있다.



## 트랜잭션 문제 해결 - 트랜잭션 템플릿

트랜잭션을 사용하는 로직을 살펴보면 다음과 같은 패턴이 반복된다.

* 다른 서비스에서 트랜잭션을 시작하려면 `try, catch,  finally`를 포함한 성공 시 커밋, 실패 시 롤백 코드가 반복될 것이다.

* <u>템플릿 콜백 패턴을 활용하면 이런 반복 문제를 해결할 수 있다.</u>

```java
// 트랜잭션 시작
TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            // 비즈니스 로직
            bizLogic(fromId, toId, money);
            transactionManager.commit(status); // 성공시 커밋
        } catch (Exception e) {
            transactionManager.rollback(status); // 실패시 롤백
            throw new IllegalStateException(e);
        }
    }
}
```



### 스프링의 트랜잭션 템플릿

템플릿 콜백 패턴을 적용하려면 템플릿을 제공하는 클래스를 작성해야 하는데, 스프링은 `TransactionTemplate`라는 템플릿 클래스를 제공한다.

* `execute()` : 응답 값이 있을 때 사용한다.
* `executeWithoutResult()` : 응답 값이 없을 때 사용한다.

```java
public class TransactionTemplate {
     private PlatformTransactionManager transactionManager;
     public <T> T execute(TransactionCallback<T> action){..}
     void executeWithoutResult(Consumer<TransactionStatus> action){..}
}
```



### 예제 - 트랜잭션 템플릿을 사용해서 반복되는 부분을 제거

[MemberServiceV3_2]

* 트랜잭션 템플릿을 사용하려면 트랜잭션 매니저가 필요하다. 
  * 생성자에서 트랜잭션 매니저를 주입 받으면서 트랜잭션 템플릿을 생성한다.
* <u>트랜잭션 템플릿 덕분에 트랜잭션을 시작하고, 커밋하거나 롤백하는 코드가 모두 제거되었다.</u>

```java
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 템플릿
 * 트랜잭션 템플릿 덕분에 트랜잭션을 사용할 때 반복되는 코드를 제거할 수 있음(커밋, 롤백 등)
 */
@Slf4j
public class MemberServiceV3_2 {

    // TransactionTemplate을 사용하려면 transactionManager가 필요하다.
    // 생성자에서 transactionManager를 주입 받으면서 TransactionTemplate을 생성했다.
    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        this.txTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    /**
     * 트랜잭션 템플릿 덕분에 트랜잭션을 시작하고, 커밋하거나 롤백하는 코드가 모두 제거됨
     * 람다에서 체크 예외를 밖으로 던질 수 없기 때문에 언체크 예외로 바꾸어 예외를 던짐
     */
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        txTemplate.executeWithoutResult((status) -> {
            try {
                // 비즈니스 로직
                bizLogic(fromId, toId, money);
            } catch (SQLException e) {
                throw new IllegalStateException(e); // 언체크 예외로 바꾸어 던짐
            }
        });

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
```



[MemberServiceV3_2Test]

* 테스트 코드는 트랜잭션 매니저를 적용했을 때(V3_1)과 같다.

```java
package hello.jdbc.service;

import hello.jdbc.domain.Member;
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
 * 트랜잭션 - 트랜잭션 템플릿
 */
class MemberServiceV3_2Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_2 memberService;

    @BeforeEach
    void beforeEach() {
        // 커넥션을 생성
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        // 서비스 계층을 단순화하고, 커넥션을 추상화 및 동기화를 진행하기 위해 트랜잭션 매니저 사용(PlatformTransactionManager 객체를 사용)
        // 또한 트랜잭션 매니저는 데이터소스를 통해 커넥션을 생성하므로 DataSource 인자가 필요하다.
        // new DataSourceTransactionManager - 지금은 JDBC 기술을 사용하기 때문에 트랜잭션 매니저(인터페이스)의 JBBC 구현체인 객체를 사용함
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberRepository = new MemberRepositoryV3(dataSource);
        memberService = new MemberServiceV3_2(transactionManager, memberRepository);
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
```



### 정리

* 트랜잭션 템플릿 덕분에 트랜잭션을 사용할 때 반복하는 코드를 제거할 수 있다.
* 하지만 아직도 서비스 레이어인데 비즈니스 로직 뿐만 아니라 트랜잭션을 처리하는 기술 로직이 함께 포함되어 있다.
  * 이 문제는 다음 스프링이 제공하는 트랜잭션 AOP를 통해 해결한다.



## 트랜잭션 문제 해결 - 트랜잭션 AOP 이해

지금까지 트랜잭션을 편리하게 처리하기 위해 다음과 같은 내용을 진행했다.

* 트랜잭션 추상화도 도입하여 특정 기술에 의존하지 않음
* 반복적인 트랜잭션 로직을 해결하기 위해 트랜잭션 템플릿을 도입



**하지만 아직 서비스 계층에 순수한 비즈니스 로직만 남기는 것을 달성하지 못했는데, 이럴 때 스프링 AOP를 통해 프록시를 도입하면 문제를 해결할 수 있다.**



### 프록시를 통한 문제 해결

프록시를 도입하기 전에는 기존처럼 서비스 로직에서 트랜잭션을 직접 시작했다.

<img width="756" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/6b19e77f-b3e5-4e8f-ba73-8c4ff9b38b6b">



**프록시를 사용하면 아래와 같이 트랜잭션을 처리하는 객체와 비즈니스 로직을 처리하는 서비스 계층을 명확하게 분리할 수 있다.**

* 트랜잭션 프록시가 트랜잭션 처리 로직을 모두 가져간다.
* 또한 트랜잭션을 시작한 이후 실제 서비스를 대신 호출한다.
* 따라서 서비스 계층에는 순수한 비즈니스 로직만 남길 수 있다.

<img width="756" alt="image" src="https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/282c0ce0-b712-4ffd-9be9-9403a1ce316b">



### 스프링이 제공하는 트랜잭션 AOP

* 스프링은 트랜잭션 AOP를 처리하기 위한 모든 기능을 제공한다. 스프링부트를 사용하면 트랜잭션 AOP를 처리하기 위해 필요한 스프링 빈들도 자동으로 등록해준다.
* 개발자는 트랜잭션 처리가 필요한 곳에 `@Transactional` 애노테이션만 붙여주면 된다.
  * 스프링의 트랜잭션 AOP는 이 애노테이션을 인식해서 트랜잭션 프록시를 적용해준다.



> @Aspect, @Advice, @Pointcut 등의 스프링 프록시 개념은 고급편에서 다룬다고 한다.
> 지금은 `@Transactional` 애노테이션을 붙이면 스프링이 AOP를 사용해서 트랜잭션을 편리하게 처리해준다 정도로 이해하고 넘어가려고 한다.



## 트랜잭션 문제 해결 - 트랜잭션 AOP 적용

트랜잭션 AOP를 이용해서 서비스 계층에 비즈니스 로직 외 트랜잭션 코드들이 있는 문제를 해결해보자



### 예제

[MemberServiceV3_3] - `@Trasactional` 애노테이션 추가

* 순수한 비즈니스 로직만 남기고, 트랜잭션 관련 코드는 모두 제거됨
* 스프링이 제공하는 트랜잭션 AOP를 적용하기 위해 `@Trasactional` 애노테이션을 추가

```java
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * 트랜잭션 - @Transactional AOP
 * 해당 애노테이션을 통해 순수한 비즈니스 로직만 남기고, 트랜잭션 관련 코드는 모두 제거할 수 있음
 * - 스프링이 제공하는 트랜잭션 AOP를 적용하기 위해 @Transactional 애노테이션을 추가
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_3 {

    private final MemberRepositoryV3 memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        // 비즈니스 로직
        bizLogic(fromId, toId, money);
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
```





[MemberServiceV3_3Test]

* `@SpringBootTest`: 스프링 AOP를 적용하려면 스프링 컨테이너가 필요하다. 해당 애노테이션이 있으면 테스트 시 스프링부트를 통해 스프링 컨테이너를 생성한다.
* `@TestConfiguration`: 테스트 안에서 내부 설정 클래스를 만들어서 이 애노테이션을 붙이면, 스프링부트가 자동으로 만들어주는 빈들에 추가로 필요한 스프링 빈들을 등록하고 테스트를 수행할 수 있다. 
* 스프링이 제공하는 트랜잭션 AOP는 스프링 빈에 등록된 트랜잭션 매니저를 찾아 사용하기 때문에 트랜잭션 매니저를 스프링 빈으로 등록해두어야 한다.

```java
package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV0;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - @Transactional AOP
 */
@Slf4j
/**
 * @SpringBootTest
 * 스프링 AOP를 적용하려면 스프링 컨테이너가 필요하다.
 * 해당 애노테이션이 있으면 테스트 시 스프링 부트를 통해 스프링 컨테이너를 생성한다.
 */
@SpringBootTest
class MemberServiceV3_3Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepositoryV3 memberRepository;
    @Autowired
    private MemberServiceV3_3 memberService;

    @AfterEach
    void afterEach() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    /**
     * @TestConfiguration
     * 테스트 안에서 내부 설정 클래스를 만들어서 사용하면서 이 애노테이션을 붙이면,
     * 스프링 부트가 자동으로 만들어주는 빈들에 추가로 필요한 스프링 빈들을 등록하고 테스트할 수 있다.
     */
    @TestConfiguration
    static class TestConfig {
        @Bean
        DataSource dataSource() {
            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }

        @Bean
        PlatformTransactionManager transactionManager() {
            /**
             * new DataSourceTransactionManager
             * 트랜잭션 매니저를 스프링 빈으로 등록한다.
             * 스프링이 제공하는 트랜잭션 AOP는 스프링 빈에 등록된 트랜잭션 매니저를 찾아서 사용하기 때문에 트랜잭션 매니저를 스프링 빈으로 등록해두어야 한다.
             */
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        MemberRepositoryV3 memberRepositoryV3() {
            return new MemberRepositoryV3(dataSource());
        }

        @Bean
        MemberServiceV3_3 memberServiceV3_3() {
            return new MemberServiceV3_3(memberRepositoryV3());
        }

    }

    @Test
    void AopCheck() {
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberRepository class = {}", memberRepository.getClass());
        assertThat(AopUtils.isAopProxy(memberService)).isTrue();
        assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
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
```



#### AOP 프록시 적용 확인

```java
@Test
void AopCheck() {
    log.info("memberService class = {}", memberService.getClass());
    log.info("memberRepository class = {}", memberRepository.getClass());
    assertThat(AopUtils.isAopProxy(memberService)).isTrue();
    assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
}
```



실행 결과

* memberService는 프록시(CGLIB)이 적용된 것을 확인할 수 있다.
  * memberRepository는 `@Trasactional`(AOP) 적용하지 않았기 때문에 프록시가 적용되지 않는다.

```cmd
memberService class = class hello.jdbc.service.MemberServiceV3_3$$SpringCGLIB$$0
memberRepository class = class hello.jdbc.repository.MemberRepositoryV3
```



## 트랜잭션 문제 해결 - 트랜잭션 AOP 정리

트랜잭션 AOP가 사용된 전체흐름을 정리해보자



![image](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/14aa4205-0560-445f-b797-44a3f7b41b5f)

1. 클라이언트의 요청이 들어오게 되면 AOP 프록시에서 트랜잭션을 시작한다.
2. 이때 스프링 컨테이너에서 트랜잭션 매니저 빈을 찾아 트랜잭션 매니저를 획득한다.
3. 획득한 트랜잭션 매니저를 통해 트랜잭션을 시작하게 된다.
4. 데이터소스를 이용해 커넥션을 생성한다.
5. 트랜잭션을 시작하기 위해 수동 커밋 모드로 변경한다.
6. 동기화를 위해 커넥션을 쓰레드 로컬을 사용하는 트랜잭션 동기화 매니저에 내부적으로 보관하게 된다.
7. AOP 프록시에서 실제 서비스를 대신 호출해준다.
8. 서비스 로직에서 리포지토리를 호출하게 된다.
9. 리포지토리는 트랜잭션 동기화 매니저에서 필요한 커넥션을 획득하게 되고(`DataSourceUtils.getConnection`), 커넥션을 통해 데이터베이스에 SQL을 실행하게 된다.
10. 성공하면 커밋, 런타임 오류가 발생하게 되면 롤백을 수행하며 트랜잭션을 종료하게 된다.



### 선언적 트랜잭션 관리 vs 프로그래밍 방식 트랜잭션 관리

* **선언적 트랜잭션 관리(Declarative Transaction Management)**
  * `@Transactional` 애노테이션 하나만 선언해서 매우 펴닐하게 트랜잭션을 적용하는 것을 선언적 트랜잭션 관리라고 한다.
  * 실무에서는 거의 대부분이 이 방식을 사용한다고 한다.

* 프로그래밍 방식 트랜잭션 관리(Programmatic Transaction Management)
  * 트랜잭션 매니저 또는 트랜잭션 템플릿 등을 사용해서 트랜잭션 관련 코드를 직접 작성하는 것을 프로그래밍 방식의 트랜잭션 관리라고 한다.



### 정리

* 스프링이 제공하는 선언적 트랜잭션 관리 덕분에 트랜잭션 관련 코드를 순수한 비즈니스 로직에서 제거할 수 있었다.
* 개발자는 트랜잭션이 필요한 곳에 `@Transactional` 애노테이션 하나만 추가하면 나머지는 스프링 트랜잭션 AOP가 자동으로 처리해준다.



## 스프링 부트의 자동 리소스 등록

스프링에서는 데이터소스와 트랜잭션 매니저를 개발자가 직접 스프링 빈으로 등록해서 사용해야 한다.
이때까지 스프링 부트에선 직접 등록한 적이 없는데, **이는 스프링이 자동으로 데이터소스와 트랜잭션 매니저를 등록해주기 때문이다.**



#### 데이터소스 - 자동 등록

* 스프링부트는 데이터소스를 스프링 빈에 자동으로 등록한다.
* 자동으로 등록되는 빈 이름: `dataSource`



스프링 부트는 다음과 같이 application.properties에 있는 속성을 사용해서 `DataSource`를 생성하며, 빈에 등록한다.

[application.properties]

* 스프링 부트가 기본으로 생성하는 데이터소스는 커넥션풀을 제공하는 `HikariDataSource`이다.

```properties
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.username=sa
spring.datasource.password=
```



#### 트랜잭션 매니저 - 자동 등록

* 스프링 부트는 적절한 트랜잭션 매니저(`PlatformTransactionManager`)를 자동으로 스프링 빈에 등록한다.
* 자동으로 등록되는 빈 이름: `transactionManager`
* 어떤 트랜잭션 매니저를 선택할지는 현재 등록된 라이브러리를 보고 판단하게 된다.



#### 데이터소스, 트랜잭션 매니저 직접 등록

아래와 같이 직접 등록하면 스프링 부트는 데이터소스와 트랜잭션 매니저를 자동으로 등록하지 않는다.

```java
@TestConfiguration
static class TestConfig {
     @Bean
     DataSource dataSource() {
         return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
     }

  	@Bean
     PlatformTransactionManager transactionManager() {
				return new DataSourceTransactionManager(dataSource());   
  	 }

  	@Bean
		MemberRepositoryV3 memberRepositoryV3() {
    return new MemberRepositoryV3(dataSource());
		}

  	@Bean
    MemberServiceV3_3 memberServiceV3_3() {
        return new MemberServiceV3_3(memberRepositoryV3());
    }
```



### 예제 - 스프링 부트 자동 등록(데이터 소스, 트랜잭션 매니저)

[MemberServiceV3_4Test]

* 이전 테스트 코드와 달리 `@TestConfiguration` 설정 클래스에 데이터 소스와 트랜잭션 매니저 빈들을 등록하지 않음

```java
package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 트랜잭션 - DataSource, transactionManager 자동 등록
 * 데이터 소스와 트랜잭션 매니저가 없으면,
 * 스프링 부트가 자동으로 기본 빈을 등록해준다.
 * - dataSource(데이터 소스 - 커넥션 풀을 제공하는 HikariDataSource)
 * - PlatformTransactionManager(트랜잭션 매니저)
 */
@Slf4j
@SpringBootTest
class MemberServiceV3_4Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepositoryV3 memberRepository;
    @Autowired
    private MemberServiceV3_3 memberService;

    @AfterEach
    void afterEach() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_EX);
    }

    @TestConfiguration
    static class TestConfig {

        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepositoryV3 memberRepositoryV3() {
            return new MemberRepositoryV3(dataSource);
        }

        @Bean
        MemberServiceV3_3 memberServiceV3_3() {
            return new MemberServiceV3_3(memberRepositoryV3());
        }

    }

    @Test
    void AopCheck() {
        log.info("memberService class = {}", memberService.getClass());
        log.info("memberRepository class = {}", memberRepository.getClass());
        assertThat(AopUtils.isAopProxy(memberService)).isTrue();
        assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
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
```



# 섹션 5 | 자바 예외 이해

## 예외 계층

1. `Object`: 예외도 객체이다. 모든 객체의 최상위 부모는 `Object`이므로 예외의 최상위 부모도 `Object`이다.
2. `Throwable`: 최상위 예외이다. 하위에 `Exception`과 `Error`가 있다.
3. **`Error`: 메모리 부족이나 심각한 시스템 오류와 같이 애플리케이션에서 복구 불가능한 시스템 예외이다.**
   * 상위 예외를 `catch`로 잡으면 하위 예외까지 함께 잡으므로 Throwable로 잡으면 안된다.
4. `Exception`: 체크 예외
   * **애플리케이션 로직에서 사용할 수 있는 실질적인 최상위 예외**
   * `Exception`과 그 하위 예외는 모두 컴파일러가 체크하는 체크 예외이다. 단, `RuntimeException`은 예외로 한다.
5. `RuntimeException`: 언체크 예외, 런타임 예외
   * **컴파일러가 체크하지 않는 언체크 예외이다.**
   * `RuntimeException`과 그 자식 예외는 모두 언체크 예외이다.

![image](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/291df724-1246-4070-9b85-510697d4c904)



## 예외 기본 규칙

예외는 잡아서 처리하거나, 처리할 수 없으면 밖으로 던져야한다.



![image](https://github.com/nickhealthy/inflearn-Spring-DB1-1/assets/66216102/3793210c-e488-433c-8d7b-41b1d60086e2)



#### 예외에 대해서는 2가지 기본 규칙을 기억하자

1. 예외는 잡아서 처리하거나 던져야 한다.
2. 예외를 잡거나 던질 때 지정한 예외뿐만 아니라 그 예외의 자식들도 함께 처리된다.
   * 예를 들어 `Exception을` `catch로` 잡으면 그 하위 예외들도 모두 잡을 수 있다.
   * 예를 들어 `Exception을` `throws로` 던지면 그 하위 예외들도 모두 던질 수 있다.



#### 예외를 처리하지 못하고 계속 던지면 어떻게 되는가?

* 자바 `main()` 쓰레드의 경우 예외 로그를 출력하면서 시스템이 종료된다. 
* 웹 애플리케이션의 경우 여러 사용자의 요청을 처리하기 때문에 하나의 예외 때문에 시스템이 종료되면 안된다.
  * WAS가 해당 예외를 받아서 처리하는데, 주로 사용자에게 개발자가 지정한, 오류 페이지를 보여준다.



## 체크 예외 기본 이해

**체크 예외는 무조건 잡아서 처리하거나, 또는 밖으로 던지도록 선언해야 한다.** 그렇지 않으면 컴파일 오류가 발생한다.



#### 예제

[CheckedTest]

```java
package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();
        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }


    /**
     * Exception을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하거나, 던지거나 둘 중 하나를 필수로 선택해야 한다.
     */
    class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                // 예외 처리 로직
                log.info("예외 처리, message = {}", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 필수로 선언해야 한다.
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }

}
```



#### 예제 실행 결과

* `checked_catch()`는 예외를 잡았기 때문에 테스트 메서드까지 예외가 올라오지 않는다.
* `checked_throw()`는 예외를 처리하지 않고 밖으로 던지기 때문에 예외가 테스트 메서드까지 올라온다.



#### 체크 예외 vs 언체크 예외

* Exception을 상속받으면 체크 예외가 된다
* RuntimeException을 상속받으면 언체크 예외가 된다.
* `catch, throws` 모두 예외를 설정한 해당 타입과 그 하위 타입을 모두 잡거나 던질 수 있다.



#### 체크 예외의 장단점

체크 예외는 예외를 잡아서 처리할 수 없을 때, 예외를 밖으로 던지는 `throws`를 필수로 선언해야 한다.
그렇지 않으면 컴파일 오류가 발생한다.

* 장점: 개발자가 실수로 예외를 누락하지 않도록 컴파일러를 통해 문제를 잡아줄 수 있다.
* 단점: 개발자가 모든 체크 예외를 반드시 잡거나 던지도록 처리해야 하기 때문에, 너무 번거로운 일이 된다.
  * 추가로 의존 관계에 따른 단점도 있다.
