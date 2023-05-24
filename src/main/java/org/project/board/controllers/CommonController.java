package org.project.board.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.board.commons.CommonException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("org.project.board.controllers") // 적용범위
public class CommonController {

    @ExceptionHandler(Exception.class) // 응답코드 제외 다른 예외들 유입
    public String errorHandler(Exception e, Model model, HttpServletRequest request, HttpServletResponse response) {
        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR; // 기본

        if (e instanceof CommonException) { // CommonException에 정의한 예외면 별도의 응답코드 가져올 수 있게 설정
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus().value();
        }

        response.setStatus(status);
        String URL = request.getRequestURI();

        model.addAttribute("status", status);
        model.addAttribute("path", URL);
        model.addAttribute("message", e.getMessage());
        model.addAttribute("exception", e);

        e.printStackTrace();

        return "error/common";
    }
}
