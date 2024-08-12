package com.youyk.anchoreerchat.common.error.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum DomainExceptionCode {
    STUDENT_EXIST("ALREADY_EXIST_STUDENT", "이미 존재하는 학생입니다. [%s]", HttpStatus.BAD_REQUEST),
    SUBJECT("ALREADY_EXIST_SUBJECT", "이미 존재하는 과목입니다. [%s]", HttpStatus.BAD_REQUEST),
    STUDENT_NOT_FOUND("STUDENT_NOT_FOUND", "학생을 찾을 수 없습니다. [%s]", HttpStatus.NOT_FOUND),
    SUBJECT_NOT_FOUND("SUBJECT_NOT_FOUND", "과목을 찾을 수 없습니다. [%s]", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    public DomainException newInstance(final Object arg){
        return new DomainException(code, String.format(message, arg), httpStatus);
    }

}
