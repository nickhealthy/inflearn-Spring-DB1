package hello.jdbc.repository.ex;

/**
 * RuntimeException을 상속받아기 때문에 MyDbException은 런타임 예외가 된다.
 */
public class MyDbException extends RuntimeException {

    public MyDbException() {
    }

    public MyDbException(String message) {
        super(message);
    }

    public MyDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDbException(Throwable cause) {
        super(cause);
    }
}
