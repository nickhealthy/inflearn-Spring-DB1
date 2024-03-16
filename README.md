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



